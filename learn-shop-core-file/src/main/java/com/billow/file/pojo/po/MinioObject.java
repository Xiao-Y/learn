package com.billow.file.pojo.po;

import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * MinIO Object实体类
 * 对应数据库表minio_object
 */
@Data
@Table("minio_object")
public class MinioObject {

    /**
     * 主键
     */
    @Id(keyType=KeyType.Generator, value= KeyGenerators.snowFlakeId)
    private String id;

    /**
     * 用户ID
     */
    private String userId;

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
     * 创建人ID
     */
    private String creatorId;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime ts;

    /**
     * 逻辑删除字段(0:正常,1:删除)
     */
    @Column(isLogicDelete = true)
    private Integer deleteFlag;
}
