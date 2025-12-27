package com.billow.file.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.billow.file.config.VideoProcessKit;
import com.billow.file.dao.MinioObjectMapper;
import com.billow.file.pojo.po.MinioObject;
import com.billow.file.pojo.search.MinioObjectSearchParam;
import com.billow.file.service.MinioObjectService;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.tools.generator.NumUtil;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * MinIO Object的Service实现类
 * 实现具体的业务逻辑
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Service
public class MinioObjectServiceImpl extends HighLevelServiceImpl<MinioObjectMapper, MinioObject, MinioObjectSearchParam> implements MinioObjectService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private VideoProcessKit videoProcessKit;

    @Value("${minio.bucketName}")
    private String minIOBucket;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MinioObject uploadFile(MultipartFile file, String userId, Boolean generateThumbnail) throws Exception {
        // 文件大小（字节 B）
        long size = file.getSize();
        // 原文件名（bg.jpg）
        String originalFilename = file.getOriginalFilename();
        // 原文件类型（jpg）
        String type = FileUtil.extName(originalFilename);

        String tempFilePath = null;
        try {
            // 1. 先将文件保存到临时目录（因为流只能读取一次）
            tempFilePath = saveFileToTempDirectory(file);

            // 2. 从临时文件计算 MD5
            String md5;
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(tempFilePath))) {
                md5 = SecureUtil.md5(bis);
            }

            // 保证对象名唯一性（文件名由 uuid 拼接生成）
            String object = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

            // 3. 查找是否存在 md5 值，如果存在直接返回该对象
            MinioObject one = this.queryChain()
                    .eq(MinioObject::getMd5, md5)
                    .eq(MinioObject::getBucket, minIOBucket)
                    .one();
            if (Objects.isNull(one)) {
                // 4. 从临时文件上传到 MinIO 服务器
                try (FileInputStream fis = new FileInputStream(tempFilePath)) {
                    minioClient.putObject(PutObjectArgs.builder()
                            .bucket(minIOBucket)                 // 存储桶名
                            .object(object)                      // 对象名
                            .stream(fis, size, -1)              // 文件输入流，文件大小（字节），-1表示默认的分片大小（通常为5MB）
                            .build());
                }
            } else {
                object = one.getObject();
            }


            // 5. 获取 MinIO 中 Object Url
            String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(minIOBucket)    // 存储桶名
                    .expiry(2, TimeUnit.DAYS)// 设置过期时间
                    .object(object)         // 对象名
                    .method(Method.GET)     // URL 请求方法
                    .build());

            // 6. 从 presignedObjectUrl 中提取过期时间
            Date expireTime = extractExpireTimeFromUrl(presignedObjectUrl);

            // 7. 保存到数据库
            MinioObject minioObject = new MinioObject();
            minioObject.setFileNo(NumUtil.makeNum("WJ"));  // 生成文件编号
            minioObject.setUserId(userId);
            minioObject.setBucket(minIOBucket);
            minioObject.setObject(object);
            minioObject.setName(originalFilename);
            minioObject.setUrl(presignedObjectUrl);
            minioObject.setType(type);
            minioObject.setSize(size);
            minioObject.setMd5(md5);
            minioObject.setExpireTime(expireTime);

            // 8. 如果是视频文件且需要生成缩略图
            if (generateThumbnail && videoProcessKit.isVideoFile(type)) {
                try {
                    // 上传缩略图到 MinIO
                    String thumbnailFileName = "thumbnails/" + IdUtil.fastSimpleUUID() + ".jpg";
                    // 生成缩略图字节数组
                    byte[] thumbnailBytes = videoProcessKit.generateVideoThumbnail(tempFilePath, thumbnailFileName, 3.0);
                    if (thumbnailBytes != null && thumbnailBytes.length > 0) {
                        minioClient.putObject(PutObjectArgs.builder()
                                .bucket(minIOBucket)
                                .object(thumbnailFileName)
                                .stream(new ByteArrayInputStream(thumbnailBytes), thumbnailBytes.length, -1)
                                .contentType("image/jpeg")
                                .build());

                        // 获取缩略图 URL
                        String thumbnailUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                                .bucket(minIOBucket)
                                .object(thumbnailFileName)
                                .method(Method.GET)
                                .build());

                        // 设置缩略图 URL 到视频对象
                        minioObject.setThumbnailUrl(thumbnailUrl);

                        log.info("为视频文件生成缩略图成功: {}", thumbnailUrl);
                    }
                } catch (Exception e) {
                    log.error("生成视频缩略图失败: {}", e.getMessage(), e);
                    // 不影响主流程，继续返回文件对象
                }
            }

            // 9. 保存到数据库
            this.saveOrUpdate(minioObject);

            return minioObject;

        } finally {
            // 10. 清理临时文件
            cleanupTempFile(tempFilePath);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MinioObject wangEditorUpload(MultipartFile file, String userId) throws Exception {
        // 文件大小（字节）
        long size = file.getSize();
        // 原文件名
        String originalFilename = file.getOriginalFilename();
        // 原文件类型
        String type = FileUtil.extName(originalFilename);
        // 保证对象名唯一性（文件名由 uuid 拼接生成）
        String object = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        String tempFilePath = null;
        try {
            // 1. 先将文件保存到临时目录（因为流只能读取一次）
            tempFilePath = saveFileToTempDirectory(file);

            // 2. 从临时文件计算 MD5
            String md5;
            try (BufferedInputStream bis = new BufferedInputStream(new java.io.FileInputStream(tempFilePath))) {
                md5 = SecureUtil.md5(bis);
            }

            // 3. 存在 md5 值，直接返回该对象
            MinioObject one = this.queryChain()
                    .eq(MinioObject::getMd5, md5)
                    .one();
            if (Objects.nonNull(one)) {
                return one;
            }

            // 4. 从临时文件上传到 MinIO 服务器
            try (java.io.FileInputStream fis = new java.io.FileInputStream(tempFilePath)) {
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(minIOBucket)                 // 存储桶名
                        .object(object)                      // 对象名
                        .stream(fis, size, -1)              // 文件输入流，文件大小（字节），-1表示默认的分片大小（通常为5MB）
                        .build());
            }

            // 5. 获取 Object Url
            String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(minIOBucket)    // 存储桶名
                    .object(object)         // 对象名
                    .method(Method.GET)     // URL 请求方法
                    .build());

            // 6. 从 presignedObjectUrl 中提取过期时间
            Date expireTime = extractExpireTimeFromUrl(presignedObjectUrl);

            // 7. 保存到数据库
            MinioObject minioObject = new MinioObject();
            minioObject.setFileNo(NumUtil.makeNum("WJ"));  // 生成文件编号
            minioObject.setUserId(userId);
            minioObject.setBucket(minIOBucket);
            minioObject.setObject(object);
            minioObject.setName(originalFilename);
            minioObject.setUrl(presignedObjectUrl);
            minioObject.setType(type);
            minioObject.setSize(size);
            minioObject.setMd5(md5);
            minioObject.setExpireTime(expireTime);

            this.saveOrUpdate(minioObject);

            return minioObject;

        } finally {
            // 8. 清理临时文件
            cleanupTempFile(tempFilePath);
        }
    }

    /**
     * 从 presignedObjectUrl 中提取过期时间
     *
     * @param presignedObjectUrl 预签名URL
     * @return 过期时间
     */
    private Date extractExpireTimeFromUrl(String presignedObjectUrl) {
        try {
            URL url = new URL(presignedObjectUrl);
            String query = url.getQuery();
            if (StringUtils.isEmpty(query)) {
                return null;
            }
            Map<String, String> map = StrUtil.split(query, "&")
                    .stream()
                    .map(param -> param.split("="))
                    .collect(Collectors.toMap(param -> param[0], param -> param[1]));
            // 获取过期时间（秒）
            Integer expires = Integer.parseInt(map.get("X-Amz-Expires")) + (8 * 3600);
            // 20251227T125830Z
            String dateStr = map.get("X-Amz-Date");
            Date expiresDateTime = DateUtil.offsetSecond(DateUtil.parse(dateStr, "yyyyMMdd'T'HHmmss'Z'"), expires);
            return expiresDateTime;
        } catch (Exception e) {
            log.warn("解析URL过期时间失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 将文件保存到临时目录
     *
     * @param file 上传的文件
     * @return 临时文件路径
     * @throws Exception 保存异常
     */
    private String saveFileToTempDirectory(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String extension = FileUtil.extName(originalFilename);
        String tempFileName = "temp_file_" + IdUtil.fastSimpleUUID() + "." + extension;

        // 获取系统临时目录
        String tempDir = System.getProperty("java.io.tmpdir");
        java.nio.file.Path tempFilePath = java.nio.file.Paths.get(tempDir, tempFileName);

        // 确保临时目录存在
        java.nio.file.Files.createDirectories(tempFilePath.getParent());

        // 保存文件
        file.transferTo(tempFilePath.toFile());

        log.debug("文件已保存到临时目录: {}", tempFilePath.toString());
        return tempFilePath.toString();
    }

    /**
     * 清理临时文件
     *
     * @param tempFilePath 临时文件路径
     */
    private void cleanupTempFile(String tempFilePath) {
        if (StrUtil.isNotBlank(tempFilePath)) {
            try {
                java.io.File tempFile = new java.io.File(tempFilePath);
                if (tempFile.exists()) {
                    boolean deleted = tempFile.delete();
                    if (deleted) {
                        log.debug("清理临时文件成功: {}", tempFilePath);
                    } else {
                        log.warn("清理临时文件失败: {}", tempFilePath);
                    }
                }
            } catch (Exception e) {
                log.warn("清理临时文件异常: {}", tempFilePath, e);
            }
        }
    }
}
