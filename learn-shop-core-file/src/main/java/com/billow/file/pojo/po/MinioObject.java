package com.billow.file.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * MinIO Object实体类
 * 对应数据库表minio_object
 */
@Data
@TableName("minio_object")
public class MinioObject {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime ts;

    /**
     * 逻辑删除字段(0:正常,1:删除)
     */
    @TableLogic
    private Integer deleteFlag;
}
