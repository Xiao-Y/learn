package com.billow.file.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * MinIO Object 搜索参数
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "MinIO Object 搜索参数")
public class MinioObjectSearchParam extends BasePage {

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "Bucket 桶名")
    private String bucket;

    @Schema(description = "文件名")
    private String name;

    @Schema(description = "文件类型")
    private String type;

    @Schema(description = "MD5值")
    private String md5;

    @Schema(description = "是否已过期（true-已过期，false-未过期）")
    private Boolean expired;

    @Schema(description = "过期时间开始")
    private LocalDateTime expireTimeStart;

    @Schema(description = "过期时间结束")
    private LocalDateTime expireTimeEnd;
}