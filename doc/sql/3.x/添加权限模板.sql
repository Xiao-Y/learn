-- 添加权限模板
-- 定义实体名称变量
SET @system_module = 'core-product';
SET @permission_code = 'pms:goods';
SET @entity_name = '品牌';
SET @entity_code = 'Brand';
SET @menu_id_value = 1946755195115208706;

-- 查询分页权限
INSERT INTO `sys_permission` (`permission_name`, `permission_code`, `url`, `system_module`, `description`, `valid_ind`,
                              `create_time`, `creator_code`, `update_time`, `updater_code`, `display`, `icon`, `pid`)
VALUES (CONCAT('查询分页', @entity_name, '表数据'), CONCAT(@permission_code, @entity_code, ':list'), CONCAT('/goods', @entity_code, 'Api/list'), @system_module,
        CONCAT('查询分页', @entity_name, '表数据'), b'1', NOW(), 'admin', NOW(), 'admin', NULL, NULL, NULL);
SET @list_permission_id = LAST_INSERT_ID();

-- 新增权限
INSERT INTO `sys_permission` (`permission_name`, `permission_code`, `url`, `system_module`, `description`, `valid_ind`,
                              `create_time`, `creator_code`, `update_time`, `updater_code`, `display`, `icon`, `pid`)
VALUES (CONCAT('新增', @entity_name, '表数据'), CONCAT(@permission_code, @entity_code, ':add'), CONCAT('/goods', @entity_code, 'Api/add'), @system_module,
        CONCAT('新增', @entity_name, '表数据'), b'1', NOW(), 'admin', NOW(), 'admin', NULL, NULL, NULL);
SET @add_permission_id = LAST_INSERT_ID();

-- 删除权限
INSERT INTO `sys_permission` (`permission_name`, `permission_code`, `url`, `system_module`, `description`, `valid_ind`,
                              `create_time`, `creator_code`, `update_time`, `updater_code`, `display`, `icon`, `pid`)
VALUES (CONCAT('删除', @entity_name, '表数据'), CONCAT(@permission_code, @entity_code, ':delById'), CONCAT('/goods', @entity_code, 'Api/delById/**'), @system_module,
        CONCAT('删除', @entity_name, '表数据'), b'1', NOW(), 'admin', NOW(), 'admin', NULL, NULL, NULL);
SET @delete_permission_id = LAST_INSERT_ID();

-- 更新权限
INSERT INTO `sys_permission` (`permission_name`, `permission_code`, `url`, `system_module`, `description`, `valid_ind`,
                              `create_time`, `creator_code`, `update_time`, `updater_code`, `display`, `icon`, `pid`)
VALUES (CONCAT('更新', @entity_name, '表数据'), CONCAT(@permission_code, @entity_code, ':update'), CONCAT('/goods', @entity_code, 'Api/update'), @system_module,
        CONCAT('更新', @entity_name, '表数据'), b'1', NOW(), 'admin', NOW(), 'admin', NULL, NULL, NULL);
SET @update_permission_id = LAST_INSERT_ID();

-- 禁用权限
INSERT INTO `sys_permission` (`permission_name`, `permission_code`, `url`, `system_module`, `description`, `valid_ind`,
                              `create_time`, `creator_code`, `update_time`, `updater_code`, `display`, `icon`, `pid`)
VALUES (CONCAT('根据ID禁用', @entity_name, '表数据'), CONCAT(@permission_code, @entity_code, ':prohibitById'), CONCAT('/goods', @entity_code, 'Api/prohibitById/**'), @system_module,
        CONCAT('根据ID禁用', @entity_name, '表数据'), b'1', NOW(), 'admin', NOW(), 'admin', NULL, NULL, NULL);
SET @prohibit_permission_id = LAST_INSERT_ID();

-- 批量插入菜单权限关联
INSERT INTO `sys_menu_permission` (`menu_id`, `permission_id`, `create_time`, `creator_code`, `update_time`, `updater_code`, `valid_ind`)
VALUES
    (@menu_id_value, @list_permission_id, NOW(), 'admin', NOW(), 'admin', b'1'),
    (@menu_id_value, @add_permission_id, NOW(), 'admin', NOW(), 'admin', b'1'),
    (@menu_id_value, @delete_permission_id, NOW(), 'admin', NOW(), 'admin', b'1'),
    (@menu_id_value, @update_permission_id, NOW(), 'admin', NOW(), 'admin', b'1'),
    (@menu_id_value, @prohibit_permission_id, NOW(), 'admin', NOW(), 'admin', b'1');
