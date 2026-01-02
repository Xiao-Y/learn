-- ************************ 分表批量修改表结构存储过程（支持原表+数字后缀分表+忽略单表错误+默认值自动拼接+极简结果） ************************
-- 特性：1. 自动识别「原表名_纯数字」格式分表（数字不限位数：2位/4位/6位等）；2. 原表优先修改且必改（不排除）；3. 支持忽略单个分表修改失败；4. 结果极简打印（表XXX，操作 XXX字段：成功/失败）；5. 分表按名称自然排序处理；6. 精准处理默认值（NULL=不加默认值，''=空字符串默认值，非空=自动拼接）
-- 参数说明：
--   operation_type：操作类型（add/modify/drop）；
--   p_original_table：原表名（必传）；
--   columnname：要操作的列名；
--   sqlstr：列定义（add/modify必填，如VARCHAR(50)、INT）；
--   p_default_value：字段默认值（NULL=不加默认值，''=空字符串默认值，非空=自动拼接DEFAULT子句）；
--   coldesc：列注释（add/modify必填）；
--   p_ignore_error：是否忽略单表失败（1=忽略，0=不忽略，默认0）
-- 调用示例1（新增字段+字符串默认值）：CALL sp_shard_column_work('add', 'test_order', 'user_name', 'VARCHAR(50)', '未知用户', '用户名称', 0);
-- 调用示例2（修改字段+数字默认值）：CALL sp_shard_column_work('modify', 'test_order', 'age', 'INT', 0, '用户年龄', 1);
-- 调用示例3（新增字段+空字符串默认值）：CALL sp_shard_column_work('add', 'test_order', 'remark', 'VARCHAR(200)', '', '备注', 0);
-- 调用示例4（新增字段+无默认值）：CALL sp_shard_column_work('add', 'test_order', 'phone', 'VARCHAR(20)', NULL, '手机号', 0);
-- 调用示例5（删除字段）：CALL sp_shard_column_work('drop', 'test_order', 'user_name', '', NULL, '', 0);
-- 注意事项：1. 仅匹配「原表名_纯数字」格式分表，含字母/符号的分表不处理；2. 原表必须存在，否则直接报错；3. drop操作时sqlstr/p_default_value/coldesc传空即可；4. 默认值为字符串且含单引号时，需手动转义（如“张三''s”）
-- *****************************************************************************
DELIMITER //

DROP PROCEDURE IF EXISTS sp_shard_column_work //

CREATE PROCEDURE `sp_shard_column_work`(
    IN operation_type VARCHAR(10),  -- add/modify/drop
    IN p_original_table VARCHAR(128), -- 原表名（如test_order）
    IN columnname VARCHAR(50),      -- 要操作的列名
    IN sqlstr VARCHAR(400),         -- 列定义（add/modify必填，如VARCHAR(50)、INT）
    IN p_default_value VARCHAR(200), -- 字段默认值（NULL=不加，''=空字符串，非空=自动拼接）
    IN coldesc VARCHAR(300),        -- 列注释（add/modify必填）
    IN p_ignore_error TINYINT(1)    -- 是否忽略单个分表修改失败（1=忽略，0=不忽略，默认0）
)
BEGIN
    -- 声明变量
    DECLARE v_shard_table VARCHAR(162);   -- 分表名
    DECLARE v_table_exists INT DEFAULT 0; -- 表存在性标记
    DECLARE v_single_result VARCHAR(100); -- 单表操作结果
    DECLARE v_exec_summary TEXT DEFAULT ''; -- 汇总结果
    DECLARE v_error_flag INT DEFAULT 0;   -- 全局错误标记
    DECLARE v_shard_error INT DEFAULT 0;  -- 分表错误标记
    DECLARE v_handling_shard TINYINT(1) DEFAULT 0; -- 是否正在处理分表
    DECLARE v_final_sqlstr VARCHAR(500);  -- 拼接默认值后的最终列定义
    -- 游标变量（仅匹配「原表名_纯数字」的分表，原表单独处理）
    DECLARE v_done INT DEFAULT 0;
    DECLARE shard_cursor CURSOR FOR
SELECT TABLE_NAME
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_TYPE = 'BASE TABLE'
  -- 核心规则：原表名 + 下划线 + 纯数字（不限位数）
  AND TABLE_NAME REGEXP CONCAT(
              '^', REPLACE(TRIM(p_original_table), '_', '\\_'), '_[0-9]+$'
          )
ORDER BY TABLE_NAME ASC; -- 按表名自然排序（如01→02→2026→202601）
-- 游标异常处理器
DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = 1;

    -- 全局异常处理器
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
GET DIAGNOSTICS CONDITION 1 @sql_error_msg = MESSAGE_TEXT;
IF v_handling_shard = 1 THEN
            IF p_ignore_error = 1 THEN
                SET v_exec_summary = CONCAT(v_exec_summary, CHAR(10), '表', v_shard_table, '，', operation_type, ' ', columnname, '字段：失败（忽略）');
                SET v_shard_error = 1;
                SET v_handling_shard = 0;
ELSE
                SET v_exec_summary = CONCAT(v_exec_summary, CHAR(10), '表', v_shard_table, '，', operation_type, ' ', columnname, '字段：失败，终止');
                SET v_error_flag = 1;
