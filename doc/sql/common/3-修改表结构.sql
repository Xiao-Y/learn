DELIMITER //

DROP PROCEDURE IF EXISTS sp_column_work //

CREATE PROCEDURE `sp_column_work`(
    operation_type VARCHAR(10), -- 操作类型（'add'=新增列，'modify'=修改列，'drop'=删除列）
    tablename varchar(200), -- 表名
    columnname varchar(100), -- 列名
    sqlstr varchar(2000), -- SQL 字符串（用于定义列的数据类型等
    coldesc varchar(500), -- 列的注释描述
    OUT result_msg VARCHAR(2000) -- 返回结果信息
)
BEGIN
    DECLARE rows1 INT;
    DECLARE exit_flag INT DEFAULT 0;
    DECLARE sql_error_msg TEXT;

    -- 定义异常处理器
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
BEGIN
GET DIAGNOSTICS CONDITION 1 sql_error_msg = MESSAGE_TEXT;
SET result_msg = CONCAT('执行失败: ', sql_error_msg);
        SET exit_flag = 1;
END;

    SET rows1 = 0;

SELECT COUNT(*)
INTO rows1
FROM information_schema.columns
WHERE table_schema = DATABASE()
  AND table_name = tablename
  AND column_name = columnname;

-- 新增列
IF (operation_type = 'add' AND rows1 <= 0) THEN
        SET sqlstr := CONCAT('ALTER TABLE ', tablename, ' ADD COLUMN ', columnname, ' ', sqlstr, ' COMMENT ''', coldesc, '''');
    -- 修改列类型
    ELSEIF (operation_type = 'modify' AND rows1 > 0) THEN
        SET sqlstr := CONCAT('ALTER TABLE ', tablename, ' MODIFY COLUMN ', columnname, ' ', sqlstr, ' COMMENT ''', coldesc, '''');
    -- 删除列
    ELSEIF (operation_type = 'drop' AND rows1 > 0) THEN
        SET sqlstr := CONCAT('ALTER TABLE ', tablename, ' DROP COLUMN ', columnname);
    -- 不符合任何条件的情况，给出异常提示
ELSE
        IF (operation_type = 'add' AND rows1 > 0) THEN
            SET result_msg = CONCAT('错误: 列 ', columnname, ' 已存在于表 ', tablename, ' 中，无法新增');
        ELSEIF ((operation_type = 'modify' OR operation_type = 'drop') AND rows1 <= 0) THEN
            SET result_msg = CONCAT('错误: 列 ', columnname, ' 在表 ', tablename, ' 中不存在，无法进行修改或删除操作');
ELSE
            SET result_msg = CONCAT('错误: 无效的操作类型 ', operation_type, ' 或参数错误');
END IF;
        SET exit_flag = 1;
END IF;

    -- 执行命令
    IF (sqlstr <> '' AND exit_flag = 0) THEN
        SET @sql1 = sqlstr;
PREPARE stmt1 FROM @sql1;
EXECUTE stmt1;

IF exit_flag = 0 THEN
            SET result_msg = CONCAT('操作成功: ', operation_type, ' 操作在表 ', tablename, ' 上执行完成');
END IF;
END IF;

    -- 如果没有设置结果信息，设置默认成功信息
    IF result_msg IS NULL AND exit_flag = 0 THEN
        SET result_msg = CONCAT('操作成功: ', operation_type, ' 操作在表 ', tablename, ' 上执行完成');
END IF;

END