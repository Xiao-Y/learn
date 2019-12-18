package com.billow.tools.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ExcelUtile 的数据模型类，可以使用 @Title 注解
 *
 * @author LiuYongTao
 * @date 2019/4/4 16:22
 */
public class ExcelData<T> implements Serializable {

    private static final long serialVersionUID = -8434436282620768376L;

    /**
     * 页签名称
     */
    private String sheetName = "Sheet1";
    /**
     * 数据源，使用 @Title 时，必须要填写
     */
    private List<T> objData;
    /**
     * 数据模型，使用 @Title 时，必须要填写
     */
    private Class<?> clazz;
    /**
     * 数据模型，数据已经写入的行数
     */
    private int endDataIndex = 0;
    /**
     * 表头，非 @Title 时，必须要填写
     */
    private LinkedHashMap<String, String> titles;

    /**
     * 非 @Title 时，数据集
     */
    private ArrayList<HashMap<String, Object>> rows;

    /**
     * 标题样式
     */
    private Map<String, HSSFCellStyle> titlesHSSFCellStyle = new HashMap<>();

    /**
     * 行样式
     */
    private Map<String, HSSFCellStyle> rowsHSSFCellStyle = new HashMap<>();

    /**
     * 通过 @Tatle 模型，构建数据,使用默认 sheetName：Sheet1
     *
     * @param objData 数据源
     * @param clazz   数据模型
     * @return
     * @author LiuYongTao
     * @date
     */
    public ExcelData(List<T> objData, Class<?> clazz) {
        this.objData = objData;
        this.clazz = clazz;
    }

    /**
     * 通过 @Tatle 模型，构建数据,使用自定义 sheetName
     *
     * @param sheetName 页签名称
     * @param objData   数据源
     * @param clazz     数据模型
     * @return
     * @author LiuYongTao
     * @date
     */
    public ExcelData(String sheetName, List<T> objData, Class<?> clazz) {
        this.sheetName = sheetName;
        this.objData = objData;
        this.clazz = clazz;
    }

    /**
     * 通过自定义 titles 和数据集,使用默认 sheetName：Sheet1
     *
     * @param titles 表头
     * @param rows   数据源
     * @return
     * @author LiuYongTao
     * @date
     */
    public ExcelData(LinkedHashMap<String, String> titles, ArrayList<HashMap<String, Object>> rows) {
        this.titles = titles;
        this.rows = rows;
    }

    /**
     * 通过自定义 titles 和数据集,使用自定义 sheetName
     *
     * @param sheetName 页签名称
     * @param titles    表头
     * @param rows      数据源
     * @return
     * @author LiuYongTao
     * @date
     */
    public ExcelData(String sheetName, LinkedHashMap<String, String> titles, ArrayList<HashMap<String, Object>> rows) {
        this.sheetName = sheetName;
        this.titles = titles;
        this.rows = rows;
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<T> getObjData() {
        return objData;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public LinkedHashMap<String, String> getTitles() {
        return titles;
    }

    public ArrayList<HashMap<String, Object>> getRows() {
        return rows;
    }

    public int getEndDataIndex() {
        return endDataIndex;
    }

    public ExcelData setEndDataIndex(int endDataIndex) {
        this.endDataIndex = endDataIndex;
        return this;
    }

    public Map<String, HSSFCellStyle> getTitlesHSSFCellStyle() {
        return titlesHSSFCellStyle;
    }

    public ExcelData setTitlesHSSFCellStyle(Map<String, HSSFCellStyle> titlesHSSFCellStyle) {
        this.titlesHSSFCellStyle = titlesHSSFCellStyle;
        return this;
    }

    public Map<String, HSSFCellStyle> getRowsHSSFCellStyle() {
        return rowsHSSFCellStyle;
    }

    public ExcelData setRowsHSSFCellStyle(Map<String, HSSFCellStyle> rowsHSSFCellStyle) {
        this.rowsHSSFCellStyle = rowsHSSFCellStyle;
        return this;
    }

    private void checkData() {

    }
}