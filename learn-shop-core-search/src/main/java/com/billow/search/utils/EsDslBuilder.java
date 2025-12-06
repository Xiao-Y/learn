package com.billow.search.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ES DSL 构建器
 * 直接通过条件拼接生成 ES 的 DSL JSON
 *
 * @author billow
 * @since 2025-01-01
 */
@Slf4j
public class EsDslBuilder {

    private static final String RANGE_SEPARATOR = "~";
    private static final String MULTI_VALUE_SEPARATOR = ",";

    /**
     * 根据查询参数构建 ES DSL（仅查询部分）
     *
     * @param params 查询参数 Map
     * @return ES DSL JSON 字符串（不包含外层 query 包装）
     */
    public static String buildDsl(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            // 返回 match_all
            JSONObject matchAll = new JSONObject();
            matchAll.put("match_all", new JSONObject());
            return matchAll.toJSONString();
        }

        JSONObject query = buildQuery(params);
        String dsl = query.toJSONString();
        log.info("ES DSL: {}", dsl);
        return dsl;
    }

    /**
     * 根据查询参数构建完整的 ES 查询（带分页）
     *
     * @param params   查询参数 Map
     * @param pageNum  页码（从 1 开始）
     * @param pageSize 每页大小
     * @return 完整的 ES 查询 JSON（包含 query、from、size）
     */
    public static String buildFullQuery(Map<String, String> params, Integer pageNum, Integer pageSize) {
        JSONObject fullQuery = new JSONObject();

        // 构建查询条件
        String queryDsl = buildDsl(params);
        fullQuery.put("query", JSON.parseObject(queryDsl));

        // 分页参数
        if (pageNum != null && pageSize != null) {
            fullQuery.put("from", (pageNum - 1) * pageSize);
            fullQuery.put("size", pageSize);
        }

        return fullQuery.toJSONString();
    }

    /**
     * 构建查询条件（返回 bool 查询或单个查询）
     */
    private static JSONObject buildQuery(Map<String, String> params) {
        List<JSONObject> mustClauses = new ArrayList<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();

            if (StringUtils.isBlank(value)) {
                continue;
            }

            JSONObject clause = buildClause(fieldName, value);
            if (clause != null) {
                mustClauses.add(clause);
            }
        }

        // 如果只有一个条件，直接返回该条件
        if (mustClauses.size() == 1) {
            return mustClauses.get(0);
        }

        // 多个条件，构建 bool 查询
        if (!mustClauses.isEmpty()) {
            JSONObject boolQuery = new JSONObject();
            boolQuery.put("must", mustClauses);
            
            JSONObject query = new JSONObject();
            query.put("bool", boolQuery);
            return query;
        }

        // 如果没有条件，返回 match_all
        JSONObject matchAll = new JSONObject();
        matchAll.put("match_all", new JSONObject());
        return matchAll;
    }

    /**
     * 构建单个查询子句
     */
    private static JSONObject buildClause(String fieldName, String value) {
        // 1. 精确查询（以 = 开头）
        if (value.startsWith("=")) {
            String exactValue = value.substring(1);
            return buildTermQuery(fieldName, exactValue);
        }

        // 2. 宽松分词查询（以 * 开头）
        if (value.startsWith("*")) {
            String matchValue = value.substring(1);
            return buildMatchQuery(fieldName, matchValue);
        }

        // 3. 通配符查询（以 + 开头）
        if (value.startsWith("+")) {
            String wildcardValue = value.substring(1);
            return buildWildcardQuery(fieldName, wildcardValue);
        }

        // 4. 大于查询
        if (value.startsWith(">=")) {
            String compareValue = value.substring(2);
            return buildRangeQuery(fieldName, "gte", compareValue);
        }
        if (value.startsWith(">")) {
            String compareValue = value.substring(1);
            return buildRangeQuery(fieldName, "gt", compareValue);
        }

        // 5. 小于查询
        if (value.startsWith("<=")) {
            String compareValue = value.substring(2);
            return buildRangeQuery(fieldName, "lte", compareValue);
        }
        if (value.startsWith("<")) {
            String compareValue = value.substring(1);
            return buildRangeQuery(fieldName, "lt", compareValue);
        }

        // 6. 范围查询（包含 ~）
        if (value.contains(RANGE_SEPARATOR)) {
            return buildRangeQuery(fieldName, value);
        }

        // 7. 多值查询（包含逗号）
        if (value.contains(MULTI_VALUE_SEPARATOR)) {
            return buildMultiValueQuery(fieldName, value);
        }

        // 8. 默认查询（智能识别）
        if (containsChinese(value)) {
            // 中文：短语匹配
            return buildMatchPhraseQuery(fieldName, value);
        } else {
            // 英文：通配符匹配
            return buildWildcardQuery(fieldName, "*" + value + "*");
        }
    }

    /**
     * 构建 term 查询（精确匹配）
     */
    private static JSONObject buildTermQuery(String fieldName, String value) {
        JSONObject term = new JSONObject();
        JSONObject termQuery = new JSONObject();
        termQuery.put(fieldName, value);
        term.put("term", termQuery);
        return term;
    }

    /**
     * 构建 match 查询（分词查询，OR 关系）
     */
    private static JSONObject buildMatchQuery(String fieldName, String value) {
        JSONObject match = new JSONObject();
        JSONObject matchQuery = new JSONObject();
        matchQuery.put(fieldName, value);
        match.put("match", matchQuery);
        return match;
    }

    /**
     * 构建 match_phrase 查询（短语匹配）
     */
    private static JSONObject buildMatchPhraseQuery(String fieldName, String value) {
        JSONObject matchPhrase = new JSONObject();
        JSONObject matchPhraseQuery = new JSONObject();
        matchPhraseQuery.put(fieldName, value);
        matchPhrase.put("match_phrase", matchPhraseQuery);
        return matchPhrase;
    }

    /**
     * 构建 wildcard 查询（通配符查询）
     */
    private static JSONObject buildWildcardQuery(String fieldName, String value) {
        JSONObject wildcard = new JSONObject();
        JSONObject wildcardQuery = new JSONObject();
        wildcardQuery.put(fieldName, value);
        wildcard.put("wildcard", wildcardQuery);
        return wildcard;
    }

    /**
     * 构建 range 查询（范围查询）
     */
    private static JSONObject buildRangeQuery(String fieldName, String operator, String value) {
        JSONObject range = new JSONObject();
        JSONObject rangeQuery = new JSONObject();
        JSONObject condition = new JSONObject();
        condition.put(operator, value);
        rangeQuery.put(fieldName, condition);
        range.put("range", rangeQuery);
        return range;
    }

    /**
     * 构建 range 查询（范围查询，min~max 格式）
     */
    private static JSONObject buildRangeQuery(String fieldName, String value) {
        String[] parts = value.split(RANGE_SEPARATOR, -1);
        if (parts.length != 2) {
            return null;
        }

        String start = parts[0].trim();
        String end = parts[1].trim();

        JSONObject range = new JSONObject();
        JSONObject rangeQuery = new JSONObject();
        JSONObject condition = new JSONObject();

        if (StringUtils.isNotBlank(start)) {
            condition.put("gte", start);
        }
        if (StringUtils.isNotBlank(end)) {
            condition.put("lte", end);
        }

        if (condition.isEmpty()) {
            return null;
        }

        rangeQuery.put(fieldName, condition);
        range.put("range", rangeQuery);
        return range;
    }

    /**
     * 构建多值查询（中文用 should + match_phrase，英文用 terms）
     */
    private static JSONObject buildMultiValueQuery(String fieldName, String value) {
        List<String> values = Arrays.stream(value.split(MULTI_VALUE_SEPARATOR))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .collect(Collectors.toList());

        if (values.isEmpty()) {
            return null;
        }

        // 检测是否包含中文
        boolean hasChinese = values.stream().anyMatch(EsDslBuilder::containsChinese);

        if (hasChinese) {
            // 中文：使用 should + match_phrase
            JSONArray shouldClauses = new JSONArray();
            for (String val : values) {
                shouldClauses.add(buildMatchPhraseQuery(fieldName, val));
            }

            JSONObject bool = new JSONObject();
            JSONObject boolQuery = new JSONObject();
            boolQuery.put("should", shouldClauses);
            boolQuery.put("minimum_should_match", 1);
            bool.put("bool", boolQuery);
            return bool;
        } else {
            // 英文：使用 terms
            JSONObject terms = new JSONObject();
            JSONObject termsQuery = new JSONObject();
            termsQuery.put(fieldName, values);
            terms.put("terms", termsQuery);
            return terms;
        }
    }

    /**
     * 检测字符串是否包含中文字符
     */
    private static boolean containsChinese(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        return str.matches(".*[\\u4e00-\\u9fa5]+.*");
    }

    /**
     * 格式化 DSL（美化输出）
     */
    public static String formatDsl(String dsl) {
        try {
            JSONObject json = JSON.parseObject(dsl);
            return JSON.toJSONString(json, true);
        } catch (Exception e) {
            return dsl;
        }
    }

    /**
     * 构建完整查询（带排序）
     */
    public static String buildFullQueryWithSort(Map<String, String> params, String sortField, String sortOrder) {
        return buildFullQueryWithSort(params, sortField, sortOrder, null, null);
    }

    /**
     * 构建完整查询（带排序和分页）
     */
    public static String buildFullQueryWithSort(Map<String, String> params, String sortField, String sortOrder,
                                                Integer pageNum, Integer pageSize) {
        JSONObject fullQuery = new JSONObject();

        // 构建查询条件
        String queryDsl = buildDsl(params);
        fullQuery.put("query", JSON.parseObject(queryDsl));

        // 排序
        if (StringUtils.isNotBlank(sortField)) {
            JSONArray sort = new JSONArray();
            JSONObject sortObj = new JSONObject();
            JSONObject sortConfig = new JSONObject();
            sortConfig.put("order", StringUtils.isNotBlank(sortOrder) ? sortOrder.toLowerCase() : "asc");
            sortObj.put(sortField, sortConfig);
            sort.add(sortObj);
            fullQuery.put("sort", sort);
        }

        // 分页参数
        if (pageNum != null && pageSize != null) {
            fullQuery.put("from", (pageNum - 1) * pageSize);
            fullQuery.put("size", pageSize);
        }

        return fullQuery.toJSONString();
    }
}
