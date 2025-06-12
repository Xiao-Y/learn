package com.billow.system.pojo.excel;

import com.billow.excel.annotation.ExcelColumn;
import com.billow.excel.annotation.ExcelDict;
import com.billow.excel.annotation.ExcelSheet;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author billow
 * @since 2021-04-01
 */
@Data
@ExcelSheet(name = "菜单信息")
public class MenuExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelColumn(title = "菜单编号", required = true)
    private String menuCode;

    @ExcelColumn(title = "菜单名称", required = true)
    private String menuName;

    @ExcelColumn(title = "父ID", required = true)
    private Long pid;

    @ExcelColumn(title = "ICON", required = true)
    private String icon;

    @ExcelColumn(title = "描述", required = true)
    private String description;

    @ExcelColumn(title = "排序", required = true)
    private Double sortField;

    @ExcelColumn(title = "创建时间", format = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    @ExcelColumn(title = "更新时间", format = "yyyy-MM-dd")
    private Date updateTime;

    @ExcelColumn(title = "是否显示", required = true, dict = @ExcelDict(dictCode = "display", tranName = "displayName", dictType = ExcelDict.DictType.REDIS))
    private Boolean display;
    private String displayName;
}
