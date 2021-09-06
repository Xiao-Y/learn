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
 * 产品评价回复表
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_goods_comment_replay")
@ApiModel(value="GoodsCommentReplayPo对象", description="产品评价回复表")
public class GoodsCommentReplayPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    @TableField("comment_id")
    private Long commentId;

    @ApiModelProperty(value = "会员昵称")
    @TableField("member_nick_name")
    private String memberNickName;

    @ApiModelProperty(value = "会员头像")
    @TableField("member_icon")
    private String memberIcon;

    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "评论人员类型；0->会员；1->管理员")
    @TableField("type")
    private Integer type;


}
