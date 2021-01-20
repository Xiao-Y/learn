package com.billow.base.workflow.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页参数
 *
 * @author liuyongtao
 * @create 2019-08-25 12:12
 */
public class CustomPage<T> {

    public static final Integer PAGE_SIZE = 10; // 每页要显示的记录数
    public static final Integer PAGE_NO = 0; // 当前页号
    public static final Integer RECORD_COUNT = 0; // 总记录数

    // 每页要显示的记录数
    private Integer pageSize = PAGE_SIZE;
    // 每页要显示的记录数
    private Integer pageNo = PAGE_NO;
    // 总记录数
    private long recordCount = RECORD_COUNT;
    // 总页数据
    private long totalPages = RECORD_COUNT;

    private Integer offset = PAGE_SIZE * PAGE_NO;
    // 分页数据
    private List<T> tableData = new ArrayList<>();

    public CustomPage() {
    }

    /**
     * 分页构造
     *
     * @param pageSize      页面大小
     * @param recordCount 总记录数
     * @author billow
     * @date 2019/8/25 12:29
     */
    public CustomPage(Integer pageSize, long recordCount) {
        this(pageSize, recordCount, null);
    }

    /**
     * 分页构造
     *
     * @param pageSize      页面大小
     * @param recordCount 总记录数
     * @param tableData       查询结果
     * @author billow
     * @date 2019/8/25 12:29
     */
    public CustomPage(Integer pageSize, long recordCount, List<T> tableData) {
        this.pageSize = pageSize;
        this.recordCount = recordCount;
        this.tableData = tableData;
        this.offset = this.pageNo * this.pageSize;
        // 计算分页
        this.totalPages = pageSize == null ? 0 : (int) Math.ceil((double) recordCount / (double) pageSize);
    }

    public long getRecordCount() {
        return recordCount;
    }

    public CustomPage<T> setRecordCount(long recordCount) {
        this.recordCount = recordCount;
        return this;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public CustomPage<T> setTotalPages(long totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public List<T> getTableData() {
        return tableData;
    }

    public CustomPage<T> setTableData(List<T> tableData) {
        this.tableData = tableData;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public CustomPage<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.offset = this.pageNo * this.pageSize;
        return this;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public CustomPage<T> setPageNo(Integer pageNo) {
        this.pageNo = pageNo - 1;
        this.offset = this.pageNo * this.pageSize;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public CustomPage<T> setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }
}
