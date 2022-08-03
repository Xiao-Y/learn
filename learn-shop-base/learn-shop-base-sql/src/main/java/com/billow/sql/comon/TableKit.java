package com.billow.sql.comon;

import com.billow.sql.constant.StConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/**
 * 表相关操作
 *
 * @author 千面
 * @date 2022/8/3 10:19
 */
@Slf4j
public class TableKit
{

    /**
     * 判断表是否存在
     *
     * @param jdbcTemplate
     * @param tableName
     * @return boolean
     * @author 千面
     * @date 2022/8/3 10:10
     */
    public static boolean judgeTableExist(JdbcTemplate jdbcTemplate, String tableName)
    {
        // 获取查询表是否存在的 st
        ST judgeTableExistSt = StConstants.COMMON_ST_GROUP.getInstanceOf(StConstants.commonStGroupTemplate.JUDGE_TABLE_EXISTS);

        // 1.判断是否有表
        judgeTableExistSt.remove(StConstants.tableNameParam.TABLE_NAME);
        judgeTableExistSt.add(StConstants.tableNameParam.TABLE_NAME, tableName);
        log.info("判断 {} 表是否存在SQL：\n{}", tableName, judgeTableExistSt.render());
        int haveDispatchOrderTable = jdbcTemplate.queryForObject(judgeTableExistSt.render(), Integer.class);
        log.info("{} 表是否存在：{}", tableName, haveDispatchOrderTable > 0);
        return haveDispatchOrderTable > 0;
    }

    /**
     * 获取表创建日期，格式：yyyyMMdd
     *
     * @param jdbcTemplate
     * @param tableName
     * @return String
     * @author 千面
     * @date 2022/8/3 10:13
     */
    public static String getCreateTableDate(JdbcTemplate jdbcTemplate, String tableName)
    {
        ST getTableCreateDateSt = StConstants.COMMON_ST_GROUP.getInstanceOf(StConstants.commonStGroupTemplate.GET_TABLE_CREATE_DATE);
        getTableCreateDateSt.remove(StConstants.tableNameParam.TABLE_NAME);
        getTableCreateDateSt.add(StConstants.tableNameParam.TABLE_NAME, tableName);
        log.info("获取 {} 表创建时间SQL：\n{}", tableName, getTableCreateDateSt.render());
        String tableCreateDateStr = jdbcTemplate.queryForObject(getTableCreateDateSt.render(), String.class);
        log.info("{} 表创建时间:{}", tableName, tableCreateDateStr);
        return tableCreateDateStr;
    }

    /**
     * 修改表名
     *
     * @param jdbcTemplate
     * @param oldTableName
     * @param newTableName
     * @return void
     * @author 千面
     * @date 2022/8/3 10:16
     */
    public static void editTableName(JdbcTemplate jdbcTemplate, String oldTableName, String newTableName)
    {
        ST alterTableNameSt = StConstants.COMMON_ST_GROUP.getInstanceOf(StConstants.commonStGroupTemplate.ALTER_TABLE_NAME);
        // 3.修改表名
        alterTableNameSt.remove(StConstants.alterTableNameParam.OLD_TABLE_NAME);
        alterTableNameSt.remove(StConstants.alterTableNameParam.NEW_TABLE_NAME);
        alterTableNameSt.add(StConstants.alterTableNameParam.OLD_TABLE_NAME, oldTableName);
        alterTableNameSt.add(StConstants.alterTableNameParam.NEW_TABLE_NAME, newTableName);
        log.info("修改 {} 表名为 {} SQL：\n{}", oldTableName, newTableName, alterTableNameSt.render());
        jdbcTemplate.execute(alterTableNameSt.render());
    }

    /**
     * 新建表
     *
     * @param stGroup             st 的分组
     * @param createTableTemplate 建表SQL模板
     * @param jdbcTemplate        jdbc操作
     * @return void
     * @author 千面
     * @date 2022/7/15 11:05
     */
    public static void createNewTable(STGroup stGroup, String createTableTemplate, JdbcTemplate jdbcTemplate)
    {
        ST createTableSt = stGroup.getInstanceOf(createTableTemplate);
        log.info("新建表SQL：\n{}", createTableSt.render());
        jdbcTemplate.execute(createTableSt.render());
    }

    /**
     * 备份老表，建新表
     *
     * @param tableName           操作的表
     * @param stGroup             st 的分组
     * @param createTableTemplate 建表SQL模板
     * @param dataSource          数据源
     * @param intervalTime        两表间隔时间（大于等于间隔时间时才会创建新表）
     * @return void
     * @author 千面
     * @date 2022/7/15 11:05
     */
    public static void createTable(String tableName, STGroup stGroup, String createTableTemplate,
                                   DataSource dataSource, int intervalTime)
    {
        createTable(tableName, stGroup, createTableTemplate, DataSourceUtils.getJdbcTemplate(dataSource), intervalTime);
    }

    /**
     * 备份老表，建新表
     *
     * @param tableName           操作的表
     * @param stGroup             st 的分组
     * @param createTableTemplate 建表SQL模板
     * @param jdbcTemplate        jdbc操作
     * @param intervalTime        两表间隔时间（大于等于间隔时间时才会创建新表）
     * @return void
     * @author 千面
     * @date 2022/7/15 11:05
     */
    public static void createTable(String tableName, STGroup stGroup, String createTableTemplate, JdbcTemplate jdbcTemplate, int intervalTime)
    {
        // 1.判断是否有表
        boolean tableExist = judgeTableExist(jdbcTemplate, tableName);
        // 表存在时
        if (tableExist)
        {
            // 2.日期比对
            // 获取当天的日期，格式：yyyyMMdd
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            LocalDate lastDayDate = LocalDate.parse(dateFormat.format(Calendar.getInstance().getTime()), DateTimeFormatter.ofPattern("yyyyMMdd"));
            // 获取表创建日期，格式：yyyyMMdd (因为定时任务是隔天凌晨运行的，所以表的创建日期实际比录入数据日期要多一天)
            String tableCreateDateStr = getCreateTableDate(jdbcTemplate, tableName);

            // 计算日期差
            LocalDate tableCreateDate = LocalDate.parse(tableCreateDateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
            // lastDayDate - tableCreateDate
            long days = ChronoUnit.DAYS.between(tableCreateDate, lastDayDate);

            // intervalTime 转存
            if (days >= intervalTime)
            {
                String newTableName = tableName + "_" + tableCreateDateStr;
                editTableName(jdbcTemplate, tableName, newTableName);
                // 4.建表
                createNewTable(stGroup, createTableTemplate, jdbcTemplate);
            }
        }
        else
        {
            createNewTable(stGroup, createTableTemplate, jdbcTemplate);
        }
    }
}
