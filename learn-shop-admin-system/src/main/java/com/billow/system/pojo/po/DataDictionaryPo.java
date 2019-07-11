package com.billow.system.pojo.po;

import com.billow.common.base.pojo.BasePo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 数据字典
 *
 * @author liuyongtao
 * @create 2019-07-11 10:47
 */
@Data
@Entity
@Table(name = "sys_data_dictionary")
public class DataDictionaryPo extends BasePo implements Serializable {
    // 系统模块
    private String systemModule;
    // 下拉字段分类
    private String fieldType;
    // 显示的名称
    private String fieldDisplay;
    // 显示名称的值
    private String fieldValue;
    // 字段排序
    private Integer fieldOrder;
}
