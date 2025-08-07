package com.billow.system.service;

import com.billow.system.pojo.excel.MenuExcel;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExcelService {

    /**
     * 异步导出
     *
     * @author 千面
     */
    String asyncExport(HttpServletResponse response);

    List<MenuExcel> importFile(HttpServletResponse response, MultipartFile file);

    /**
     * 异步分页导出（游标）
     *
     * @param response
     * @return
     */
    String exportToFileWithRange(HttpServletResponse response);
}
