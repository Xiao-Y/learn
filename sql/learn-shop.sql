/*
 Navicat Premium Data Transfer

 Source Server         : 119.23.27.78
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : 119.23.27.78:3306
 Source Schema         : learn

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 17/07/2019 09:54:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('app', NULL, 'app', 'app', 'authorization_code,password,refresh_token,client_credentials', 'http://www.baidu.com', NULL, NULL, NULL, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('authServe', '', 'authServe', 'app', 'authorization_code,password,refresh_token,client_credentials', 'http://127.0.0.1:9999/login', '', NULL, NULL, '', 'true');
INSERT INTO `oauth_client_details` VALUES ('client1', NULL, 'client1', 'app', 'authorization_code,password,refresh_token,client_credentials', 'http://127.0.0.1:8080/client1/login', NULL, NULL, NULL, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('learn-shop-admin-system', NULL, 'learn-shop-admin-system', 'app', 'authorization_code,password,refresh_token,client_credentials', 'http://127.0.0.1:8811/admin-system/login', NULL, 1200, 5000, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('swagger2', '', 'swagger2', 'app', 'authorization_code,password,refresh_token,client_credentials', 'http://127.0.0.1:8771/webjars/springfox-swagger-ui/oauth2-redirect.html', '', 1200, 5000, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('swagger3', '', 'swagger3', 'app', 'authorization_code,password,refresh_token,client_credentials', 'http://127.0.0.1:8811/admin-system/webjars/springfox-swagger-ui/oauth2-redirect.html', '', 1200, 5000, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('webapp', NULL, 'webapp', 'app', 'authorization_code,password,refresh_token,client_credentials', 'http://baidu.com', NULL, NULL, NULL, NULL, 'true');

-- ----------------------------
-- Table structure for r_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `r_role_menu`;
CREATE TABLE `r_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `menu_id` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_role_menu
-- ----------------------------
INSERT INTO `r_role_menu` VALUES (71, 2, 11, '2019-07-16 17:03:59', 'admin', '2019-07-16 17:03:59', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (72, 2, 14, '2019-07-16 17:03:59', 'admin', '2019-07-16 17:03:59', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (73, 2, 2, '2019-07-16 17:03:59', 'admin', '2019-07-16 17:03:59', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (78, 3, 1, '2019-07-16 17:15:34', 'admin', '2019-07-16 17:15:34', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (79, 3, 4, '2019-07-16 17:15:34', 'admin', '2019-07-16 17:15:34', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (80, 3, 2, '2019-07-16 17:15:34', 'admin', '2019-07-16 17:15:34', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (96, 1, 1, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (97, 1, 2, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (98, 1, 3, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (99, 1, 4, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (100, 1, 11, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (101, 1, 14, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (102, 1, 12, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (103, 1, 13, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (104, 1, 5, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (105, 1, 6, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (106, 1, 7, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (107, 1, 8, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (108, 1, 9, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (109, 1, 10, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (110, 1, 16, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');

-- ----------------------------
-- Table structure for r_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `r_role_permission`;
CREATE TABLE `r_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `permission_id` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_role_permission
-- ----------------------------
INSERT INTO `r_role_permission` VALUES (96, 2, 4, '2019-07-16 17:03:59', 'admin', '2019-07-16 17:03:59', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (97, 2, 5, '2019-07-16 17:03:59', 'admin', '2019-07-16 17:03:59', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (100, 3, 1, '2019-07-16 17:15:34', 'admin', '2019-07-16 17:15:34', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (101, 3, 4, '2019-07-16 17:15:34', 'admin', '2019-07-16 17:15:34', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (119, 1, 1, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (120, 1, 4, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (121, 1, 5, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (122, 1, 7, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (123, 1, 8, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (124, 1, 9, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (125, 1, 12, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (126, 1, 13, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (127, 1, 14, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (128, 1, 15, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (129, 1, 16, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (130, 1, 17, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (131, 1, 18, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (132, 1, 19, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (133, 1, 20, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (134, 1, 21, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (135, 1, 22, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (136, 1, 6, '2019-07-16 17:16:49', 'admin', '2019-07-16 17:16:49', 'admin', b'1');

-- ----------------------------
-- Table structure for r_user_role
-- ----------------------------
DROP TABLE IF EXISTS `r_user_role`;
CREATE TABLE `r_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_user_role
-- ----------------------------
INSERT INTO `r_user_role` VALUES (1, '2019-04-23 15:40:49', 'admin', '2019-04-23 15:40:54', 'admin', b'1', 1, 1);
INSERT INTO `r_user_role` VALUES (2, '2019-04-23 15:40:49', 'admin', '2019-04-23 15:40:54', 'admin', b'1', 1, 2);

-- ----------------------------
-- Table structure for sys_data_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_dictionary`;
CREATE TABLE `sys_data_dictionary`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `field_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `field_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `field_display` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `system_module` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `field_order` int(11) NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_dictionary
-- ----------------------------
INSERT INTO `sys_data_dictionary` VALUES (1, 'systemModule', '1', 'admin-system', 'adminSystem', 1, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (2, 'systemModule', '2', 'core-product', 'adminSystem', 2, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (3, 'systemModule', '3', 'core-order', 'adminSystem', 3, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `display` bit(1) NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `descritpion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '/Readme', 'Readme', 'Readme', NULL, b'1', NULL, NULL, '2019-04-23 15:41:51', 'admin', '2019-04-23 15:42:00', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (2, '', 'sys', '系统管理', NULL, b'1', '', '', '2019-04-23 15:41:51', 'admin', '2019-06-03 21:47:42', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (3, '/sys/whiteList/index', 'sysWhiteListIndex', '白名单', 2, b'1', '', '', '2019-04-23 15:41:51', 'admin', '2019-05-27 13:47:44', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (4, '/sys/menuList/index', 'sysMenuListIndex', '菜单管理', 2, b'1', '', '', '2019-04-23 15:41:51', 'admin', '2019-04-23 15:42:00', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (5, '', 'job', '自动任务', NULL, b'1', '', '', '2019-04-23 15:41:51', 'admin', '2019-04-23 15:42:00', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (6, '/job/autoTaskList/index', 'jobAutoTaskListIndex', '自动任务', 5, b'1', '', '', '2019-04-23 15:41:51', 'admin', '2019-05-27 13:48:16', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (7, '', 'pro', '产品管理', NULL, b'1', '', '', '2019-04-23 15:41:51', 'admin', '2019-04-23 15:42:00', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (8, '/product/productList/index', 'proProductListIndex', '产品管理', 7, b'1', '', '', '2019-04-23 15:41:51', 'admin', '2019-05-27 13:48:33', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (9, '/product/productList/productEdit', 'proProductEdit', '修改产品信息', 8, b'0', '', '', '2019-04-23 15:41:51', 'admin', '2019-05-27 13:50:24', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (10, '/product/productList/productImageEdit', 'proProductImageEdit', '修改产品图片', 8, b'0', '', '', '2019-04-23 15:41:51', 'admin', '2019-05-27 13:50:53', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (11, '/sys/roleList/index', 'sysRoleListIndex', '角色管理', 2, b'1', '', '', '2019-04-23 15:41:51', 'admin', '2019-04-23 15:42:00', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (12, '/sys/permissionList/index', 'sysPermissionListIndex', '权限管理', 2, b'1', '', NULL, '2019-07-10 15:22:05', 'admin', '2019-07-10 15:40:23', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (13, '/sys/permissionList/permissionEdit', 'sysPermissionEdit', '修改权限信息', 12, b'0', '', NULL, '2019-07-10 17:42:20', 'admin', '2019-07-10 17:42:20', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (14, '/sys/roleList/roleEdit', 'sysRoleEdit', '修改角色信息', 11, b'0', '', NULL, '2019-07-11 18:00:41', 'admin', '2019-07-11 18:00:41', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (16, '/Readme2', 'Readme2', 'Readme2', NULL, b'1', '', NULL, '2019-07-14 17:14:34', 'admin', '2019-07-14 17:43:55', 'admin', b'1');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `system_module` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `descritpion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `display` bit(1) NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '查询订单', NULL, '/api/order/**', '3', '查询定单相关', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:00:00', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (2, '测试', '测试', '/api/test/**', '1', '测试', b'0', '2019-04-23 15:41:51', '', '2019-07-12 14:00:15', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (3, '测试', '测试', '/api/getWords', '1', '测试', b'0', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:00:23', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (4, '初始化菜单信息', NULL, '/menuApi/homeMenus', '1', '初始化菜单信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:01:05', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (5, '菜单管理信息', NULL, '/menuApi/findMenus', '1', '菜单管理信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:01:15', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (6, '根据id查询菜单信息', NULL, '/menuApi/findMenuById/**', '1', '根据id查询菜单信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:01:27', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (7, '修改、添加菜单信息', NULL, '/menuApi/saveOrUpdateMenu', '1', '修改、添加菜单信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:01:40', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (8, '删除菜单信息', NULL, '/menuApi/delMenuByIds', '1', '根据ID 删除菜单信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:02:24', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (9, '查询角色列表信息', '', '/roleApi/findRoleList', '1', '根据条件查询角色列表信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:02:50', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (12, '查询数据字典', '', '/dataDictionaryApi/findDataDictionary/**', '1', '从数据字典中获取下拉列表', b'1', '2019-07-11 14:11:09', 'admin', '2019-07-11 14:11:09', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (13, '查询角色权限', '3123', '/roleApi/findPermissionByRoleId/**', '1', '根据角色查询出权限ID', b'1', '2019-07-11 17:21:50', 'admin', '2019-07-12 13:57:24', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (14, '查询权限信息', '23', '/permissionApi/findPermissionList', '1', '根据条件查询权限信息', b'1', '2019-07-11 17:22:24', 'admin', '2019-07-12 13:57:35', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (15, '保存权限信息', '312312', '/permissionApi/savePermission', '1', '保存权限信息', b'1', '2019-07-11 17:37:53', 'admin', '2019-07-12 13:57:42', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (16, '查询权限列表', '312312', '/permissionApi/findPermissionAll', '1', '查询权限列表', b'1', '2019-07-11 17:37:53', 'admin', '2019-07-12 13:57:49', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (17, '根据ID删除权限', '', '/permissionApi/deletePermissionById/**', '1', '根据ID删除权限', b'1', '2019-07-12 13:53:12', 'admin', '2019-07-12 13:53:12', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (18, '根据ID禁用权限', '', '/permissionApi/prohibitPermissionById/**', '1', '根据ID禁用权限', b'1', '2019-07-12 13:53:50', 'admin', '2019-07-12 13:53:50', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (19, '添加权限信息', '', '/permissionApi/savePermission', '1', '添加权限信息', b'1', '2019-07-12 13:54:12', 'admin', '2019-07-12 13:54:12', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (20, '更新权限信息', '', '/permissionApi/updatePermission', '1', '更新权限信息', b'1', '2019-07-12 13:54:28', 'admin', '2019-07-12 13:56:57', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (21, '根据角色ID查询菜单ID', '', '/roleApi/findMenuByRoleId/**', '1', '根据角色ID查询菜单ID', b'1', '2019-07-12 15:23:35', 'admin', '2019-07-12 15:23:35', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (22, '保存角色信息、角色菜单和角色权限', '', '/roleApi/saveRole', '1', '保存角色信息、角色菜单和角色权限', b'1', '2019-07-12 17:38:29', 'admin', '2019-07-12 17:38:29', 'admin', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `descritpion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '2019-04-23 15:40:22', 'admin', '2019-04-23 15:40:25', 'admin', b'1', 'admin', 'ADMIN', 'admin');
INSERT INTO `sys_role` VALUES (2, '2019-04-23 15:40:22', 'admin', '2019-04-23 15:40:25', 'admin', b'1', 'custom', 'custom', 'custom');
INSERT INTO `sys_role` VALUES (3, '2019-07-16 17:08:53', 'admin', '2019-07-16 17:08:53', 'admin', b'1', '测试使用', 'TEST', 'test');

SET FOREIGN_KEY_CHECKS = 1;
