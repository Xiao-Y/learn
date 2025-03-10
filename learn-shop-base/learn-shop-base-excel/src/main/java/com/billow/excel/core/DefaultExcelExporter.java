package com.billow.excel.core;

import com.billow.excel.annotation.ExcelColumn;
import com.billow.excel.annotation.ExcelSheet;
import com.billow.excel.model.ExcelTask;
import com.billow.excel.service.DictService;
import com.billow.excel.service.ExcelTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 默认Excel导出实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultExcelExporter implements ExcelExporter {

    private final DictService dictService;
    private final ExcelTaskService taskService;
    private static final int MAX_DROPDOWN_LENGTH = 50;
    private static final String HIDDEN_SHEET_PREFIX = "dict_";

    @Override
    public <T> void exportToFile(List<T> dataList, String filePath) {
        try (Workbook workbook = createWorkbook(dataList);
             FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (IOException e) {
            log.error("导出Excel到文件失败", e);
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    @Override
    public <T> void exportToResponse(List<T> dataList, HttpServletResponse response) {
        try (Workbook workbook = createWorkbook(dataList)) {
            String fileName = getFileName(dataList);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error("导出Excel到响应流失败", e);
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    @Override
    public <T> byte[] exportToBytes(List<T> dataList) {
        try (Workbook workbook = createWorkbook(dataList);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            log.error("导出Excel到字节数组失败", e);
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    @Override
    @Async
    public <T> Future<String> exportAsync(List<T> dataList, String taskId) {
        // 创建任务记录
        ExcelTask task = new ExcelTask()
                .setTaskId(taskId)
                .setType(ExcelTask.TaskType.EXPORT)
                .setFileName(getFileName(dataList))
                .setStatus(ExcelTask.TaskStatus.PROCESSING)
                .setTotal(dataList.size())
                .setSuccessCount(0)
                .setErrorCount(0);
        taskService.createTask(task);

        return CompletableFuture.supplyAsync(() -> {
            try {
//                String filePath = System.getProperty("java.io.tmpdir") + "/" + taskId + ".xlsx";
                String filePath = "D:/" + taskId + ".xlsx";
                exportToFile(dataList, filePath);

                // 更新任务状态为成功
                task.setStatus(ExcelTask.TaskStatus.SUCCESS)
                        .setSuccessCount(dataList.size())
                        .setFilePath(filePath);
                taskService.updateTask(task);

                return filePath;
            } catch (Exception e) {
                log.error("异步导出Excel失败", e);

                // 更新任务状态为失败
                task.setStatus(ExcelTask.TaskStatus.FAILED)
                        .setErrorCount(dataList.size())
                        .setErrorMessage(e.getMessage());
                taskService.updateTask(task);

                throw new RuntimeException("异步导出Excel失败", e);
            }
        });
    }

    @Override
    public <T> void exportTemplate(Class<T> clazz, HttpServletResponse response) {
        try (Workbook workbook = createTemplateWorkbook(clazz)) {
            String fileName = getTemplateFileName(clazz);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error("导出Excel模板失败", e);
            throw new RuntimeException("导出Excel模板失败", e);
        }
    }

    private <T> Workbook createWorkbook(List<T> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("数据列表不能为空");
        }

        // 使用SXSSF处理大数据量
        SXSSFWorkbook workbook = new SXSSFWorkbook(500);
        workbook.setCompressTempFiles(true);  // 压缩临时文件

        Class<?> clazz = dataList.get(0).getClass();
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        String sheetName = sheetAnno != null ? sheetAnno.name() : clazz.getSimpleName();

        Sheet sheet = workbook.createSheet(sheetName);
        if (sheetAnno != null && sheetAnno.frozen()) {
            sheet.createFreezePane(0, 1);
        }

        // 创建表头
        List<Field> fields = getExcelFields(clazz);
        createHeader(workbook, sheet, fields);

        // 填充数据
        fillData(sheet, dataList, fields);

        // 设置列宽
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            // 如果指定了列宽，使用指定的宽度
            if (column.width() > 0) {
                sheet.setColumnWidth(i, column.width() * 256);
            } else {
                // 否则设置一个默认宽度
                sheet.setColumnWidth(i, 15 * 256);
            }
        }

        return workbook;
    }

    private List<Field> getExcelFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
                .sorted(Comparator.comparingInt(field ->
                        field.getAnnotation(ExcelColumn.class).order()))
                .collect(Collectors.toList());
    }

    private void createHeader(Workbook workbook, Sheet sheet, List<Field> fields) {
        Row headerRow = sheet.createRow(0);
        CellStyle normalHeaderStyle = createHeaderStyle(workbook, false);
        CellStyle requiredHeaderStyle = createHeaderStyle(workbook, true);
        Map<String, Sheet> dictSheets = new HashMap<>();

        for (int i = 0; i < fields.size(); i++) {
            Cell cell = headerRow.createCell(i);
            Field field = fields.get(i);
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            cell.setCellValue(column.name());
            // 根据是否必填设置不同的样式
            cell.setCellStyle(column.required() ? requiredHeaderStyle : normalHeaderStyle);

            // 设置下拉列表
            if (!column.dictCode().isEmpty()) {
                List<String> options = dictService.getDictLabels(column.dictCode());
                if (!options.isEmpty()) {
                    if (options.size() <= MAX_DROPDOWN_LENGTH) {
                        // 如果选项数量较少，使用普通下拉列表
                        addSimpleDropDownList(sheet, options, i, 1, 65535);
                    } else {
                        // 如果选项数量较多，使用隐藏sheet存储数据
                        addDropDownListWithHiddenSheet(workbook, sheet, dictSheets,
                                column.dictCode(), options, i, 1, 65535);
                    }
                }
            }
        }
    }

    private void addSimpleDropDownList(Sheet sheet, List<String> options,
                                       int columnIndex, int firstRow, int lastRow) {
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, columnIndex, columnIndex);

        DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(
                options.toArray(new String[0]));

        DataValidation validation = validationHelper.createValidation(constraint, addressList);
        validation.setShowErrorBox(true);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("错误", "请从下拉列表中选择值");
        validation.setShowPromptBox(true);
        validation.createPromptBox("提示", "请从下拉列表中选择值");

        sheet.addValidationData(validation);
    }

    private void addDropDownListWithHiddenSheet(Workbook workbook, Sheet mainSheet,
                                                Map<String, Sheet> dictSheets, String dictCode, List<String> options,
                                                int columnIndex, int firstRow, int lastRow) {
        // 获取或创建隐藏的数据sheet
        Sheet hiddenSheet = dictSheets.computeIfAbsent(dictCode, k -> {
            Sheet sheet = workbook.createSheet(HIDDEN_SHEET_PREFIX + k);
            workbook.setSheetOrder(sheet.getSheetName(), workbook.getNumberOfSheets() - 1);
            workbook.setSheetHidden(workbook.getSheetIndex(sheet), true);
            return sheet;
        });

        // 在隐藏sheet中写入选项数据
        for (int i = 0; i < options.size(); i++) {
            Row row = hiddenSheet.getRow(i);
            if (row == null) {
                row = hiddenSheet.createRow(i);
            }
            row.createCell(0).setCellValue(options.get(i));
        }

        // 创建名称管理器
        String rangeName = "dict_" + dictCode;
        workbook.createName().setNameName(rangeName);
        String formula = hiddenSheet.getSheetName() + "!$A$1:$A$" + options.size();
        workbook.getName(rangeName).setRefersToFormula(formula);

        // 设置数据有效性
        DataValidationHelper validationHelper = mainSheet.getDataValidationHelper();
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, columnIndex, columnIndex);
        DataValidationConstraint constraint = validationHelper.createFormulaListConstraint(rangeName);

        DataValidation validation = validationHelper.createValidation(constraint, addressList);
        validation.setShowErrorBox(true);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("错误", "请从下拉列表中选择值");
        validation.setShowPromptBox(true);
        validation.createPromptBox("提示", "请从下拉列表中选择值");

        mainSheet.addValidationData(validation);
    }

    private CellStyle createHeaderStyle(Workbook workbook, boolean required) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);

        Font font = workbook.createFont();
        font.setBold(true);
        if (required) {
            font.setColor(IndexedColors.RED.getIndex());
        }
        style.setFont(font);

        return style;
    }

    private <T> void fillData(Sheet sheet, List<T> dataList, List<Field> fields) {
        // 创建日期和数字格式化的样式
        Map<String, CellStyle> formatStyleMap = new HashMap<>();

        for (int i = 0; i < dataList.size(); i++) {
            Row row = sheet.createRow(i + 1);
            T data = dataList.get(i);

            for (int j = 0; j < fields.size(); j++) {
                Cell cell = row.createCell(j);
                Field field = fields.get(j);
                field.setAccessible(true);

                try {
                    Object value = field.get(data);
                    if (value != null) {
                        ExcelColumn column = field.getAnnotation(ExcelColumn.class);

                        // 1. 首先检查是否需要字典转换
                        if (!column.dictCode().isEmpty()) {
                            String dictValue = value.toString();
                            String label = dictService.getLabelByValue(column.dictCode(), dictValue);
                            cell.setCellValue(label);
                            continue;
                        }

                        // 2. 处理日期类型
                        if (value instanceof Date) {
                            String format = column.format().isEmpty() ? "yyyy-MM-dd HH:mm:ss" : column.format();
                            CellStyle style = formatStyleMap.computeIfAbsent(format, k -> {
                                CellStyle dateStyle = sheet.getWorkbook().createCellStyle();
                                CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
                                dateStyle.setDataFormat(createHelper.createDataFormat().getFormat(format));
                                return dateStyle;
                            });
                            cell.setCellStyle(style);
                            cell.setCellValue((Date) value);
                            continue;
                        }

                        // 3. 处理数字类型
                        if (value instanceof Number && !column.format().isEmpty()) {
                            CellStyle style = formatStyleMap.computeIfAbsent(column.format(), k -> {
                                CellStyle numberStyle = sheet.getWorkbook().createCellStyle();
                                CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
                                numberStyle.setDataFormat(createHelper.createDataFormat().getFormat(column.format()));
                                return numberStyle;
                            });
                            cell.setCellStyle(style);
                            if (value instanceof Double) {
                                cell.setCellValue((Double) value);
                            } else if (value instanceof Integer) {
                                cell.setCellValue((Integer) value);
                            } else if (value instanceof Long) {
                                cell.setCellValue((Long) value);
                            }
                            continue;
                        }

                        // 4. 其他类型直接转字符串
                        cell.setCellValue(value.toString());
                    }
                } catch (IllegalAccessException e) {
                    log.error("获取字段值失败", e);
                }
            }
        }
    }

    private <T> String getFileName(List<T> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return "export.xlsx";
        }

        Class<?> clazz = dataList.get(0).getClass();
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        String fileName = sheetAnno != null ? sheetAnno.name() : clazz.getSimpleName();
        return fileName + "_" + System.currentTimeMillis() + ".xlsx";
    }

    private <T> Workbook createTemplateWorkbook(Class<T> clazz) {
        // 使用SXSSF处理大数据量
        SXSSFWorkbook workbook = new SXSSFWorkbook(500);
        workbook.setCompressTempFiles(true);  // 压缩临时文件

        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        String sheetName = sheetAnno != null ? sheetAnno.name() : clazz.getSimpleName();

        Sheet sheet = workbook.createSheet(sheetName);
        if (sheetAnno != null && sheetAnno.frozen()) {
            sheet.createFreezePane(0, 1);
        }

        // 创建表头
        List<Field> fields = getExcelFields(clazz);
        createHeader(workbook, sheet, fields);

        // 添加示例数据行（可选）
        addExampleRow(sheet, fields);

        // 添加批注说明
        addComments(sheet, fields);

        // 设置列宽
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            // 如果指定了列宽，使用指定的宽度
            if (column.width() > 0) {
                sheet.setColumnWidth(i, column.width() * 256);
            } else {
                // 否则设置一个默认宽度
                sheet.setColumnWidth(i, 15 * 256);
            }
        }

        return workbook;
    }

    private void addExampleRow(Sheet sheet, List<Field> fields) {
        Row row = sheet.createRow(1);
        CellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 0; i < fields.size(); i++) {
            Cell cell = row.createCell(i);
            ExcelColumn column = fields.get(i).getAnnotation(ExcelColumn.class);

            // 设置示例值
            String exampleValue = getExampleValue(fields.get(i), column);
            cell.setCellValue(exampleValue);
            cell.setCellStyle(style);
        }
    }

    private String getExampleValue(Field field, ExcelColumn column) {
        if (!column.dictCode().isEmpty()) {
            List<String> options = dictService.getDictLabels(column.dictCode());
            return options.isEmpty() ? "示例值" : options.get(0);
        }

        Class<?> type = field.getType();
        if (type == String.class) {
            return "示例文本";
        } else if (type == Integer.class || type == int.class) {
            return "0";
        } else if (type == Long.class || type == long.class) {
            return "0";
        } else if (type == Double.class || type == double.class) {
            return "0.00";
        } else if (type == Boolean.class || type == boolean.class) {
            return "true";
        } else if (type == Date.class) {
            return "2024-01-01";
        }

        return "示例值";
    }

    private void addComments(Sheet sheet, List<Field> fields) {
        Drawing<?> drawing = sheet.createDrawingPatriarch();

        for (int i = 0; i < fields.size(); i++) {
            Cell cell = sheet.getRow(0).getCell(i);
            ExcelColumn column = fields.get(i).getAnnotation(ExcelColumn.class);

            // 创建批注
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0,
                    i, 0, i + 3, 4);
            Comment comment = drawing.createCellComment(anchor);
            RichTextString text = sheet.getWorkbook().getCreationHelper()
                    .createRichTextString(createCommentText(fields.get(i), column));
            comment.setString(text);

            cell.setCellComment(comment);
        }
    }

    private String createCommentText(Field field, ExcelColumn column) {
        StringBuilder sb = new StringBuilder();
        sb.append("字段说明：\n");

        // 添加必填说明
        if (column.required()) {
            sb.append("※ 必填项\n");
        }

        // 添加字典说明
        if (!column.dictCode().isEmpty()) {
            sb.append("可选值：\n");
            List<String> options = dictService.getDictLabels(column.dictCode());
            sb.append(String.join("、", options)).append("\n");
        }

        // 添加格式说明
        if (!column.format().isEmpty()) {
            sb.append("格式：").append(column.format()).append("\n");
        }

        // 添加类型说明
        sb.append("类型：").append(getTypeDescription(field.getType()));

        return sb.toString();
    }

    private String getTypeDescription(Class<?> type) {
        if (type == String.class) {
            return "文本";
        } else if (type == Integer.class || type == int.class) {
            return "整数";
        } else if (type == Long.class || type == long.class) {
            return "长整数";
        } else if (type == Double.class || type == double.class) {
            return "小数";
        } else if (type == Boolean.class || type == boolean.class) {
            return "是/否";
        } else if (type == Date.class) {
            return "日期";
        }
        return "文本";
    }

    private <T> String getTemplateFileName(Class<T> clazz) {
        ExcelSheet sheetAnno = clazz.getAnnotation(ExcelSheet.class);
        String fileName = sheetAnno != null ? sheetAnno.name() : clazz.getSimpleName();
        return fileName + "_导入模板_" + System.currentTimeMillis() + ".xlsx";
    }
} 