-- ************************ 分表批量列操作存储过程（兼容单表+分表，支持add/modify/drop列） ************************
-- 特性：
-- 1. 兼容单表/分表操作：自动识别「原表名_纯数字」格式分表，原表优先且必处理；
-- 2. 支持列操作类型：add（新增列）、modify（修改列）、drop（删除列）；
-- 3. 精准处理默认值：NULL=不加默认值，''=空字符串默认值，非空=自动拼接DEFAULT子句；
-- 4. 异常机制：单表操作失败直接终止全量处理，输出具体失败原因；
-- 5. 结果极简：逐行输出每个表的操作结果（成功/无需处理/失败+原因）；
-- 6. 分表按名称自然排序处理；
--   operation_type：操作类型（add/modify/drop）；
--   p_original_table：原表名（必传，单表操作时即目标表）；
--   columnname：要操作的列名（必传）；
--   sqlstr：列定义（add/modify必填，如VARCHAR(50)、INT；drop传空）；
--   p_default_value：字段默认值（NULL=不加，''=空字符串，非空=自动拼接；drop传NULL）；
--   coldesc：列注释（add/modify必填；drop传空）；
-- 调用示例1（新增列+字符串默认值）：CALL sp_shard_column_work('add', 'test_order', 'user_name', 'VARCHAR(50)', '未知用户', '用户名称');
-- 调用示例2（修改列+数字默认值）：CALL sp_shard_column_work('modify', 'test_order', 'age', 'INT', 0, '用户年龄');
-- 调用示例3（删除列）：CALL sp_shard_column_work('drop', 'test_order', 'user_name', '', NULL, '');
-- 注意事项：1. 仅匹配「原表名_纯数字」格式分表；2. 原表必须存在；3. 单表失败直接终止；
-- *****************************************************************************
DELIMITER //

DROP PROCEDURE IF EXISTS sp_shard_column_work //

CREATE PROCEDURE `sp_shard_column_work`(
    IN operation_type VARCHAR(10),  -- add/modify/drop
    IN p_original_table VARCHAR(128), -- 原表名
    IN columnname VARCHAR(50),      -- 列名
    IN sqlstr VARCHAR(400),         -- 列定义（add/modify必填）
    IN p_default_value VARCHAR(200), -- 默认值
    IN coldesc VARCHAR(300)         -- 列注释（add/modify必填）
)
BEGIN
    -- 变量声明（移除p_ignore_error相关）
    DECLARE v_table_name VARCHAR(162);    -- 当前处理的表名（原表/分表）
    DECLARE v_table_exists INT DEFAULT 0; -- 表存在性标记
    DECLARE v_column_exists INT DEFAULT 0; -- 列存在性标记
    DECLARE v_single_result VARCHAR(500); -- 单表操作结果
    DECLARE v_exec_summary TEXT DEFAULT ''; -- 汇总结果
    DECLARE v_error_flag INT DEFAULT 0;   -- 全局错误标记
    DECLARE v_handling_table TINYINT(1) DEFAULT 0; -- 是否正在处理某张表
    DECLARE v_final_col_def VARCHAR(500); -- 拼接默认值后的列定义
    DECLARE v_alter_sql VARCHAR(2000);    -- 最终执行的ALTER SQL
    -- 游标变量（预加载分表列表）
    DECLARE v_done INT DEFAULT 0;
    DECLARE table_cursor CURSOR FOR
SELECT table_name FROM tmp_shard_tables ORDER BY table_name ASC;

-- 异常处理器（移除忽略失败逻辑，失败直接终止）
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
BEGIN
GET DIAGNOSTICS CONDITION 1 @sql_error_msg = MESSAGE_TEXT;
IF v_handling_table = 1 THEN
            SET v_single_result = CONCAT('表', v_table_name, '，', operation_type, ' ', columnname, '字段：失败（终止），原因：', @sql_error_msg);
            SET v_exec_summary = CONCAT(v_exec_summary, CHAR(10), v_single_result);
            SET v_error_flag = 1;
            SET v_handling_table = 0;
ELSE
            SET v_exec_summary = CONCAT('全局错误：', @sql_error_msg);
            SET v_error_flag = 1;
END IF;
END;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = 1;

    -- 初始化参数（移除p_ignore_error）
    SET coldesc = IF(coldesc IS NULL, '', TRIM(coldesc));

    -- 基础参数校验（移除p_ignore_error相关）
    IF UPPER(operation_type) NOT IN ('ADD', 'MODIFY', 'DROP') THEN
        SET v_exec_summary = '错误：操作类型仅支持add/modify/drop';
        SET v_error_flag = 1;
    ELSEIF TRIM(COALESCE(p_original_table, '')) = '' THEN
        SET v_exec_summary = '错误：原表名不能为空';
        SET v_error_flag = 1;
    ELSEIF TRIM(COALESCE(columnname, '')) = '' THEN
        SET v_exec_summary = '错误：列名不能为空';
        SET v_error_flag = 1;
    ELSEIF (UPPER(operation_type) IN ('ADD', 'MODIFY') AND TRIM(COALESCE(sqlstr, '')) = '') THEN
        SET v_exec_summary = '错误：add/modify操作必须指定列定义（sqlstr）';
        SET v_error_flag = 1;
