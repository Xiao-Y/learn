package com.billow.search.pojo.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * canal 传到 MQ 中的数据模型
 *
 * @author liuyongtao
 * @since 2021-9-2 8:20
 */
@Data
public class CanalDbVo {

    /**
     * 消息id
     */
    private Long id;
    private Long es;
    private Long ts;

    /**
     * 操作的数据库
     */
    private String database;

    /**
     * 表名
     */
    private String table;

    /**
     * 操作类型：CREATE，QUERY，INSERT，UPDATE，DELETE，ALTER
     */
    private String type;

    /**
     * 主键
     */
    private List<String> pkNames;

    /**
     * 是否ddl语句（表结构变更）
     */
    private Boolean isDdl;

    /**
     * 插入、修改时的新数据
     */
    private Map<String, String> data;

    /**
     * 列名称和类型
     */
    private Map<String, String> mysqlType;

    /**
     * 列名称和类型代号
     */
    private Map<String, String> sqlType;

    /**
     * 修改前的旧数据
     */
    private List<String> old;

    /**
     * 执行 sql
     */
    private String sql;
}