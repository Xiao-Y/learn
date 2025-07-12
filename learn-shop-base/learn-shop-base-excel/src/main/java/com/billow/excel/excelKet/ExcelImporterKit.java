package com.billow.excel.excelKet;

import com.billow.excel.annotation.ExcelColumn;
import com.billow.excel.model.ImportResult;
import com.billow.excel.service.DictService;
import com.billow.tools.utlis.SpringContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * 默认Excel导入实现
 */
@Slf4j
@RequiredArgsConstructor
public class ExcelImporterKit {

    private final DictService dictService;

    public static ExcelImporterKit getInstance() {
        return SpringContextUtil.getBean(ExcelImporterKit.class);
    }

    /**
     * 从文件导入
     *
     * @param filePath 文件路径
     * @param clazz    目标类型
     * @param <T>      数据类型
     * @return 导入的数据列表
     */
    public <T> List<T> importFromFile(String filePath, Class<T> clazz) {
        try (InputStream is = new FileInputStream(new File(filePath))) {
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
            return processWorkbook(workbook, clazz);
        } catch (IOException e) {
            log.error("从输入流导入Excel失败", e);
            throw new RuntimeException("导入Excel失败", e);
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
     * 异步导入
     *
     * @param file  上传的文件
     * @param clazz 目标类型
     * @param <T>   数据类型
     * @return 导入结果Future
     */
    public <T> Future<ImportResult<T>> importAsync(MultipartFile file, Class<T> clazz) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                List<T> dataList = importFromMultipartFile(file, clazz);
                ImportResult<T> result = new ImportResult<>();
                result.setSuccessList(dataList);
                result.setTotal(dataList.size());
                result.setSuccessCount(dataList.size());
                result.setErrorCount(0);
                return result;
            } catch (Exception e) {
                log.error("异步导入Excel失败", e);
                throw new RuntimeException("异步导入Excel失败", e);
            }
        });
    }

    private <T> List<T> processWorkbook(Workbook workbook, Class<T> clazz) {
        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, Field> fieldMap = getFieldMap(clazz);
        List<T> resultList = new ArrayList<>();

        // 跳过表头行
        int firstRowNum = sheet.getFirstRowNum() + 1;
        int lastRowNum = sheet.getLastRowNum();

        for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }

            T instance = createInstance(clazz, row, fieldMap, rowNum);
            if (instance != null) {
                resultList.add(instance);
            }
        }
        dictService.clearCache();
        return resultList;
    }

    private <T> Map<Integer, Field> getFieldMap(Class<T> clazz) {
        Map<Integer, Field> fieldMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            ExcelColumn column = field.getAnnotation(ExcelColumn.class);
            if (column != null) {
                fieldMap.put(column.order(), field);
            }
        }

        return fieldMap;
    }

    private <T> T createInstance(Class<T> clazz, Row row, Map<Integer, Field> fieldMap, int rowNum) {
        try {
            T instance = clazz.newInstance();

            for (Map.Entry<Integer, Field> entry : fieldMap.entrySet()) {
                int columnIndex = entry.getKey();
                Field field = entry.getValue();
                Cell cell = row.getCell(columnIndex);

                if (cell != null) {
                    Object value = getCellValue(cell);
                    if (value != null) {
                        field.setAccessible(true);
                        ExcelColumn column = field.getAnnotation(ExcelColumn.class);

                        // 处理字典值转换
                        if (!column.dictCode().isEmpty()) {
                            String dictValue = dictService.getValueByLabel(column.dictCode(), value.toString(), column.dictType());
                            field.set(instance, convertValue(dictValue, field.getType()));
                        } else {
                            field.set(instance, convertValue(value, field.getType()));
                        }
                    }
                }
            }

            return instance;
        } catch (Exception e) {
            log.error("创建实例失败，行号：{}", rowNum + 1, e);
            return null;
        }
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    private Object convertValue(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }

        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }

        if (targetType == String.class) {
            return value.toString();
        }

        if (targetType == Integer.class || targetType == int.class) {
            return value instanceof Number ? ((Number) value).intValue() :
                    Integer.parseInt(value.toString());
        }

        if (targetType == Long.class || targetType == long.class) {
            return value instanceof Number ? ((Number) value).longValue() :
                    Long.parseLong(value.toString());
        }

        if (targetType == Double.class || targetType == double.class) {
            return value instanceof Number ? ((Number) value).doubleValue() :
                    Double.parseDouble(value.toString());
        }

        if (targetType == Float.class || targetType == float.class) {
            return value instanceof Number ? ((Number) value).floatValue() :
                    Float.parseFloat(value.toString());
        }

        if (targetType == Boolean.class || targetType == boolean.class) {
            return value instanceof Boolean ? value :
                    Boolean.parseBoolean(value.toString());
        }

        return value;
    }
} 
