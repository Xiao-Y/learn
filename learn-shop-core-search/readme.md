# ES 查询工具完整使用手册

> **版本：** 2.0  
> **更新日期：** 2025-01-01  
> **作者：** billow

---

## 📋 目录

1. [概述](#1-概述)
2. [快速开始](#2-快速开始)
3. [查询规则详解](#3-查询规则详解)
4. [两种使用方式](#4-两种使用方式)
5. [API 接口文档](#5-api-接口文档)
6. [高级功能](#6-高级功能)
7. [中文查询专题](#7-中文查询专题)
8. [最佳实践](#8-最佳实践)
9. [常见问题](#9-常见问题)
10. [方案对比与选择](#10-方案对比与选择)

---

## 1. 概述

本项目提供了两套完整的 ES 查询解决方案，支持智能中文查询、泛型返回、可变参数等特性。

### 1.1 核心特性

- ✅ **智能识别**：自动识别中文/英文，选择最佳查询方式
- ✅ **简单易用**：通过 Map 参数即可构建复杂查询
- ✅ **功能完整**：支持所有常用查询类型（精确、模糊、范围、多值等）
- ✅ **易于调试**：可以直接查看生成的 DSL
- ✅ **灵活强大**：支持泛型返回、可变参数、指定字段
- ✅ **性能优秀**：支持分页、字段过滤、排序

### 1.2 两套方案

#### 方案一：EsQueryBuilder + Easy-ES

基于 Easy-ES 框架的查询构建器。

**核心类：**
- `EsQueryBuilder` - 查询构建器
- `CommonEsService` - 通用查询服务

**特点：**
- 与 Easy-ES 深度集成
- 类型安全
- 自动映射实体

#### 方案二：EsDslBuilder + RestHighLevelClient

直接生成 ES DSL 并通过 RestHighLevelClient 执行。

**核心类：**
- `EsDslBuilder` - DSL 构建器
- `EsRestClientService` - RestHighLevelClient 查询服务

**特点：**
- 直接生成 DSL JSON
- 易于调试
- 支持泛型返回
- 灵活性高

---

## 2. 快速开始

### 2.1 配置

```yaml
# application.yml
elasticsearch:
  host: localhost
  port: 9200
  scheme: http
```

### 2.2 基础查询示例

#### 使用 Easy-ES

```java
@Autowired
private CommonEsService commonEsService;
@Autowired
private DocumentMapper documentMapper;

// 查询
Map<String, String> params = new HashMap<>();
params.put("title", "我的");
List<Document> results = commonEsService.query(documentMapper, params, Document.class);
```

#### 使用 RestHighLevelClient

```java
@Autowired
private EsRestClientService esRestClientService;

// 查询返回 Map
List<Map> results = esRestClientService.searchAsList("document", params, Map.class);

// 查询返回自定义类型
List<Document> results = esRestClientService.searchAsList("document", params, Document.class);
```

---

## 3. 查询规则详解

### 3.1 完整规则速查表

| 查询类型 | 语法 | 示例 | ES 查询 | 说明 |
|---------|------|------|---------|------|
| **默认（中文）** | `value` | `"title": "我的"` | `match_phrase` | 短语匹配，保持词序 ⭐ |
| **默认（英文）** | `value` | `"code": "ABC"` | `wildcard` | 通配符匹配 |
| 宽松分词 | `*value` | `"title": "*我的 包养"` | `match` (OR) | 匹配任意分词 |
| 通配符 | `+value` | `"code": "+ABC*"` | `wildcard` | 强制通配符 |
| 精确 | `=value` | `"status": "=1"` | `term` | 完全精确匹配 |
| 大于 | `>value` | `"price": ">100"` | `range` (gt) | 数值比较 |
| 大于等于 | `>=value` | `"price": ">=100"` | `range` (gte) | 数值比较 |
| 小于 | `<value` | `"price": "<100"` | `range` (lt) | 数值比较 |
| 小于等于 | `<=value` | `"price": "<=100"` | `range` (lte) | 数值比较 |
| 范围 | `min~max` | `"price": "100~500"` | `range` | 范围查询 |
| 大于等于 | `value~` | `"date": "2025-01-01~"` | `range` (gte) | 时间范围 |
| 小于等于 | `~value` | `"date": "~2025-12-31"` | `range` (lte) | 时间范围 |
| 多值（中文） | `v1,v2` | `"title": "我的,我是"` | `should` + `match_phrase` | OR 短语匹配 |
| 多值（英文） | `v1,v2` | `"status": "1,2,3"` | `terms` | IN 查询 |

### 3.2 智能识别机制

系统会自动识别查询内容：

- **包含中文** → 使用 `match_phrase`（短语匹配，精确度高）
- **纯英文/数字** → 使用 `wildcard`（通配符匹配）

### 3.3 查询示例代码

```java
Map<String, String> params = new HashMap<>();

// 中文短语匹配（推荐）
params.put("title", "我的");

// 中文多值（OR）
params.put("title", "我的,我是,手机");

// 范围查询
params.put("price", "1000~5000");
params.put("createTime", "2025-01-01 00:00:00~2025-12-31 23:59:59");

// 比较查询
params.put("price", ">1000");
params.put("stock", ">=10");

// 精确查询
params.put("status", "=1");

// 宽松分词（OR）- 返回更多结果
params.put("title", "*我的 包养");

// 通配符
params.put("code", "+ABC*");
```


---

## 4. 两种使用方式

### 4.1 Easy-ES 方式

#### 基础查询

```java
Map<String, String> params = new HashMap<>();
params.put("title", "手机");

List<Document> results = commonEsService.query(documentMapper, params, Document.class);
```

#### 带分页

```java
List<Document> results = commonEsService.queryPage(
    documentMapper, params, Document.class, 1, 10
);
```

#### 统计数量

```java
Long count = commonEsService.count(documentMapper, params, Document.class);
```

### 4.2 RestHighLevelClient 方式

#### 基础查询

```java
// 返回 Map
List<Map> results = esRestClientService.searchAsList("products", params, Map.class);

// 返回自定义类型
List<Product> results = esRestClientService.searchAsList("products", params, Product.class);
```

#### 带分页

```java
// 返回 Map
List<Map> results = esRestClientService.searchAsListWithPage(
    "products", params, 1, 10, Map.class
);

// 返回自定义类型
List<Product> results = esRestClientService.searchAsListWithPage(
    "products", params, 1, 10, Product.class
);
```

#### 带排序和分页

```java
SearchResponse response = esRestClientService.searchWithSortAndPage(
    "products", params, "price", "desc", 1, 10
);
```

#### 查询指定字段（可变参数）

```java
// 指定返回字段
List<Map> results = esRestClientService.searchAsListWithPage(
    "products", params, 1, 10, Map.class, "title", "price", "status"
);

// 不传字段参数，返回所有字段
List<Map> results = esRestClientService.searchAsListWithPage(
    "products", params, 1, 10, Map.class
);
```

#### 排除指定字段

```java
// 排除大字段
List<Map> results = esRestClientService.searchAsListWithExcludeFields(
    "products", params, Map.class, "content", "description"
);
```

#### 统计数量

```java
long count = esRestClientService.count("products", params);
```

---

## 5. API 接口文档

### 5.1 Easy-ES 接口

#### 通用查询

```http
POST /common/query/document
Content-Type: application/json

{
  "title": "我的"
}
```

### 5.2 RestHighLevelClient 接口

#### 通用查询

```http
POST /es/rest/search/products
Content-Type: application/json

{
  "title": "手机",
  "price": ">1000"
}
```

**响应：**
```json
{
  "success": true,
  "total": 100,
  "queryDsl": "{\n  \"match_phrase\": {\n    \"title\": \"手机\"\n  }\n}",
  "data": [...]
}
```

#### 带分页

```http
POST /es/rest/search/products/page?pageNum=1&pageSize=10
Content-Type: application/json

{
  "title": "手机"
}
```

#### 带排序和分页

```http
POST /es/rest/search/products/sort?sortField=price&sortOrder=desc&pageNum=1&pageSize=10
Content-Type: application/json

{
  "title": "手机"
}
```

#### 查询指定字段

```http
POST /es/rest/search/products/fields?pageNum=1&pageSize=10&fields=title&fields=price&fields=status
Content-Type: application/json

{
  "title": "手机"
}
```

#### 排除指定字段

```http
POST /es/rest/search/products/exclude?excludeFields=content&excludeFields=description
Content-Type: application/json

{
  "title": "手机"
}
```

#### 统计数量

```http
POST /es/rest/count/products
Content-Type: application/json

{
  "title": "手机"
}
```

---

## 6. 高级功能

### 6.1 生成 DSL

使用 `EsDslBuilder` 直接生成 ES DSL JSON：

```java
Map<String, String> params = new HashMap<>();
params.put("title", "手机");
params.put("price", "1000~5000");

// 生成查询 DSL
String queryDsl = EsDslBuilder.buildDsl(params);

// 格式化输出
String formattedDsl = EsDslBuilder.formatDsl(queryDsl);
System.out.println(formattedDsl);
```

**输出：**
```json
{
  "bool": {
    "must": [
      {
        "match_phrase": {
          "title": "手机"
        }
      },
      {
        "range": {
          "price": {
            "gte": "1000",
            "lte": "5000"
          }
        }
      }
    ]
  }
}
```

### 6.2 泛型返回

支持返回任意类型：

```java
// 返回 Map
List<Map> maps = esRestClientService.searchAsList("products", params, Map.class);

// 返回自定义类型
List<Product> products = esRestClientService.searchAsList("products", params, Product.class);

// 返回 Object（实际是 Map）
List<Object> objects = esRestClientService.searchAsList("products", params, Object.class);
```

**内部转换机制：**
```java
// 使用 FastJSON 进行 Map 到对象的转换
private <T> T convertMapToObject(Map<String, Object> map, Class<T> clazz) {
    String json = com.alibaba.fastjson.JSON.toJSONString(map);
    return com.alibaba.fastjson.JSON.parseObject(json, clazz);
}
```

### 6.3 可变参数

所有字段相关方法都支持可变参数：

```java
// 指定字段
esRestClientService.searchAsList("products", params, Map.class, "title", "price");

// 不传参数，返回所有字段
esRestClientService.searchAsList("products", params, Map.class);
```


---

## 7. 中文查询专题

### 7.1 问题背景

在使用 ES 进行中文查询时，可能会遇到以下问题：

1. **查询不到数据**：使用 `{"title": "我的"}` 查询不到包含 "我的" 的文档
2. **查询到不相关数据**：查询 "我的" 却匹配到 "我是样电活细保即二。"
3. **多值查询失效**：使用 `{"title": "我的,我是"}` 查询不到数据

### 7.2 解决方案

#### 方案一：默认查询（推荐）⭐

**现在支持智能识别中文，无需添加前缀！**

```java
params.put("title", "我的");  // ✅ 自动使用 match_phrase
```

**查询行为：**
- ✅ 匹配："我的手机"、"这是我的"
- ❌ 不匹配："我是"、"的确"（只包含单个字）

**生成的 DSL：**
```json
{
  "match_phrase": {
    "title": "我的"
  }
}
```

#### 方案二：宽松分词查询（可选）

如果需要更宽松的匹配，使用 `*` 前缀：

```java
params.put("title", "*我的 包养");  // 使用 match (OR)
```

**查询行为：**
- ✅ 匹配："我的手机"（包含 "我" 和 "的"）
- ✅ 匹配："我是"（包含 "我"）
- ✅ 匹配："包养费"（包含 "包养"）

**注意：** 这种查询会返回很多结果，因为是 OR 关系。

#### 方案三：中文多值查询

使用逗号分隔多个关键词：

```java
params.put("title", "我的,我是,手机");  // OR 短语匹配
```

**生成的 DSL：**
```json
{
  "bool": {
    "should": [
      {"match_phrase": {"title": "我的"}},
      {"match_phrase": {"title": "我是"}},
      {"match_phrase": {"title": "手机"}}
    ]
  }
}
```

### 7.3 中文分词器配置

确保 ES 配置了中文分词器（推荐 IK 分词器）：

**安装 IK 分词器：**
```bash
cd elasticsearch-8.x.x
./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v8.x.x/elasticsearch-analysis-ik-8.x.x.zip
```

**配置索引：**
```json
{
  "settings": {
    "analysis": {
      "analyzer": {
        "default": {
          "type": "ik_max_word"
        },
        "default_search": {
          "type": "ik_smart"
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "title": {
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart"
      }
    }
  }
}
```

### 7.4 查询对比

| 查询方式 | 查询词 | 匹配 "我的手机" | 匹配 "我是" | 匹配 "的确" | 推荐场景 |
|---------|--------|--------------|-----------|-----------|---------|
| 默认查询 | `"我的"` | ✅ | ❌ | ❌ | **推荐，精确度高** |
| 宽松分词 | `"*我的"` | ✅ | ✅ | ✅ | 需要更多结果 |
| 多值查询 | `"我的,我是"` | ✅ | ✅ | ❌ | 多个关键词 OR |

---

## 8. 最佳实践

### 8.1 中文查询

**✅ 推荐：** 直接使用，系统自动识别

```java
params.put("title", "我的");  // 自动使用 match_phrase
```

**❌ 不推荐：** 使用宽松分词可能返回太多不相关结果

```java
params.put("title", "*我的");  // 会匹配 "我是"、"的确" 等
```

### 8.2 多值查询

**✅ 推荐：** 使用逗号分隔

```java
params.put("title", "我的,我是,手机");  // OR 短语匹配
```

**❌ 不推荐：** 使用空格分隔

```java
params.put("title", "我的 我是 手机");  // 会被当作普通查询
```

### 8.3 性能优化

#### 使用分页

```java
// ✅ 推荐
esRestClientService.searchAsListWithPage("products", params, 1, 10, Map.class);

// ❌ 不推荐（可能返回大量数据）
esRestClientService.searchAsList("products", params, Map.class);
```

#### 使用精确查询

```java
// ✅ 推荐（更快）
params.put("status", "=1");

// ❌ 不推荐（较慢）
params.put("status", "1");
```

#### 指定返回字段

```java
// ✅ 推荐（减少数据传输）
esRestClientService.searchAsList("products", params, Map.class, "title", "price");

// ❌ 不推荐（返回所有字段，包括大字段）
esRestClientService.searchAsList("products", params, Map.class);
```

#### 排除大字段

```java
// ✅ 推荐（排除 content 和 description 等大字段）
esRestClientService.searchAsListWithExcludeFields(
    "products", params, Map.class, "content", "description"
);
```

### 8.4 日志记录

系统会自动记录查询信息：

```
[EsQueryBuilder] 默认查询(中文-matchPhrase): title matchPhrase 我的
[EsQueryBuilder] 多值查询(中文-OR-matchPhrase): title in [我的, 我是]
Query fields: title, price, status
Query all fields
```


---

## 9. 常见问题

### 9.1 中文查询不到数据

**问题：** 使用 `{"title": "我的"}` 查询不到数据

**可能原因：**
1. ES 索引没有配置中文分词器
2. 字段类型不是 `text`
3. 查询词和文档内容不匹配

**解决方案：**
1. 配置 IK 分词器（见 7.3 节）
2. 确保字段类型为 `text`
3. 使用默认查询（系统已自动处理）

### 9.2 查询返回太多不相关数据

**问题：** 查询 "我的" 返回了包含 "我" 或 "的" 的所有文档

**原因：** 使用了宽松分词查询（`*` 前缀）

**解决方案：** 使用默认查询（短语匹配）

```java
// ✅ 推荐（短语匹配，更精确）
params.put("title", "我的");

// ❌ 不推荐（分词匹配，返回更多结果）
params.put("title", "*我的");
```

### 9.3 多值查询不生效

**问题：** 使用 `{"title": "我的 我是"}` 查询不到预期结果

**原因：** 空格不会被识别为分隔符

**解决方案：** 使用逗号分隔

```java
// ✅ 正确
params.put("title", "我的,我是");

// ❌ 错误
params.put("title", "我的 我是");
```

### 9.4 如何调试查询

**方法 1：** 查看日志

```
[EsQueryBuilder] 默认查询(中文-matchPhrase): title matchPhrase 我的
```

**方法 2：** 使用 API 接口查看 DSL

```http
POST /es/rest/search/products
Content-Type: application/json

{
  "title": "我的"
}
```

响应中会包含生成的 DSL：

```json
{
  "queryDsl": "{\n  \"match_phrase\": {\n    \"title\": \"我的\"\n  }\n}"
}
```

**方法 3：** 使用 EsDslBuilder 生成 DSL

```java
String dsl = EsDslBuilder.buildDsl(params);
System.out.println(EsDslBuilder.formatDsl(dsl));
```

---

## 10. 方案对比与选择

### 10.1 核心区别

| 特性 | EsQueryBuilder | EsDslBuilder |
|------|---------------|--------------|
| **依赖** | Easy-ES Wrapper | 无依赖（仅 FastJSON） |
| **输出** | `LambdaEsQueryWrapper<T>` | DSL JSON 字符串 |
| **使用方式** | 配合 Easy-ES Mapper | 可直接发送到 ES |
| **类型安全** | 有（泛型） | 无（字符串） |
| **调试** | 需要执行查询 | 直接生成 DSL |
| **灵活性** | 中等 | 高 |
| **学习成本** | 需要了解 Easy-ES | 只需了解 ES DSL |

### 10.2 选择建议

#### 选择 Easy-ES 当：

- ✅ 项目已使用 Easy-ES 框架
- ✅ 需要类型安全
- ✅ 需要自动映射实体
- ✅ 不需要查看原始 DSL

#### 选择 RestHighLevelClient 当：

- ✅ 需要直接发送到 ES
- ✅ 需要调试和查看 DSL
- ✅ 需要在 API 中返回 DSL
- ✅ 需要记录查询日志
- ✅ 需要灵活的泛型返回
- ✅ 不使用 Easy-ES

#### 同时使用两者

可以在同一个项目中同时使用：

```java
// 使用 Easy-ES 进行查询
List<Document> results = commonEsService.query(documentMapper, params, Document.class);

// 使用 EsDslBuilder 记录日志
String dsl = EsDslBuilder.buildDsl(params);
log.info("ES Query: {}", EsDslBuilder.formatDsl(dsl));
```

### 10.3 完整示例

```java
@Service
public class ProductSearchService {
    
    @Autowired
    private EsRestClientService esRestClientService;
    
    public Map<String, Object> searchProducts(String keyword, int page, int size) {
        try {
            // 构建查询参数
            Map<String, String> params = new HashMap<>();
            params.put("title", keyword);
            params.put("status", "1,2");
            params.put("price", ">0");
            
            // 执行查询（返回自定义类型）
            List<Product> data = esRestClientService.searchAsListWithPage(
                "products", params, page, size, Product.class, "title", "price", "status"
            );
            
            // 统计总数
            long total = esRestClientService.count("products", params);
            
            // 返回结果
            return Map.of(
                "success", true,
                "total", total,
                "page", page,
                "size", size,
                "data", data
            );
        } catch (Exception e) {
            return Map.of(
                "success", false,
                "error", e.getMessage()
            );
        }
    }
}
```

---

## 附录

### A. 查询规则总结

1. **默认查询**：中文用短语匹配，英文用通配符
2. **多值查询**：使用逗号分隔，不使用空格
3. **范围查询**：使用 `~` 分隔，支持时间和数值
4. **比较查询**：使用 `>`, `<`, `>=`, `<=` 前缀
5. **精确查询**：使用 `=` 前缀
6. **宽松分词**：使用 `*` 前缀
7. **通配符**：使用 `+` 前缀

### B. 性能优化总结

1. **使用分页**：避免一次性返回大量数据
2. **指定字段**：减少数据传输量
3. **排除大字段**：排除 content、description 等大字段
4. **使用精确查询**：对于已知精确值使用 `=` 前缀
5. **合理使用索引**：确保查询字段有索引

### C. 参考文档

- Easy-ES 官方文档：https://www.easy-es.cn/
- Elasticsearch 官方文档：https://www.elastic.co/guide/
- IK 分词器：https://github.com/medcl/elasticsearch-analysis-ik
- FastJSON 文档：https://github.com/alibaba/fastjson

---

**🎉 现在你已经掌握了 ES 查询工具的所有功能！**

如有问题，请查看日志或使用调试接口排查。
