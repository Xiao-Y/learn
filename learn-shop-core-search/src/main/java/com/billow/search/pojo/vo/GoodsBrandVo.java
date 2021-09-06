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
 * 品牌表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsBrandVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "首字母")
    @JSONField(name = "first_letter")
    private String firstLetter;

    @ApiModelProperty(value = "品牌名称")
    @JSONField(name = "brand_name")
    private String brandName;

    @ApiModelProperty(value = "分类排序")
    @JSONField(name = "brand_sort")
    private Long brandSort;

    @ApiModelProperty(value = "是否显示")
    @JSONField(name = "show_status")
    private Integer showStatus;

    @ApiModelProperty(value = "产品数量")
    @JSONField(name = "product_count")
    private Integer productCount;

    @ApiModelProperty(value = "品牌logo")
    private String logo;

    @ApiModelProperty(value = "专区大图")
    @JSONField(name = "big_pic")
    private String bigPic;

    @ApiModelProperty(value = "品牌故事")
    @JSONField(name = "brand_story")
    private String brandStory;

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
