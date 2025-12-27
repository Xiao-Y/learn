package com.billow.file.api;

import cn.hutool.core.io.unit.DataSize;
import com.billow.file.config.MinioConfig;
import com.billow.file.config.MinioKit;
import com.billow.file.pojo.build.MinioBuildParam;
import com.billow.file.pojo.po.MinioObject;
import com.billow.file.pojo.search.MinioObjectSearchParam;
import com.billow.file.pojo.vo.MinioVo;
import com.billow.file.service.MinioObjectService;
import com.billow.mybatis.base.HighLevelApi;
import io.minio.*;
import io.minio.messages.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * MinIO 文件存储 前端控制器
 * 整合了文件对象管理和存储桶操作功能
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Tag(name = "MinioObjectApi", description = "MinIO 文件存储接口")
@RestController
@RequestMapping("/minioObjectApi")
public class MinioObjectApi extends HighLevelApi<MinioObjectService, MinioObject, MinioObjectSearchParam> {
    @Autowired
    private MinioClient minioClient;    // MinIO 客户端，主要用来调用 API

    @Autowired
    private MinioKit minioKit;

    @Autowired
    private MinioConfig minioConfig;

    @Value("${spring.servlet.multipart.max-file-size:30MB}")
    private String maxSize;             // yml 配置文件获取
    @Value("${minio.bucketName}")
    private String minIOBucket;         // yml 配置文件获取

    // ================================ 存储桶操作 ================================

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

    @Operation(summary = "获取 Object 状态信息")
    @PostMapping("/statObject")
    public MinioVo statObject(@RequestBody MinioBuildParam minioObjectDto) throws Exception {
        return minioKit.statObject(minioConfig.getBucketName(), minioObjectDto.getFileName());
    }

    @Operation(summary = "获取访问 Object 的预签名 URL")
    @PostMapping("/getPresignedObjectUrl")
    public String getPresignedObjectUrl(@RequestBody MinioBuildParam minioObjectDto) throws Exception {
        return minioKit.getFileUrl(minioConfig.getBucketName(), minioObjectDto.getFileName());
    }

    // ================================ 文件上传下载操作 ================================


    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "文件上传")
    @PostMapping("/upload/{userId}")
    public MinioObject upload(@RequestParam MultipartFile file,
                              @PathVariable(value = "userId") String userId,
                              @RequestParam(value = "generateThumbnail", defaultValue = "true") Boolean generateThumbnail) throws Exception {
        if (file.getSize() > DataSize.parse(maxSize).toBytes()) {
            throw new RuntimeException("上传失败，上传文件过大");
        }

        return this.getService().uploadFile(file, userId, generateThumbnail);
    }

    @Operation(summary = "wang-editor 富文本编辑器文件上传")
    @PostMapping("/wangEditorUpload/{userId}")
    public MinioObject wangEditorUpload(@RequestParam MultipartFile file,
                                        @PathVariable(value = "userId") String userId) throws Exception {
        if (file.getSize() > DataSize.parse(maxSize).toBytes()) {
            throw new RuntimeException("上传失败，上传文件过大");
        }

        return this.getService().wangEditorUpload(file, userId);
    }

    /**
     * @param id       : MinioObject ID
     * @param response
     * @throws Exception
     */
    @Operation(summary = "根据 ID 文件下载")
    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws Exception {
        MinioObject one = this.getService().queryChain()
                .eq(MinioObject::getId, id)
                .one();

        if (one == null) {
            throw new RuntimeException("文件不存在");
        }

        String bucket = one.getBucket();
        String object = one.getObject();
        String name = one.getName();

        // 设置响应头
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        response.addHeader("Content-Disposition", "attachment;filename=" + encodedName);
        response.setContentType("application/octet-stream");

        // 从 MinIO 获取文件流并传输
        try (GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build())) {

            getObjectResponse.transferTo(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("下载文件失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件下载失败: " + e.getMessage());
        }
    }

    @Operation(summary = "根据 URL 文件下载")
    @PostMapping("/downloadByUrl")
    public void downloadByUrl(@RequestBody MinioBuildParam stringDTO, HttpServletResponse response) throws Exception {
        // 解密 url
        String decodedUrl = URLDecoder.decode(stringDTO.getUrl(), StandardCharsets.UTF_8);

        MinioObject one = this.getService().queryChain()
                .eq(MinioObject::getUrl, decodedUrl)
                .one();

        if (one == null) {
            throw new RuntimeException("文件不存在");
        }

        String bucket = one.getBucket();
        String object = one.getObject();
        String name = one.getName();

        // 设置响应头
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        response.addHeader("Content-Disposition", "attachment;filename=" + encodedName);
        response.setContentType("application/octet-stream");

        // 从 MinIO 获取文件流并传输
        try (GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build())) {

            getObjectResponse.transferTo(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("下载文件失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件下载失败: " + e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "删除")
    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable("id") Long id) throws Exception {
        MinioObject object = this.getService().getById(id);

        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(minIOBucket)            // 存储桶名
                .object(object.getObject())     // 对象名
                .build());

        this.getService().removeById(id);
    }
}