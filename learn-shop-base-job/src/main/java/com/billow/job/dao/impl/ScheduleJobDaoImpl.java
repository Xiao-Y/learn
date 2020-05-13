package com.billow.job.dao.impl;

import com.billow.job.common.CustomPage;
import com.billow.job.dao.ScheduleJobDao;
import com.billow.job.pojo.po.ScheduleJobPo;
import com.billow.job.util.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyongtao
 * @create 2019-12-20 15:47
 */
@Slf4j
public class ScheduleJobDaoImpl implements ScheduleJobDao {

    private static final String table = "sys_schedule_job";

    private static String columnSql = " creator_code,updater_code,create_time,update_time,valid_ind,job_name,job_group," +
            "job_status,cron_expression,description,class_type,run_class,http_url,routing_key,is_concurrent,method_name," +
            "is_exception_stop,is_save_log,is_send_mail,template_id,mail_receive ";

    private static String columnSql2 = " id,creator_code as creatorCode,updater_code as updaterCode,create_time as createTime," +
            "update_time as updateTime,valid_ind as validInd,job_name as jobName,job_group as jobGroup," +
            "job_status as jobStatus,cron_expression as cronExpression,description,class_type as classType," +
            "run_class as runClass,http_url as httpUrl,routing_key as routingKey,is_concurrent as isConcurrent," +
            "method_name as methodName, is_exception_stop as isExceptionStop,is_save_log as isSaveLog," +
            "is_send_mail as isSendMail,template_id as templateId,mail_receive as mailReceive ";

    private static String updateSql = " updater_code = ?,update_time = ?,valid_ind = ?,job_name = ?,job_group = ?," +
            "job_status = ?,cron_expression = ?,description = ?,class_type = ?,run_class = ?,http_url = ?,routing_key = ?,is_concurrent = ?,method_name = ?," +
            "is_exception_stop = ?,is_save_log = ?,is_send_mail = ?,template_id = ?,mail_receive = ? ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ScheduleJobPo findByIdAndValidIndIsTrueAndIsExceptionStopIsTrue(Long id) {
        String select_sql = "select " + columnSql2 + " from " + table + " where 1=1 ";

        List<Object> param = new ArrayList<>();

        ScheduleJobPo scheduleJobPo = new ScheduleJobPo();
        scheduleJobPo.setId(id);
        scheduleJobPo.setValidInd(true);
        scheduleJobPo.setIsExceptionStop(true);

