package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author billow
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cp_product")
public class ProductPo extends BasePo {

    private static final long serialVersionUID = 1L;

    /**
     * 删除标志，0-删除，1-正常
     */
    @TableLogic
    private String deleFlag;

    /**
     * 商品信息
     */
    private String commodityInfo;

    /**
     * 等级
     */
    private String grade;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 产地
     */
    private String localityGrowth;

    /**
     * 包装
     */
    private String packing;

    /**
     * 规格
     */
    private String spec;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 状态：0-无货，1-有货
     */
    private String status;

    /**
     * 销售数量
     */
    private Integer quantity;

    /**
     * 商品图片名称
     */
    private String img;


}
