package com.billow.sql.comon;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

/**
 * 数据源工具类
 *
 * @author 千面
 * @date 2022/8/3 17:07
 */
public class DataSourceUtils
{
    public static JdbcTemplate getJdbcTemplate(DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }

    public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(DataSource dataSource)
    {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * 开启事务
     *
     * @param dataSource
     * @param transactionManager
     * @return TransactionStatus
     * @author 千面
     * @date 2022/8/3 16:53
     */
    public static TransactionStatus getTransactionStatus(DataSource dataSource, DataSourceTransactionManager transactionManager)
    {
        transactionManager.setDataSource(dataSource);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        return status;
    }

    /**
     * 提交事务
     *
     * @param transactionManager
     * @param status
     * @return void
     * @author 千面
     * @date 2022/8/3 16:55
     */
    public static void commitTransaction(DataSourceTransactionManager transactionManager, TransactionStatus status)
    {
        transactionManager.commit(status);
    }

    /**
     * 回滚事务
     *
     * @param transactionManager
     * @param status
     * @return void
     * @author 千面
     * @date 2022/8/3 16:55
     */
    public static void rollbackTransaction(DataSourceTransactionManager transactionManager, TransactionStatus status)
    {
        transactionManager.rollback(status);
    }
}
