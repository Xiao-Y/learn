package com.billow.pojo.base;

import java.io.Serializable;

/**
 * 分布数据
 *
 * @author LiuYongTao
 * @date 2018/4/27 11:46
 */
public abstract class BasePage implements Serializable {

    private static final long serialVersionUID = 1716751981762447850L;

    private static final Integer PAGE_SIZE = 10; // 每页要显示的记录数
    private static final Integer PAGE_NO = 0; // 当前页号
    private static final Integer RECORD_COUNT = 0; // 总记录数

    private String requestUrl; // 请求url
    private Integer pageSize = PAGE_SIZE; // 每页要显示的记录数
    private Integer pageNo = PAGE_NO; // 当前页号
    private Integer recordCount = RECORD_COUNT; // 总记录数
    private String objectOrderBy;

    public String getObjectOrderBy() {
        return objectOrderBy;
    }

    public void setObjectOrderBy(String objectOrderBy) {
        this.objectOrderBy = objectOrderBy;
    }

    /**
     * 请求url
     *
     * @return
     * @author XiaoY
     * @date: 2016年12月3日 下午3:57:19
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * 请求url
     *
     * @param requestUrl
     * @author XiaoY
     * @date: 2016年12月3日 下午3:57:23
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

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
     * 当前页号
     *
     * @return
     * @author XiaoY
     * @date: 2016年12月3日 下午3:56:44
     */
    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * 当前页号
     *
     * @param pageNo
     * @author XiaoY
     * @date: 2016年12月3日 下午3:56:48
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo == null ? PAGE_NO : pageNo;
    }

    /**
     * 总记录数
     *
     * @return
     * @author XiaoY
     * @date: 2016年12月3日 下午3:56:56
     */
    public Integer getRecordCount() {
        return recordCount;
    }

    /**
     * 总记录数
     *
     * @param recordCount
     * @author XiaoY
     * @date: 2016年12月3日 下午3:56:59
     */
    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount == null ? RECORD_COUNT : recordCount;
    }

    @Override
    public String toString() {
        return "BasePage{" +
                "requestUrl='" + requestUrl + '\'' +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", recordCount=" + recordCount +
                ", objectOrderBy='" + objectOrderBy + '\'' +
                '}';
    }
}