        String sql = this.genQuery(scheduleJobPo, param);
        Object[] objects = param.toArray(new Object[param.size()]);
        String sqlPage = select_sql + sql;
        log.debug("sql:{}", sqlPage);
        return jdbcTemplate.queryForObject(sqlPage, objects, new BeanPropertyRowMapper<>(ScheduleJobPo.class));
    }

    @Override
    public int countByJobNameAndJobGroup(String jobName, String jobGroup) {
        String sql = "select count(1) from " + table + " where job_name = ? and job_group = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{jobName, jobGroup}, Integer.class);
    }

    @Override
    public List<ScheduleJobPo> findAll(ScheduleJobPo scheduleJobPo) {
        String select_sql = "select " + columnSql2 + " from " + table + " where 1=1 ";

        List<Object> param = new ArrayList<>();
        String sql = this.genQuery(scheduleJobPo, param);
        Object[] objects = param.toArray(new Object[param.size()]);
        String sqlPage = select_sql + sql;
        log.debug("sql:{}", sqlPage);
        List<ScheduleJobPo> scheduleJobPos = jdbcTemplate.query(sqlPage, objects, new BeanPropertyRowMapper<>(ScheduleJobPo.class));
        return scheduleJobPos;
    }

    @Override
    public ScheduleJobPo findById(Long id) {
        String select_sql = "select " + columnSql2 + " from " + table + " where 1=1 and id = ?";
        log.debug("sql:{}", select_sql);
        return jdbcTemplate.queryForObject(select_sql, new Object[]{id}, new BeanPropertyRowMapper<>(ScheduleJobPo.class));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from " + table + " where id = ?";
        log.debug("sql:{}", sql);
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ScheduleJobPo save(ScheduleJobPo scheduleJobPo) {
        String sql = "insert into " + table + "(" + columnSql + ") value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((con) -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, scheduleJobPo.getCreatorCode());
            ps.setString(2, scheduleJobPo.getUpdaterCode());
            ps.setObject(3, scheduleJobPo.getCreateTime());
            ps.setObject(4, scheduleJobPo.getUpdateTime());
            ps.setBoolean(5, scheduleJobPo.getValidInd());
            ps.setString(6, scheduleJobPo.getJobName());
            ps.setString(7, scheduleJobPo.getJobGroup());
            ps.setString(8, scheduleJobPo.getJobStatus());
            ps.setString(9, scheduleJobPo.getCronExpression());
            ps.setString(10, scheduleJobPo.getDescription());
            ps.setString(11, scheduleJobPo.getClassType());
            ps.setString(12, scheduleJobPo.getRunClass());
            ps.setString(13, scheduleJobPo.getHttpUrl());
            ps.setString(14, scheduleJobPo.getRoutingKey());
            ps.setString(15, scheduleJobPo.getIsConcurrent());
            ps.setString(16, scheduleJobPo.getMethodName());
            ps.setBoolean(17, scheduleJobPo.getIsExceptionStop());
            ps.setBoolean(18, scheduleJobPo.getIsSaveLog());
            ps.setString(19, scheduleJobPo.getIsSendMail());
            ps.setObject(20, scheduleJobPo.getTemplateId());
            ps.setString(21, scheduleJobPo.getMailReceive());
            return ps;
        }, keyHolder);
        scheduleJobPo.setId(keyHolder.getKey().longValue());
        return scheduleJobPo;
    }

    @Override
    public void updateById(ScheduleJobPo scheduleJobPo) {
        String sql = "update " + table + " set " + updateSql + " where id = ?";
        jdbcTemplate.update(sql, scheduleJobPo.getUpdaterCode(),
                scheduleJobPo.getUpdateTime(),
                scheduleJobPo.getValidInd(),
                scheduleJobPo.getJobName(),
                scheduleJobPo.getJobGroup(),

                scheduleJobPo.getJobStatus(),
                scheduleJobPo.getCronExpression(),
                scheduleJobPo.getDescription(),
                scheduleJobPo.getClassType(),
                scheduleJobPo.getRunClass(),
                scheduleJobPo.getHttpUrl(),
                scheduleJobPo.getRoutingKey(),
                scheduleJobPo.getIsConcurrent(),
                scheduleJobPo.getMethodName(),

                scheduleJobPo.getIsExceptionStop(),
                scheduleJobPo.getIsSaveLog(),
                scheduleJobPo.getIsSendMail(),
                scheduleJobPo.getTemplateId(),
                scheduleJobPo.getMailReceive(),
                scheduleJobPo.getId());

    }

    @Override
    public CustomPage<ScheduleJobPo> findByPage(ScheduleJobPo scheduleJobPo, Integer pageNo, Integer pageSize) {
        String select_sql = "select " + columnSql2 + " from " + table + " where 1=1 ";

        List<Object> param = new ArrayList<>();

        String sql = this.genQuery(scheduleJobPo, param);
        Object[] objects = param.toArray(new Object[param.size()]);

        String sqlPage = select_sql + sql + " limit " + pageSize + " offset " + pageSize * pageNo;
        log.debug("sql:{}", sqlPage);
        List<ScheduleJobPo> scheduleJobPos = jdbcTemplate.query(sqlPage, objects, new BeanPropertyRowMapper<>(ScheduleJobPo.class));

        String totalSql = "select count(1) from " + table + " where 1=1 " + sql;
        log.debug("totalSql:{}", totalSql);
        Integer total = jdbcTemplate.queryForObject(totalSql, objects, Integer.class);

        CustomPage<ScheduleJobPo> page = new CustomPage<>();
        page.setRecordCount(total);
        page.setTableData(scheduleJobPos);
        page.setTotalPages(ToolsUtils.getPages(pageSize, total));
        return page;
    }

    /**
     * 构建查询语句
     *
     * @param scheduleJobPo
     * @param queryParam
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/12/19 11:10
     */
    private String genQuery(ScheduleJobPo scheduleJobPo, List<Object> queryParam) {
        String sql = "";
        if (scheduleJobPo.getId() != null) {
            sql += " and id = ? ";
            queryParam.add(scheduleJobPo.getId());
        }
        if (ToolsUtils.isNotEmpty(scheduleJobPo.getJobName())) {
            sql += " and job_name like ? ";
            queryParam.add("%" + scheduleJobPo.getJobName() + "%");
        }
        if (ToolsUtils.isNotEmpty(scheduleJobPo.getJobGroup())) {
            sql += " and job_group like ? ";
            queryParam.add("%" + scheduleJobPo.getJobGroup() + "%");
        }
        if (ToolsUtils.isNotEmpty(scheduleJobPo.getMethodName())) {
            sql += " and method_name like ? ";
            queryParam.add("%" + scheduleJobPo.getMethodName() + "%");
        }
        if (ToolsUtils.isNotEmpty(scheduleJobPo.getClassType())) {
            sql += " and class_type = ? ";
            queryParam.add(scheduleJobPo.getClassType());
        }
        if (ToolsUtils.isNotEmpty(scheduleJobPo.getIsSendMail())) {
            sql += " and is_send_mail = ? ";
            queryParam.add(scheduleJobPo.getIsSendMail());
        }
        if (ToolsUtils.isNotEmpty(scheduleJobPo.getJobStatus())) {
            sql += " and job_status = ? ";
            queryParam.add(scheduleJobPo.getJobStatus());
        }
        if (scheduleJobPo.getIsExceptionStop() != null) {
            sql += " and is_exception_stop = ? ";
            queryParam.add(scheduleJobPo.getIsExceptionStop());
        }
        if (scheduleJobPo.getValidInd() != null) {
            sql += " and valid_ind = ? ";
            queryParam.add(scheduleJobPo.getValidInd());
        }
        if (scheduleJobPo.getJobStatus() != null) {
            sql += " and job_status = ? ";
            queryParam.add(scheduleJobPo.getJobStatus());
        }
        return sql;
    }
}