ELSE
        -- 检查原表是否存在
SELECT COUNT(*) INTO v_table_exists
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = TRIM(p_original_table) AND TABLE_TYPE = 'BASE TABLE';
IF v_table_exists = 0 THEN
            SET v_exec_summary = CONCAT('错误：原表', p_original_table, '不存在');
            SET v_error_flag = 1;
END IF;
END IF;

    -- 预拼接列定义（仅执行1次）
    IF v_error_flag = 0 AND UPPER(operation_type) IN ('ADD', 'MODIFY') THEN
        SET v_final_col_def = sqlstr;
        IF p_default_value IS NOT NULL THEN
            -- 数字/NULL字符串：直接拼接；其他（含空字符串）：加单引号
            IF p_default_value REGEXP '^[0-9]+(\\.[0-9]+)?$' OR UPPER(p_default_value) = 'NULL' THEN
                SET v_final_col_def = CONCAT(v_final_col_def, ' DEFAULT ', p_default_value);
ELSE
                SET v_final_col_def = CONCAT(v_final_col_def, ' DEFAULT ''', p_default_value, '''');
END IF;
END IF;
ELSE
        SET v_final_col_def = sqlstr;
END IF;

    -- 预加载分表列表到临时表（保留效率优化）
    IF v_error_flag = 0 THEN
        DROP TEMPORARY TABLE IF EXISTS tmp_shard_tables;
        CREATE TEMPORARY TABLE tmp_shard_tables (table_name VARCHAR(162) PRIMARY KEY);
        -- 1. 插入原表（必处理）
INSERT INTO tmp_shard_tables VALUES (TRIM(p_original_table));
-- 2. 插入符合规则的分表
INSERT INTO tmp_shard_tables
SELECT TABLE_NAME
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_TYPE = 'BASE TABLE'
  AND TABLE_NAME REGEXP CONCAT('^', REPLACE(TRIM(p_original_table), '_', '\\_'), '_[0-9]+$');
END IF;

    -- 统一处理原表+分表（移除待处理列表、忽略失败逻辑）
    IF v_error_flag = 0 THEN
        OPEN table_cursor;
        table_loop: LOOP
            FETCH table_cursor INTO v_table_name;
            IF v_done = 1 THEN LEAVE table_loop; END IF;

            -- 重置标记
            SET v_handling_table = 1;
            SET v_column_exists = 0;

            -- 查询列存在性
SELECT COUNT(*) INTO v_column_exists
FROM information_schema.columns
WHERE table_schema = DATABASE() AND table_name = v_table_name AND column_name = columnname;

-- 拼接ALTER SQL + 容错处理（标记无需处理）
IF operation_type = 'add' THEN
                IF v_column_exists > 0 THEN
                    SET v_single_result = CONCAT('表', v_table_name, '，add ', columnname, '字段：无需处理（列已存在）');
                    SET v_handling_table = 0;
ELSE
                    SET v_alter_sql = CONCAT('ALTER TABLE ', v_table_name, ' ADD COLUMN ', columnname, ' ', v_final_col_def, ' COMMENT ''', coldesc, '''');
END IF;
            ELSEIF operation_type = 'modify' THEN
                IF v_column_exists = 0 THEN
                    SET v_single_result = CONCAT('表', v_table_name, '，modify ', columnname, '字段：无需处理（列不存在）');
                    SET v_handling_table = 0;
ELSE
                    SET v_alter_sql = CONCAT('ALTER TABLE ', v_table_name, ' MODIFY COLUMN ', columnname, ' ', v_final_col_def, ' COMMENT ''', coldesc, '''');
END IF;
            ELSEIF operation_type = 'drop' THEN
                IF v_column_exists = 0 THEN
                    SET v_single_result = CONCAT('表', v_table_name, '，drop ', columnname, '字段：无需处理（列不存在）');
                    SET v_handling_table = 0;
ELSE
                    SET v_alter_sql = CONCAT('ALTER TABLE ', v_table_name, ' DROP COLUMN ', columnname);
END IF;
END IF;

            -- 执行ALTER SQL（仅当需要处理时）
            IF v_handling_table = 1 THEN
                SET @sql = v_alter_sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 未触发异常则标记成功
IF v_handling_table = 1 THEN
                    SET v_single_result = CONCAT('表', v_table_name, '，', operation_type, ' ', columnname, '字段：成功');
                    SET v_handling_table = 0;
END IF;
END IF;

            -- 汇总结果
            SET v_exec_summary = CONCAT(v_exec_summary, CHAR(10), v_single_result);

            -- 失败直接终止循环
            IF v_error_flag = 1 THEN
                LEAVE table_loop;
END IF;
END LOOP table_loop;
CLOSE table_cursor;
DROP TEMPORARY TABLE IF EXISTS tmp_shard_tables;
END IF;

    -- 最终打印结果（无待处理列表）
SELECT v_exec_summary AS '批量列操作结果';

END //

DELIMITER ;