-- ************************ 分表批量添加索引存储过程（支持原表+数字后缀分表+忽略单表错误+极简结果） ************************
-- 特性：
-- 1. 自动识别「原表名_纯数字」格式分表（数字不限位数）；
-- 2. 原表优先添加且必加（不排除）；
-- 3. 支持忽略单个分表添加失败（如索引已存在）；
-- 4. 结果极简打印（表XXX，添加XXX索引：成功/失败）；
-- 5. 分表按名称自然排序处理；
-- 6. 支持普通索引(INDEX)、唯一索引(UNIQUE)，支持复合索引；
-- 参数说明：
--   index_type：索引类型（index/unique，默认index）；
--   p_original_table：原表名（必传）；
--   index_name：索引名称（必传，建议规范命名如idx_字段名）；
--   index_columns：索引字段（必传，复合索引用逗号分隔如"user_id,order_id"）；
--   p_ignore_error：是否忽略单表失败（1=忽略，0=不忽略，默认0）
-- 调用示例1（添加普通索引）：CALL sp_shard_add_index('index', 'test_order', 'idx_user_id', 'user_id', 0);
-- 调用示例2（添加唯一索引）：CALL sp_shard_add_index('unique', 'test_order', 'uk_order_no', 'order_no', 1);
-- 调用示例3（添加复合索引）：CALL sp_shard_add_index('index', 'test_order', 'idx_user_time', 'user_id,create_time', 0);
-- 注意事项：1. 仅匹配「原表名_纯数字」格式分表；2. 原表必须存在；3. 索引名建议唯一，避免重复添加；
-- *****************************************************************************
DELIMITER //

DROP PROCEDURE IF EXISTS sp_shard_add_index //

CREATE PROCEDURE `sp_shard_add_index`(
    IN index_type VARCHAR(10),       -- 索引类型：index/unique
    IN p_original_table VARCHAR(128), -- 原表名（如test_order）
    IN index_name VARCHAR(128),      -- 索引名称（如idx_user_id）
    IN index_columns VARCHAR(500),   -- 索引字段（复合索引用逗号分隔：user_id,order_id）
    IN p_ignore_error TINYINT(1)     -- 是否忽略单个分表失败（1=忽略，0=不忽略，默认0）
)
BEGIN
    -- 声明变量
    DECLARE v_shard_table VARCHAR(162);   -- 分表名
    DECLARE v_table_exists INT DEFAULT 0; -- 表存在性标记
    DECLARE v_single_result VARCHAR(500); -- 单表操作结果
    DECLARE v_exec_summary TEXT DEFAULT ''; -- 汇总结果
    DECLARE v_error_flag INT DEFAULT 0;   -- 全局错误标记
    DECLARE v_shard_error INT DEFAULT 0;  -- 分表错误标记
    DECLARE v_handling_shard TINYINT(1) DEFAULT 0; -- 是否正在处理分表
    DECLARE v_handling_original TINYINT(1) DEFAULT 0; -- 是否正在处理原表
    DECLARE v_index_sql VARCHAR(1000);    -- 拼接后的索引添加SQL
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
ORDER BY TABLE_NAME ASC; -- 按表名自然排序
-- 游标异常处理器
DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = 1;

    -- 全局异常处理器（CONTINUE，避免终止存储过程）
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
BEGIN
GET DIAGNOSTICS CONDITION 1 @sql_error_msg = MESSAGE_TEXT;
-- 处理原表异常
IF v_handling_original = 1 THEN
            SET v_exec_summary = CONCAT('表', p_original_table, '，添加', index_name, '索引：失败，原因：', @sql_error_msg);
            SET v_error_flag = 1;
            IF p_ignore_error = 1 THEN
                SET v_exec_summary = CONCAT(v_exec_summary, '（忽略，继续分表）');
END IF;
            SET v_handling_original = 0;
        -- 处理分表异常
        ELSEIF v_handling_shard = 1 THEN
            IF p_ignore_error = 1 THEN
                SET v_exec_summary = CONCAT(v_exec_summary, CHAR(10), '表', v_shard_table, '，添加', index_name, '索引：失败（忽略），原因：', @sql_error_msg);
                SET v_shard_error = 1;
                SET v_handling_shard = 0;
