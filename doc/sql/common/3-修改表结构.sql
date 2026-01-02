DELIMITER //

DROP PROCEDURE IF EXISTS sp_column_work //

CREATE PROCEDURE `sp_column_work`(
    IN operation_type VARCHAR(10),  -- 操作类型（'add'=新增列，'modify'=修改列，'drop'=删除列）
    IN tablename VARCHAR(50),       -- 表名（必填）
    IN columnname VARCHAR(50),      -- 列名（必填）
    IN sqlstr VARCHAR(400),         -- 列定义字符串（add/modify必填，如VARCHAR(50) NOT NULL DEFAULT ''）
    IN coldesc VARCHAR(300),        -- 列注释（add/modify必填，drop可传空）
    OUT result_msg VARCHAR(500)     -- 返回结果信息
)
BEGIN
    -- 声明变量
    DECLARE rows1 INT DEFAULT 0;          -- 字段存在性计数
    DECLARE table_exists INT DEFAULT 0;   -- 表存在性计数
    DECLARE exit_flag INT DEFAULT 0;      -- 退出标记（1=异常/不满足条件）
    DECLARE sql_error_msg TEXT;           -- 异常信息
    DECLARE safe_tablename VARCHAR(60);   -- 加反引号的安全表名
    DECLARE safe_columnname VARCHAR(60);  -- 加反引号的安全列名
    DECLARE final_sql TEXT;               -- 最终执行的SQL

    -- 1. 异常处理器：捕获SQL异常，记录错误信息
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
GET DIAGNOSTICS CONDITION 1 sql_error_msg = MESSAGE_TEXT;
SET result_msg = CONCAT('执行失败: ', sql_error_msg);
        -- 释放预处理语句（避免内存泄漏）
        IF @sql1 IS NOT NULL THEN
            DEALLOCATE PREPARE stmt1;
END IF;
ROLLBACK;
END;

    -- 2. 初始化返回结果
    SET result_msg = '';
    SET safe_tablename = CONCAT('`', REPLACE(tablename, '`', '``'), '`');  -- 转义反引号，防止注入
    SET safe_columnname = CONCAT('`', REPLACE(columnname, '`', '``'), '`');

    -- 3. 基础参数校验（非空+合法操作类型）
    -- 3.1 校验表名/列名非空
    IF TRIM(COALESCE(tablename, '')) = '' THEN
        SET result_msg = '错误: 表名不能为空';
        SET exit_flag = 1;
    ELSEIF TRIM(COALESCE(columnname, '')) = '' THEN
        SET result_msg = '错误: 列名不能为空';
        SET exit_flag = 1;
    -- 3.2 校验操作类型（兼容大小写）
    ELSEIF UPPER(operation_type) NOT IN ('ADD', 'MODIFY', 'DROP') THEN
        SET result_msg = CONCAT('错误: 无效的操作类型「', operation_type, '」，仅支持add/modify/drop');
        SET exit_flag = 1;
END IF;

    -- 4. 校验表是否存在（若参数校验通过）
    IF exit_flag = 0 THEN
SELECT COUNT(*) INTO table_exists
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = TRIM(tablename);

IF table_exists = 0 THEN
            SET result_msg = CONCAT('错误: 表「', tablename, '」在当前数据库中不存在');
            SET exit_flag = 1;
END IF;
END IF;

    -- 5. 校验字段是否存在（若表存在）
    IF exit_flag = 0 THEN
SELECT COUNT(*) INTO rows1
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = TRIM(tablename)
  AND COLUMN_NAME = TRIM(columnname);
END IF;

    -- 6. 按操作类型拼接SQL
    IF exit_flag = 0 THEN
        CASE UPPER(operation_type)
            -- 6.1 新增列（字段不存在）
            WHEN 'ADD' THEN
                IF rows1 > 0 THEN
                    SET result_msg = CONCAT('错误: 列「', columnname, '」已存在于表「', tablename, '」中，无法新增');
                    SET exit_flag = 1;
ELSE
                    -- 校验sqlstr/coldesc非空
                    IF TRIM(COALESCE(sqlstr, '')) = '' THEN
                        SET result_msg = '错误: 新增列时sqlstr（列定义）不能为空（如VARCHAR(50)）';
                        SET exit_flag = 1;
                    ELSEIF TRIM(COALESCE(coldesc, '')) = '' THEN
                        SET result_msg = '错误: 新增列时coldesc（列注释）不能为空';
                        SET exit_flag = 1;
ELSE
                        -- 转义注释中的单引号，避免语法错误
                        SET coldesc = REPLACE(coldesc, '''', '\\''');
                        SET final_sql = CONCAT(
                            'ALTER TABLE ', safe_tablename,
                            ' ADD COLUMN ', safe_columnname, ' ', sqlstr,
                            ' COMMENT ''', coldesc, ''''
                        );
END IF;
END IF;

            -- 6.2 修改列（字段存在）
WHEN 'MODIFY' THEN
                IF rows1 <= 0 THEN
                    SET result_msg = CONCAT('错误: 列「', columnname, '」在表「', tablename, '」中不存在，无法修改');
                    SET exit_flag = 1;
ELSE
                    -- 校验sqlstr/coldesc非空
                    IF TRIM(COALESCE(sqlstr, '')) = '' THEN
                        SET result_msg = '错误: 修改列时sqlstr（列定义）不能为空（如VARCHAR(100)）';
                        SET exit_flag = 1;
                    ELSEIF TRIM(COALESCE(coldesc, '')) = '' THEN
                        SET result_msg = '错误: 修改列时coldesc（列注释）不能为空';
                        SET exit_flag = 1;
ELSE
                        -- 转义注释中的单引号
                        SET coldesc = REPLACE(coldesc, '''', '\\''');
                        SET final_sql = CONCAT(
                            'ALTER TABLE ', safe_tablename,
                            ' MODIFY COLUMN ', safe_columnname, ' ', sqlstr,
                            ' COMMENT ''', coldesc, ''''
                        );
END IF;
END IF;

            -- 6.3 删除列（字段存在）
WHEN 'DROP' THEN
                IF rows1 <= 0 THEN
                    SET result_msg = CONCAT('错误: 列「', columnname, '」在表「', tablename, '」中不存在，无法删除');
                    SET exit_flag = 1;
ELSE
                    -- 删除列无需sqlstr/coldesc，提示冗余参数忽略
                    IF TRIM(COALESCE(sqlstr, '')) <> '' OR TRIM(COALESCE(coldesc, '')) <> '' THEN
                        SET result_msg = '提示: 删除列时sqlstr/coldesc参数无效，已忽略；';
END IF;
                    SET final_sql = CONCAT(
                        'ALTER TABLE ', safe_tablename,
                        ' DROP COLUMN ', safe_columnname
                    );
END IF;
END CASE;
END IF;

    -- 7. 执行SQL（若无异常标记）
    IF exit_flag = 0 AND final_sql <> '' THEN
        SET @sql1 = final_sql;
PREPARE stmt1 FROM @sql1;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;  -- 释放预处理语句

-- 拼接成功提示
IF result_msg = '' THEN
            SET result_msg = CONCAT('操作成功: 已对表「', tablename, '」的列「', columnname, '」执行「', operation_type, '」操作');
ELSE
            SET result_msg = CONCAT(result_msg, '操作成功: 已对表「', tablename, '」的列「', columnname, '」执行「', operation_type, '」操作');
END IF;
END IF;

    -- 8. 兜底：若未设置结果信息，补充默认提示
    IF result_msg = '' THEN
        SET result_msg = '操作失败: 未知错误，请检查参数';
END IF;

END //

DELIMITER ;