package com.billow.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.billow.excel.excelKet.ExcelKit;
import com.billow.excel.model.ExportPageResult;
import com.billow.system.pojo.excel.MenuExcel;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.service.ExcelService;
import com.billow.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private MenuService menuService;

    @Override
    public String asyncExport(HttpServletResponse response) {
        List<MenuPo> list = menuService.list();
        String taskId = ExcelKit.getInstance().exportAsync(MenuExcel.class, BeanUtil.copyToList(list, MenuExcel.class));
        System.out.println(taskId);
        return taskId;
    }

    @Override
    public List<MenuExcel> importFile(HttpServletResponse response, MultipartFile file) {
        return ExcelKit.getInstance().importFromMultipartFile(file, MenuExcel.class);
    }

    @Override
    public String exportToFileWithRange(HttpServletResponse response) {
        String taskId = ExcelKit.getInstance().
                exportToFileWithRange(MenuExcel.class, 2, 0L, (queryParam, pageSize) -> {
                    List<MenuPo> list = menuService.lambdaQuery()
                            .gt(MenuPo::getId, queryParam)
                            .last("limit " + pageSize)
                            .orderByAsc(MenuPo::getId)
                            .list();
                    Long lastId = Optional.ofNullable(CollUtil.getLast(list))
                            .map(MenuPo::getId).orElse(0L);
                    return new ExportPageResult<>(lastId, BeanUtil.copyToList(list, MenuExcel.class));
                });
        return taskId;
    }
}
