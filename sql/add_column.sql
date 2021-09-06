

drop procedure if exists pro_column_work;
delimiter$$
-- 1表示新增列,2表示修改列类型,3表示删除列
create procedure pro_column_work(ctype int,tablename varchar(50), columnname varchar(50), sqlstr varchar(400),coldesc varchar(300) )
begin
    declare rows1 int;
    set rows1 = 0;
    select count(*)
    into rows1
    from information_schema.columns
    where table_schema = database()
      and table_name = tablename
      and column_name = columnname;
-- 新增列
    if (ctype = 1 and rows1 <= 0) then
        set sqlstr := concat( 'alter table ',tablename,' add column ',columnname,' ',sqlstr,' comment ''',coldesc,'''');
-- 修改列类型
    elseif (ctype = 2 and rows1 > 0) then
        set sqlstr := concat('alter table ', tablename, ' modify column ', columnname, ' ', sqlstr,' comment ''',coldesc,'''');
-- 删除列
    elseif (ctype = 3 and rows1 > 0) then
        set sqlstr := concat('alter table  ', tablename, ' drop column  ', columnname);
    else
        set sqlstr := '';
    end if;
-- 执行命令
    if (sqlstr <> '') then
        set @sql1 = sqlstr;
        prepare stmt1 from @sql1;
        execute stmt1;
    end if;
    end$$
delimiter ;


-- 当前数据库 tablename表名 columnname列名
-- 新增列
-- call pro_column_work (1,'表名','字段名','字段参数 ', '说明信息');
-- call pro_column_work (1,'表名','字段名','int(11) null default null after `xxxxxxxxxxx`; ', '说明信息');
-- 删除列
-- call pro_column_work (3,'e_handcard_control','entrancetype','', '说明信息');


call pro_column_work (1,'sms_seckill','valid_ind','bit(1) NULL DEFAULT NULL', '是否有效');
call pro_column_work (1,'sms_seckill','create_time','datetime(0) NULL DEFAULT NULL', '创建时间');
call pro_column_work (1,'sms_seckill','creator_code','varchar(255) NULL DEFAULT NULL', '创建人');
call pro_column_work (1,'sms_seckill','update_time','datetime(0) NULL DEFAULT NULL', '更新时间');
call pro_column_work (1,'sms_seckill','updater_code','varchar(255) NULL DEFAULT NULL', '更新人');