package com.billow.search.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 分类表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsCategoryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类名称")
    @JSONField(name = "category_name")
    private String categoryName;

    @ApiModelProperty(value = "分类排序")
    @JSONField(name = "category_sort")
    private Long categorySort;

    @ApiModelProperty(value = "父类目id,顶级类目填0")
    @JSONField(name = "parent_id")
    private Long parentId;

    @ApiModelProperty(value = "是否为父节点，0为否，1为是")
    @JSONField(name = "is_parent")
    private Boolean parent;

    // 主键id
    private Long id;

    // 创建人
    @JSONField(name = "creator_code")
    private String creatorCode;

    // 创建人
    @JSONField(name = "updater_code")
    private String updaterCode;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JSONField(name = "create_time")
    private Date createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JSONField(name = "update_time")
    private Date updateTime;

    // 有效标志
    @JSONField(name = "valid_ind")
    private Boolean validInd;
}
