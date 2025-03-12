package com.billow.product.pojo.excel;

import com.billow.excel.annotation.ExcelColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品SPU导入导出对象
 *
 * @author billow
 * @since 2024-01-01
 */
@Data
@ApiModel(value = "商品SPU导入导出对象")
public class GoodsSpuExcel {

    @ApiModelProperty(value = "商品编号")
    @ExcelColumn(name = "商品编号", width = 20, required = true)
    private String spuNo;

    @ApiModelProperty(value = "商品名称")
    @ExcelColumn(name = "商品名称", width = 30, required = true)
    private String goodsName;

    @ApiModelProperty(value = "品牌名称")
    @ExcelColumn(name = "品牌名称", width = 15, required = true)
    private String brandName;

    @ApiModelProperty(value = "分类名称")
    @ExcelColumn(name = "分类名称", width = 15, required = true)
    private String categoryName;

    @ApiModelProperty(value = "关键字")
    @ExcelColumn(name = "关键字", width = 20)
    private String keywords;

    @ApiModelProperty(value = "副标题")
    @ExcelColumn(name = "副标题", width = 30)
    private String subTitle;

    @ApiModelProperty(value = "价格(元)")
    @ExcelColumn(name = "价格(元)", width = 15, required = true)
    private Integer price;

    @ApiModelProperty(value = "最低售价(元)")
    @ExcelColumn(name = "最低售价(元)", width = 15, required = true)
    private Integer lowPrice;

    @ApiModelProperty(value = "销量")
    @ExcelColumn(name = "销量", width = 10)
    private Long sale;

    @ApiModelProperty(value = "库存")
    @ExcelColumn(name = "库存", width = 10, required = true)
    private Long stock;

    @ApiModelProperty(value = "库存预警值")
    @ExcelColumn(name = "库存预警值", width = 15)
    private Long lowStock;

    @ApiModelProperty(value = "上架状态（已上架/已下架）")
    @ExcelColumn(name = "上架状态", width = 10, required = true, dictType = ExcelColumn.DictType.ENUM, dictCode = "SpuPublishStatusEnum")
    private String publishStatus;

    @ApiModelProperty(value = "商品描述")
    @ExcelColumn(name = "商品描述", width = 50)
    private String description;
} 