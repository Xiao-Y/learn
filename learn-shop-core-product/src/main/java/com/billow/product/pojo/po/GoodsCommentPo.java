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
 * 商品评价表
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("pms_goods_comment")
@Schema(title = "GoodsCommentPo对象", description="商品评价表")
public class GoodsCommentPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "商品id")
    @Column("spu_id")
    private Long spuId;

    @Schema(title = "会员昵称")
    @Column("member_nick_name")
    private String memberNickName;

    @Schema(title = "商品名称")
    @Column("product_name")
    private String productName;

    @Schema(title = "评价星数：0->5")
    @Column("star")
    private Integer star;

    @Schema(title = "评价的ip")
    @Column("member_ip")
    private String memberIp;

    @Schema(title = "是否显示")
    @Column("show_status")
    private Integer showStatus;

    @Schema(title = "商品skuid")
    @Column("sku_id")
    private Long skuId;

    @Schema(title = "收藏数")
    @Column("collect_couont")
    private Integer collectCouont;

    @Schema(title = "阅读数")
    @Column("read_count")
    private Integer readCount;

    @Schema(title = "内容")
    @Column("content")
    private String content;

    @Schema(title = "上传图片地址，以逗号隔开")
    @Column("pics")
    private String pics;

    @Schema(title = "评论用户头像")
    @Column("member_icon")
    private String memberIcon;

    @Schema(title = "回复数")
    @Column("replay_count")
    private Integer replayCount;


}
