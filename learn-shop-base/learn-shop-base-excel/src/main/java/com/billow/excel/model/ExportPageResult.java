package com.billow.excel.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ExportPageResult<T> {

    /**
     * 查询出来的数据
     */
    List<T> dataList;

    /**
     * 下次的查询参数
     */
    Object lastQueryParam;
}
