package com.billow.system.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import java.io.Serial;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2025-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_schedule_job")
@Schema(title = "ScheduleJobPo对象", description="")
public class ScheduleJobPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "任务分组")
    @TableField("job_group")
    private String jobGroup;

    @Schema(title = "任务名称")
    @TableField("job_name")
    private String jobName;

    @Schema(title = "任务状态")
    @TableField("job_status")
    private String jobStatus;

    @Schema(title = "运行类型")
    @TableField("class_type")
    private String classType;

    @Schema(title = "Routing Key")
    @TableField("routing_key")
    private String routingKey;

    @Schema(title = "HTTP URL")
    @TableField("http_url")
    private String httpUrl;

    @Schema(title = "运行的类")
    @TableField("run_class")
    private String runClass;

    @Schema(title = "执行方法")
    @TableField("method_name")
    private String methodName;

    @Schema(title = "Cron表达式")
    @TableField("cron_expression")
    private String cronExpression;

    @Schema(title = "是否发送信息，0-不发送，1-全发送，2-异常时发送，3-成功时发送")
    @TableField("is_send_info")
    private String isSendInfo;

    @Schema(title = "发送放式，email-邮件，dingding-钉钉")
    @TableField("send_type")
    private String sendType;

    @Schema(title = "邮件模板")
    @TableField("template_id")
    private Long templateId;

    @Schema(title = "邮件接收人")
    @TableField("mail_receive")
    private String mailReceive;

    @Schema(title = "钉钉机器人Webhook")
    @TableField("ding_webhook")
    private String dingWebhook;

    @Schema(title = "钉钉机器人RobotKey")
    @TableField("ding_robot_key")
    private String dingRobotKey;

    @Schema(title = "是否串行，0-串行，1-并行")
    @TableField("is_concurrent")
    private String isConcurrent;

    @Schema(title = "异常是否停止，0-是，1-否")
    @TableField("is_exception_stop")
    private Boolean isExceptionStop;

    @Schema(title = "是否记录日志，0-是，1-否")
    @TableField("is_save_log")
    private Boolean isSaveLog;

    @Schema(title = "任务描述")
    @TableField("description")
    private String description;

    @Schema(title = "备注")
    @TableField("remark")
    private String remark;


}
