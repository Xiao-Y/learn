package com.billow.excel.service.impl;

import com.billow.excel.model.ExcelTask;
import com.billow.excel.service.ExcelTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Excel任务服务实现
 */
@Slf4j
@RequiredArgsConstructor
public class ExcelTaskServiceImpl implements ExcelTaskService {

    private static final String TABLE_NAME = "t_excel_task";
    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME +
            " (task_id, type, file_name, status, total, success_count, error_count, error_message, file_path, time_difference, create_time, update_time) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME +
            " SET status = ?, total = ?, success_count = ?, error_count = ?, error_message = ?, file_path = ?, time_difference = ? , update_time = ?" +
            "WHERE task_id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE task_id = ?";
    private static final String SELECT_BY_TYPE_STATUS_SQL = "SELECT * FROM " + TABLE_NAME +
            " WHERE (? IS NULL OR type = ?) AND (? IS NULL OR status = ?) ORDER BY create_time DESC";
    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE task_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String createTask(ExcelTask task) {
        log.debug("INSERT_SQL:{}", INSERT_SQL);
        jdbcTemplate.update(INSERT_SQL,
                task.getTaskId(),
                task.getType().name(),
                task.getFileName(),
                task.getStatus().name(),
                task.getTotal(),
                task.getSuccessCount(),
                task.getErrorCount(),
                task.getErrorMessage(),
                task.getFilePath(),
                task.getTimeDifference(),
                task.getCreateTime(),
                task.getUpdateTime()
        );
        return task.getTaskId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateTask(ExcelTask task) {
        log.debug("UPDATE_SQL:{}", UPDATE_SQL);
        jdbcTemplate.update(UPDATE_SQL,
                task.getStatus().name(),
                task.getTotal(),
                task.getSuccessCount(),
                task.getErrorCount(),
                task.getErrorMessage(),
                task.getFilePath(),
                task.getTimeDifference(),
                task.getUpdateTime(),
                task.getTaskId()
        );
    }

    @Override
    public ExcelTask getTask(String taskId) {
        log.debug("SELECT_BY_ID_SQL:{}", SELECT_BY_ID_SQL);
        List<ExcelTask> tasks = jdbcTemplate.query(SELECT_BY_ID_SQL, new ExcelTaskRowMapper(), taskId);
        return tasks.isEmpty() ? null : tasks.get(0);
    }

    @Override
    public List<ExcelTask> listTasks(ExcelTask.TaskType type, ExcelTask.TaskStatus status) {
        log.debug("SELECT_BY_TYPE_STATUS_SQL:{}", SELECT_BY_TYPE_STATUS_SQL);
        String typeStr = type != null ? type.name() : null;
        String statusStr = status != null ? status.name() : null;
        return jdbcTemplate.query(SELECT_BY_TYPE_STATUS_SQL, new ExcelTaskRowMapper(),
                typeStr, typeStr, statusStr, statusStr);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deleteTask(String taskId) {
        log.debug("DELETE_SQL:{}", DELETE_SQL);
        jdbcTemplate.update(DELETE_SQL, taskId);
    }

    private static class ExcelTaskRowMapper implements RowMapper<ExcelTask> {
        @Override
        public ExcelTask mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ExcelTask()
                    .setTaskId(rs.getString("task_id"))
                    .setType(ExcelTask.TaskType.valueOf(rs.getString("type")))
                    .setFileName(rs.getString("file_name"))
                    .setStatus(ExcelTask.TaskStatus.valueOf(rs.getString("status")))
                    .setTotal(rs.getInt("total"))
                    .setSuccessCount(rs.getInt("success_count"))
                    .setErrorCount(rs.getInt("error_count"))
                    .setErrorMessage(rs.getString("error_message"))
                    .setFilePath(rs.getString("file_path"))
                    .setTimeDifference(rs.getString("time_difference"))
                    .setCreateTime(rs.getTimestamp("create_time"))
                    .setUpdateTime(rs.getTimestamp("update_time"));
        }
    }
} 