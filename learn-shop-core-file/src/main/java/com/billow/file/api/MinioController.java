package com.billow.file.api;

import com.billow.file.config.MinioConfig;
import com.billow.file.config.MinioKit;
import com.billow.file.pojo.build.MinioBuildParam;
import com.billow.file.pojo.vo.MinioVo;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Minio接口")
@RestController
@RequestMapping("/minio")
public class MinioController {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioKit minioKit;
    @Autowired
    private MinioConfig minioConfig;

    // 1. 操作 Bucket 存储桶 -----------------------------------------------------------------

    @Operation(summary = "创建 Bucket 存储桶")
    @PostMapping("/makeBucket")
    public void makeBucket() throws Exception {
        minioKit.makeBucket(minioConfig.getBucketName());
    }

    @Operation(summary = "获取 Bucket 存储桶列表")
    @GetMapping("/listBuckets")
    public List<String> listBuckets() throws Exception {
        return minioKit.listBuckets();
    }

    @Operation(summary = "删除 Bucket 存储桶")
    @GetMapping("/removeBuckets")
    public void removeBuckets() throws Exception {
        minioKit.removeBuckets(minioConfig.getBucketName());
    }


    // 2. 操作 Object 对象 -----------------------------------------------------------------

    @Operation(summary = "上传 Object")
    @PostMapping("/uploadObject")
    public MinioVo uploadObject(MultipartFile file) throws Exception {
        return minioKit.uploadFile(minioConfig.getBucketName(), file);
    }

    @Operation(summary = "获取 Object 状态信息")
    @PostMapping("/statObject")
    public MinioVo statObject(@RequestBody MinioBuildParam minioObjectDto) throws Exception {
        return minioKit.statObject(minioConfig.getBucketName(), minioObjectDto.getFileName());
    }

    /**
     * @param minioObjectDto
     * @return
     * @throws Exception
     */
    @Operation(summary = "获取访问 Object 的预签名 URL")
    @PostMapping("/getPresignedObjectUrl")
    public String getPresignedObjectUrl(@RequestBody MinioBuildParam minioObjectDto) throws Exception {
        return minioKit.getFileUrl(minioConfig.getBucketName(), minioObjectDto.getFileName());
    }

    @Operation(summary = "获取所有 Object")
    @PostMapping("/listObjects")
    public List<String> listObjects() throws Exception {
        // 1. 获取存储桶中的对象列表
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(minioConfig.getBucketName())    // 存储桶名
                .build());

        // 2. 提取对象名到 List<String>
        List<String> list = new ArrayList<>();
        for (Result<Item> item : results) {
            list.add(item.get().objectName()); // 获取每个对象的名称
        }
        return list;
    }

    @Operation(summary = "删除 Object")
    @PostMapping("/removeObject")
    public void removeObject(@RequestBody MinioBuildParam minioObjectDto) throws Exception {
        minioKit.deleteFile(minioConfig.getBucketName(), minioObjectDto.getFileName());
    }

}