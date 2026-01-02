-- ************************ 通用分表存储过程（支持空后缀+单字段返回+指定起始序号+序号补零） ************************
-- 特性：1. p_suffix为空时不拼接后缀；2. 所有执行日志合并为单个字段返回；3. 支持指定分表起始序号（兼容低版本MySQL）；4. 序号自动补零为两位数
-- 参数说明：p_original_table：原表名；p_suffix：分表后缀（可选，为空则不拼接）；p_shard_count：分表数量（正整数）；p_start_shard：分表起始序号（不传则默认1）
-- 调用示例1（默认从1开始）：CALL sp_copy_table_shards('sys_oper_log', 'q', 4);
-- 调用示例2（空后缀+从2开始）：CALL sp_copy_table_shards('sys_oper_log', '', 4, 2);
-- 调用示例3（从5开始创建3个表）：CALL sp_copy_table_shards('sys_oper_log', 'm', 3, 5);
-- 调用示例4（2025后缀+12个表）：CALL sp_copy_table_shards('sys_oper_log', '2025', 12); -- 生成sys_oper_log_202501 ~ sys_oper_log_202512
-- *****************************************************************************

DELIMITER //

DROP PROCEDURE IF EXISTS sp_copy_table_shards //

CREATE PROCEDURE sp_copy_table_shards(
    IN p_original_table VARCHAR(128),  -- 原表名
    IN p_suffix VARCHAR(32),          -- 分表后缀（可选，为空则不拼接）
    IN p_shard_count INT UNSIGNED,    -- 分表数量（正整数）
    IN p_start_shard INT UNSIGNED     -- 分表起始序号（传 NULL 则默认1）
)
BEGIN
    -- 声明变量
    DECLARE v_current_shard INT;      -- 当前分片序号
    DECLARE v_start_shard INT;      -- 分表起始序号
    DECLARE v_original_table_quoted VARCHAR(130);
    DECLARE v_shard_table VARCHAR(162);  -- 不带反引号的表名，用于检查
    DECLARE v_shard_table_quoted VARCHAR(162);  -- 带反引号的表名，用于创建
    DECLARE v_create_sql TEXT;
    DECLARE v_table_exists INT DEFAULT 0;
    DECLARE v_created_or_existed VARCHAR(50);  -- 记录是创建还是已存在
    DECLARE v_error_msg VARCHAR(256);
    DECLARE v_exec_summary TEXT DEFAULT '';    -- 合并所有执行日志的变量
    DECLARE v_shard_num_str VARCHAR(2);       -- 格式化后的序号字符串（补零）
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
ROLLBACK;
RESIGNAL;
END;

    -- ************************ 参数预处理（核心：兼容默认值） ************************
    -- 处理起始序号默认值：如果p_start_shard为NULL（调用时不传），则设为0
    SET v_start_shard = IF(p_start_shard IS NULL OR p_start_shard < 0, 1, p_start_shard);
    SET v_current_shard = IF(p_start_shard IS NULL OR p_start_shard < 0, 0, p_start_shard);

    -- 给原表名加反引号
    SET v_original_table_quoted = CONCAT('`', TRIM(p_original_table), '`');

    -- 校验参数
    IF p_shard_count < 1 THEN
        SET v_error_msg = '错误：分表数量必须大于0！';
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = v_error_msg;
END IF;

    IF TRIM(COALESCE(p_original_table, '')) = '' THEN
        SET v_error_msg = '错误：原表名不能为空！';
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = v_error_msg;
END IF;

    -- ************************ 检查原表是否存在 ************************
SELECT COUNT(*) INTO v_table_exists
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = TRIM(p_original_table);

IF v_table_exists = 0 THEN
        SET v_error_msg = CONCAT('错误：原表「', p_original_table, '」在当前数据库中不存在！');
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = v_error_msg;
END IF;

    -- ************************ 循环创建分表（适配起始序号+序号补零） ************************
    -- 循环条件：从起始序号开始，创建p_shard_count个表
    WHILE (v_current_shard - v_start_shard) < p_shard_count DO
        -- 核心修改：将序号格式化为两位数（不足补0）
        SET v_shard_num_str = LPAD(v_current_shard, 2, '0');

        -- 拼接分表名：后缀为空则仅「原表名+补零序号」，否则「原表名+后缀+补零序号」
        IF TRIM(COALESCE(p_suffix, '')) = '' THEN
            SET v_shard_table = CONCAT(TRIM(p_original_table), '_', v_shard_num_str);
ELSE
            SET v_shard_table = CONCAT(TRIM(p_original_table), '_', TRIM(p_suffix), v_shard_num_str);
END IF;
        -- 给分表名加反引号（用于创建表）
        SET v_shard_table_quoted = CONCAT('`', v_shard_table, '`');

        -- 检查分表是否已存在
SELECT COUNT(*) INTO v_table_exists
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = v_shard_table;

-- 如果表不存在，则创建
IF v_table_exists = 0 THEN
            -- 生成建表SQL
            SET v_create_sql = CONCAT(
                'CREATE TABLE ', v_shard_table_quoted,
                ' LIKE ', v_original_table_quoted
            );

            -- 执行动态SQL
            SET @sql = v_create_sql;
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET v_created_or_existed = '（新创建）';
ELSE
            SET v_created_or_existed = '（已存在则跳过）';
END IF;

        -- 拼接当前分表的执行日志到汇总变量（换行分隔）
        SET v_exec_summary = CONCAT(
            v_exec_summary,
            '分表处理完成：', v_shard_table, ' ', v_created_or_existed,
            CHAR(10)  -- MySQL换行符，适配多数客户端
        );

        -- 序号自增
        SET v_current_shard = v_current_shard + 1;
END WHILE;

    -- 拼接最终结果到汇总变量（补充起始/结束序号）
    SET v_exec_summary = CONCAT(
        v_exec_summary,
        CHAR(10),
        '全部分表处理完成！原表：', p_original_table,
        IF(TRIM(COALESCE(p_suffix, '')) = '', '', CONCAT(' | 后缀：', p_suffix)),
        ' | 分表数量：', p_shard_count,
        ' | 起始序号：', v_start_shard,
        ' | 结束序号：', (v_start_shard + p_shard_count - 1)
    );

    -- 合并为单个字段返回
SELECT v_exec_summary AS exec_summary_msg;

END //

DELIMITER ;