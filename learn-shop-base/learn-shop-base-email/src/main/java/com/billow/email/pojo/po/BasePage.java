package com.billow.email.pojo.po;

import java.io.Serializable;

/**
 * 分页数据
 *
 * @author LiuYongTao
 * @date 2018/4/27 11:46
 */
public abstract class BasePage implements Serializable {

    private static final Integer PAGE_SIZE = 10; // 每页要显示的记录数
    private static final Integer PAGE_NO = 1; // 当前页号

    // 每页要显示的记录数
    private Integer pageSize = PAGE_SIZE;
    // "当前页号"
    private Integer pageNo = PAGE_NO;


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
     * 当前页号(从1开始的，所以要-1)
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

    @Override
    public String toString() {
        return "BasePage{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                '}';
    }
}
