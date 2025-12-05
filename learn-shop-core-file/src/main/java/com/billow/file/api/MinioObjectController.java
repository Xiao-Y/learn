package com.billow.file.api;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.unit.DataSize;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.mybatisflex.core.paginate.Page;
import com.billow.file.pojo.build.MinioBuildParam;
import com.billow.file.pojo.po.MinioObject;
import com.billow.file.service.MinioObjectService;
import io.minio.*;
import io.minio.http.Method;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <p>
 * MinIO Object表 前端控制器
 * </p>
 *
 * @author dragon
 * @since 2025-05-13
 */
@Tag(name = "Minio Object 接口")
@RestController
@RequestMapping("/minioObject")
public class MinioObjectController {
    @Autowired
    private MinioObjectService minioObjectService;
    @Autowired
    private MinioClient minioClient;    // MinIO 客户端，主要用来调用 API

    @Value("${spring.servlet.multipart.max-file-size:30}")
    private String maxSize;             // yml 配置文件获取
    @Value("${minio.bucketName}")
    private String minIOBucket;         // yml 配置文件获取


    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "文件上传")
    @PostMapping("/upload/{userId}")
    public MinioObject upload(@RequestParam MultipartFile file, @PathVariable(value = "userId") String userId) throws Exception {
        if (file.getSize() > DataSize.parse(maxSize).toBytes()) {
            throw new RuntimeException("上传失败，上传文件过大");
        }

        // 文件大小（字节 B）
        long size = file.getSize();
        // 原文件名（bg.jpg）
        String originalFilename = file.getOriginalFilename();
        // 原文件类型（jpg）
        String type = FileUtil.extName(originalFilename);
        // 保证对象名唯一性（文件名由 uuid 拼接生成）
        String object = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        // 生成文件唯一标识 md5，保证不会存储重复的文件
        String md5;
        try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream())) {
            // 使用流计算 md5
            md5 = SecureUtil.md5(bis);
        }

        // 查找是否存在 md5 值，如果存在直接返回该对象
        MinioObject one = minioObjectService.queryChain()
                .eq(MinioObject::getMd5, md5)
                .one();
        if (Objects.nonNull(one)) {
            return one;
        }

        // 上传到 MinIO 服务器，object 对象名同名会被覆盖
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minIOBucket)                 // 存储桶名
                .object(object)                      // 对象名
                .stream(file.getInputStream(), size, -1)   // 文件输入流，文件大小（字节），-1表示默认的分片大小（通常为5MB）
                .build());

        // 获取 MinIO 中 Object Url
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(minIOBucket)    // 存储桶名
                .object(object)         // 对象名
                .method(Method.GET)     // URL 请求方法
                .build());

        // 保存到数据库
        MinioObject minioObject = new MinioObject();
        minioObject.setUserId(userId);
        minioObject.setBucket(minIOBucket);
        minioObject.setObject(object);
        minioObject.setName(originalFilename);
        minioObject.setUrl(presignedObjectUrl);
        minioObject.setType(type);
        minioObject.setSize(size);
        minioObject.setMd5(md5);
        minioObjectService.saveOrUpdate(minioObject);

        return minioObject;
    }

    @Operation(summary = "wang-editor 富文本编辑器文件上传")
    @PostMapping("/wangEditorUpload/{userId}")
    public MinioObject wangEditorUpload(@RequestParam MultipartFile file, @PathVariable(value = "userId") String userId) throws Exception {
        if (file.getSize() > DataSize.parse(maxSize).toBytes()) {
            throw new RuntimeException("上传失败，上传文件过大");
        }

        // 文件大小（字节）
        long size = file.getSize();
        // 原文件名
        String originalFilename = file.getOriginalFilename();
        // 原文件类型
        String type = FileUtil.extName(originalFilename);
        // 保证对象名唯一性（文件名由 uuid 拼接生成）
        String object = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        // 生成文件唯一标识 md5，保证不会在磁盘存储重复的文件
        String md5;
        try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream())) {
            // 使用流计算 md5
            md5 = SecureUtil.md5(bis);
        }

        // 存在 md5 值，直接返回该对象
        MinioObject one = minioObjectService.queryChain()
                .eq(MinioObject::getMd5, md5)
                .one();
        if (Objects.nonNull(one)) {
            return one;
        }

        // 上传到 MinIO 服务器，object 对象名同名会被覆盖
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minIOBucket)                 // 存储桶名
                .object(object)                      // 对象名
                .stream(file.getInputStream(), size, -1)   // 文件输入流，文件大小（字节），-1表示默认的分片大小（通常为5MB）
                .build());

        // 获取 Object Url
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(minIOBucket)    // 存储桶名
                .object(object)         // 对象名
                .method(Method.GET)     // URL 请求方法
                .build());

        // 保存到数据库
        MinioObject minioObject = new MinioObject();
        minioObject.setUserId(userId);
        minioObject.setBucket(minIOBucket);
        minioObject.setObject(object);
        minioObject.setName(originalFilename);
        minioObject.setUrl(presignedObjectUrl);
        minioObject.setType(type);
        minioObject.setSize(size);
        minioObject.setMd5(md5);
        minioObjectService.saveOrUpdate(minioObject);

        return minioObject;
    }

    /**
     * @param id       : MinioObject ID
     * @param response
     * @throws Exception
     */
    @Operation(summary = "根据 ID 文件下载")
    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws Exception {
        MinioObject one = minioObjectService.queryChain()
                .eq(MinioObject::getId, id)
                .one();

        if (one == null) {
            response.setStatus(404);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\": 404, \"msg\": \"文件不存在\"}");
            return;
        }

        String bucket = one.getBucket();
        String object = one.getObject();
        String name = one.getName();

        // 设置输出流格式
        // 添加 CORS 暴露请求头（必须！）
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        // 设置下载文件名
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        response.addHeader("Content-Disposition", "attachment;filename=" + encodedName);
        response.setContentType("application/octet-stream");

        try {
            // 从MinIO获取文件流
            GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .build());

            // 流式传输到HTTP响应
            getObjectResponse.transferTo(response.getOutputStream());
        } catch (Exception e) {
            response.setStatus(404);
        }
        response.getOutputStream().flush();
    }

    @Operation(summary = "根据 URL 文件下载")
    @PostMapping("/downloadByUrl")
    public void downloadByUrl(@RequestBody MinioBuildParam stringDTO, HttpServletResponse response) throws Exception {
        // 解密 url
        String decodedUrl = URLDecoder.decode(stringDTO.getUrl(), StandardCharsets.UTF_8);

        MinioObject one = minioObjectService.queryChain()
                .eq(MinioObject::getUrl, decodedUrl)
                .one();

        if (one == null) {
            response.setStatus(404);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\": 404, \"msg\": \"文件不存在\"}");
            return;
        }

        String bucket = one.getBucket();
        String object = one.getObject();
        String name = one.getName();

        // 设置输出流格式
        // 添加 CORS 暴露请求头（必须！）
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        // 设置下载文件名
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        response.addHeader("Content-Disposition", "attachment;filename=" + encodedName);
        response.setContentType("application/octet-stream");

        try {
            // 从MinIO获取文件流
            GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .build());

            // 流式传输到HTTP响应
            getObjectResponse.transferTo(response.getOutputStream());
        } catch (Exception e) {
            response.setStatus(404);
        }
        response.getOutputStream().flush();
    }

