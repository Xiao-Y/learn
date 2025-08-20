package com.billow.system.api;

import com.billow.system.pojo.excel.MenuExcel;
import com.billow.system.service.ExcelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/excelApi")
@Tag(name = "ExcelApi", description = "excelApi")
public class ExcelApi {

    @Autowired
    private ExcelService excelService;

    @Operation(summary = "异步导出")
    @GetMapping("/export")
    public String asyncExport(HttpServletResponse response) {
        return excelService.asyncExport(response);
    }


    @Operation(summary = "异步分页导出（游标）")
    @GetMapping("/exportToFileWithRange")
    public String exportToFileWithRange(HttpServletResponse response) {
        log.info("开始异步分页导出");
        return excelService.exportToFileWithRange(response);
    }

    @Operation(summary = "导入")
    @PostMapping("/import")
    public List<MenuExcel> importFile(HttpServletResponse response,
                                      @RequestParam(value = "file") MultipartFile file) {
        return excelService.importFile(response, file);
    }
}
