package com.billow.product.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * spu表（Standard Product Unit, 标准产品单元） 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsSpuSearchParam extends BasePage implements Serializable {

    @Schema(title = "商品编号，唯一")
    private String spuNo;

    @Schema(title = "商品名称")
    private String goodsName;

    @Schema(title = "品牌id")
    private Long brandId;

    @Schema(title = "品牌分类id")
    private Long categoryId;

    // 查询测试
    // 指定时间查询
    private Date createTime;
    // 时间范围查询（dateRange开头）
    private String dateRangeCreateTime;
    // 多数值查询（List结尾）
    private List<String> spuNoList;
}
