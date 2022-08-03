package com.billow.sql.comon;

import com.billow.sql.vo.TableFieldVo;
import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 构建SQL 语句
 *
 * @author 千面
 * @date 2022/8/3 17:07
 */
@Slf4j
public class BuildSqlUtils
{

    /**
     * 构建 删除 sql
     *
     * @param tableName     表名
     * @param conditionList 删除条件
     * @return String
     * @author 千面
     * @date 2022/6/15 15:09
     */
    public static String genDelSql(String tableName, List<TableFieldVo> conditionList)
    {
        // 构建sql
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        // 设置表名
        DbTable dbTable = schema.addTable(tableName);
        // 构建插入对象
        DeleteQuery deleteQuery = new DeleteQuery(dbTable);
        // 根据主键更新
        for (TableFieldVo tableField : conditionList)
        {
            DbColumn queryColumn = dbTable.addColumn(tableField.getDbFieldName(), tableField.getDbType().getCode(), null);
            deleteQuery.addCondition(BinaryCondition.equalTo(queryColumn, tableField.getObjValue()));
        }
        try
        {
            String s = deleteQuery.validate().toString();
            log.info("生成SQL:{}", s);
            return s;
        }
        catch (Exception e)
        {
            log.error("ModelQueryServiceImpl", e);
            throw e;
        }
    }

    /**
     * 构建 更新 sql
     *
     * @param tableName     表名
     * @param conditionList 更新条件
     * @param valueList     更新的字段
     * @return String
     * @author 千面
     * @date 2022/6/15 15:01
     */
    public static String genUpdateSql(String tableName, List<TableFieldVo> conditionList, List<TableFieldVo> valueList)
    {

        // 构建sql
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        // 设置表名
        DbTable dbTable = schema.addTable(tableName);
        // 构建插入对象
        UpdateQuery updateQuery = new UpdateQuery(dbTable);
        // 更新条件
        for (TableFieldVo tableField : conditionList)
        {
            DbColumn queryColumn = dbTable.addColumn(tableField.getDbFieldName(), tableField.getDbType().getCode(), null);
            updateQuery.addCondition(BinaryCondition.equalTo(queryColumn, tableField.getObjValue()));
        }
        // 设置需要更新的字段
        for (TableFieldVo tableField : valueList)
        {
            DbColumn dbColumn = dbTable.addColumn(tableField.getDbFieldName(), tableField.getDbType().getCode(), null);
            updateQuery.addSetClause(dbColumn, tableField.getObjValue());
        }
        try
        {
            String s = updateQuery.validate().toString();
            log.info("生成SQL:{}", s);
            return s;
        }
        catch (Exception e)
        {
            log.error("genUpdateSql", e);
            throw e;
        }
    }

    /**
     * 构建 保存 sql
     *
     * @param tableName 表名
     * @param valueList 保存值
     * @return String
     * @author 千面
     * @date 2022/6/15 11:05
     */
    public static String genSaveSql(String tableName, List<TableFieldVo> valueList)
    {
        // 构建sql
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        // 设置表名
        DbTable dbTable = schema.addTable(tableName);
        // 构建插入对象
        InsertQuery insertQuery = new InsertQuery(dbTable);
        // 设置参数
        for (TableFieldVo tableField : valueList)
        {
            // 页面取值
            if (tableField.getObjValue() != null)
            {
                // 构建列表
                DbColumn dbColumn = dbTable.addColumn(tableField.getDbFieldName(), tableField.getDbType().getCode(), null);
                insertQuery.addColumn(dbColumn, tableField.getObjValue());
            }
        }
        try
        {
            String s = insertQuery.validate().toString();
            log.info("生成SQL:{}", s);
            return s;
        }
        catch (Exception e)
        {
            log.error("genSaveSql", e);
            throw e;
        }
    }

    /**
     * 生成查询sql
     *
     * @param tableName     表名
     * @param conditionList 查询条件
     * @param selectColumn  查询的字段
     * @return void
     * @author 千面
     * @date 2022/6/17 11:02
     */
    public static String genSelectSql(String tableName, List<TableFieldVo> conditionList, TableFieldVo... selectColumn)
    {
        // 构建sql
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        // 设置表名
        DbTable dbTable = schema.addTable(tableName);
        // 构建查询sql
        SelectQuery selectQuery = new SelectQuery();
        // 添加查询的字段
        if (selectColumn != null)
        {
            for (TableFieldVo tableField : selectColumn)
            {
                DbColumn dbColumn = dbTable.addColumn(tableField.getDbFieldName(), tableField.getDbType().getCode(), null);
                selectQuery.addColumns(dbColumn);
            }
        }
        else
        {
            selectQuery.addAllTableColumns(dbTable);
        }

        for (TableFieldVo tableField : conditionList)
        {
            if (tableField.getObjValue() != null)
            {
                // 构建列表
                DbColumn dbColumn = dbTable.addColumn(tableField.getDbFieldName(), tableField.getDbType().getCode(), null);
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, tableField.getObjValue()));
            }
        }
        try
        {
            String s = selectQuery.validate().toString();
            log.info("生成SQL:{}", s);
            return s;
        }
        catch (Exception e)
        {
            log.error("genSelectSql", e);
            throw e;
        }
    }
}
