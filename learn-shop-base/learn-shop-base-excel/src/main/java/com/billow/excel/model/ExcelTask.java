package com.billow.excel.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * Excel导入导出任务
 */
@Data
@Accessors(chain = true)
public class ExcelTask {
    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务类型（IMPORT/EXPORT）
     */
    private TaskType type;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 任务状态
     */
    private TaskStatus status = TaskStatus.PENDING;

    /**
     * 处理总数
     */
    private Integer total;

    /**
     * 成功数量
     */
    private Integer successCount;

    /**
     * 失败数量
     */
    private Integer errorCount;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 数据类型
     */
    private Class<?> dataClass;

    /**
     * 数据列表
     */
    private List<?> data;

    /**
     * 任务类型枚举
     */
    public enum TaskType {
        IMPORT("导入"),
        EXPORT("导出");

        private final String desc;

        TaskType(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 任务状态枚举
     */
    public enum TaskStatus {
        PENDING("待处理"),
        PROCESSING("处理中"),
        COMPLETED("完成"),
        FAILED("失败");

        private final String desc;

        TaskStatus(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }
} 