package com.billow.sql.comon;

import com.billow.sql.constant.StConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

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
        ST st = StConstants.COMMON_ST_GROUP.getInstanceOf(StConstants.commonStGroupTemplate.JUDGE_TABLE_EXISTS);

        // 移除模板的参数。
        st.remove(StConstants.tableNameParam.TABLE_NAME);
        // 添加模板的参数。
        st.add(StConstants.tableNameParam.TABLE_NAME, tableName);
        log.info("判断 {} 表是否存在SQL：\n{}", tableName, st.render());
        int haveDispatchOrderTable = jdbcTemplate.queryForObject(st.render(), Integer.class);
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
        ST st = StConstants.COMMON_ST_GROUP.getInstanceOf(StConstants.commonStGroupTemplate.GET_TABLE_CREATE_DATE);
        // 1.判断是否有表
        boolean tableExist = judgeTableExist(jdbcTemplate, tableName);
        // 表不存在时
        if (!tableExist)
        {
            return "";
        }

        st.remove(StConstants.tableNameParam.TABLE_NAME);
        st.add(StConstants.tableNameParam.TABLE_NAME, tableName);
        log.info("获取 {} 表创建时间SQL：\n{}", tableName, st.render());
        String tableCreateDateStr = jdbcTemplate.queryForObject(st.render(), String.class);
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
    public static boolean editTableName(JdbcTemplate jdbcTemplate, String oldTableName, String newTableName)
    {
        ST st = StConstants.COMMON_ST_GROUP.getInstanceOf(StConstants.commonStGroupTemplate.ALTER_TABLE_NAME);

        // 1.判断是否有表
        boolean tableExist = judgeTableExist(jdbcTemplate, oldTableName);
        // 表不存在时
        if (!tableExist)
        {
            return false;
        }
        boolean newTableExist = judgeTableExist(jdbcTemplate, newTableName);
        // 表存在时
        if (newTableExist)
        {
            return false;
        }
        // 3.修改表名
        st.remove(StConstants.alterTableNameParam.OLD_TABLE_NAME);
        st.remove(StConstants.alterTableNameParam.NEW_TABLE_NAME);
        st.add(StConstants.alterTableNameParam.OLD_TABLE_NAME, oldTableName);
        st.add(StConstants.alterTableNameParam.NEW_TABLE_NAME, newTableName);
        log.info("修改 {} 表名为 {} SQL：\n{}", oldTableName, newTableName, st.render());
        jdbcTemplate.execute(st.render());
        return true;
    }

    /**
     * 清空表数据
     *
     * @param jdbcTemplate
     * @param tableName
     * @return void
     * @author 千面
     * @date 2022/8/3 10:16
     */
    public static boolean truncateTableName(JdbcTemplate jdbcTemplate, String tableName)
    {
        ST st = StConstants.COMMON_ST_GROUP.getInstanceOf(StConstants.commonStGroupTemplate.TRUNCATE_TABLE_NAME);
        // 1.判断是否有表
        boolean tableExist = judgeTableExist(jdbcTemplate, tableName);
        // 表不存在时
        if (tableExist)
        {
            return false;
        }

        // 3.修改表名
        st.remove(StConstants.tableNameParam.TABLE_NAME);
        st.add(StConstants.tableNameParam.TABLE_NAME, tableName);
        log.info("清空表 {}，SQL：\n{}", tableName, st.render());
        jdbcTemplate.execute(st.render());
        return true;
    }

    /**
     * 新建表
     *
     * @param stGroup             st 的分组
     * @param createTableTemplate 建表SQL模板
     * @param jdbcTemplate        jdbc操作
     * @return boolean 是否创建成功
     * @author 千面
     * @date 2022/7/15 11:05
     */
    public static boolean createNewTable(STGroup stGroup, String createTableTemplate, JdbcTemplate jdbcTemplate)
    {
        ST st = stGroup.getInstanceOf(createTableTemplate);
        log.info("新建表SQL：\n{}", st.render());
        String tableName = getTableName(st.render());
        // 1.判断是否有表
        boolean tableExist = judgeTableExist(jdbcTemplate, tableName);
        // 表不存在时
        if (!tableExist)
        {
            jdbcTemplate.execute(st.render());
        }
        return !tableExist;
    }

    /**
     * 备份老表，建新表
     *
     * @param stGroup             st 的分组
     * @param createTableTemplate 建表SQL模板
     * @param jdbcTemplate        jdbc操作
     * @param intervalTime        两表间隔时间（大于等于间隔时间时才会创建新表）
     * @return int 0-未改变，1-新建表，2-备份表，新建表
     * @author 千面
     * @date 2022/7/15 11:05
     */
    public static int createTable(STGroup stGroup, String createTableTemplate, JdbcTemplate jdbcTemplate, int intervalTime)
    {
        // 获取建表时的 表名
        ST st = stGroup.getInstanceOf(createTableTemplate);
        String tableName = getTableName(st.render());

        // 1.判断是否有表，如果不存在新建一个
        boolean success = createNewTable(stGroup, createTableTemplate, jdbcTemplate);
        // 表不存在时
        if (success)
        {
            return 1;
        }

        // 表存在时
        // 日期比对。获取当天的日期，格式：yyyyMMdd
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
            // 修改表名
            editTableName(jdbcTemplate, tableName, tableName + "_" + tableCreateDateStr);
            // 建新表
            createNewTable(stGroup, createTableTemplate, jdbcTemplate);
            return 2;
        }
        return 0;
    }

    /**
     * 获取表名
     *
     * @param createTableSql
     * @return String
     * @author 千面
     * @date 2022/8/3 18:36
     */
    private static String getTableName(String createTableSql)
    {
        return createTableSql.substring(0, createTableSql.indexOf("("))
                .toLowerCase()
                .replace("create table", "")
                .replaceAll("`", "")
                .trim();
    }
}
