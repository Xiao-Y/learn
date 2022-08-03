package com.billow.sql.comon;

import com.billow.sql.vo.TableFieldVo;
import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class BuildSqlUtils
{

    /**
     * 构建 删除 sql
     *
     * @param tableName
     * @param fieldList
     * @param paramMap
     * @return String
     * @author 千面
     * @date 2022/6/15 15:09
     */
    private String genDelSql(String tableName, List<TableFieldVo> fieldList, Map<String, Object> paramMap)
    {
        // 表的列名与类型
        Map<String, String> fieldNameAndTypeMap = fieldList.stream().collect(Collectors.toMap(TableFieldVo::getDbFieldName,
                TableFieldVo::getDbType));
        // 构建sql
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        // 设置表名
        DbTable dbTable = schema.addTable(tableName);
        // 构建插入对象
        DeleteQuery deleteQuery = new DeleteQuery(dbTable);
        // 根据主键更新
        for (TableFieldVo tableField : fieldList)
        {
            if (tableField.getDbIsKey())
            {
                String pk = tableField.getDbFieldName();
                DbColumn queryColumn = dbTable.addColumn(pk, fieldNameAndTypeMap.get(pk), null);
                deleteQuery.addCondition(BinaryCondition.equalTo(queryColumn, paramMap.get(pk)));
            }
        }
        String s = "";
        try
        {
            s = deleteQuery.validate().toString();
        }
        catch (Exception e)
        {
            log.error("ModelQueryServiceImpl", e);
        }
        log.info("数据模型SQL:{}", s);
        return s;
    }

    /**
     * 构建 更新 sql
     *
     * @param tableName
     * @param fieldList
     * @param paramMap
     * @return String
     * @author 千面
     * @date 2022/6/15 15:01
     */
    private String genUpdateSql(String tableName, Map<String, TableFieldVo> paramMap)
    {

        // 构建sql
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        // 设置表名
        DbTable dbTable = schema.addTable(tableName);
        // 构建插入对象
        UpdateQuery updateQuery = new UpdateQuery(dbTable);
        // 根据主键更新
        for (TableFieldVo tableField : fieldList)
        {
            if (tableField.getDbIsKey())
            {
                String pk = tableField.getDbFieldName();
                DbColumn queryColumn = dbTable.addColumn(pk, TableFieldVoMap.get(pk).getDbType(), null);
                updateQuery.addCondition(BinaryCondition.equalTo(queryColumn, paramMap.get(pk)));
            }
        }
        // 设置需要更新的字段
        for (Map.Entry<String, TableFieldVo> entry : paramMap.entrySet())
        {
            String dbFieldName = entry.getKey();
            DbColumn dbColumn = dbTable.addColumn(dbFieldName, TableFieldVoMap.get(dbFieldName).getDbType(), null);
            updateQuery.addSetClause(dbColumn, entry.getValue());
        }
        String s = "";
        try
        {
            s = updateQuery.validate().toString();
        }
        catch (Exception e)
        {
            log.error("ModelQueryServiceImpl", e);
        }
        log.info("数据模型SQL:{}", s);
        return s;
    }

    /**
     * 构建 保存 sql
     *
     * @param tableName
     * @param paramMap
     * @return String
     * @author 千面
     * @date 2022/6/15 11:05
     */
    private String genSaveSql(String tableName, Map<String, TableFieldVo> paramMap)
    {
        // 构建sql
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        // 设置表名
        DbTable dbTable = schema.addTable(tableName);
        // 构建插入对象
        InsertQuery insertQuery = new InsertQuery(dbTable);
        // 设置参数
        for (Map.Entry<String, TableFieldVo> entry : paramMap.entrySet())
        {
            // 页面取值
            TableFieldVo fieldVo = entry.getValue();
            if (fieldVo.getObjValue() != null)
            {
                // 构建列表
                DbColumn dbColumn = dbTable.addColumn(entry.getKey(), fieldVo.getDbType(), null);
                insertQuery.addColumn(dbColumn, fieldVo.getObjValue());
            }
        }
        String s = "";
        try
        {
            s = insertQuery.validate().toString();
        }
        catch (Exception e)
        {
            log.error("ModelQueryServiceImpl", e);
        }
        log.info("数据模型SQL:{}", s);
        return s;
    }

    /**
     * 生成校验唯一键是否冲突
     *
     * @param tableName
     * @param paramMap
     * @return void
     * @author 千面
     * @date 2022/6/17 11:02
     */
    private String genCheckUniqueIndex(String tableName, Map<String, TableFieldVo> paramMap)
    {
        // 构建sql
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();
        // 设置表名
        DbTable dbTable = schema.addTable(tableName);
        // 构建查询sql
        SelectQuery selectQuery = new SelectQuery()
                .addAllTableColumns(dbTable);

        for (Map.Entry<String, TableFieldVo> entry : paramMap.entrySet())
        {

            TableFieldVo fieldVo = entry.getValue();
            if (fieldVo.getObjValue() != null)
            {
                // 构建列表
                DbColumn dbColumn = dbTable.addColumn(entry.getKey(), fieldVo.getDbType(), null);
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, fieldVo.getObjValue()));
            }
        }
        String sql = selectQuery.validate().toString();
        log.info("生成通过唯一键查询数据sql:{}", sql);
        return sql;
    }
}
