package com.billow.excel.excelKet;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.billow.excel.annotation.ExcelColumn;
import com.billow.excel.annotation.ExcelDict;
import com.billow.excel.annotation.ExcelSheet;
import com.billow.excel.converter.ExcelCover;
import com.billow.excel.model.ExcelColumnModel;
import com.billow.excel.service.DictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

/**
 * ExcelCore
 *
 * @author 千面
 * @date 2025/6/11 18:24
 */
@Slf4j
@RequiredArgsConstructor
public class ExcelCore {

    private static final int MAX_DROPDOWN_LENGTH = 50;
    private static final String HIDDEN_SHEET_PREFIX = "dict_";

    private final DictService dictService;

    /**
     * 从文件导入
     *
     * @param filePath 文件路径
     * @param clazz    目标类型
     * @param <T>      数据类型
     * @return 导入的数据列表
     */
    public <T> List<T> importFromFile(String filePath, Class<T> clazz) {
        try (InputStream is = Files.newInputStream(new File(filePath).toPath())) {
            return importFromStream(is, clazz);
        } catch (IOException e) {
            log.error("从文件导入Excel失败", e);
            throw new RuntimeException("导入Excel失败", e);
        }
    }

    /**
     * 从输入流导入
     *
     * @param inputStream 输入流
     * @param clazz       目标类型
     * @param <T>         数据类型
     * @return 导入的数据列表
     */
    public <T> List<T> importFromStream(InputStream inputStream, Class<T> clazz) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            return this.processWorkbook(workbook, clazz);
        } catch (IOException e) {
            log.error("从输入流导入Excel失败", e);
            throw new RuntimeException("导入Excel失败", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从MultipartFile导入
     *
     * @param file  上传的文件
     * @param clazz 目标类型
     * @param <T>   数据类型
     * @return 导入的数据列表
     */
    public <T> List<T> importFromMultipartFile(MultipartFile file, Class<T> clazz) {
        try (InputStream is = file.getInputStream()) {
            return importFromStream(is, clazz);
        } catch (IOException e) {
            log.error("从MultipartFile导入Excel失败", e);
            throw new RuntimeException("导入Excel失败", e);
        }
    }

    /**
     * 导出到文件
     *
     * @param dataList 数据列表
     * @param filePath 文件路径
     * @param <T>      数据类型
     */
    public <T> void exportToFile(List<T> dataList, String filePath) {
        try (Workbook workbook = createWorkbook(dataList, ExcelDict.EXPORT);
             FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (Exception e) {
            log.error("导出Excel到文件失败", e);
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    /**
     * 导出到响应流
     *
     * @param dataList 数据列表
     * @param response HTTP响应对象
     * @param <T>      数据类型
     */
    public <T> void exportToResponse(HttpServletResponse response, String fileName, List<T> dataList) {
        try (Workbook workbook = createWorkbook(dataList, ExcelDict.EXPORT)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("导出Excel到响应流失败", e);
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    /**
     * 导出Excel导入模板
     *
     * @param clazz    目标类型
     * @param response HTTP响应对象
     * @param <T>      数据类型
     */
    public <T> void exportTemplate(HttpServletResponse response, Class<T> clazz, String fileName) {
        // 通过反射获取类注释上的数据
        ExcelSheet excelSheet = clazz.getAnnotation(ExcelSheet.class);
        if (excelSheet == null) {
            throw new RuntimeException("@ExcelSheet注解不能为空");
        }
        // 读取字段上的注释，查询字典
        Map<String, ExcelColumnModel> excelTitleModelByFieldNameMap = this.genExcelColumnModel(clazz, ExcelDict.TEMPLATE);
        List<ExcelColumnModel> values = new ArrayList<>(excelTitleModelByFieldNameMap.values());
        // 创建表头
        try (Workbook workbook = this.createHeader(excelSheet, values)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("导出Excel模板失败", e);
            throw new RuntimeException("导出Excel模板失败", e);
        }
    }

    public <T> Workbook createWorkbook(List<T> dataList, String type) {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new RuntimeException("数据列表不能为空");
        }
        Class<?> clazz = dataList.get(0).getClass();
        // 通过反射获取类注释上的数据
        ExcelSheet excelSheet = clazz.getAnnotation(ExcelSheet.class);
        if (excelSheet == null) {
            return null;
        }
        // 读取字段上的注释，查询字典
        Map<String, ExcelColumnModel> excelTitleModelByFieldNameMap = this.genExcelColumnModel(clazz, type);

        List<ExcelColumnModel> values = new ArrayList<>(excelTitleModelByFieldNameMap.values());

        SXSSFWorkbook wb = null;
        try {
            // 创建表头
            wb = this.createHeader(excelSheet, values);
            // 写入导出的数据
            this.createExportData(wb, excelSheet, dataList, values);
        } catch (Exception e) {
            log.error("导出异常:{}", e.getMessage(), e);
        }
        return wb;
    }

    /**
     * 写入导出的数据
     *
     * @author 千面
     */
    private <T> void createExportData(SXSSFWorkbook wb, ExcelSheet excelSheet, List<T> dataList, List<ExcelColumnModel> values) throws Exception {
        // 数据样式
        CellStyle cellStyle = wb.createCellStyle();
        Row row = null;
        Cell cell = null;
        Sheet sheet = wb.getSheet(excelSheet.name());
        // 写入数据
        for (int i = 0; i < dataList.size(); i++) {
            // 空出标题行
            row = sheet.createRow(i + 1);
            // 取一行数据
            T t = dataList.get(i);
            for (int col = 0; col < values.size(); col++) {
                ExcelColumnModel excelTitleModel = values.get(col);
                String fieldName = excelTitleModel.getField().getName();
                Field field = t.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(t);
                // 字典翻译
                Map<String, String> dictionaryNameMap = excelTitleModel.getDictMap();
                if (MapUtils.isNotEmpty(dictionaryNameMap) && value != null) {
                    value = dictionaryNameMap.get(value.toString());
                }
                // 日期格式处理
                String dateFormat = excelTitleModel.getFormat();
                if (StringUtils.isNotBlank(dateFormat)) {
                    if (value instanceof Date) {
                        value = DateUtil.format((Date) value, dateFormat);
                    } else if (value instanceof LocalDateTime) {
                        value = DateUtil.format((LocalDateTime) value, dateFormat);
                    }
                }
                // 填充值
                createCell(row, cell, cellStyle, col, value);
            }
        }
    }

    /**
     * 创建表头
     *
     * @author 千面
     */
    private SXSSFWorkbook createHeader(ExcelSheet excelSheet, List<ExcelColumnModel> values) {
        // 使用SXSSF处理大数据量
        SXSSFWorkbook wb = new SXSSFWorkbook(500);
        // 压缩临时文件
        wb.setCompressTempFiles(true);

        // 标题样式=================================
        // 标题样式-必传字段为红色
        CellStyle cellStyleRed = createHeaderStyle(wb, true);
        // 标题样式-非必传字段为黑色
        CellStyle cellStyleBlue = createHeaderStyle(wb, false);
        // 标题样式=================================

        // 在工作薄上建一张工作表
        Sheet sheet = wb.createSheet(excelSheet.name());
        // 冻结行
        if (excelSheet.freezeRow() > 0) {
            sheet.createFreezePane(0, excelSheet.freezeRow());
        }

        Map<String, Sheet> dictSheets = new HashMap<>();
        // 写入表头
        Row rowHeader = sheet.createRow(0);
        Cell cell = null;
        for (int col = 0; col < values.size(); col++) {
            ExcelColumnModel excelTitleModel = values.get(col);
            CellStyle cellStyleTitle = excelTitleModel.isRequired() ? cellStyleRed : cellStyleBlue;
            createCellHeader(sheet, rowHeader, cell, cellStyleTitle, col, excelTitleModel);
            // 冻结行
            if (excelSheet.freezeRow() > 0) {
                sheet.createFreezePane(0, excelSheet.freezeRow());
            }
            // 设置下拉列表
            Map<String, String> dictMapValue = excelTitleModel.getDictMap();

            if (MapUtils.isNotEmpty(dictMapValue)) {
                List<String> options = new ArrayList<>(dictMapValue.values());
                if (options.size() <= MAX_DROPDOWN_LENGTH) {
                    // 如果选项数量较少，使用普通下拉列表
                    this.addSimpleDropDownList(sheet, options, col, 1, 65535);
                } else {
                    // 如果选项数量较多，使用隐藏sheet存储数据
                    this.addDropDownListWithHiddenSheet(wb, sheet, dictSheets,
                            excelTitleModel.getField().getName(), options, col, 1, 65535);
                }
            }
        }
        return wb;
    }

    private <T> List<T> processWorkbook(Workbook workbook, Class<T> clazz) throws Exception {
        List<T> dataList = new ArrayList<>();

        ExcelSheet exd = clazz.getAnnotation(ExcelSheet.class);
        if (exd == null) {
            return new ArrayList<>();
        }
        Map<String, ExcelColumnModel> excelColumnModelMapTitle = this.genExcelColumnModel(clazz, ExcelDict.IMPORT);
        // 表头的位置
        int headerIndex = exd.headerIndex() - 1;
        // 获取字段对应的列号
        Map<Integer, ExcelColumnModel> reflectionMap = new HashMap<>();
        // 读取sheet
        Sheet sheet = workbook.getSheetAt(exd.startSheetIndex() - 1);
        boolean isIndexField = true;
        // 读取行
        for (int i = headerIndex; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            // 忽略空白行
            if (row == null) {
                continue;
            }
            // 判断是否为空白行
            boolean allBlank = true;
            // 数据对象
            T t = clazz.newInstance();
            if (isIndexField) {
                try {
                    // 填充索引字段
                    Field indexField = clazz.getDeclaredField("index");
                    indexField.setAccessible(true);
                    handleField(t, String.valueOf(i + exd.headerIndex()), indexField, null);
                } catch (Exception ignored) {
                    isIndexField = false;
                }
            }
            // 读取列
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                String cellValue = getCellValue(cell);
                if (StringUtils.isBlank(cellValue)) {
                    continue;
                }
                if (i == headerIndex) {
                    // 表头区域获取字段对应的列
                    reflectionMap.put(j, excelColumnModelMapTitle.get(cellValue));
                } else {
                    // 数据区
                    if (!reflectionMap.containsKey(j)) {
                        continue;
                    }
                    ExcelColumnModel excelTitleModel = reflectionMap.get(j);
                    Field field = excelTitleModel.getField();
                    Field tranField = excelTitleModel.getTranField();
                    String dateFormat = excelTitleModel.getFormat();
                    try {
                        if (tranField != null) {
                            handleField(t, cellValue, tranField, dateFormat);
                            Map<String, String> dictionaryNameMap = excelTitleModel.getDictMap();
                            if (MapUtils.isNotEmpty(dictionaryNameMap)) {
                                String valueName = dictionaryNameMap.get(cellValue);
                                if (StringUtils.isNotBlank(valueName)) {
                                    handleField(t, valueName, field, dateFormat);
                                }
                            }
                        } else {
                            handleField(t, cellValue, field, dateFormat);
                        }
                    } catch (Exception e) {
                        log.error("反射填充值异常：reflect field:{} value:{} exception!", field.getName(), cellValue, e);
                    }
                    allBlank = false;
                }
            }
            if (!allBlank) {
                dataList.add(t);
            }
        }
        return dataList;
    }

    /**
     * 构建一个方格
     *
     * @param row
     * @param cell
     * @param cellstyle
     * @param col
     * @param val
     * @return void
     * @author 千面
     * @date 2023/11/27 11:03
     */
    private static void createCell(Row row, Cell cell, CellStyle cellstyle, int col, Object val) {
        cell = row.createCell(col);
        if (val instanceof String) {
            cell.setCellValue((String) val);
        } else if (val instanceof Double) {
            cell.setCellValue((Double) val);
        } else if (val instanceof Date) {
            cell.setCellValue((Date) val);
        } else if (val instanceof Integer) {
            cell.setCellValue((Integer) val);
        } else if (val instanceof Long) {
            cell.setCellValue(String.valueOf(val));
        } else if (val instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) val).stripTrailingZeros().toPlainString());
        }
        cell.setCellStyle(cellstyle);
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

    /**
     * 创建表头
     *
     * @param sheet
     * @param row
     * @param col
     * @param val
     * @return void
     * @author 千面
     * @date 2023/11/24 17:48
     */
    private static void createCellHeader(Sheet sheet, Row row, Cell cell,
                                         CellStyle cellstyle, int col, ExcelColumnModel excelTitleModel) {
        // 1.5 * 265 = 384
        String title = excelTitleModel.getTitle();
        int width = excelTitleModel.getWidth();

        // 如果指定了列宽，使用指定的宽度
        if (width > 0) {
            sheet.setColumnWidth(col, width * 256);
        } else {
            // 否则设置一个默认宽度
            sheet.setColumnWidth(col, title.getBytes().length * 256);
        }
        cell = row.createCell(col);
        cell.setCellStyle(cellstyle);
//        cell.setCellType(CellType.STRING);
        cell.setCellValue(title);
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

    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return StringUtils.trimToEmpty(cell.getStringCellValue());
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue());
                    return DateUtil.formatDateTime(date);
                }
                return Double.toString(cell.getNumericCellValue());
            case BLANK:
            case ERROR:
                return "";
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return StringUtils.trimToEmpty(cell.toString());
        }
    }

    /**
     * 注释: 获取数据原始类型
     *
     * @param t
     * @param value
     * @param field
     * @return void
     * @author yangyongzhuo 2022/11/25 13:25
     */
    private static <T> void handleField(T t, String value, Field field, String dateFormat) throws Exception {
        Class<?> type = field.getType();
        if (type == null || type == void.class || StringUtils.isBlank(value)) {
            return;
        }
        if (type == Object.class) {
            field.set(t, value);
            // 数字类型
        } else if (type.getSuperclass() == null || type.getSuperclass() == Number.class) {
            if (type == int.class || type == Integer.class) {
                field.set(t, NumberUtils.toInt(value));
            } else if (type == long.class || type == Long.class) {
                field.set(t, NumberUtils.toLong(value));
            } else if (type == byte.class || type == Byte.class) {
                field.set(t, NumberUtils.toByte(value));
            } else if (type == short.class || type == Short.class) {
                field.set(t, NumberUtils.toShort(value));
            } else if (type == double.class || type == Double.class) {
                field.set(t, NumberUtils.toDouble(value));
            } else if (type == float.class || type == Float.class) {
                field.set(t, NumberUtils.toFloat(value));
            } else if (type == char.class || type == Character.class) {
                field.set(t, CharUtils.toChar(value));
            } else if (type == boolean.class) {
                field.set(t, BooleanUtils.toBoolean(value));
            } else if (type == BigDecimal.class) {
                field.set(t, new BigDecimal(value));
            }
        } else if (type == Boolean.class) {
            field.set(t, BooleanUtils.toBoolean(value));
        } else if (type == Date.class) {
            if (StringUtils.isNotBlank(dateFormat)) {
                field.set(t, DateUtil.parse(value, dateFormat));
            } else {
                field.set(t, DateUtil.parse(value));
            }
        } else if (type == LocalDateTime.class) {
            if (StringUtils.isNotBlank(dateFormat)) {
                field.set(t, DateUtil.parse(value, dateFormat).toLocalDateTime());
            } else {
                field.set(t, DateUtil.parse(value).toLocalDateTime());
            }
        } else if (type == String.class) {
            Optional.ofNullable(value).ifPresent(String::trim);
            field.set(t, value);
        } else {
            Constructor<?> constructor = type.getConstructor(String.class);
            field.set(t, constructor.newInstance(value));
        }
    }

    /**
     * 读取字段上的注释，查询字典
     *
     * @param clsss
     * @return Map<String, ExcelColumnModel>
     * @author 千面
     * @date 2023/11/24 13:57
     */
    public <T> Map<String, ExcelColumnModel> genExcelColumnModel(Class<T> clsss, String type) {
        Map<String, ExcelColumnModel> excelTitleModelMap = new LinkedHashMap<>();

        Map<String, Field> fieldByNameMap = new LinkedHashMap<>();
        for (Field field : clsss.getDeclaredFields()) {
            fieldByNameMap.put(field.getName(), field);
        }
        for (Map.Entry<String, Field> entry : fieldByNameMap.entrySet()) {
            Field field = entry.getValue();
            // 获取字段注解
            ExcelColumn excel = field.getAnnotation(ExcelColumn.class);
            if (ObjectUtil.isEmpty(excel)) {
                continue;
            }
            String title = excel.title();
            if (StrUtil.isEmpty(title)) {
                continue;
            }
            ExcelColumnModel model = new ExcelColumnModel();
            model.setTitle(title);
            model.setRequired(excel.required());
            model.setFormat(excel.format());
            // 导入导出查询数据字典
            if (StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(excel.dict().dictCode())) {
                // 收集字典数据
                findImportDictionary(field, fieldByNameMap, excel, model, type);
            }
            field.setAccessible(true);
            model.setField(field);
            model.setWidth(excel.width());
            if (StringUtils.equals(ExcelDict.IMPORT, type)) {
                ExcelColumnModel excelColumnModel = excelTitleModelMap.putIfAbsent(title, model);
                if (excelColumnModel != null) {
                    throw new RuntimeException("导入的Excel标题 " + title + " 重复");
                }
            } else if (StringUtils.equals(ExcelDict.EXPORT, type) || StringUtils.equals(ExcelDict.TEMPLATE, type)) {
                excelTitleModelMap.put(entry.getKey(), model);
            }
        }
        return excelTitleModelMap;
    }

    /**
     * 收集字典数据
     *
     * @param excel
     * @return void
     * @author 千面
     * @date 2023/11/24 11:19
     */
    private void findImportDictionary(Field currentField, Map<String, Field> fieldByNameMap, ExcelColumn
            excel, ExcelColumnModel model, String type) {
        String title = excel.title();
        ExcelDict excelDict = excel.dict();
        model.setTitle(title);
        model.setDict(excelDict);
        Field field = currentField;
        if (StringUtils.isNotBlank(excelDict.tranName())) {
            field = fieldByNameMap.get(excelDict.tranName());
        }
        if (field != null) {
            field.setAccessible(true);
            model.setTranField(field);
        }
        try {
            if (StringUtils.equals(ExcelDict.IMPORT, type)) {
                if (!ExcelCover.class.getSimpleName().equals(excelDict.coverClass().getSimpleName())) {
                    model.setDictMap(excelDict.coverClass().newInstance().importCover());
                } else {
                    model.setDictMap(dictService.getDictMapLabel(excelDict.dictCode(), excelDict.dictType()));
                }
            } else {
                if (!ExcelCover.class.getSimpleName().equals(excelDict.coverClass().getSimpleName())) {
                    model.setDictMap(excelDict.coverClass().newInstance().exportCover());
                } else {
                    model.setDictMap(dictService.getDictMapValue(excelDict.dictCode(), excelDict.dictType()));
                }
            }
        } catch (Exception e) {
            log.error("字典转换异常：{}", e.getMessage(), e);
        }
    }
}
