package com.billow.excel.excelKet;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
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
import java.util.function.BiFunction;

/**
 * ExcelCore 类提供了 Excel 文件的导入导出核心功能，支持从文件、输入流、MultipartFile 导入数据，
 * 也支持将数据导出到文件、响应流以及导出 Excel 导入模板。通过反射和注解处理，结合 Apache POI 库操作 Excel 文件。
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
     * 从文件导入 Excel 数据。
     *
     * @param filePath 文件路径，指定要导入的 Excel 文件的位置。
     * @param clazz    目标类型，指定导入数据要转换的对象类型。
     * @param <T>      数据类型，泛型参数，代表导入数据的类型。
     * @return 导入的数据列表，包含转换后的对象。
     * @throws RuntimeException 若文件读取失败或导入过程中出现异常。
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
     * 从输入流导入 Excel 数据。
     *
     * @param inputStream 输入流，包含 Excel 文件数据的输入流。
     * @param clazz       目标类型，指定导入数据要转换的对象类型。
     * @param <T>         数据类型，泛型参数，代表导入数据的类型。
     * @return 导入的数据列表，包含转换后的对象。
     * @throws RuntimeException 若输入流读取失败或导入过程中出现异常。
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
     * 从 MultipartFile 导入 Excel 数据。
     *
     * @param file  上传的文件，Spring 框架中的 MultipartFile 对象。
     * @param clazz 目标类型，指定导入数据要转换的对象类型。
     * @param <T>   数据类型，泛型参数，代表导入数据的类型。
     * @return 导入的数据列表，包含转换后的对象。
     * @throws RuntimeException 若文件读取失败或导入过程中出现异常。
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
     * 导出数据到指定文件。
     *
     * @param dataList 数据列表，要导出的数据集合。
     * @param filePath 文件路径，指定导出 Excel 文件的保存位置。
     * @param <T>      数据类型，泛型参数，代表要导出的数据类型。
     * @throws RuntimeException 若导出过程中出现异常。
     */
    public <T> void exportToFile(List<T> dataList, String filePath) {
        try (Workbook workbook = createWorkbook(dataList, ExcelDict.EXPORT); FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (Exception e) {
            log.error("导出Excel到文件失败", e);
            throw new RuntimeException("导出Excel失败", e);
        }
    }

    /**
     * 导出数据到 HTTP 响应流，供客户端下载。
     *
     * @param response HTTP响应对象，用于设置响应头和输出流。
     * @param fileName 文件名，指定客户端下载的 Excel 文件名称。
     * @param dataList 数据列表，要导出的数据集合。
     * @param <T>      数据类型，泛型参数，代表要导出的数据类型。
     * @throws RuntimeException 若导出过程中出现异常。
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
     * 导出 Excel 导入模板。
     *
     * @param response HTTP响应对象，用于设置响应头和输出流。
     * @param clazz    目标类型，指定模板对应的对象类型。
     * @param fileName 文件名，指定客户端下载的模板文件名称。
     * @param <T>      数据类型，泛型参数，代表模板对应的数据类型。
     * @throws RuntimeException 若导出过程中出现异常或缺少 @ExcelSheet 注解。
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

    /**
     * 分页导出数据到 filePath流，数据分页查询，避免一次性加载到内存。
     *
     * @param filePath    文件保存路径。
     * @param pageSize    每页数据数量。
     * @param totalCount  数据总数量。
     * @param dataFetcher 分页数据获取函数，接收页码和每页数量，返回对应页的数据列表。
     * @param <T>         数据类型，泛型参数，代表要导出的数据类型。
     * @return 返回导出数据的总条数。
     */
    public <T> int exportToFileWithPage(String filePath, int pageSize, int totalCount,
                                        BiFunction<Integer, Integer, List<T>> dataFetcher) {
        int count = 0;
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(500); FileOutputStream fos = new FileOutputStream(filePath)) {
            boolean isFirstPage = true;
            Sheet sheet = null;
            List<ExcelColumnModel> columnModels = null;
            Class<?> clazz = null;

            int pageCount = (int) Math.ceil((double) totalCount / pageSize);
            for (int page = 1; page <= pageCount; page++) {
                List<T> pageData = dataFetcher.apply(page, pageSize);
                if (CollectionUtils.isEmpty(pageData)) {
                    continue;
                }

                count += pageData.size();

                if (isFirstPage) {
                    clazz = pageData.get(0).getClass();
                    ExcelSheet excelSheet = clazz.getAnnotation(ExcelSheet.class);
                    if (excelSheet == null) {
                        throw new RuntimeException("@ExcelSheet 注解不能为空");
                    }

                    columnModels = new ArrayList<>(genExcelColumnModel(clazz, ExcelDict.EXPORT).values());
                    sheet = createHeaderForPagedExport(workbook, excelSheet, columnModels);
                    isFirstPage = false;
                }

                createExportDataForPage(sheet, pageData, columnModels);
            }
            workbook.write(fos);
        } catch (Exception e) {
            log.error("分页导出 Excel 到响应流失败", e);
            throw new RuntimeException("分页导出 Excel 失败", e);
        }
        return count;
    }

    /**
     * 为分页导出创建表头。
     *
     * @param workbook     工作簿对象。
     * @param excelSheet   工作表注解对象。
     * @param columnModels 表头列模型列表。
     * @return 创建好表头的工作表对象。
     */
    private Sheet createHeaderForPagedExport(Workbook workbook, ExcelSheet excelSheet, List<ExcelColumnModel> columnModels) {
        Sheet sheet = workbook.createSheet(excelSheet.name());
        // 标题样式 - 必传字段为红色
        CellStyle cellStyleRed = createHeaderStyle(workbook, true);
        // 标题样式 - 非必传字段为黑色
        CellStyle cellStyleBlue = createHeaderStyle(workbook, false);

        Row headerRow = sheet.createRow(0);
        Cell cell = null;
        for (int col = 0; col < columnModels.size(); col++) {
            ExcelColumnModel model = columnModels.get(col);
            CellStyle cellStyle = model.isRequired() ? cellStyleRed : cellStyleBlue;
            createCellHeader(sheet, headerRow, cell, cellStyle, col, model);
        }
        return sheet;
    }

    /**
     * 为分页导出写入单页数据。
     *
     * @param sheet        工作表对象。
     * @param pageData     单页数据列表。
     * @param columnModels 表头列模型列表。
     * @param <T>          数据类型，泛型参数。
     */
    private <T> void createExportDataForPage(Sheet sheet, List<T> pageData, List<ExcelColumnModel> columnModels) throws Exception {
        int startRow = sheet.getLastRowNum() + 1;
        Row row = null;
        Cell cell = null;
        for (int i = 0; i < pageData.size(); i++) {
            row = sheet.createRow(startRow + i);
            T item = pageData.get(i);

            for (int col = 0; col < columnModels.size(); col++) {
                ExcelColumnModel model = columnModels.get(col);
                String fieldName = model.getField().getName();
                Field field = item.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(item);

                // 字典翻译
                Map<String, String> dictMap = model.getDictMap();
                if (MapUtils.isNotEmpty(dictMap) && value != null) {
                    value = dictMap.get(value.toString());
                }

                // 日期格式处理
                String dateFormat = model.getFormat();
                if (StringUtils.isNotBlank(dateFormat)) {
                    if (value instanceof Date) {
                        value = DateUtil.format((Date) value, dateFormat);
                    } else if (value instanceof LocalDateTime) {
                        value = DateUtil.format((LocalDateTime) value, dateFormat);
                    }
                }

                createCell(row, cell, col, value);
            }
        }
    }

    /**
     * 创建 Excel 工作簿，包含表头和导出数据。
     *
     * @param dataList 数据列表，要导出的数据集合。
     * @param type     导出类型，如 ExcelDict.EXPORT 或 ExcelDict.TEMPLATE。
     * @param <T>      数据类型，泛型参数，代表要导出的数据类型。
     * @return 包含导出数据的工作簿对象，若缺少 @ExcelSheet 注解则返回 null。
     */
    private <T> Workbook createWorkbook(List<T> dataList, String type) {
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
     * 写入导出的数据到指定的工作簿中。
     *
     * @param wb         工作簿对象，用于写入数据。
     * @param excelSheet 工作表注解对象，包含工作表的配置信息。
     * @param dataList   数据列表，要写入的数据集合。
     * @param values     表头列模型列表，包含表头信息。
     * @param <T>        数据类型，泛型参数，代表要写入的数据类型。
     * @throws Exception 若反射操作或数据处理过程中出现异常。
     */
    private <T> void createExportData(Workbook wb, ExcelSheet excelSheet, List<T> dataList, List<ExcelColumnModel> values) throws Exception {
        Row row = null;
        Cell cell = null;
        Sheet sheet = wb.getSheet(excelSheet.name());
        int startRowNum = sheet.getLastRowNum();
        int endRowNum = startRowNum + dataList.size();
        // 写入数据
        for (int i = startRowNum; i < endRowNum; i++) {
            // 空出标题行
            row = sheet.createRow(i + excelSheet.headerIndex());
            // 取一行数据
            T t = dataList.get(i - startRowNum);
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
                createCell(row, cell, col, value);
            }
        }
    }

    /**
     * 创建 Excel 表头，设置列宽、样式和下拉列表。
     *
     * @param excelSheet 工作表注解对象，包含工作表的配置信息。
     * @param values     表头列模型列表，包含表头信息。
     * @return 包含表头的工作簿对象。
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
                    this.addDropDownListWithHiddenSheet(wb, sheet, dictSheets, excelTitleModel.getField().getName(), options, col, 1, 65535);
                }
            }

            // 添加提示信息，范围从第 1 行到第 65535 行
            String message = excelTitleModel.getMessage();
            if (StringUtils.isNotBlank(message)) {
                DataValidationHelper validationHelper = sheet.getDataValidationHelper();
                // 第 1 行索引为 0，这里设置提示信息从第 1 行（索引 0）到第 65535 行（索引 65534）
                CellRangeAddressList addressList = new CellRangeAddressList(0, 65534, col, col);
                DataValidationConstraint constraint = validationHelper.createCustomConstraint("TRUE");
                DataValidation validation = validationHelper.createValidation(constraint, addressList);
                // 设置提示标题和内容
                validation.setShowPromptBox(true);
                validation.createPromptBox("提示", message);
                sheet.addValidationData(validation);
            }
        }
        return wb;
    }

    /**
     * 处理 Excel 工作簿，读取数据并转换为指定类型的对象列表。
     *
     * @param workbook 工作簿对象，包含要处理的 Excel 数据。
     * @param clazz    目标类型，指定读取数据要转换的对象类型。
     * @param <T>      数据类型，泛型参数，代表读取数据的类型。
     * @return 处理后的数据列表，包含转换后的对象。
     * @throws Exception 若反射操作或数据处理过程中出现异常。
     */
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
     * 构建一个单元格，设置单元格的值和样式。
     *
     * @param row  行对象，用于创建单元格。
     * @param cell 单元格对象，用于设置值和样式。
     * @param col  列索引，指定单元格所在的列位置。
     * @param val  单元格的值，可以是不同的数据类型。
     */
    private static void createCell(Row row, Cell cell, int col, Object val) {
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
    }

    /**
     * 添加简单的下拉列表到指定工作表的列。
     *
     * @param sheet       工作表对象，用于添加数据验证。
     * @param options     下拉列表选项列表，包含可选择的值。
     * @param columnIndex 列索引，指定下拉列表所在的列位置。
     * @param firstRow    起始行索引，指定下拉列表生效的起始行。
     * @param lastRow     结束行索引，指定下拉列表生效的结束行。
     */
    private void addSimpleDropDownList(Sheet sheet, List<String> options, int columnIndex, int firstRow, int lastRow) {
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, columnIndex, columnIndex);

        DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(options.toArray(new String[0]));

        DataValidation validation = validationHelper.createValidation(constraint, addressList);
        validation.setShowErrorBox(true);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("错误", "请从下拉列表中选择值");
        validation.setShowPromptBox(true);
        validation.createPromptBox("提示", "请从下拉列表中选择值");

        sheet.addValidationData(validation);
    }

    /**
     * 使用隐藏工作表添加下拉列表到指定工作表的列。
     *
     * @param workbook    工作簿对象，用于创建隐藏工作表和名称管理器。
     * @param mainSheet   主工作表对象，用于添加数据验证。
     * @param dictSheets  字典工作表映射，存储已创建的隐藏工作表。
     * @param dictCode    字典代码，用于标识隐藏工作表的名称。
     * @param options     下拉列表选项列表，包含可选择的值。
     * @param columnIndex 列索引，指定下拉列表所在的列位置。
     * @param firstRow    起始行索引，指定下拉列表生效的起始行。
     * @param lastRow     结束行索引，指定下拉列表生效的结束行。
     */
    private void addDropDownListWithHiddenSheet(Workbook workbook, Sheet mainSheet, Map<String, Sheet> dictSheets, String dictCode, List<String> options, int columnIndex, int firstRow, int lastRow) {
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
     * 创建表头单元格，设置单元格样式、列宽并填充表头标题。
     *
     * @param sheet           工作表对象，用于设置列宽等操作。
     * @param row             表头所在行对象，用于创建单元格。
     * @param cell            单元格对象，用于设置样式和填充值。
     * @param cellstyle       表头单元格样式，包含字体、颜色、边框等样式信息。
     * @param col             列索引，指定表头单元格所在的列位置。
     * @param excelTitleModel 表头列模型，包含表头标题、列宽、是否必填等信息。
     * @return void
     * @author 千面
     * @date 2023/11/24 17:48
     */
    private static void createCellHeader(Sheet sheet, Row row, Cell cell, CellStyle cellstyle, int col, ExcelColumnModel excelTitleModel) {
        // 1.5 * 265 = 384
        String title = excelTitleModel.getTitle();
        int width = Optional.of(excelTitleModel.getWidth()).filter(w -> w > 0).orElse(ReUtil.findAll("[\\u4e00-\\u9fa5]", title, 0).size());
        // 设置列宽
        sheet.setColumnWidth(col, (int) ((width * 2.2) + 2) * 256);
        cell = row.createCell(col);
        cell.setCellStyle(cellstyle);
//        cell.setCellType(CellType.STRING);
        cell.setCellValue(title);
    }

    /**
     * 创建表头单元格样式，根据是否必填设置不同的字体颜色。
     *
     * @param workbook 工作簿对象，用于创建样式和字体。
     * @param required 是否必填，若为 true 则字体颜色为红色，否则为黑色。
     * @return 表头单元格样式对象。
     */
    private CellStyle createHeaderStyle(Workbook workbook, boolean required) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER); // 设置水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        Font font = workbook.createFont();
        font.setBold(true);
        if (required) {
            font.setColor(IndexedColors.RED.getIndex());
        }
        style.setFont(font);

        return style;
    }

    /**
     * 获取单元格的值，根据单元格类型进行不同的处理。
     *
     * @param cell 单元格对象，要获取值的单元格。
     * @return 单元格的值，处理后的字符串形式。
     */
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
     * 处理字段值，将字符串值转换为指定字段的类型并设置到对象中。
     *
     * @param t          目标对象，要设置字段值的对象。
     * @param value      字符串值，要转换并设置的字段值。
     * @param field      字段对象，指定要设置值的字段。
     * @param dateFormat 日期格式，若字段为日期类型，用于日期转换。
     * @param <T>        目标对象类型，泛型参数。
     * @throws Exception 若反射操作或类型转换过程中出现异常。
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
     * 读取类字段上的注解信息，生成 Excel 列模型映射。
     *
     * @param clsss 目标类型，要读取注解信息的类。
     * @param type  操作类型，如 ExcelDict.IMPORT、ExcelDict.EXPORT 或 ExcelDict.TEMPLATE。
     * @param <T>   目标类型，泛型参数。
     * @return Excel 列模型映射，键为字段名或表头标题，值为 ExcelColumnModel 对象。
     */
    private <T> Map<String, ExcelColumnModel> genExcelColumnModel(Class<T> clsss, String type) {
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
            model.setMessage(excel.message());
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
     * 收集字典数据，根据操作类型获取字典映射并设置到 Excel 列模型中。
     *
     * @param currentField   当前字段对象，要处理的字段。
     * @param fieldByNameMap 字段名到字段对象的映射，用于查找转换字段。
     * @param excel          字段注解对象，包含字典信息。
     * @param model          Excel 列模型对象，用于存储字典映射。
     * @param type           操作类型，如 ExcelDict.IMPORT 或 ExcelDict.EXPORT。
     */
    private void findImportDictionary(Field currentField, Map<String, Field> fieldByNameMap, ExcelColumn excel, ExcelColumnModel model, String type) {
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