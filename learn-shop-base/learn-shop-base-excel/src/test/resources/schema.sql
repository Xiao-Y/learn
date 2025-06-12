-- Excel任务表
CREATE TABLE IF NOT EXISTS t_excel_task (
    task_id VARCHAR(50) NOT NULL COMMENT '任务ID',
    type VARCHAR(20) NOT NULL COMMENT '任务类型',
    file_name VARCHAR(200) COMMENT '文件名',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '任务状态',
    total INT DEFAULT 0 COMMENT '处理总数',
    success_count INT DEFAULT 0 COMMENT '成功数量',
    error_count INT DEFAULT 0 COMMENT '失败数量',
    error_message TEXT COMMENT '错误信息',
    file_path VARCHAR(500) COMMENT '文件路径',
    time_difference varchar(255) COMMENT '耗时',
    create_time TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    update_time TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
    PRIMARY KEY (task_id)
) COMMENT 'Excel任务表';

-- 数据字典表
CREATE TABLE IF NOT EXISTS t_sys_dict (
    dict_code VARCHAR(50) NOT NULL COMMENT '字典编码',
    dict_value VARCHAR(50) NOT NULL COMMENT '字典值',
    dict_label VARCHAR(100) NOT NULL COMMENT '字典标签',
    sort_order INT DEFAULT 0 COMMENT '排序号',
    PRIMARY KEY (dict_code, dict_value)
) COMMENT '数据字典表'; 