ELSE
                SET v_exec_summary = CONCAT(v_exec_summary, CHAR(10), '表', v_shard_table, '，添加', index_name, '索引：失败（终止），原因：', @sql_error_msg);
                SET v_error_flag = 1;
                SET v_handling_shard = 0;
END IF;
        -- 全局异常
ELSE
            SET v_exec_summary = CONCAT('全局错误：', @sql_error_msg);
            SET v_error_flag = 1;
END IF;
END;

    -- 初始化参数
    SET index_type = IF(UPPER(index_type) = 'UNIQUE', 'UNIQUE', 'INDEX'); -- 统一索引类型格式
    SET p_ignore_error = IF(p_ignore_error IS NULL OR p_ignore_error NOT IN (0,1), 0, p_ignore_error);

    -- 基础参数校验
    IF TRIM(COALESCE(p_original_table, '')) = '' THEN
        SET v_exec_summary = '错误：原表名不能为空';
        SET v_error_flag = 1;
    ELSEIF TRIM(COALESCE(index_name, '')) = '' THEN
        SET v_exec_summary = '错误：索引名称不能为空';
        SET v_error_flag = 1;
    ELSEIF TRIM(COALESCE(index_columns, '')) = '' THEN
        SET v_exec_summary = '错误：索引字段不能为空';
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

    -- 第一步：优先处理原表（必加索引）
    IF v_error_flag = 0 THEN
        -- 标记：开始处理原表
        SET v_handling_original = 1;
        -- 拼接原表的索引添加SQL（移除注释逻辑）
        SET v_index_sql = CONCAT(
            'ALTER TABLE ', p_original_table,
            ' ADD ', index_type, ' ', index_name,
            ' (', index_columns, ')'
        );

        -- 执行原表索引添加（MySQL 原生方式，无局部EXCEPTION）
        SET @sql = v_index_sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 若未触发异常，标记原表处理成功
IF v_handling_original = 1 THEN
            SET v_exec_summary = CONCAT('表', p_original_table, '，添加', index_name, '索引：成功');
            SET v_handling_original = 0;
END IF;
END IF;

    -- 第二步：批量处理数字后缀分表
    IF (v_error_flag = 0) OR (v_error_flag = 1 AND p_ignore_error = 1) THEN
        OPEN shard_cursor;
        shard_loop: LOOP
            FETCH shard_cursor INTO v_shard_table;
            IF v_done = 1 THEN LEAVE shard_loop; END IF;

            -- 重置分表错误标记
            SET v_shard_error = 0;
            -- 标记：开始处理当前分表
            SET v_handling_shard = 1;

            -- 拼接分表的索引添加SQL（移除注释逻辑）
            SET v_index_sql = CONCAT(
                'ALTER TABLE ', v_shard_table,
                ' ADD ', index_type, ' ', index_name,
                ' (', index_columns, ')'
            );

            -- 执行分表索引添加
            SET @sql = v_index_sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 标记：结束处理当前分表（若未触发异常）
IF v_handling_shard = 1 THEN
                SET v_handling_shard = 0;
                -- 汇总分表操作结果
                SET v_single_result = CONCAT('表', v_shard_table, '，添加', index_name, '索引：成功');
                SET v_exec_summary = CONCAT(v_exec_summary, CHAR(10), v_single_result);
END IF;
END LOOP shard_loop;
CLOSE shard_cursor;

-- 无分表时的提示
IF NOT v_exec_summary LIKE CONCAT('%', CHAR(10), '表%，添加%索引：%') THEN
            SET v_exec_summary = CONCAT(v_exec_summary, CHAR(10), '提示：未识别到「', p_original_table, '_数字」格式的分表');
END IF;
END IF;

    -- 最终打印极简结果
SELECT v_exec_summary AS '批量添加索引结果';

END //

DELIMITER ;