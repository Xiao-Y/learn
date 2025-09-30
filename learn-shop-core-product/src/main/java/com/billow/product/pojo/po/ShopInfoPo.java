package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 店铺表
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_shop_info")
@Schema(title = "ShopInfoPo对象", description="店铺表")
public class ShopInfoPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "店铺编号，唯一")
    @TableField("shop_no")
    private String shopNo;

    @Schema(title = "店铺名称")
    @TableField("shop_name")
    private String shopName;

    @Schema(title = "店铺排序")
    @TableField("shop_sort")
    private Long shopSort;


}
