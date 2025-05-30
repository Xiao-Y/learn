package com.billow.excel.provider.impl;

import com.billow.excel.annotation.ExcelColumn.DictType;
import com.billow.excel.provider.DictProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库字典提供者
 */
@Order(3)
@RequiredArgsConstructor
public class DatabaseDictProvider implements DictProvider {
    
    private final JdbcTemplate jdbcTemplate;
    
    /**
     * 字典表结构：
     * CREATE TABLE t_sys_dict (
     *     dict_code VARCHAR(50) NOT NULL COMMENT '字典编码',
     *     dict_value VARCHAR(50) NOT NULL COMMENT '字典值',
     *     dict_label VARCHAR(100) NOT NULL COMMENT '字典标签',
     *     sort_order INT DEFAULT 0 COMMENT '排序号',
     *     PRIMARY KEY (dict_code, dict_value)
     * );
     */

    @Override
    public boolean support(String dictCode) {
        String sql = "SELECT COUNT(1) FROM t_sys_dict WHERE dict_code = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, dictCode);
        return count > 0;
    }

    @Override
    public Map<String, String> getDictData(String dictCode) {
        String sql = "SELECT dict_value, dict_label FROM t_sys_dict " +
                    "WHERE dict_code = ? ORDER BY sort_order";
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, dictCode);
        Map<String, String> dictMap = new HashMap<>();
        
        for (Map<String, Object> row : rows) {
            String value = String.valueOf(row.get("dict_value"));
            String label = String.valueOf(row.get("dict_label"));
            dictMap.put(value, label);
        }
        
        return dictMap;
    }

    @Override
    public DictType getType() {
        return DictType.DATABASE;
    }
} 
