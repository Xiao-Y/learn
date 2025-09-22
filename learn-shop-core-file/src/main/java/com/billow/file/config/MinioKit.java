package com.billow.file.config;

import com.billow.file.pojo.vo.MinioVo;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 千面
 * @date 2025-09-16 21:37:49
 */
@Service
@RequiredArgsConstructor
public class MinioKit {

    private final MinioClient minioClient;

    /**
     * 创建 Bucket 存储桶
     *
     * @param bucketName
     * @throws Exception
     * @author 千面
     * @date 2025-09-16 21:42:20
     */
    public void makeBucket(String bucketName) throws Exception {
        // 判断是否存在 Bucket 存储桶
        boolean b = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)     // 存储桶名
                .build());

        if (!b) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)     // 存储桶名
                    .build());
        }
    }

    /**
     * 获取 Bucket 存储桶列表
     *
     * @return
     * @throws Exception
     * @author 千面
     * @date 2025-09-16 21:43:55
     */
    public List<String> listBuckets() throws Exception {
        List<Bucket> buckets = minioClient.listBuckets();
        return buckets.stream().map(Bucket::name).toList();
    }

    /**
     * 删除 Bucket 存储桶
     *
     * @param bucketName
     * @throws Exception
     * @author 千面
     * @date 2025-09-16 21:44:28
     */
    public void removeBuckets(String bucketName) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder()
                .bucket(bucketName)     // 存储桶名
                .build());
    }

    /**
     * 获取 文件 状态信息
     *
     * @param bucketName
     * @param fileName
     * @return
     * @throws Exception
     * @author 千面
     * @date 2025-09-16 21:51:46
     */
    public MinioVo statObject(String bucketName, String fileName) throws Exception {
        StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder()
                .bucket(bucketName)    // 存储桶名
                .object(fileName)    // 对象名
                .build());

        MinioVo minioVo = new MinioVo();
        minioVo.setBucketName(statObjectResponse.bucket());
        minioVo.setEtag(statObjectResponse.etag());
        minioVo.setFileName(statObjectResponse.object());
        minioVo.setFileSize(statObjectResponse.size());
        minioVo.setLastModified(statObjectResponse.lastModified());
        minioVo.setContentType(statObjectResponse.contentType());
        return minioVo;
    }

    /**
     * 上传文件
     */
    public MinioVo uploadFile(String bucketName, MultipartFile file) throws Exception {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        MinioVo minioVo = new MinioVo();
        minioVo.setBucketName(objectWriteResponse.bucket());
        minioVo.setEtag(objectWriteResponse.etag());
        minioVo.setFileName(objectWriteResponse.object());
        return minioVo;
    }

    /**
     * 下载文件
     */
    public InputStream downloadFile(String bucketName, String fileName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
    }

    /**
     * 删除文件
     */
    public void deleteFile(String bucketName, String fileName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
    }

    /**
     * 获取文件URL
     */
    public String getFileUrl(String bucketName,String fileName) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileName)
//                        .expiry(7, TimeUnit.DAYS) // 7天有效期
                        .build()
        );
    }
}
