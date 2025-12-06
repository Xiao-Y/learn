package com.billow.search.api;

import com.billow.search.service.EsRestClientService;
import com.billow.search.utils.EsDslBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ES RestHighLevelClient 查询接口
 *
 * @author billow
 * @since 2025-01-01
 */
@Tag(name = "ES RestHighLevelClient 查询")
@RestController
@RequestMapping("/es/rest")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EsRestClientController {

    private final EsRestClientService esRestClientService;

    @Operation(summary = "通用查询")
    @PostMapping("/search/{indexName}")
    public Map<String, Object> search(
            @PathVariable String indexName,
            @RequestBody Map<String, String> params) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 生成 DSL（用于展示）
            String queryDsl = EsDslBuilder.buildDsl(params);
            result.put("queryDsl", EsDslBuilder.formatDsl(queryDsl));

            // 执行查询（返回 Map 类型）
            List<Map> data = esRestClientService.searchAsList(indexName, params, Map.class);
            long total = esRestClientService.count(indexName, params);

            result.put("success", true);
            result.put("total", total);
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Operation(summary = "带分页的查询")
    @PostMapping("/search/{indexName}/page")
    public Map<String, Object> searchWithPage(
            @PathVariable String indexName,
            @RequestBody Map<String, String> params,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 生成 DSL（用于展示）
            String queryDsl = EsDslBuilder.buildDsl(params);
            result.put("queryDsl", EsDslBuilder.formatDsl(queryDsl));

            // 执行查询（返回 Map 类型）
            List<Map> data = esRestClientService.searchAsListWithPage(
                    indexName, params, pageNum, pageSize, Map.class);
            long total = esRestClientService.count(indexName, params);

            result.put("success", true);
            result.put("total", total);
            result.put("pageNum", pageNum);
            result.put("pageSize", pageSize);
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Operation(summary = "带排序和分页的查询")
    @PostMapping("/search/{indexName}/sort")
    public Map<String, Object> searchWithSortAndPage(
            @PathVariable String indexName,
            @RequestBody Map<String, String> params,
            @RequestParam String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 生成 DSL（用于展示）
            String queryDsl = EsDslBuilder.buildDsl(params);
            result.put("queryDsl", EsDslBuilder.formatDsl(queryDsl));

            // 执行查询
            SearchResponse response = esRestClientService.searchWithSortAndPage(
                    indexName, params, sortField, sortOrder, pageNum, pageSize);

            result.put("success", true);
            result.put("total", response.getHits().getTotalHits().value);
            result.put("pageNum", pageNum);
            result.put("pageSize", pageSize);
            result.put("sortField", sortField);
            result.put("sortOrder", sortOrder);
            result.put("data", response.getHits().getHits());
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Operation(summary = "统计数量")
    @PostMapping("/count/{indexName}")
    public Map<String, Object> count(
            @PathVariable String indexName,
            @RequestBody Map<String, String> params) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 生成 DSL（用于展示）
            String queryDsl = EsDslBuilder.buildDsl(params);
            result.put("queryDsl", EsDslBuilder.formatDsl(queryDsl));

            // 执行统计
            long count = esRestClientService.count(indexName, params);

            result.put("success", true);
            result.put("count", count);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Operation(summary = "查询指定字段（带分页）")
    @PostMapping("/search/{indexName}/fields")
    public Map<String, Object> searchWithFields(
            @PathVariable String indexName,
            @RequestBody Map<String, String> params,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String[] fields) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 生成 DSL（用于展示）
            String queryDsl = EsDslBuilder.buildDsl(params);
            result.put("queryDsl", EsDslBuilder.formatDsl(queryDsl));

            // 执行查询（返回 Map 类型）
            List<Map> data;
            if (fields != null && fields.length > 0) {
                data = esRestClientService.searchAsListWithPage(
                        indexName, params, pageNum, pageSize, Map.class, fields);
            } else {
                data = esRestClientService.searchAsListWithPage(
                        indexName, params, pageNum, pageSize, Map.class);
            }
            long total = esRestClientService.count(indexName, params);

            result.put("success", true);
            result.put("total", total);
            result.put("pageNum", pageNum);
            result.put("pageSize", pageSize);
            result.put("fields", fields != null ? fields : "all");
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Operation(summary = "排除指定字段查询")
    @PostMapping("/search/{indexName}/exclude")
    public Map<String, Object> searchWithExcludeFields(
            @PathVariable String indexName,
            @RequestBody Map<String, String> params,
            @RequestParam String[] excludeFields) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 生成 DSL（用于展示）
            String queryDsl = EsDslBuilder.buildDsl(params);
            result.put("queryDsl", EsDslBuilder.formatDsl(queryDsl));

            // 执行查询（返回 Map 类型）
            List<Map> data = esRestClientService.searchAsListWithExcludeFields(
                    indexName, params, Map.class, excludeFields);
            long total = esRestClientService.count(indexName, params);

            result.put("success", true);
            result.put("total", total);
            result.put("excludeFields", excludeFields);
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Operation(summary = "示例 - 查询文档（返回 Map）")
    @GetMapping("/example/document")
    public Map<String, Object> exampleDocument() {
        Map<String, String> params = new HashMap<>();
        params.put("title", "我的");

        Map<String, Object> result = new HashMap<>();
        try {
            List<Map> data = esRestClientService.searchAsList("document", params, Map.class);
            result.put("success", true);
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Operation(summary = "示例 - 组合查询")
    @GetMapping("/example/combined")
    public Map<String, Object> exampleCombined() {
        Map<String, String> params = new HashMap<>();
        params.put("title", "手机");
        params.put("price", "1000~5000");
        params.put("status", "1,2,3");

        Map<String, Object> result = new HashMap<>();
        try {
            List<Map> data = esRestClientService.searchAsListWithPage(
                    "products", params, 1, 10, Map.class);
            result.put("success", true);
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Operation(summary = "示例 - 查询指定字段")
    @GetMapping("/example/fields")
    public Map<String, Object> exampleFields() {
        Map<String, String> params = new HashMap<>();
        params.put("title", "手机");

        Map<String, Object> result = new HashMap<>();
        try {
            // 使用可变参数，指定返回字段
            List<Map> data = esRestClientService.searchAsListWithPage(
                    "products", params, 1, 10, Map.class, "title", "price", "status");
            result.put("success", true);
            result.put("fields", new String[]{"title", "price", "status"});
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Operation(summary = "示例 - 查询所有字段")
    @GetMapping("/example/allFields")
    public Map<String, Object> exampleAllFields() {
        Map<String, String> params = new HashMap<>();
        params.put("title", "手机");

        Map<String, Object> result = new HashMap<>();
        try {
            // 不传字段参数，返回所有字段
            List<Map> data = esRestClientService.searchAsListWithPage(
                    "products", params, 1, 10, Map.class);
            result.put("success", true);
            result.put("fields", "all");
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @Operation(summary = "示例 - 排除字段查询")
    @GetMapping("/example/exclude")
    public Map<String, Object> exampleExclude() {
        Map<String, String> params = new HashMap<>();
        params.put("title", "手机");

        Map<String, Object> result = new HashMap<>();
        try {
            // 使用可变参数，排除指定字段
            List<Map> data = esRestClientService.searchAsListWithExcludeFields(
                    "products", params, Map.class, "content", "description");
            result.put("success", true);
            result.put("excludeFields", new String[]{"content", "description"});
            result.put("data", data);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }
}
