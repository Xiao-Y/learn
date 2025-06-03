package com.billow.system.pojo.excel;

import com.billow.excel.annotation.ExcelColumn;
import com.billow.excel.annotation.ExcelSheet;
import com.billow.mybatis.pojo.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author billow
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ExcelSheet(name = "菜单信息")
public class MenuExcel extends BasePo {

    private static final long serialVersionUID = 1L;

    @ExcelColumn(name = "菜单编号", required = true)
    private String menuCode;

    @ExcelColumn(name = "菜单名称", required = true)
    private String menuName;

    @ExcelColumn(name = "父ID", required = true)
    private Long pid;

    @ExcelColumn(name = "是否显示", required = true, dictCode = "display", dictType = ExcelColumn.DictType.REDIS)
    private Boolean display;

    @ExcelColumn(name = "ICON", required = true)
    private String icon;

    @ExcelColumn(name = "描述", required = true)
    private String description;

    @ExcelColumn(name = "排序", required = true)
    private Double sortField;


}
