package com.billow.product.pojo.build;

import com.billow.mybatis.pojo.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品评价表 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsCommentBuildParam extends BasePo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "商品id")
    private Long spuId;

    @Schema(title = "会员昵称")
    private String memberNickName;

    @Schema(title = "商品名称")
    private String productName;

    @Schema(title = "评价星数：0->5")
    private Integer star;

    @Schema(title = "评价的ip")
    private String memberIp;

    @Schema(title = "是否显示")
    private Integer showStatus;

    @Schema(title = "商品skuid")
    private Long skuId;

    @Schema(title = "收藏数")
    private Integer collectCouont;

    @Schema(title = "阅读数")
    private Integer readCount;

    @Schema(title = "内容")
    private String content;

    @Schema(title = "上传图片地址，以逗号隔开")
    private String pics;

    @Schema(title = "评论用户头像")
    private String memberIcon;

    @Schema(title = "回复数")
    private Integer replayCount;


}
