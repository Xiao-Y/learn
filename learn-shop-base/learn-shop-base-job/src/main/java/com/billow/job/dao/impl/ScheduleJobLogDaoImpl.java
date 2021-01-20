package com.billow.job.dao.impl;

import com.billow.job.common.CustomPage;
import com.billow.job.dao.ScheduleJobLogDao;
import com.billow.job.pojo.po.ScheduleJobLogPo;
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
public class ScheduleJobLogDaoImpl implements ScheduleJobLogDao {

    private static final String table = "sys_schedule_job_log";

    private static String columnSql = "creator_code,updater_code,create_time,update_time,valid_ind,log_id,job_name,job_group,job_id,info,is_success,run_time ";

    private static String columnSql2 = "id,creator_code as creatorCode,updater_code as updaterCode,create_time as createTime," +
            "update_time as updateTime,valid_ind as validInd,log_id as logId,job_name as jobName,job_group as jobGroup," +
            "job_id as jobId,info,is_success as isSuccess,run_time as runTime ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ScheduleJobLogPo save(ScheduleJobLogPo scheduleJobLogPo) {
        String sql = "insert into " + table + "(" + columnSql + ") value(?,?,?,?,?,?,?,?,?,?,?,?)";
        log.debug("sql:{}", sql);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((con) -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, scheduleJobLogPo.getCreatorCode());
            ps.setString(2, scheduleJobLogPo.getUpdaterCode());
            ps.setObject(3, scheduleJobLogPo.getCreateTime());
            ps.setObject(4, scheduleJobLogPo.getUpdateTime());
            ps.setBoolean(5, scheduleJobLogPo.getValidInd());
            ps.setString(6, scheduleJobLogPo.getLogId());
            ps.setString(7, scheduleJobLogPo.getJobName());
            ps.setString(8, scheduleJobLogPo.getJobGroup());
            ps.setLong(9, scheduleJobLogPo.getJobId());
            ps.setString(10, scheduleJobLogPo.getInfo());
            ps.setBoolean(11, scheduleJobLogPo.getIsSuccess());
            ps.setString(12, scheduleJobLogPo.getRunTime());
            return ps;
        }, keyHolder);
        scheduleJobLogPo.setId(keyHolder.getKey().longValue());
        return scheduleJobLogPo;
    }

    @Override
    public CustomPage<ScheduleJobLogPo> findByPage(ScheduleJobLogPo scheduleJobLogPo, Integer pageNo, Integer pageSize) {
        String select_sql = "select " + columnSql2 + " from " + table + " where 1=1 ";

        List<Object> param = new ArrayList<>();

        String sql = this.genQuery(scheduleJobLogPo, param);
        Object[] objects = param.toArray(new Object[param.size()]);

        String sqlPage = select_sql + sql + " order by update_time limit " + pageSize + " offset " + pageSize * pageNo;
        log.debug("sql:{}", sqlPage);
        List<ScheduleJobLogPo> scheduleJobLogPos = jdbcTemplate.query(sqlPage, objects, new BeanPropertyRowMapper<>(ScheduleJobLogPo.class));

        String totalSql = "select count(1) from " + table + " where 1=1 " + sql;
        log.debug("totalSql:{}", totalSql);
        Integer total = jdbcTemplate.queryForObject(totalSql, objects, Integer.class);

        CustomPage<ScheduleJobLogPo> page = new CustomPage<>();
        page.setRecordCount(total);
        page.setTableData(scheduleJobLogPos);
        page.setTotalPages(ToolsUtils.getPages(pageSize, total));
        return page;
    }

    /**
     * 构建查询语句
     *
     * @param scheduleJobLogPo
     * @param queryParam
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/12/19 11:10
     */
    private String genQuery(ScheduleJobLogPo scheduleJobLogPo, List<Object> queryParam) {
        String sql = "";
        if (scheduleJobLogPo.getId() != null) {
            sql += " and id = ? ";
            queryParam.add(scheduleJobLogPo.getId());
        }
        if (ToolsUtils.isNotEmpty(scheduleJobLogPo.getJobName())) {
            sql += " and job_name = ? ";
            queryParam.add(scheduleJobLogPo.getJobName());
        }
        if (ToolsUtils.isNotEmpty(scheduleJobLogPo.getJobGroup())) {
            sql += " and job_group = ? ";
            queryParam.add(scheduleJobLogPo.getJobGroup());
        }
        if (scheduleJobLogPo.getLogId() != null) {
            sql += " and log_id = ? ";
            queryParam.add(scheduleJobLogPo.getLogId());
        }
        if (scheduleJobLogPo.getJobId() != null) {
            sql += " and job_id = ? ";
            queryParam.add(scheduleJobLogPo.getJobId());
        }
        if (scheduleJobLogPo.getIsSuccess() != null) {
            sql += " and is_success = ? ";
            queryParam.add(scheduleJobLogPo.getIsSuccess());
        }
        if (scheduleJobLogPo.getValidInd() != null) {
            sql += " and valid_ind = ? ";
            queryParam.add(scheduleJobLogPo.getValidInd());
        }
        return sql;
    }
}
