# learn-shop-base-excel

Excel导入导出工具包，支持同步/异步导入导出、数据字典转换、数据校验等功能。

## 功能特性

- 支持同步/异步Excel导入导出
- 支持数据字典转换（枚举字典、Redis字典、数据库字典）
- 支持数据格式化和校验
- 支持导入导出进度跟踪
- 支持自定义样式
- 支持模板导出

## 快速开始

### 1. 添加依赖

```xml
<dependency>
    <groupId>com.billow</groupId>
    <artifactId>learn-shop-base-excel</artifactId>
    <version>3.0-SNAPSHOT</version>
</dependency>
```

### 2. 定义Excel模型

```java
@Data
@Accessors(chain = true)
@ExcelSheet(name = "用户信息")
public class UserExcelModel {
    @ExcelColumn(name = "用户ID", order = 0)
    private Long userId;

    @ExcelColumn(name = "用户名", order = 1, required = true)
    private String username;

    @ExcelColumn(name = "性别", order = 2, dictCode = "USER_GENDER")
    private String gender;

    @ExcelColumn(name = "创建时间", order = 3, format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
```

### 3. 配置数据字典（可选）

#### 3.1 枚举字典

```java
public enum UserGenderEnum implements DictEnum {
    MALE("1", "男"),
    FEMALE("2", "女"),
    UNKNOWN("0", "未知");

    private final String value;
    private final String label;

    // 实现 getValue() 和 getLabel() 方法
}
```

#### 3.2 配置扫描路径

```yaml
learn:
  excel:
    dict:
      enable-enum-scan: true
      enum-scan-packages:
        - "com.billow.excel.test.enums"
```

### 4. 使用示例

#### 4.1 导出Excel

```java
@RestController
@RequiredArgsConstructor
public class ExcelController {
    private final ExcelExporter excelExporter;

    // 同步导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        List<UserExcelModel> dataList = getUserList();
        excelExporter.exportToResponse(dataList, response);
    }

    // 异步导出
    @GetMapping("/export/async")
    public String exportAsync() {
        List<UserExcelModel> dataList = getUserList();
        String taskId = UUID.randomUUID().toString();
        excelExporter.exportAsync(dataList, taskId);
        return taskId;
    }

    // 导出模板
    @GetMapping("/template")
    public void exportTemplate(HttpServletResponse response) {
        excelExporter.exportTemplate(UserExcelModel.class, response);
    }
}
```

#### 4.2 导入Excel

```java
@RestController
@RequiredArgsConstructor
public class ExcelController {
    private final ExcelImporter excelImporter;

    // 同步导入
    @PostMapping("/import")
    public List<UserExcelModel> importExcel(@RequestParam("file") MultipartFile file) {
        return excelImporter.importFromMultipartFile(file, UserExcelModel.class);
    }

    // 异步导入
    @PostMapping("/import/async")
    public String importAsync(@RequestParam("file") MultipartFile file) {
        Future<ImportResult<UserExcelModel>> future = 
            excelImporter.importAsync(file, UserExcelModel.class);
        return "导入任务已提交";
    }
}
```

#### 4.3 任务管理

```java
@RestController
@RequiredArgsConstructor
public class ExcelController {
    private final ExcelTaskService taskService;

    // 获取任务信息
    @GetMapping("/task/{taskId}")
    public ExcelTask getTask(@PathVariable String taskId) {
        return taskService.getTask(taskId);
    }

    // 获取任务列表
    @GetMapping("/tasks")
    public List<ExcelTask> getTasks(
            @RequestParam(required = false) ExcelTask.TaskType type,
            @RequestParam(required = false) ExcelTask.TaskStatus status) {
        return taskService.listTasks(type, status);
    }
}
```

## 注解说明

### @ExcelSheet

用于标注Excel模型类，指定sheet名称等信息。

| 属性 | 类型 | 说明 | 默认值 |
|------|------|------|--------|
| name | String | sheet名称 | "" |

### @ExcelColumn

用于标注Excel列属性。

| 属性 | 类型 | 说明 | 默认值 |
|------|------|------|--------|
| name | String | 列名称 | "" |
| order | int | 列顺序 | 0 |
| required | boolean | 是否必填 | false |
| dictCode | String | 字典编码 | "" |
| format | String | 日期格式 | "" |

## 高级配置

```yaml
learn:
  excel:
    # 字典配置
    dict:
      redis-key-prefix: "DICT:" # Redis字典前缀
      enable-enum-scan: true # 启用枚举扫描
      enum-scan-packages: # 枚举扫描包路径
        - "com.billow.excel.test.enums"
    
    # 导入配置
    import:
      batch-size: 1000 # 批量处理大小
      thread-pool:
        core-size: 5
        max-size: 10
        queue-capacity: 200
    
    # 导出配置
    export:
      temp-dir: "/temp/excel" # 临时文件目录
```

## 常见问题

1. 导入失败如何处理？
   - 检查Excel文件格式是否正确
   - 检查必填字段是否填写
   - 检查数据字典配置是否正确

2. 异步任务状态说明
   - PENDING: 待处理
   - PROCESSING: 处理中
   - COMPLETED: 已完成
   - FAILED: 失败

3. 如何自定义样式？
   - 实现`ExcelStyleProvider`接口
   - 在配置中注册样式提供者

## 更多信息

- [API文档](http://your-domain/swagger-ui/)
- [示例代码](src/test/java/com/billow/excel/test)
- [问题反馈](https://github.com/your-repo/issues) 