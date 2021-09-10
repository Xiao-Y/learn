package com.billow.search.common;

import com.billow.aop.commons.CustomPage;
import com.billow.tools.utlis.FieldUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        List<T> contents = new ArrayList<>();
        // 高亮处理
        List<SearchHit<T>> list = searchHits.getSearchHits();
        for (SearchHit<T> hit : list) {
            // 单个内容
            T content = hit.getContent();
            Map<String, List<String>> highlightFields = hit.getHighlightFields();
            if (MapUtils.isNotEmpty(highlightFields)) {
                for (Map.Entry<String, List<String>> entry : highlightFields.entrySet()) {
                    FieldUtils.setStrValue(content, entry.getKey(), entry.getValue().get(0));
                }
            }
            contents.add(content);
        }
        page.setTableData(contents);
        return page;
    }
}
