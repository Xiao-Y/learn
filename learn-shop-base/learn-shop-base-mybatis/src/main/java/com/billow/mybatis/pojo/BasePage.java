package com.billow.mybatis.pojo;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 分页数据
 *
 * @author LiuYongTao
 * @date 2018/4/27 11:46
 */
@EqualsAndHashCode
public abstract class BasePage implements Serializable {

    /**
     * 每页要显示的记录数
     *
     * @author xiaoy
     * @since 2021/11/20 9:48
     */
    public static final Integer PAGE_SIZE = 10;
    /**
     * 当前页号
     *
     * @author xiaoy
     * @since 2021/11/20 9:48
     */
    public static final Integer PAGE_NO = 1;

    /**
     * 每页要显示的记录数
     *
     * @author xiaoy
     * @since 2021/11/20 10:00
     */
    private Integer pageSize = PAGE_SIZE;
    /**
     * 当前页号
     *
     * @author xiaoy
     * @since 2021/11/20 10:00
     */
    private Integer pageNo = PAGE_NO;

    /**
     * 分页字段（对象名称）
     *
     * @author xiaoy
     * @since 2021/11/20 10:01
     */
    private String orderBy;

    /**
     * 是否正序排序
     *
     * @author xiaoy
     * @since 2021/11/20 10:01
     */
    private boolean isAsc = true;

    /**
     * 每页要显示的记录数
     *
     * @return
     * @author XiaoY
     * @date: 2016年12月3日 下午3:57:08
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 每页要显示的记录数
     *
     * @param pageSize
     * @author XiaoY
     * @date: 2016年12月3日 下午3:57:11
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null ? PAGE_SIZE : pageSize;
    }

    /**
     * 当前页号，从0开始
     *
     * @return
     * @author XiaoY
     * @date: 2016年12月3日 下午3:56:44
     */
    public Integer getPageNo() {
        if (pageNo == null) {
            return null;
        }
        return pageNo - 1;
    }

    /**
     * 当前页号
     *
     * @param pageNo
     * @author XiaoY
     * @date: 2016年12月3日 下午3:56:48
     */
    public void setPageNo(Integer pageNo) {
        if (pageNo != null) {
            this.pageNo = pageNo;
        }
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }
}
