package com.billow.search.service;

import com.billow.search.utils.EsDslBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ES RestHighLevelClient 查询服务
 * 使用 EsDslBuilder 生成 DSL，通过 RestHighLevelClient 执行查询
 *
 * @author billow
 * @since 2025-01-01
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EsRestClientService {

    private final RestHighLevelClient restHighLevelClient;

    /**
     * 通用查询方法（支持指定字段）
     *
     * @param indexName 索引名称
     * @param params    查询参数
     * @param fields    需要返回的字段列表（可变参数，不传则返回所有字段）
     * @return 查询结果
     */
    public SearchResponse search(String indexName, Map<String, String> params, String... fields) throws IOException {
        return buildAndExecuteSearch(indexName, params, null, null, null, null, fields);
    }

    /**
     * 带分页的查询（支持指定字段）
     *
     * @param indexName 索引名称
     * @param params    查询参数
     * @param pageNum   页码（从 1 开始）
     * @param pageSize  每页大小
     * @param fields    需要返回的字段列表（可变参数，不传则返回所有字段）
     * @return 查询结果
     */
    public SearchResponse searchWithPage(String indexName, Map<String, String> params,
                                         Integer pageNum, Integer pageSize, String... fields) throws IOException {
        return buildAndExecuteSearch(indexName, params, pageNum, pageSize, null, null, fields);
    }

    /**
     * 带排序和分页的查询（支持指定字段）
     *
     * @param indexName 索引名称
     * @param params    查询参数
     * @param sortField 排序字段
     * @param sortOrder 排序方向（asc/desc）
     * @param pageNum   页码（从 1 开始）
     * @param pageSize  每页大小
     * @param fields    需要返回的字段列表（可变参数，不传则返回所有字段）
     * @return 查询结果
     */
    public SearchResponse searchWithSortAndPage(String indexName, Map<String, String> params,
                                                String sortField, String sortOrder,
                                                Integer pageNum, Integer pageSize, String... fields) throws IOException {
        return buildAndExecuteSearch(indexName, params, pageNum, pageSize, sortField, sortOrder, fields);
    }

    /**
     * 查询并返回指定类型列表（支持指定字段）
     *
     * @param indexName 索引名称
     * @param params    查询参数
     * @param clazz     返回对象类型
     * @param fields    需要返回的字段列表（可变参数，不传则返回所有字段）
     * @param <T>       泛型类型
     * @return 指定类型列表
     */
    public <T> List<T> searchAsList(String indexName, Map<String, String> params, Class<T> clazz, String... fields)
            throws IOException {
        SearchResponse response = search(indexName, params, fields);
        return convertToList(response, clazz);
    }

    /**
     * 带分页查询并返回指定类型列表（支持指定字段）
     *
     * @param indexName 索引名称
     * @param params    查询参数
     * @param pageNum   页码
     * @param pageSize  每页大小
     * @param clazz     返回对象类型
     * @param fields    需要返回的字段列表（可变参数，不传则返回所有字段）
     * @param <T>       泛型类型
     * @return 指定类型列表
     */
    public <T> List<T> searchAsListWithPage(String indexName, Map<String, String> params,
                                             Integer pageNum, Integer pageSize, Class<T> clazz, String... fields)
            throws IOException {
        SearchResponse response = searchWithPage(indexName, params, pageNum, pageSize, fields);
        return convertToList(response, clazz);
    }

    /**
     * 统计查询结果数量
     *
     * @param indexName 索引名称
     * @param params    查询参数
     * @return 结果数量
     */
    public long count(String indexName, Map<String, String> params) throws IOException {
        SearchResponse response = search(indexName, params);
        return response.getHits().getTotalHits().value;
    }

    /**
     * 排除指定字段查询（可变参数）
     *
     * @param indexName      索引名称
     * @param params         查询参数
     * @param excludeFields  需要排除的字段列表（可变参数，不传则返回所有字段）
     * @return 查询结果
     */
    public SearchResponse searchWithExcludeFields(String indexName, Map<String, String> params,
                                                   String... excludeFields) throws IOException {
        // 构建查询 DSL
        String queryDsl = EsDslBuilder.buildDsl(params);
        log.info("ES Query DSL: {}", EsDslBuilder.formatDsl(queryDsl));

        // 创建搜索请求
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 使用 wrapperQuery 包装 DSL
        sourceBuilder.query(QueryBuilders.wrapperQuery(queryDsl));

        // 排除字段（如果传了参数）
        if (excludeFields != null && excludeFields.length > 0) {
            sourceBuilder.fetchSource(null, excludeFields);
            log.info("Exclude fields: {}", String.join(", ", excludeFields));
        } else {
            log.info("Query all fields (no exclusion)");
        }

        searchRequest.source(sourceBuilder);

        // 执行查询
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    /**
     * 排除指定字段查询并返回指定类型列表（可变参数）
     *
     * @param indexName      索引名称
     * @param params         查询参数
     * @param clazz          返回对象类型
     * @param excludeFields  需要排除的字段列表（可变参数，不传则返回所有字段）
     * @param <T>            泛型类型
     * @return 指定类型列表
     */
    public <T> List<T> searchAsListWithExcludeFields(String indexName, Map<String, String> params,
                                                      Class<T> clazz, String... excludeFields) throws IOException {
        SearchResponse response = searchWithExcludeFields(indexName, params, excludeFields);
        return convertToList(response, clazz);
    }

    /**
     * 构建并执行搜索（核心方法）
     *
     * @param indexName 索引名称
     * @param params    查询参数
     * @param pageNum   页码（可选）
     * @param pageSize  每页大小（可选）
     * @param sortField 排序字段（可选）
     * @param sortOrder 排序方向（可选）
     * @param fields    返回字段列表（可选）
     * @return 查询结果
     */
    private SearchResponse buildAndExecuteSearch(String indexName, Map<String, String> params,
                                                  Integer pageNum, Integer pageSize,
                                                  String sortField, String sortOrder,
                                                  String... fields) throws IOException {
        // 构建查询 DSL
        String queryDsl = EsDslBuilder.buildDsl(params);
        log.info("ES Query DSL: {}", EsDslBuilder.formatDsl(queryDsl));

        // 创建搜索请求
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 使用 wrapperQuery 包装 DSL
        sourceBuilder.query(QueryBuilders.wrapperQuery(queryDsl));

        // 指定返回字段（如果传了参数）
        if (fields != null && fields.length > 0) {
            sourceBuilder.fetchSource(fields, null);
            log.info("Query fields: {}", String.join(", ", fields));
        } else {
            log.info("Query all fields");
        }

        // 排序（如果指定）
        if (sortField != null && !sortField.isEmpty()) {
            SortOrder order = "desc".equalsIgnoreCase(sortOrder) ? SortOrder.DESC : SortOrder.ASC;
            sourceBuilder.sort(sortField, order);
            log.info("Sort by: {} {}", sortField, order);
        }

        // 分页（如果指定）
        if (pageNum != null && pageSize != null) {
            sourceBuilder.from((pageNum - 1) * pageSize);
            sourceBuilder.size(pageSize);
            log.info("Pagination: page={}, size={}", pageNum, pageSize);
        }

        searchRequest.source(sourceBuilder);

        // 执行查询
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    /**
     * 转换 SearchResponse 为指定类型列表
     *
     * @param response ES 查询响应
     * @param clazz    目标类型
     * @param <T>      泛型类型
     * @return 指定类型列表
     */
    private <T> List<T> convertToList(SearchResponse response, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            
            // 如果目标类型是 Map，直接返回
            if (clazz == Map.class || clazz == Object.class) {
                list.add((T) sourceAsMap);
            } else {
                // 否则转换为指定类型
                T obj = convertMapToObject(sourceAsMap, clazz);
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * 将 Map 转换为指定类型对象
     *
     * @param map   源 Map
     * @param clazz 目标类型
     * @param <T>   泛型类型
     * @return 目标对象
     */
    private <T> T convertMapToObject(Map<String, Object> map, Class<T> clazz) {
        try {
            // 使用 JSON 转换（需要引入 Jackson 或 FastJSON）
            // 这里使用 FastJSON
            String json = com.alibaba.fastjson.JSON.toJSONString(map);
            return com.alibaba.fastjson.JSON.parseObject(json, clazz);
        } catch (Exception e) {
            log.error("Convert map to object error", e);
            throw new RuntimeException("Convert map to object error: " + e.getMessage(), e);
        }
    }
}
