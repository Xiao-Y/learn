package com.billow.sql.comon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.List;

/**
 * 执行SQL
 *
 * @author 千面
 * @date 2022/8/3 17:07
 */
@Slf4j
public class RunSqlUtils
{

    /**
     * 条件查询
     *
     * @param dataSource
     * @param sql
     * @param tClass
     * @return List<T>
     * @author 千面
     * @date 2022/8/3 16:35
     */
    public static <T> List<T> findList(DataSource dataSource, String sql, Class<T> tClass)
    {
        return DataSourceUtils.getJdbcTemplate(dataSource).query(sql, new BeanPropertyRowMapper<>(tClass));
    }

    /**
     * 条件查询
     *
     * @param dataSource
     * @param sql
     * @param tClass
     * @return List<T>
     * @author 千面
     * @date 2022/8/3 16:35
     */
    public static <T> T findOne(DataSource dataSource, String sql, Class<T> tClass)
    {
        return DataSourceUtils.getJdbcTemplate(dataSource).queryForObject(sql, new BeanPropertyRowMapper<>(tClass));
    }

    /**
     * 执行sql语句 增、删、改 等语句
     *
     * @param dataSource
     * @param sql
     * @return int
     * @author 千面
     * @date 2022/8/3 16:37
     */
    public static int update(DataSource dataSource, String sql)
    {
        return DataSourceUtils.getJdbcTemplate(dataSource).update(sql);
    }

    /**
     * 保存
     *
     * @param dataSource
     * @param sql
     * @return int
     * @author 千面
     * @date 2022/8/3 16:39
     */
    public static int save(DataSource dataSource, String sql)
    {
        return update(dataSource, sql);
    }

    /**
     * 删除
     *
     * @param dataSource
     * @param sql
     * @return int
     * @author 千面
     * @date 2022/8/3 16:39
     */
    public static int delete(DataSource dataSource, String sql)
    {
        return update(dataSource, sql);
    }
}
