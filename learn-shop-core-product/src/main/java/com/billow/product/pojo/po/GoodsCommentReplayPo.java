package com.billow.product.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table("pms_goods_comment_replay")
@Schema(title = "GoodsCommentReplayPo对象", description="产品评价回复表")
public class GoodsCommentReplayPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "评论id")
    @Column("comment_id")
    private Long commentId;

    @Schema(title = "会员昵称")
    @Column("member_nick_name")
    private String memberNickName;

    @Schema(title = "会员头像")
    @Column("member_icon")
    private String memberIcon;

    @Schema(title = "内容")
    @Column("content")
    private String content;

    @Schema(title = "评论人员类型；0->会员；1->管理员")
    @Column("type")
    private Integer type;


}
