package com.billow.file.pojo.po;

import com.billow.mybatis.pojo.BasePo;
import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * MinIO Object实体类
 * 对应数据库表minio_object
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_minio_object")
public class MinioObject extends BasePo {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 文件编号
     */
    private String fileNo;

    /**
     * Bucket 桶名
     */
    private String bucket;

    /**
     * Object 对象名
     */
    private String object;

    /**
     * 文件名
     */
    private String name;

    /**
     * URL
     */
    private String url;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小(B)
     */
    private Long size;

    /**
     * md5 文件唯一标识
     */
    private String md5;

    /**
     * 文件过期时间
     */
    private Date expireTime;

    /**
     * 视频缩略图链接（视频文件专用）
     */
    private String thumbnailUrl;
}
