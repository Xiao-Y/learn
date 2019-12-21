package com.billow.tools.excel;

import com.billow.tools.utlis.FieldUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Excel工具类
 *
 * @author liuyongtao
 * @create 2018-07-09 10:29
 */
public class ExcelUtils {

    public final static String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 导出Excel
     *
     * @param response
     * @param fileName 文件名
     * @param data     需要的参数 sheetName, objData, clazz <br/>
     *                 或者sheetName, titles, rows;
     * @return void
     * @author LiuYongTao
     * @date 2018/7/9 11:39
     */

    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData data) throws Exception {
        response.setContentType("application/x-download");
        response.setCharacterEncoding("utf-8");
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "utf-8"));
        exportExcel(data, response.getOutputStream());
    }

    /**
     * 导出Excel(多sheet)
     *
     * @param response
     * @param fileName   文件名
     * @param excelDatas 需要的参数 sheetName, objData, clazz <br/>
     *                   或者 sheetName, titles, rows;
     * @return void
     * @author LiuYongTao
     * @date 2018/7/9 11:39
     */
    public static void exportExcel(HttpServletResponse response, String fileName, List<ExcelData> excelDatas) throws Exception {
        response.setContentType("application/x-download");
        response.setCharacterEncoding("utf-8");
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "utf-8"));
        exportExcel(excelDatas, response.getOutputStream());
    }

    /**
     * 创建Excel(多sheet)
     *
     * @param excelDatas 数据集
     * @param out        输出流
     * @return void
     * @author LiuYongTao
     * @date 2018/7/9 11:39
     */
    public static void exportExcel(List<ExcelData> excelDatas, OutputStream out) throws Exception {
        try (HSSFWorkbook wb = new HSSFWorkbook()) {
            for (int i = 0; i < excelDatas.size(); i++) {
                ExcelData data = excelDatas.get(i);
                String sheetName = data.getSheetName();
                HSSFSheet sheet = wb.createSheet(sheetName);
                wb.setSheetName(i, sheetName);
                exportExcel(wb, sheet, data);
            }
            wb.write(out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建Excel
     *
     * @param data 数据集
     * @param out  输出流
     * @return void
     * @author LiuYongTao
     * @date 2018/7/9 11:39
     */
    public static void exportExcel(ExcelData data, OutputStream out) throws Exception {

        try (HSSFWorkbook wb = new HSSFWorkbook()) {
            String sheetName = data.getSheetName();
            HSSFSheet sheet = wb.createSheet(sheetName);
            exportExcel(wb, sheet, data);
            wb.write(out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 写入数据到 excel,可以自定义样式。可以持续写入
     *
     * @param wb    HSSFWorkbook
     * @param sheet HSSFSheet
     * @param data  数据集
     * @return void
     * @author LiuYongTao
     * @date 2019/12/17 17:55
     */
    public static void exportExcel(HSSFWorkbook wb, HSSFSheet sheet, ExcelData data) throws NoSuchFieldException, IllegalAccessException {
        //获取标题
        LinkedHashMap<String, String> titles = data.getTitles();
        if (ToolsUtils.isEmpty(titles)) {
            titles = getExcelTitle(data);
        }
        //获取数据集
        ArrayList<HashMap<String, Object>> rows = data.getRows();
        if (ToolsUtils.isEmpty(rows)) {
            rows = getExcelRows(data, titles.keySet());
        }
        //写入标题
        writeTitlesToExcel(wb, sheet, titles, data.getEndDataIndex(), data.getTitlesHSSFCellStyle());
        //写入数据
        writeRowsToExcel(wb, sheet, titles, rows, data.getEndDataIndex(), data.getRowsHSSFCellStyle());
    }

    /**
     * 获取数据集
     *
     * @param data   数据集
     * @param titles 标题集
     * @return java.util.ArrayList<java.util.HashMap < java.lang.String, java.lang.Object>>
     * @author LiuYongTao
     * @date 2018/7/10 9:45
     */
    private static <T> ArrayList<HashMap<String, Object>> getExcelRows(ExcelData data, Set<String> titles) throws NoSuchFieldException, IllegalAccessException {
        List<T> objData = data.getObjData();
        ArrayList<HashMap<String, Object>> rows = new ArrayList<>();
        for (T obj : objData) {
            HashMap<String, Object> map = FieldUtils.entityToMap(obj, titles);
            rows.add(map);
        }
        return rows;
    }

    /**
     * 获取标题
     *
     * @param data 数据集
     * @return java.util.LinkedHashMap<java.lang.String, java.lang.String>
     * @author LiuYongTao
     * @date 2018/7/10 9:26
     */
    private static LinkedHashMap<String, String> getExcelTitle(ExcelData data) throws NoSuchFieldException {
        Class clazz = data.getClazz();
        Field[] declaredFields = clazz.getDeclaredFields();

        LinkedHashMap<String, Double> mapTemp = new LinkedHashMap<>();
        // 获取标记 @Title 的属性和 order
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Title title = field.getAnnotation(Title.class);
            if (title != null) {
                mapTemp.put(field.getName(), title.order());
            }
        }
        // 对order 进行排序
        Map<String, Double> map = sortByValue(mapTemp);
        // 获取 @Title 中的name
        LinkedHashMap<String, String> titles = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            Field field = clazz.getDeclaredField(key);
            Title title = field.getAnnotation(Title.class);
            if (title != null) {
                if (ToolsUtils.isNotEmpty(title.name())) {
                    titles.put(key, title.name());
                } else if (title.isField()) {
                    titles.put(key, key);
                } else {
                    titles.put(key, "");
                }
            }
        }
        return titles;
    }

    /**
     * 写入标题
     *
     * @param wb                  HSSFWorkbook
     * @param sheet               HSSFSheet
     * @param titles              标题集合
     * @param endDataIndex        标题的起始行，
     * @param titlesHSSFCellStyle 标题样式
     * @return void
     * @author LiuYongTao
     * @date 2018/7/9 11:39
     */
    private static void writeTitlesToExcel(HSSFWorkbook wb, HSSFSheet sheet, LinkedHashMap<String, String> titles,
                                           int endDataIndex, Map<String, HSSFCellStyle> titlesHSSFCellStyle) {
        int colIndex = 0;

        HSSFCellStyle titleStyle = wb.createCellStyle();
        HSSFRow titleRow = sheet.createRow(endDataIndex);

        for (Map.Entry<String, String> entry : titles.entrySet()) {
            HSSFCell cell = titleRow.createCell(colIndex);
            cell.setCellValue(entry.getValue());
            HSSFCellStyle hssfCellStyle = titlesHSSFCellStyle.get(entry.getKey());
            if (hssfCellStyle == null) {
                hssfCellStyle = titleStyle;
            }
            cell.setCellStyle(hssfCellStyle);

            colIndex++;
        }
    }

    /**
     * 写入数据
     *
     * @param wb                HSSFWorkbook
     * @param sheet             HSSFSheet
     * @param titles            标题集
     * @param rows              行数据
     * @param endDataIndex      已写入数据行
     * @param rowsHSSFCellStyle
     * @return void
     * @author LiuYongTao
     * @date 2019/12/17 18:02
     */
    private static void writeRowsToExcel(HSSFWorkbook wb, HSSFSheet sheet, LinkedHashMap<String, String> titles,
                                         ArrayList<HashMap<String, Object>> rows, int endDataIndex,
                                         Map<String, HSSFCellStyle> rowsHSSFCellStyle) {
        HSSFCellStyle dataStyle = wb.createCellStyle();

        //标题的行数
        int rowStartIndex = endDataIndex + 1;
        // 遍历数据源(每个HashMap中key 与titles中的key 相同)
        for (HashMap<String, Object> rowData : rows) {
            HSSFRow dataRow = sheet.createRow(rowStartIndex);
            // 遍历标题(通过标题key,找到rowData中的value)
            int colIndex = 0;
            for (Map.Entry<String, String> entry : titles.entrySet()) {
                HSSFCell cell = dataRow.createCell(colIndex);
                String key = entry.getKey();
                String value = converValue(rowData.get(key));
                cell.setCellType(CellType.STRING);
                cell.setCellValue(value);
                HSSFCellStyle hssfCellStyle = rowsHSSFCellStyle.get(key);
                if (hssfCellStyle == null) {
                    hssfCellStyle = dataStyle;
                }
                cell.setCellStyle(hssfCellStyle);
                colIndex++;
            }
            rowStartIndex++;
        }
    }

    /**
     * 数据格式转化
     *
     * @param obj
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2018/11/10 16:10
     */
    private static String converValue(Object obj) {
        String value = "";
        if (obj != null) {
            if (obj instanceof BigDecimal) {
                BigDecimal bigDecimal = (BigDecimal) obj;
                bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
                value = bigDecimal.toString();
            } else if (obj instanceof Date) {
                Date date = (Date) obj;
                value = DateFormatUtils.format(date, FORMAT);
            } else {
                value = obj.toString();
            }
        }
        return value;
    }

    /**
     * 对map 中的value 进行排序
     *
     * @param map
     * @return java.util.Map<K, V>
     * @author LiuYongTao
     * @date 2019/4/4 14:55
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();
        st.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}
