-- 参数说明：1、原表名；2、分表后缀（可选，为空则不拼接）；3、分表数量（正整数）；4、分表起始序号（不传则默认1）

CALL sp_copy_table_shards('oms_order', '2026', 12, 1);
CALL sp_copy_table_shards('oms_order_item', '2026', 12, 1);
CALL sp_copy_table_shards('oms_order_pay_record', '2026', 12, 1);

CALL sp_copy_table_shards('oms_order', '2027', 12, 1);
CALL sp_copy_table_shards('oms_order_item', '2027', 12, 1);
CALL sp_copy_table_shards('oms_order_pay_record', '2027', 12, 1);
