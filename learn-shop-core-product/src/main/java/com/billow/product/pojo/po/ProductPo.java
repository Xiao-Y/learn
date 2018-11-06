package com.billow.product.pojo.po;


import com.billow.common.base.pojo.BasePoDefault;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "cp_product")
public class ProductPo extends BasePoDefault implements Serializable {

    // 删除标志，0-删除，1-正常
    private String deleFlag;
    // 商品信息
    private String commodityInfo;
    // 等级
    private String grade;
    // 单价
    private BigDecimal unitPrice;
    // 产地
    private String localityGrowth;
    // 包装
    private String packing;
    // 规格
    private String spec;
    // 商品名称
    private String commodityName;
    // 状态：0-无货，1-有货
    private String status;
    //销售数量
    private Integer quantity;
    //商品图片名称
    private String img;

    public String getDeleFlag() {
        return deleFlag;
    }

    public ProductPo setDeleFlag(String deleFlag) {
        this.deleFlag = deleFlag;
        return this;
    }

    public String getCommodityInfo() {
        return commodityInfo;
    }

    public ProductPo setCommodityInfo(String commodityInfo) {
        this.commodityInfo = commodityInfo;
        return this;
    }

    public String getGrade() {
        return grade;
    }

    public ProductPo setGrade(String grade) {
        this.grade = grade;
        return this;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public ProductPo setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public String getLocalityGrowth() {
        return localityGrowth;
    }

    public ProductPo setLocalityGrowth(String localityGrowth) {
        this.localityGrowth = localityGrowth;
        return this;
    }

    public String getPacking() {
        return packing;
    }

    public ProductPo setPacking(String packing) {
        this.packing = packing;
        return this;
    }

    public String getSpec() {
        return spec;
    }

    public ProductPo setSpec(String spec) {
        this.spec = spec;
        return this;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public ProductPo setCommodityName(String commodityName) {
        this.commodityName = commodityName;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ProductPo setStatus(String status) {
        this.status = status;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductPo setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getImg() {
        return img;
    }

    public ProductPo setImg(String img) {
        this.img = img;
        return this;
    }
}
