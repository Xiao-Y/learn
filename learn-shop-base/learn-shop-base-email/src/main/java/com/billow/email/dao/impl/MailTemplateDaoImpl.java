package com.billow.email.dao.impl;

import com.billow.email.common.CustomPage;
import com.billow.email.dao.MailTemplateDao;
import com.billow.email.pojo.po.MailTemplatePo;
import com.billow.email.utils.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 邮件模板实现类
 *
 * @author liuyongtao
 * @create 2019-12-19 10:49
 */
@Slf4j
public class MailTemplateDaoImpl implements MailTemplateDao, Serializable {

    private static String select_sql = "select id, mail_code as mailCode, mail_type as mailType, data_sources as dataSources, " +
            "run_sql as runSql, mail_temp as mailTemp, mail_markdown as mailMarkdown, descritpion as descritpion, " +
            "to_emails as toEmails,subject as subject, template_name as templateName,single_result as singleResult," +
            "valid_ind as validInd, creator_code as creatorCode," +
            "updater_code as updaterCode, create_time as createTime, update_time as updateTime from sys_mail_template where 1=1 ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public MailTemplatePo findByMailCodeAndValidIndIsTrue(String mailCode) {
        List<Object> param = new ArrayList<>();
        MailTemplatePo mailTemplatePo = new MailTemplatePo();
        mailTemplatePo.setMailCode(mailCode);
        mailTemplatePo.setValidInd(true);
        String sql = this.genQuery(mailTemplatePo, param);
        Object[] objects = param.toArray(new Object[param.size()]);
        sql = select_sql + sql;
        log.debug("sql:{}", sql);
        return jdbcTemplate.queryForObject(sql, objects, new BeanPropertyRowMapper<>(MailTemplatePo.class));
    }

    @Override
    public Integer countByMailCodeIs(String mailCode) {
        String sql = "select count(1) from sys_mail_template where mail_code = ?";
        log.debug("sql:{}", sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{mailCode}, Integer.class);
    }

    @Override
    public MailTemplatePo findByIdAndValidIndIsTrue(Long id) {
        List<Object> param = new ArrayList<>();
        MailTemplatePo mailTemplatePo = new MailTemplatePo();
        mailTemplatePo.setId(id);
        mailTemplatePo.setValidInd(true);
        String sql = this.genQuery(mailTemplatePo, param);
        Object[] objects = param.toArray(new Object[param.size()]);
        sql = select_sql + sql;
        log.debug("sql:{}", sql);
        return jdbcTemplate.queryForObject(sql, objects, new BeanPropertyRowMapper<>(MailTemplatePo.class));
    }

    @Override
    public MailTemplatePo findById(Long id) {
        String sql = select_sql + " and id = ?";
        log.debug("sql:{}", sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(MailTemplatePo.class));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from sys_mail_template where id = ?";
        log.debug("sql:{}", sql);
        jdbcTemplate.update(sql, id);
    }

    @Override
    public MailTemplatePo save(MailTemplatePo mailTemplatePo) {
        String sql = "insert into sys_mail_template(mail_code, mail_type, data_sources, run_sql, mail_temp, mail_markdown, " +
                "descritpion, to_emails, subject, template_name, single_result, valid_ind, creator_code, " +
                "updater_code, create_time, update_time) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        log.debug("sql:{}", sql);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, mailTemplatePo.getMailCode());
                ps.setString(2, mailTemplatePo.getMailType());
                ps.setString(3, mailTemplatePo.getDataSources());
                ps.setString(4, mailTemplatePo.getRunSql());
                ps.setString(5, mailTemplatePo.getMailTemp());
                ps.setString(6, mailTemplatePo.getMailMarkdown());
                ps.setString(7, mailTemplatePo.getDescritpion());
                ps.setString(8, mailTemplatePo.getToEmails());
                ps.setString(9, mailTemplatePo.getSubject());
                ps.setString(10, mailTemplatePo.getTemplateName());
                ps.setBoolean(11, mailTemplatePo.getSingleResult());
//                ps.setBoolean(12, mailTemplatePo.getAttachment());
                ps.setBoolean(12, mailTemplatePo.getValidInd());
                ps.setString(13, mailTemplatePo.getCreatorCode());
                ps.setString(14, mailTemplatePo.getUpdaterCode());
                ps.setObject(15, mailTemplatePo.getCreateTime());
                ps.setObject(16, mailTemplatePo.getUpdateTime());
                return ps;
            }
        }, keyHolder);
        mailTemplatePo.setId(keyHolder.getKey().longValue());
        return mailTemplatePo;
    }

    @Override
    public void updateById(MailTemplatePo mailTemplatePo) {
        String sql = "update sys_mail_template set mail_code = ?, mail_type = ?, data_sources = ?, run_sql = ?, " +
                "mail_temp = ?, mail_markdown = ?, descritpion = ?, to_emails = ?, subject = ?, template_name = ?, " +
                "single_result = ?, valid_ind = ?,updater_code = ?, update_time = ? where id = ?";
        log.debug("sql:{}", sql);
        jdbcTemplate.update(sql, mailTemplatePo.getMailCode(),
                mailTemplatePo.getMailType(),
                mailTemplatePo.getDataSources(),
                mailTemplatePo.getRunSql(),
                mailTemplatePo.getMailTemp(),
                mailTemplatePo.getMailMarkdown(),
                mailTemplatePo.getDescritpion(),
                mailTemplatePo.getToEmails(),
                mailTemplatePo.getSubject(),
                mailTemplatePo.getTemplateName(),
                mailTemplatePo.getSingleResult(),
                mailTemplatePo.getValidInd(),
                mailTemplatePo.getUpdaterCode(),
                mailTemplatePo.getUpdateTime(),
                mailTemplatePo.getId());
    }

    @Override
    public CustomPage<MailTemplatePo> findAll(MailTemplatePo mailTemplatePo, Integer pageNo, Integer pageSize) {
        List<Object> param = new ArrayList<>();

        String sql = this.genQuery(mailTemplatePo, param);
        Object[] objects = param.toArray(new Object[param.size()]);

        String sqlPage = select_sql + sql + " limit " + pageSize + " offset " + pageSize * pageNo;
        log.debug("sql:{}", sqlPage);
        List<MailTemplatePo> mailTemplatePos = jdbcTemplate.query(sqlPage, objects, new BeanPropertyRowMapper<>(MailTemplatePo.class));

        String totalSql = "select count(1) from sys_mail_template where 1=1 " + sql;
        log.debug("totalSql:{}", totalSql);
        Integer total = jdbcTemplate.queryForObject(totalSql, objects, Integer.class);

        CustomPage<MailTemplatePo> page = new CustomPage<>();
        page.setRecordCount(total);
        page.setTableData(mailTemplatePos);
        page.setTotalPages(this.getPages(pageSize, total));
        return page;
    }

    @Override
    public Map<String, Object> runSqlSingleResult(String runSql) {
        return jdbcTemplate.queryForMap(runSql);
    }

    @Override
    public List<Map<String, Object>> runSqlResultList(String runSql) {
        return jdbcTemplate.queryForList(runSql);
    }

    /**
     * 构建查询语句
     *
     * @param mailTemplatePo
     * @param queryParam
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/12/19 11:10
     */
    private String genQuery(MailTemplatePo mailTemplatePo, List<Object> queryParam) {
        String sql = "";
        if (mailTemplatePo.getId() != null) {
            sql += " and id = ? ";
            queryParam.add(mailTemplatePo.getId());
        }
        if (ToolsUtils.isNotEmpty(mailTemplatePo.getMailCode())) {
            sql += " and mail_code = ? ";
            queryParam.add(mailTemplatePo.getMailCode());
        }
        if (ToolsUtils.isNotEmpty(mailTemplatePo.getMailType())) {
            sql += " and mail_type = ? ";
            queryParam.add(mailTemplatePo.getMailType());
        }
        if (mailTemplatePo.getValidInd() != null) {
            sql += " and valid_ind = ? ";
            queryParam.add(mailTemplatePo.getValidInd());
        }
        if (ToolsUtils.isNotEmpty(mailTemplatePo.getDataSources())) {
            sql += " and data_sources = ? ";
            queryParam.add(mailTemplatePo.getDataSources());
        }
        return sql;
    }

    /**
     * 计算总页数
     *
     * @param pageSize
     * @param total
     * @return int
     * @author LiuYongTao
     * @date 2019/12/19 15:56
     */
    private int getPages(Integer pageSize, Integer total) {
        if (pageSize == 0) {
            return 0;
        }
        int pages = total / pageSize;
        if (total % pageSize != 0) {
            pages++;
        }
        return pages;
    }
}