END IF;
ELSE
            SET v_exec_summary = CONCAT('全局错误：', @sql_error_msg);
            SET v_error_flag = 1;
END IF;
SELECT v_exec_summary AS '批量修改结果';
ROLLBACK;
END;

    -- 初始化参数（仅处理p_ignore_error，保留p_default_value的原始NULL状态）
    SET p_ignore_error = IF(p_ignore_error IS NULL OR p_ignore_error NOT IN (0,1), 0, p_ignore_error);

    -- 基础参数校验（极简）
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
        -- 检查原表是否存在（原表必须存在才继续）
SELECT COUNT(*) INTO v_table_exists
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = TRIM(p_original_table)
  AND TABLE_TYPE = 'BASE TABLE';
IF v_table_exists = 0 THEN
            SET v_exec_summary = CONCAT('错误：原表', p_original_table, '不存在');
            SET v_error_flag = 1;
END IF;
END IF;

    -- 拼接默认值到列定义（仅add/modify操作，精准处理NULL/空字符串/非空）
    IF v_error_flag = 0 AND UPPER(operation_type) IN ('ADD', 'MODIFY') THEN
        SET v_final_sqlstr = sqlstr;
        -- 仅当p_default_value不为NULL时，才拼接DEFAULT子句
        IF p_default_value IS NOT NULL THEN
            -- 区分值类型拼接：数字/NULL字符串 vs 普通字符串（含空字符串）
            IF p_default_value REGEXP '^[0-9]+(\\.[0-9]+)?$' OR UPPER(p_default_value) = 'NULL' THEN
                SET v_final_sqlstr = CONCAT(v_final_sqlstr, ' DEFAULT ', p_default_value);
ELSE
                -- 空字符串/普通字符串：加单引号（包括''）
                SET v_final_sqlstr = CONCAT(v_final_sqlstr, ' DEFAULT ''', p_default_value, '''');
END IF;
END IF;
ELSE
        SET v_final_sqlstr = sqlstr; -- DROP操作直接使用原sqlstr
END IF;

    -- 第一步：优先修改原表（核心：不排除原表，必改）
    IF v_error_flag = 0 THEN
        SET @detail_result = '';
        -- 调用基础表结构修改存储过程（传入拼接后的列定义）
CALL sp_column_work(operation_type, p_original_table, columnname, v_final_sqlstr, coldesc, @detail_result);
-- 简化结果判断
IF LEFT(@detail_result, 2) = '错误' OR LEFT(@detail_result, 4) = '执行失败' THEN
            SET v_exec_summary = CONCAT('表', p_original_table, '，', operation_type, ' ', columnname, '字段：失败');
            SET v_error_flag = 1;
            IF p_ignore_error = 1 THEN
                SET v_exec_summary = CONCAT(v_exec_summary, '（忽略，继续分表）');
END IF;
ELSE
            SET v_exec_summary = CONCAT('表', p_original_table, '，', operation_type, ' ', columnname, '字段：成功');
END IF;
END IF;

    -- 第二步：批量修改数字后缀分表（原表已单独改，此处仅改分表）
    IF (v_error_flag = 0) OR (v_error_flag = 1 AND p_ignore_error = 1) THEN
        OPEN shard_cursor;
        shard_loop: LOOP
            FETCH shard_cursor INTO v_shard_table;
            IF v_done = 1 THEN LEAVE shard_loop; END IF;

            -- 重置分表错误标记
            SET v_shard_error = 0;
            -- 标记：开始处理当前分表
            SET v_handling_shard = 1;

            -- 调用基础表结构修改存储过程（传入拼接后的列定义）
            SET @detail_result = '';
CALL sp_column_work(operation_type, v_shard_table, columnname, v_final_sqlstr, coldesc, @detail_result);

-- 标记：结束处理当前分表
SET v_handling_shard = 0;

            -- 汇总分表操作结果
            IF v_shard_error = 0 THEN
                IF LEFT(@detail_result, 2) = '错误' OR LEFT(@detail_result, 4) = '执行失败' THEN
                    SET v_single_result = CONCAT('表', v_shard_table, '，', operation_type, ' ', columnname, '字段：失败');
                    IF p_ignore_error = 1 THEN
                        SET v_single_result = CONCAT(v_single_result, '（忽略）');
ELSE
                        SET v_single_result = CONCAT(v_single_result, '，终止');
                        SET v_error_flag = 1;
END IF;
ELSE
                    SET v_single_result = CONCAT('表', v_shard_table, '，', operation_type, ' ', columnname, '字段：成功');
END IF;
                SET v_exec_summary = CONCAT(v_exec_summary, CHAR(10), v_single_result);
END IF;

            -- 不忽略错误且分表失败，终止循环
            IF p_ignore_error = 0 AND v_shard_error = 1 THEN
                LEAVE shard_loop;
END IF;
END LOOP shard_loop;
CLOSE shard_cursor;

-- 无分表时的提示（仅提示，不报错）
IF NOT v_exec_summary LIKE CONCAT('%', CHAR(10), '表%，% %字段：%') THEN
            SET v_exec_summary = CONCAT(v_exec_summary, CHAR(10), '提示：未识别到「', p_original_table, '_数字」格式的分表');
END IF;
END IF;

    -- 最终打印极简结果
SELECT v_exec_summary AS '批量修改结果';

END //

DELIMITER ;