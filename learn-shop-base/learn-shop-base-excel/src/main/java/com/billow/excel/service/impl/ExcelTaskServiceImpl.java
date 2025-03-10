package com.billow.excel.service.impl;

import com.billow.excel.model.ExcelTask;
import com.billow.excel.service.ExcelTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Excel任务服务实现
 */
@Service
@RequiredArgsConstructor
public class ExcelTaskServiceImpl implements ExcelTaskService {

    private final JdbcTemplate jdbcTemplate;

    private static final String TABLE_NAME = "t_excel_task";
    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + 
        " (task_id, type, file_name, status, total, success_count, error_count, error_message, file_path, create_time, update_time) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + 
        " SET status = ?, total = ?, success_count = ?, error_count = ?, error_message = ?, file_path = ?, update_time = ? " +
        "WHERE task_id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE task_id = ?";
    private static final String SELECT_BY_TYPE_STATUS_SQL = "SELECT * FROM " + TABLE_NAME + 
        " WHERE (? IS NULL OR type = ?) AND (? IS NULL OR status = ?) ORDER BY create_time DESC";
    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE task_id = ?";

    @Override
    public String createTask(ExcelTask task) {
        String taskId = UUID.randomUUID().toString();
        task.setTaskId(taskId)
            .setCreateTime(new Date())
            .setUpdateTime(new Date());

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
            task.getCreateTime(),
            task.getUpdateTime()
        );

        return taskId;
    }

    @Override
    public void updateTask(ExcelTask task) {
        task.setUpdateTime(new Date());

        jdbcTemplate.update(UPDATE_SQL,
            task.getStatus().name(),
            task.getTotal(),
            task.getSuccessCount(),
            task.getErrorCount(),
            task.getErrorMessage(),
            task.getFilePath(),
            task.getUpdateTime(),
            task.getTaskId()
        );
    }

    @Override
    public ExcelTask getTask(String taskId) {
        List<ExcelTask> tasks = jdbcTemplate.query(SELECT_BY_ID_SQL, new ExcelTaskRowMapper(), taskId);
        return tasks.isEmpty() ? null : tasks.get(0);
    }

    @Override
    public List<ExcelTask> listTasks(ExcelTask.TaskType type, ExcelTask.TaskStatus status) {
        String typeStr = type != null ? type.name() : null;
        String statusStr = status != null ? status.name() : null;
        return jdbcTemplate.query(SELECT_BY_TYPE_STATUS_SQL, new ExcelTaskRowMapper(), 
            typeStr, typeStr, statusStr, statusStr);
    }

    @Override
    public void deleteTask(String taskId) {
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
                .setCreateTime(rs.getTimestamp("create_time"))
                .setUpdateTime(rs.getTimestamp("update_time"));
        }
    }
} 