package com.billow.product.pojo.po;


import java.io.Serializable;

/**
 * 商品图片实体类
 */
public class ProductImagePo implements Serializable {
    // 商品图片
    private String productId;
    // 原始图上名
    private String oldImageName;
    // 新图片各
    private String newImageName;
    // 图片类型 1-mini,2-min,3-max
    private String imageType;
    // 排序
    private Integer seqNo;
    // 文件内容类型
    private String contentType;

    public String getProductId() {
        return productId;
    }

    public ProductImagePo setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getOldImageName() {
        return oldImageName;
    }

    public ProductImagePo setOldImageName(String oldImageName) {
        this.oldImageName = oldImageName;
        return this;
    }

    public String getNewImageName() {
        return newImageName;
    }

    public ProductImagePo setNewImageName(String newImageName) {
        this.newImageName = newImageName;
        return this;
    }

    public String getImageType() {
        return imageType;
    }

    public ProductImagePo setImageType(String imageType) {
        this.imageType = imageType;
        return this;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public ProductImagePo setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public ProductImagePo setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
}
