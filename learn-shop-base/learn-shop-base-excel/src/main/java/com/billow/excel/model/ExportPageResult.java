package com.billow.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ExportPageResult<Q, T> {

    /**
     * 下次的查询参数
     */
    Q lastQueryParam;

    /**
     * 查询出来的数据
     */
    List<T> dataList;
}
