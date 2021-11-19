package com.billow.search.common;

import com.billow.aop.commons.CustomPage;
import com.billow.tools.utlis.FieldUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuyongtao
 * @since 2021-9-8 14:27
 */
public class EsPageUtils {

    /**
     * 构建分页对象
     *
     * @param searchHits
     * @param pageSize
     * @return {@link CustomPage}
     * @author liuyongtao
     * @since 2021-9-8 14:42
     */
    public static <T> CustomPage page(SearchHits<T> searchHits, int pageSize) {
        CustomPage page = CustomPage.build();
        // 设置总记录数
        page.setRecordCount(searchHits.getTotalHits());
        // 计算页面
        long size = page.getRecordCount() / pageSize;
        size = (page.getRecordCount() % pageSize) == 0 ? size : (size + 1);
        page.setTotalPages(size);
        // 高亮处理
        List<SearchHit<T>> list = searchHits.getSearchHits();

        List<T> contents = list.stream().map(m -> {
            // 单个内容
            T content = m.getContent();
            // 高亮设置
            Map<String, List<String>> highlightFields = m.getHighlightFields();
            if (MapUtils.isNotEmpty(highlightFields))
            {
                highlightFields.forEach((k, v) -> FieldUtils.setStrValue(content, k, v.get(0)));
            }
            return content;
        }).collect(Collectors.toList());
        page.setTableData(contents);
        return page;
    }
}
