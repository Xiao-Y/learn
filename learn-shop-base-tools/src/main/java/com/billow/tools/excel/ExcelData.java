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
     * @param sheetName    页签名称
     * @param objData 数据源
     * @param clazz   数据模型
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
     * @param sheetName   页签名称
     * @param titles 表头
     * @param rows   数据源
     * @return
     * @author LiuYongTao
     * @date
     */
    public ExcelData(String sheetName, LinkedHashMap<String, String> titles, ArrayList<HashMap<String, Object>> rows) {
        this.sheetName = sheetName;
        this.titles = titles;
        this.rows = rows;
    }

    /**
     * 页签名称
     */
    private String sheetName = "Sheet1";
    /**
     * 数据源，必须要填写
     */
    private List<T> objData;
    /**
     * 数据模型，使用 @Title 时，必须要填写
     */
    private Class<?> clazz;
    /**
     * 数据模型，标题从第几行可以写，默认为第一行
     */
    private int titleIndex = 0;
    /**
     * 表头，非 @Title 时，必须要填写
     */
    private LinkedHashMap<String, String> titles;

    /**
     * 数据
     */
    private ArrayList<HashMap<String, Object>> rows;

    private Map<String, HSSFCellStyle> titlesHSSFCellStyle = new HashMap<>();
    private Map<String, HSSFCellStyle> rowsHSSFCellStyle = new HashMap<>();

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

    public int getTitleIndex() {
        return titleIndex;
    }

    public ExcelData setTitleIndex(int titleIndex) {
        this.titleIndex = titleIndex;
        return this;
    }

    public Map<String, HSSFCellStyle> getTitlesHSSFCellStyle() {
        return titlesHSSFCellStyle;
    }

    public void setTitlesHSSFCellStyle(Map<String, HSSFCellStyle> titlesHSSFCellStyle) {
        this.titlesHSSFCellStyle = titlesHSSFCellStyle;
    }

    public Map<String, HSSFCellStyle> getRowsHSSFCellStyle() {
        return rowsHSSFCellStyle;
    }

    public void setRowsHSSFCellStyle(Map<String, HSSFCellStyle> rowsHSSFCellStyle) {
        this.rowsHSSFCellStyle = rowsHSSFCellStyle;
    }
}