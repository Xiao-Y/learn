package com.billow.search.interfaces.commons;

/**
 * 统一分页数据
 *
 * @author liuyongtao
 * @create 2019-11-01 9:07
 */
public class CustomPage {
    // 分页数据列表
    private Object tableData;
    // 总记录数
    private long recordCount;
    // 总页数据
    private long totalPages;

    /**
     * 分页数据列表
     */
    public Object getTableData() {
        return tableData;
    }

    /**
     * 分页数据列表
     */
    public CustomPage setTableData(Object tableData) {
        this.tableData = tableData;
        return this;
    }

    /**
     * 总记录数
     */
    public long getRecordCount() {
        return recordCount;
    }

    /**
     * 总记录数
     */
    public CustomPage setRecordCount(long recordCount) {
        this.recordCount = recordCount;
        return this;
    }

    /**
     * 总页数据
     */
    public long getTotalPages() {
        return totalPages;
    }

    /**
     * 总页数据
     */
    public CustomPage setTotalPages(long totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public static CustomPage build() {
        return new CustomPage();
    }
}
