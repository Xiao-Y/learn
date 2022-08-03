package com.billow.sql.comon;

import com.billow.sql.vo.TableFieldVo;
import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RunSqlUtils
{
    /**
     * 校验唯一键是否冲突
     *
     * @param tableName
     * @param TableFieldVoMap
     * @param paramMap
     * @return void
     * @author 千面
     * @date 2022/6/17 11:02
     */
    private void checkUniqueIndex(String tableName, List<String> tableIndexList, Map<String, TableFieldVo> TableFieldVoMap,
                                  Map<String, Object> paramMap)
    {
        // 校验唯一索引
        for (String tableIndex : tableIndexList)
        {
            // 唯一索引列表
            String[] indexFields = tableIndex.split(",");
            // 构建sql
            DbSpec spec = new DbSpec();
            DbSchema schema = spec.addDefaultSchema();
            // 设置表名
            DbTable dbTable = schema.addTable(tableName);
            // 构建查询sql
            SelectQuery selectQuery = new SelectQuery()
                    .addAllTableColumns(dbTable);
            // 用于主键冲突时提示信息
            List<String> ukList = new ArrayList<>();
            for (String indexField : indexFields)
            {
                // 构建列表
                DbColumn dbColumn = dbTable.addColumn(indexField, TableFieldVoMap.get(indexField).getDbType(), null);
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, paramMap.get(indexField)));
                // 用于主键冲突时提示信息
                ukList.add(TableFieldVoMap.get(indexField).getDbFieldRemark());
            }
            String sql = selectQuery.validate().toString();
            log.info("生成通过唯一键查询数据sql:{}", sql);
            // 查询出数据库中已经存在的
            Map<String, Object> resultMap = (Map<String, Object>) DynamicDBUtil.findOne(tableHead.getDataSourceCode(), sql);
            if (MapUtils.isEmpty(resultMap))
            {
                continue;
            }
            // 找出主键列
            for (Map.Entry<String, TableFieldVo> entry : TableFieldVoMap.entrySet())
            {
                if (!entry.getValue().getDbIsKey())
                {
                    continue;
                }
                if (paramMap.get(entry.getKey()) == null
                        || (paramMap.get(entry.getKey()) != null
                        && !Objects.equals(paramMap.get(entry.getKey()).toString(), resultMap.get(entry.getKey()).toString())))
                {

                    log.error("表：{}，唯一键冲突，ukColumn：{}", tableName, ukList);
                }
            }
        }
    }
}