//    @Operation(summary = "分页列表")
//    @PostMapping("/list")
//    public SaResult list(@RequestBody MinioObjectPageDto pageDto) {
//        // 创建分页对象
//        Page<MinioObjectDto> page = new Page<>(pageDto.getCurrentPage(), pageDto.getPageSize());
//
//        // 构造询条件
//        MPJLambdaWrapper<MinioObject> qw = new MPJLambdaWrapper<MinioObject>()
//                .selectAll(MinioObject.class)
//                .selectAs(User::getName, MinioObjectDto::getUserName)
//                .leftJoin(User.class, User::getId, MinioObject::getUserId)
//                .like(StringUtils.isNotBlank(pageDto.getName()), MinioObject::getName, pageDto.getName())
//                .like(StringUtils.isNotBlank(pageDto.getBucket()), MinioObject::getBucket, pageDto.getBucket())
//                .like(StringUtils.isNotBlank(pageDto.getType()), MinioObject::getType, pageDto.getType())
//                .orderByDesc(MinioObject::getCreateTime);
//
//        // 根据查询条件，将结果封装到分页对象
//        Page<MinioObjectDto> response = minioObjectService.selectJoinListPage(page, MinioObjectDto.class, qw);
//
//        return SaResult.ok().setData(response);
//    }

    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "删除")
    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable("id") Long id) throws Exception {
        MinioObject object = minioObjectService.getById(id);

        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(minIOBucket)            // 存储桶名
                .object(object.getObject())     // 对象名
                .build());

        minioObjectService.removeById(id);
    }

}