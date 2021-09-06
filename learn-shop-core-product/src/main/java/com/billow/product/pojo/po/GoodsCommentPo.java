package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品评价表
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_goods_comment")
@ApiModel(value="GoodsCommentPo对象", description="商品评价表")
public class GoodsCommentPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty(value = "会员昵称")
    @TableField("member_nick_name")
    private String memberNickName;

    @ApiModelProperty(value = "商品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty(value = "评价星数：0->5")
    @TableField("star")
    private Integer star;

    @ApiModelProperty(value = "评价的ip")
    @TableField("member_ip")
    private String memberIp;

    @ApiModelProperty(value = "是否显示")
    @TableField("show_status")
    private Integer showStatus;

    @ApiModelProperty(value = "商品skuid")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "收藏数")
    @TableField("collect_couont")
    private Integer collectCouont;

    @ApiModelProperty(value = "阅读数")
    @TableField("read_count")
    private Integer readCount;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "上传图片地址，以逗号隔开")
    @TableField("pics")
    private String pics;

    @ApiModelProperty(value = "评论用户头像")
    @TableField("member_icon")
    private String memberIcon;

    @ApiModelProperty(value = "回复数")
    @TableField("replay_count")
    private Integer replayCount;


}
