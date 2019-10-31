package com.billow.order.pojo.po;

import com.billow.jpa.base.pojo.BasePo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 测试用
 *
 * @author liuyongtao
 * @create 2018-02-11 9:39
 */

/**
 * @author liuyongtao
 * @create 2018-04-27 11:18
 */
//@Data
@Entity
@Table(name = "t_order")
public class OrderPo extends BasePo implements Serializable {

    private String productNo;
    private String productName;
    private Long userId;

    public String getProductNo() {
        return productNo;
    }

    public OrderPo setProductNo(String productNo) {
        this.productNo = productNo;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public OrderPo setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public OrderPo setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "OrderPo{" +
                "productNo='" + productNo + '\'' +
                ", productName='" + productName + '\'' +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
