package com.billow.file.pojo.vo;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author 千面
 * @date 2025-09-16 21:46:08
 */
@Data
public class MinioVo {

    /**
     * 存储桶名称
     */
    private String bucketName;
    /**
     * 文件的 etag
     */
    public String etag;

    /**
     * 文件名
     */
    public String fileName;

    /**
     * 文件大小
     */
    private long fileSize;

    /**
     * 最后修改时间
     */
    private ZonedDateTime lastModified;

    /**
     * 文件类型
     */
    private String contentType;
}
