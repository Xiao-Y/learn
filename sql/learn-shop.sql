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

 Date: 02/08/2019 18:04:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 557 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_role_menu
-- ----------------------------
INSERT INTO `r_role_menu` VALUES (239, 2, 11, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (240, 2, 14, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (241, 2, 20, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (242, 2, 2, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (530, 3, 1, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (531, 3, 7, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (532, 3, 8, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (533, 3, 9, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (534, 3, 10, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (535, 3, 4, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (536, 3, 29, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (537, 3, 2, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (538, 3, 20, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (539, 3, 24, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (540, 1, 1, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (541, 1, 25, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (542, 1, 26, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (543, 1, 27, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (544, 1, 7, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (545, 1, 8, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (546, 1, 9, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (547, 1, 10, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (548, 1, 5, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (549, 1, 6, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (550, 1, 2, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (551, 1, 4, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (552, 1, 11, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (553, 1, 14, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (554, 1, 12, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (555, 1, 13, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (556, 1, 3, '2019-08-02 14:30:35', 'admin', '2019-08-02 14:30:35', 'admin', b'1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 906 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_role_permission
-- ----------------------------
INSERT INTO `r_role_permission` VALUES (288, 2, 4, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (289, 2, 5, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (870, 3, 1, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (871, 3, 4, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (872, 3, 5, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (873, 3, 7, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (874, 3, 8, '2019-08-02 11:10:58', 'admin', '2019-08-02 11:10:58', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (875, 1, 1, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (876, 1, 4, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (877, 1, 5, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (878, 1, 6, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (879, 1, 7, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (880, 1, 8, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (881, 1, 9, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (882, 1, 12, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (883, 1, 13, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (884, 1, 14, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (885, 1, 15, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (886, 1, 16, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (887, 1, 17, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (888, 1, 18, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (889, 1, 19, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (890, 1, 20, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (891, 1, 21, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (892, 1, 22, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (893, 1, 23, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (894, 1, 24, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (895, 1, 25, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (896, 1, 26, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (897, 1, 27, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (898, 1, 28, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (899, 1, 29, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (900, 1, 30, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (901, 1, 31, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (902, 1, 32, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (903, 1, 33, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (904, 1, 34, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (905, 1, 3, '2019-08-02 14:30:36', 'admin', '2019-08-02 14:30:36', 'admin', b'1');

-- ----------------------------
-- Table structure for r_user_role
-- ----------------------------
DROP TABLE IF EXISTS `r_user_role`;
CREATE TABLE `r_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 131 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_user_role
-- ----------------------------
INSERT INTO `r_user_role` VALUES (6, 3, 1, b'1', '2019-07-31 10:42:37', 'admin', '2019-07-31 10:42:37', 'admin');
INSERT INTO `r_user_role` VALUES (7, 3, 3, b'1', '2019-07-31 10:42:37', 'admin', '2019-07-31 10:42:37', 'admin');
INSERT INTO `r_user_role` VALUES (8, NULL, 3, b'1', '2019-07-31 10:45:43', 'admin', '2019-07-31 10:45:43', 'admin');
INSERT INTO `r_user_role` VALUES (9, NULL, 2, b'1', '2019-07-31 10:47:29', 'admin', '2019-07-31 10:47:29', 'admin');
INSERT INTO `r_user_role` VALUES (10, NULL, 2, b'1', '2019-07-31 10:48:15', 'admin', '2019-07-31 10:48:15', 'admin');
INSERT INTO `r_user_role` VALUES (11, NULL, 1, b'1', '2019-07-31 10:49:49', 'admin', '2019-07-31 10:49:49', 'admin');
INSERT INTO `r_user_role` VALUES (12, NULL, 2, b'1', '2019-07-31 10:49:49', 'admin', '2019-07-31 10:49:49', 'admin');
INSERT INTO `r_user_role` VALUES (13, 6, 3, b'1', '2019-07-31 10:51:41', 'admin', '2019-07-31 10:51:41', 'admin');
INSERT INTO `r_user_role` VALUES (14, 5, 2, b'1', '2019-07-31 10:53:17', 'admin', '2019-07-31 10:53:17', 'admin');
INSERT INTO `r_user_role` VALUES (15, NULL, 3, b'1', '2019-07-31 10:53:59', 'admin', '2019-07-31 10:53:59', 'admin');
INSERT INTO `r_user_role` VALUES (20, 7, 3, b'1', '2019-07-31 11:00:04', 'admin', '2019-07-31 11:00:04', 'admin');
INSERT INTO `r_user_role` VALUES (21, 7, 2, b'1', '2019-07-31 11:00:04', 'admin', '2019-07-31 11:00:04', 'admin');
INSERT INTO `r_user_role` VALUES (73, 8, 1, b'1', '2019-07-31 15:40:25', 'admin', '2019-07-31 15:40:25', 'admin');
INSERT INTO `r_user_role` VALUES (74, 8, 3, b'1', '2019-07-31 15:40:25', 'admin', '2019-07-31 15:40:25', 'admin');
INSERT INTO `r_user_role` VALUES (114, 9, 1, b'1', '2019-08-01 18:41:03', 'admin', '2019-08-01 18:41:03', 'admin');
INSERT INTO `r_user_role` VALUES (115, 9, 2, b'1', '2019-08-01 18:41:03', 'admin', '2019-08-01 18:41:03', 'admin');
INSERT INTO `r_user_role` VALUES (116, NULL, 2, b'1', '2019-08-01 18:41:25', 'admin', '2019-08-01 18:41:25', 'admin');
INSERT INTO `r_user_role` VALUES (121, 10, 2, b'1', '2019-08-02 16:27:35', 'admin', '2019-08-02 16:27:35', 'admin');
INSERT INTO `r_user_role` VALUES (126, 1, 1, b'1', '2019-08-02 17:42:03', 'admin', '2019-08-02 17:42:03', 'admin');
INSERT INTO `r_user_role` VALUES (127, 1, 2, b'1', '2019-08-02 17:42:03', 'admin', '2019-08-02 17:42:03', 'admin');
INSERT INTO `r_user_role` VALUES (130, 2, 1, b'1', '2019-08-02 17:53:51', 'admin', '2019-08-02 17:53:51', 'admin');

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_dictionary
-- ----------------------------
INSERT INTO `sys_data_dictionary` VALUES (1, 'systemModule', '1', 'admin-system', 'adminSystem', 1, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (2, 'systemModule', '2', 'core-product', 'adminSystem', 2, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (3, 'systemModule', '3', 'core-order', 'adminSystem', 3, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (4, 'systemModule', '4', 'admin-user', 'adminSystem', 3, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (5, 'sexType', '1', '男', 'adminUser', 1, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (6, 'sexType', '2', '女', 'adminUser', 2, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (7, 'sexType', '3', '未知', 'adminUser', 3, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `display` bit(1) NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `descritpion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort_field` double NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 'homeIndex', '首页', NULL, b'1', NULL, NULL, 0, '2019-04-23 15:41:51', 'admin', '2019-07-30 14:30:15', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (2, 'sys', '系统管理', NULL, b'1', '', '', 100, '2019-04-23 15:41:51', 'admin', '2019-06-03 21:47:42', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (3, 'sysWhiteListIndex', '白名单', 2, b'1', '', '', 4, '2019-04-23 15:41:51', 'admin', '2019-05-27 13:47:44', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (4, 'sysMenuListIndex', '菜单管理', 2, b'1', '', '', 1, '2019-04-23 15:41:51', 'admin', '2019-04-23 15:42:00', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (5, 'job', '自动任务', NULL, b'1', '', '', 99, '2019-04-23 15:41:51', 'admin', '2019-04-23 15:42:00', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (6, 'jobAutoTaskListIndex', '自动任务', 5, b'1', '', '', 1, '2019-04-23 15:41:51', 'admin', '2019-05-27 13:48:16', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (7, 'pro', '产品系统', NULL, b'1', '', '', 10, '2019-04-23 15:41:51', 'admin', '2019-07-17 11:22:59', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (8, 'proProductListIndex', '产品管理', 7, b'1', '', '', 2, '2019-04-23 15:41:51', 'admin', '2019-05-27 13:48:33', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (9, 'proProductEdit', '修改产品信息', 8, b'0', '', '', 3, '2019-04-23 15:41:51', 'admin', '2019-05-27 13:50:24', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (10, 'proProductImageEdit', '修改产品图片', 8, b'0', '', '', 4, '2019-04-23 15:41:51', 'admin', '2019-05-27 13:50:53', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (11, 'sysRoleListIndex', '角色管理', 2, b'1', '', '', 2, '2019-04-23 15:41:51', 'admin', '2019-04-23 15:42:00', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (12, 'sysPermissionListIndex', '权限管理', 2, b'1', '', NULL, 3, '2019-07-10 15:22:05', 'admin', '2019-07-10 15:40:23', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (13, 'sysPermissionEdit', '修改权限信息', 12, b'0', '', NULL, 1, '2019-07-10 17:42:20', 'admin', '2019-07-10 17:42:20', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (14, 'sysRoleEdit', '修改角色信息', 11, b'0', '', NULL, 2, '2019-07-11 18:00:41', 'admin', '2019-07-11 18:00:41', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (20, 'edit', '编辑', NULL, b'1', '', NULL, 120, '2019-07-23 14:13:26', 'admin', '2019-07-26 14:26:39', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (24, 'editMarkdown', 'Markdown', 20, b'1', '', NULL, 1, '2019-07-26 14:27:36', 'admin', '2019-07-26 14:27:36', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (25, 'user', '用户系统', NULL, b'1', '', NULL, 1, '2019-07-30 14:29:20', 'admin', '2019-07-30 14:30:24', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (26, 'userUserListIndex', '用户管理', 25, b'1', '', NULL, 0, '2019-07-30 14:31:46', 'admin', '2019-07-30 15:28:19', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (27, 'userUserEdit', '用户修改/添加', 26, b'0', '', NULL, 1, '2019-07-30 15:30:22', 'admin', '2019-07-30 15:30:22', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (28, '23', '333', 24, b'1', '', NULL, 0, '2019-08-02 10:36:19', 'admin', '2019-08-02 10:36:19', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (29, '567', '567', 24, b'1', '', NULL, 1, '2019-08-02 11:10:43', 'admin', '2019-08-02 11:10:43', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (30, '232', '22', 24, b'1', '', NULL, 0, '2019-08-02 11:11:31', 'admin', '2019-08-02 11:11:31', 'admin', b'1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '查询订单', NULL, '/api/order/**', '3,2,1', '查询定单相关', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-18 16:52:59', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (2, '测试', '测试', '/api/test/**', '1,2,3', '测试', b'0', '2019-04-23 15:41:51', '', '2019-07-18 16:52:46', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (3, '根据登陆token查询用户信息', 'UserApi-getUserInfo', '/userApi/getUserInfo', '4', '根据登陆token查询用户信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-08-02 14:30:20', 'admin', NULL, NULL, NULL);
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
INSERT INTO `sys_permission` VALUES (23, '查询 menuCode 的个数', 'checkMenuCode', '/menuApi/checkMenuCode/**', '1', '查询 menuCode 的个数', b'1', '2019-07-18 16:47:01', 'admin', '2019-07-24 14:36:28', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (24, '根据id禁用角色信息', 'RoleApi-prohibitRoleById', '/roleApi/prohibitRoleById/**', '1', '根据id禁用角色信息', b'1', '2019-07-19 15:54:00', 'admin', '2019-07-30 10:25:25', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (25, '文件处理API', 'FileHandleApi-upload', '/fileHandleApi/upload', '1', '上传文件，保存图片信息', b'1', '2019-07-26 15:17:04', 'admin', '2019-07-26 15:17:04', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (26, '根据id删除角色信息', 'RoleApi-deleteRoleById', '/roleApi/deleteRoleById/**', '1', '根据id删除角色信息', b'1', '2019-07-30 10:26:02', 'admin', '2019-07-30 10:26:02', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (27, '保存用户信息', 'UserApi-saveUser', '/userApi/saveUser', '4', '保存用户信息', b'1', '2019-07-30 10:32:08', 'admin', '2019-07-30 15:32:41', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (28, '更新用户信息', 'UserApi-updateUser', '/userApi/updateUser', '4', '更新用户信息', b'1', '2019-07-30 10:34:35', 'admin', '2019-07-30 15:33:21', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (29, '根据id禁用用户信息', 'UserApi-prohibitUserById', '/userApi/prohibitUserById/**', '4', '根据id禁用用户信息', b'1', '2019-07-30 10:38:17', 'admin', '2019-07-30 15:34:10', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (30, '根据条件查询用户信息', 'UserApi-findUserList', '/userApi/findUserList', '4', '根据条件查询用户信息', b'1', '2019-07-30 14:34:25', 'admin', '2019-07-30 14:45:30', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (31, '根据id删除用户信息', 'UserApi-deleteUserById', '/userApi/deleteUserById/**', '4', '根据id删除用户信息', b'1', '2019-07-30 15:34:51', 'admin', '2019-07-30 15:34:51', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (32, '加载下拉列表角色信息', 'RoleApi-findSelectRole', '/roleApi/findSelectRole', '1', '加载下拉列表角色信息', b'1', '2019-07-30 16:21:01', 'admin', '2019-07-30 16:21:01', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (33, '根据id查询有效的用户角色', 'UserApi-findRoleIdsByUserId', '/userApi/findRoleIdsByUserId/**', '4', '根据id查询有效的用户角色', b'1', '2019-07-30 18:21:41', 'admin', '2019-07-30 18:21:41', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (34, '根据id查询角色信息', 'RoleApi-findRoleById', '/roleApi/findRoleById', '1', '根据id查询角色信息', b'1', '2019-07-31 12:34:18', 'admin', '2019-07-31 12:34:18', 'admin', NULL, NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '2019-04-23 15:40:22', 'admin', '2019-04-23 15:40:25', 'admin', b'1', 'admin', 'ADMIN', 'admin');
INSERT INTO `sys_role` VALUES (2, '2019-04-23 15:40:22', 'admin', '2019-04-23 15:40:25', 'admin', b'1', 'custom', 'custom', 'custom');
INSERT INTO `sys_role` VALUES (3, '2019-07-16 17:08:53', 'admin', '2019-07-30 10:44:04', 'admin', b'1', '测试使用', 'TEST4515', 'test588');

-- ----------------------------
-- Table structure for u_user
-- ----------------------------
DROP TABLE IF EXISTS `u_user`;
CREATE TABLE `u_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `usercode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birth_date` datetime(0) NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_user
-- ----------------------------
INSERT INTO `u_user` VALUES (1, 'liuyongtao', 'liuyongtao', '1', '$2a$10$NJu4/jCYjtvqY1Rc8AfOVeUxq173TKrYy9.ap7rRvJjc9lEZH/QUe', '1231', '2019-08-15 00:00:00', '154244554', b'1', '2019-04-23 15:26:47', 'admin', '2019-08-02 17:42:03', 'admin');
INSERT INTO `u_user` VALUES (2, 'admin', 'admin', '3', '$2a$10$seb.F5SucYtqqhNJqFhvu.91M5Ja8v2d2g0BVJ3y12f5fPgZRTtce', '555', '2019-08-02 17:44:09', '1111', b'1', '2019-04-23 15:26:47', 'admin', '2019-08-02 17:53:51', 'admin');
INSERT INTO `u_user` VALUES (3, '1212', '1212', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', NULL, NULL, NULL, b'0', '2019-07-30 15:35:18', 'admin', '2019-07-30 15:54:56', 'admin');
INSERT INTO `u_user` VALUES (5, '345', '34', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', NULL, NULL, NULL, b'1', '2019-07-31 10:45:43', 'admin', '2019-07-31 10:45:43', 'admin');
INSERT INTO `u_user` VALUES (6, '678', '6786', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', NULL, NULL, NULL, b'1', '2019-07-31 10:47:29', 'admin', '2019-07-31 10:47:29', 'admin');
INSERT INTO `u_user` VALUES (7, '123XX', '123XXX', NULL, '$2a$10$S8iKAT6DLZ4aK54HoR2DN.o5r1Io4LZEpoq3onFD7hp4/Hjv4iQ8W', NULL, NULL, NULL, b'1', '2019-07-31 10:48:07', 'admin', '2019-07-31 10:59:44', 'admin');
INSERT INTO `u_user` VALUES (8, '1231AAA', '123www', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', NULL, NULL, NULL, b'1', '2019-07-31 10:49:49', 'admin', '2019-07-31 15:40:25', 'admin');
INSERT INTO `u_user` VALUES (9, '12AAA', '12AAA', NULL, '$2a$10$G6hI07wyWrLwMcwGS6L45un02TetHxzpwcpats6Crqe/.Wbdgd21W', NULL, NULL, NULL, b'1', '2019-07-31 10:53:59', 'admin', '2019-08-01 18:41:03', 'admin');
INSERT INTO `u_user` VALUES (10, '124', '123234', NULL, '$2a$10$mw8dlaPZPCkT/Tkpbhe8K.wlXN2uK08AAjsHJqXOYAZSIbmtsxqLi', NULL, NULL, NULL, b'1', '2019-08-01 18:41:25', 'admin', '2019-08-02 16:27:35', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
