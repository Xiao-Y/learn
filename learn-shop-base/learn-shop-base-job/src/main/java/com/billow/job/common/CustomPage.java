package com.billow.job.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据
 *
 * @author liuyongtao
 * @create 2019-11-01 9:07
 */
public class CustomPage<T> {
    private List<T> tableData;
    private long recordCount;
    private long totalPages;

    public List<T> getTableData() {
        return tableData;
    }

    public CustomPage<T> setTableData(List<T> tableData) {
        this.tableData = tableData;
        return this;
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

    public <U> CustomPage<U> map(Converter<? super T, ? extends U> converter) {
        CustomPage<U> page = new CustomPage<>();
        page.setRecordCount(this.recordCount);
        page.setTotalPages(this.totalPages);
        page.setTableData(this.getConvertedContent(converter));
        return page;
    }

    protected <S> List<S> getConvertedContent(Converter<? super T, ? extends S> converter) {
        Assert.notNull(converter, "Converter must not be null!");
        List<S> result = new ArrayList<>(tableData.size());
        for (T element : tableData) {
            result.add(converter.convert(element));
        }
        return result;
    }
}
