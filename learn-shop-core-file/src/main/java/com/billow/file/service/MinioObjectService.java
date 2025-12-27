package com.billow.file.service;

import com.billow.file.pojo.po.MinioObject;
import com.billow.file.pojo.search.MinioObjectSearchParam;
import com.billow.mybatis.base.HighLevelService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * MinIO Object的Service接口
 * 定义业务逻辑方法
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
public interface MinioObjectService extends HighLevelService<MinioObject, MinioObjectSearchParam> {

    /**
     * 上传文件
     * @param file 文件
     * @param userId 用户ID
     * @param generateThumbnail 是否生成缩略图
     * @return MinioObject
     * @throws Exception 异常
     */
    MinioObject uploadFile(MultipartFile file, String userId, Boolean generateThumbnail) throws Exception;

    /**
     * wang-editor 富文本编辑器文件上传
     * @param file 文件
     * @param userId 用户ID
     * @return MinioObject
     * @throws Exception 异常
     */
    MinioObject wangEditorUpload(MultipartFile file, String userId) throws Exception;
}
