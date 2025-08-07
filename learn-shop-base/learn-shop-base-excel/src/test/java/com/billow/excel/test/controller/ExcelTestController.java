//package com.billow.excel.test.controller;
//
//import com.billow.excel.core.ExcelExporter;
//import com.billow.excel.core.ExcelImporter;
//import com.billow.excel.model.ExcelTask;
//import com.billow.excel.model.ImportResult;
//import com.billow.excel.service.DictService;
//import com.billow.excel.service.ExcelTaskService;
//import com.billow.excel.test.model.UserExcelModel;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.*;
//import java.util.concurrent.Future;
//
///**
// * Excel测试控制器
// */
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/excel/test")
//@Api(tags = "Excel测试接口")
//public class ExcelTestController {
//
//    private final ExcelExporter excelExporter;
//    private final ExcelImporter excelImporter;
//    private final DictService dictService;
//    private final ExcelTaskService taskService;
//
//    @ApiOperation("导出用户数据")
//    @GetMapping("/export")
//    public void exportUsers(HttpServletResponse response) {
//        List<UserExcelModel> dataList = generateTestData();
//        excelExporter.exportToResponse(dataList, response);
//    }
//
//    @ApiOperation("异步导出用户数据")
//    @GetMapping("/export/async")
//    public String exportUsersAsync() {
//        List<UserExcelModel> dataList = generateTestData();
//        String taskId = UUID.randomUUID().toString();
//        excelExporter.exportAsync(dataList, taskId);
//        return taskId;
//    }
//
//    @ApiOperation("导出用户数据模板")
//    @GetMapping("/template")
//    public void exportTemplate(HttpServletResponse response) {
//        excelExporter.exportTemplate(UserExcelModel.class, response);
//    }
//
//    @ApiOperation("导入用户数据")
//    @PostMapping("/import")
//    public List<UserExcelModel> importUsers(@RequestParam("file") MultipartFile file) {
//        return excelImporter.importFromMultipartFile(file, UserExcelModel.class);
//    }
//
//    @ApiOperation("异步导入用户数据")
//    @PostMapping("/import/async")
//    public String importUsersAsync(@RequestParam("file") MultipartFile file) {
//        Future<ImportResult<UserExcelModel>> future = excelImporter.importAsync(file, UserExcelModel.class);
//        return "导入任务已提交";
//    }
//
//    @ApiOperation("获取任务信息")
//    @GetMapping("/task/{taskId}")
//    public ExcelTask getTask(@PathVariable String taskId) {
//        return taskService.getTask(taskId);
//    }
//
//    @ApiOperation("获取任务列表")
//    @GetMapping("/tasks")
//    public List<ExcelTask> getTasks(
//            @RequestParam(required = false) ExcelTask.TaskType type,
//            @RequestParam(required = false) ExcelTask.TaskStatus status) {
//        return taskService.listTasks(type, status);
//    }
//
//    @ApiOperation("删除任务")
//    @DeleteMapping("/task/{taskId}")
//    public void deleteTask(@PathVariable String taskId) {
//        taskService.deleteTask(taskId);
//    }
//
//    @ApiOperation("获取性别字典")
//    @GetMapping("/dict/gender")
//    public Map<String, String> getGenderDict() {
//        return dictService.getDictMap("USER_GENDER");
//    }
//
//    @ApiOperation("获取状态字典")
//    @GetMapping("/dict/status")
//    public Map<String, String> getStatusDict() {
//        return dictService.getDictMap("USER_STATUS");
//    }
//
//    /**
//     * 生成测试数据
//     */
//    private List<UserExcelModel> generateTestData() {
//        List<UserExcelModel> dataList = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            UserExcelModel user = new UserExcelModel()
//                    .setUserId((long) i)
//                    .setUsername("user" + i)
//                    .setNickname("用户" + i)
//                    .setGender(i % 3 == 0 ? "0" : i % 2 == 0 ? "2" : "1")
//                    .setStatus("1")
//                    .setAge(20 + i)
//                    .setEmail("user" + i + "@example.com")
//                    .setMobile("1380000" + String.format("%04d", i))
//                    .setCreateTime(new Date())
//                    .setUpdateTime(new Date());
//            dataList.add(user);
//        }
//        return dataList;
//    }
//}