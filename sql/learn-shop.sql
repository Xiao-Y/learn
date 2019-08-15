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

 Date: 15/08/2019 11:36:07
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
) ENGINE = InnoDB AUTO_INCREMENT = 1012 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_role_menu
-- ----------------------------
INSERT INTO `r_role_menu` VALUES (239, 2, 11, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (240, 2, 14, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (241, 2, 20, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (242, 2, 2, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (991, 1, 1, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (992, 1, 25, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (993, 1, 26, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (994, 1, 27, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (995, 1, 7, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (996, 1, 8, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (997, 1, 9, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (998, 1, 10, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (999, 1, 5, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1000, 1, 6, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1001, 1, 32, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1002, 1, 2, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1003, 1, 4, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1004, 1, 11, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1005, 1, 14, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1006, 1, 12, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1007, 1, 13, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1008, 1, 3, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1009, 1, 31, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1010, 1, 20, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1011, 1, 24, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1799 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_role_permission
-- ----------------------------
INSERT INTO `r_role_permission` VALUES (288, 2, 4, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (289, 2, 5, '2019-07-23 14:21:04', 'admin', '2019-07-23 14:21:04', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1753, 1, 1, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1754, 1, 2, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1755, 1, 3, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1756, 1, 4, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1757, 1, 5, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1758, 1, 6, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1759, 1, 7, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1760, 1, 8, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1761, 1, 9, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1762, 1, 12, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1763, 1, 13, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1764, 1, 14, '2019-08-15 10:53:39', 'admin', '2019-08-15 10:53:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1765, 1, 15, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1766, 1, 16, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1767, 1, 17, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1768, 1, 18, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1769, 1, 19, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1770, 1, 20, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1771, 1, 21, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1772, 1, 22, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1773, 1, 23, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1774, 1, 24, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1775, 1, 25, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1776, 1, 26, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1777, 1, 27, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1778, 1, 28, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1779, 1, 29, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1780, 1, 30, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1781, 1, 31, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1782, 1, 32, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1783, 1, 33, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1784, 1, 34, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1785, 1, 35, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1786, 1, 37, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1787, 1, 38, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1788, 1, 39, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1789, 1, 42, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1790, 1, 41, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1791, 1, 43, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1792, 1, 44, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1793, 1, 45, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1794, 1, 46, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1795, 1, 47, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1796, 1, 48, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1797, 1, 49, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (1798, 1, 50, '2019-08-15 10:53:40', 'admin', '2019-08-15 10:53:40', 'admin', b'1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 156 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `r_user_role` VALUES (15, NULL, 3, b'1', '2019-07-31 10:53:59', 'admin', '2019-07-31 10:53:59', 'admin');
INSERT INTO `r_user_role` VALUES (20, 7, 3, b'1', '2019-07-31 11:00:04', 'admin', '2019-07-31 11:00:04', 'admin');
INSERT INTO `r_user_role` VALUES (21, 7, 2, b'1', '2019-07-31 11:00:04', 'admin', '2019-07-31 11:00:04', 'admin');
INSERT INTO `r_user_role` VALUES (73, 8, 1, b'1', '2019-07-31 15:40:25', 'admin', '2019-07-31 15:40:25', 'admin');
INSERT INTO `r_user_role` VALUES (74, 8, 3, b'1', '2019-07-31 15:40:25', 'admin', '2019-07-31 15:40:25', 'admin');
INSERT INTO `r_user_role` VALUES (116, NULL, 2, b'1', '2019-08-01 18:41:25', 'admin', '2019-08-01 18:41:25', 'admin');
INSERT INTO `r_user_role` VALUES (130, 2, 1, b'1', '2019-08-02 17:53:51', 'admin', '2019-08-02 17:53:51', 'admin');
INSERT INTO `r_user_role` VALUES (144, 10, 2, b'1', '2019-08-06 10:18:10', 'admin', '2019-08-06 10:18:10', 'admin');
INSERT INTO `r_user_role` VALUES (145, 9, 2, b'1', '2019-08-06 10:20:47', 'admin', '2019-08-06 10:20:47', 'admin');
INSERT INTO `r_user_role` VALUES (146, 9, 3, b'1', '2019-08-06 10:20:48', 'admin', '2019-08-06 10:20:48', 'admin');
INSERT INTO `r_user_role` VALUES (148, 1, 1, b'1', '2019-08-06 10:22:12', 'admin', '2019-08-06 10:22:12', 'admin');
INSERT INTO `r_user_role` VALUES (149, NULL, 3, b'1', '2019-08-06 15:42:57', 'admin', '2019-08-06 15:42:57', 'admin');
INSERT INTO `r_user_role` VALUES (150, NULL, 2, b'1', '2019-08-06 15:44:16', 'admin', '2019-08-06 15:44:16', 'admin');
INSERT INTO `r_user_role` VALUES (151, NULL, 3, b'1', '2019-08-06 15:44:59', 'admin', '2019-08-06 15:44:59', 'admin');
INSERT INTO `r_user_role` VALUES (152, 5, 2, b'1', '2019-08-07 09:50:15', 'admin', '2019-08-07 09:50:15', 'admin');
INSERT INTO `r_user_role` VALUES (154, 12, 2, b'1', '2019-08-07 10:54:57', 'admin', '2019-08-07 10:54:57', 'admin');
INSERT INTO `r_user_role` VALUES (155, 12, 1, b'1', '2019-08-07 10:54:57', 'admin', '2019-08-07 10:54:57', 'admin');

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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `sys_data_dictionary` VALUES (8, 'systemModule', '5', 'public-job', 'adminSystem', 4, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');

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
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `sys_menu` VALUES (31, 'sysCacheMagIndex', '缓存管理', 2, b'1', '', NULL, 99, '2019-08-10 11:43:37', 'admin', '2019-08-10 11:43:37', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (32, 'jobAutoTaskEdit', '修改自动任务', 6, b'0', '', NULL, 0, '2019-08-12 17:41:49', 'admin', '2019-08-12 17:41:49', 'admin', b'1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '查询省市区', 'CityApi-findCity', '/cityApi/findCity/**', '1', '查询省市区', b'1', '2019-04-23 15:41:51', 'admin', '2019-08-04 14:04:04', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (2, '修改密码', 'UserApi-editPassWord', '/userApi/editPassWord', '4', '修改密码', b'1', '2019-04-23 15:41:51', '', '2019-08-03 18:33:28', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (3, '根据登陆token查询用户信息', 'UserApi-getUserInfo', '/userApi/getUserInfo', '4', '根据登陆token查询用户信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-08-02 14:30:20', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (4, '初始化菜单信息', NULL, '/menuApi/homeMenus', '1', '初始化菜单信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:01:05', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (5, '菜单管理信息', NULL, '/menuApi/findMenus', '1', '菜单管理信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:01:15', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (6, '根据id查询菜单信息', NULL, '/menuApi/findMenuById/**', '1', '根据id查询菜单信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:01:27', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (7, '修改、添加菜单信息', NULL, '/menuApi/saveOrUpdateMenu', '1', '修改、添加菜单信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:01:40', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (8, '删除菜单信息', NULL, '/menuApi/delMenuByIds', '1', '根据ID 删除菜单信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:02:24', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (9, '查询角色列表信息', '', '/roleApi/findRoleList', '1', '根据条件查询角色列表信息', b'1', '2019-04-23 15:41:51', 'admin', '2019-07-12 14:02:50', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (12, '查询数据字典', '', '/dataDictionaryApi/findDataDictionary/**', '1', '从数据字典中获取下拉列表', b'1', '2019-07-11 14:11:09', 'admin', '2019-08-07 15:47:37', 'admin', NULL, NULL, NULL);
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
INSERT INTO `sys_permission` VALUES (25, '批量上传文件', 'FileApi-batchUpload', '/fileApi/batchUpload/**', '1', '批量上传文件', b'1', '2019-07-26 15:17:04', 'admin', '2019-08-08 11:35:26', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (26, '根据id删除角色信息', 'RoleApi-deleteRoleById', '/roleApi/deleteRoleById/**', '1', '根据id删除角色信息', b'1', '2019-07-30 10:26:02', 'admin', '2019-07-30 10:26:02', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (27, '保存用户信息', 'UserApi-saveUser', '/userApi/saveUser', '4', '保存用户信息', b'1', '2019-07-30 10:32:08', 'admin', '2019-07-30 15:32:41', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (28, '更新用户信息', 'UserApi-updateUser', '/userApi/updateUser', '4', '更新用户信息', b'1', '2019-07-30 10:34:35', 'admin', '2019-07-30 15:33:21', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (29, '根据id禁用用户信息', 'UserApi-prohibitUserById', '/userApi/prohibitUserById/**', '4', '根据id禁用用户信息', b'1', '2019-07-30 10:38:17', 'admin', '2019-07-30 15:34:10', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (30, '根据条件查询用户信息', 'UserApi-findUserList', '/userApi/findUserList', '4', '根据条件查询用户信息', b'1', '2019-07-30 14:34:25', 'admin', '2019-07-30 14:45:30', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (31, '根据id删除用户信息', 'UserApi-deleteUserById', '/userApi/deleteUserById/**', '4', '根据id删除用户信息', b'1', '2019-07-30 15:34:51', 'admin', '2019-07-30 15:34:51', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (32, '加载下拉列表角色信息', 'RoleApi-findSelectRole', '/roleApi/findSelectRole', '1', '加载下拉列表角色信息', b'1', '2019-07-30 16:21:01', 'admin', '2019-07-30 16:21:01', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (33, '根据id查询有效的用户角色', 'UserApi-findRoleIdsByUserId', '/userApi/findRoleIdsByUserId/**', '4', '根据id查询有效的用户角色', b'1', '2019-07-30 18:21:41', 'admin', '2019-07-30 18:21:41', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (34, '根据id查询角色信息', 'RoleApi-findRoleById', '/roleApi/findRoleById', '1', '根据id查询角色信息', b'1', '2019-07-31 12:34:18', 'admin', '2019-07-31 12:34:18', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (35, '查询 userCode 的个数', 'UserApi-checkUserCode', '/userApi/checkUserCode/**', '4', '查询 userCode 的个数', b'1', '2019-08-03 16:50:10', 'admin', '2019-08-06 18:09:32', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (37, '查询 roleCode 的个数', 'RoleApi-checkRoleCode', '/roleApi/checkRoleCode/**', '1', '查询 roleCode 的个数', b'1', '2019-08-07 10:32:32', 'admin', '2019-08-07 10:33:39', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (38, '单个上传文件', 'FileApi-singleUpload', '/fileApi/singleUpload/**', '1', '文件处理API', b'1', '2019-08-08 09:14:29', 'admin', '2019-08-08 11:35:45', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (39, '修改用户头像', 'UserApi-updateUserIcon', '/userApi/updateUserIcon', '4', '修改用户头像', b'1', '2019-08-08 14:34:21', 'admin', '2019-08-08 14:34:21', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (41, '初始化所有缓存', 'CacheApi-initAll', '/cacheApi/initAll', '1', '初始化所有缓存', b'1', '2019-08-10 12:15:54', 'admin', '2019-08-10 14:04:06', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (42, '初始化指定的缓存', 'CacheApi-initCacheByType', '/cacheApi/init/**', '1', '初始化指定的缓存，initDictionary,initRoleMenu,initRolePermission', b'1', '2019-08-10 12:16:40', 'admin', '2019-08-10 12:16:40', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (43, '查询自动任务列表', 'CoreAutoTaskApi-findAutoTask', '/coreAutoTaskApi/findAutoTask', '5', '查询自动任务列表', b'1', '2019-08-12 16:35:36', 'admin', '2019-08-12 16:35:36', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (44, '保存/修改自动任务', 'CoreAutoTaskApi-saveAutoTask', '/coreAutoTaskApi/saveAutoTask', '5', '保存/修改自动任务', b'1', '2019-08-12 18:52:51', 'admin', '2019-08-12 18:52:51', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (45, '测试Cron表达式下次运行的时间', 'CoreAutoTaskApi-testRunCron', '/coreAutoTaskApi/testRunCron/**', '5', '测试Cron表达式下次运行的时间', b'1', '2019-08-13 09:55:30', 'admin', '2019-08-13 09:55:30', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (46, '启用、停止自动任务', 'CoreAutoTaskApi-updateJobStatus', '/coreAutoTaskApi/updateJobStatus/**', '5', '启用、停止自动任务', b'1', '2019-08-14 09:00:10', 'admin', '2019-08-14 09:00:10', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (47, '禁用自动任务', 'CoreAutoTaskApi-updateJobValidInd', '/coreAutoTaskApi/updateJobValidInd/**', '5', '123123', b'1', '2019-08-14 20:40:53', 'admin', '2019-08-14 20:40:53', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (48, '根据任务id,删除自动任务', 'CoreAutoTaskApi-deleteAutoTask', '/coreAutoTaskApi/deleteAutoTask/**', '5', '根据任务id,删除自动任务', b'1', '2019-08-14 20:49:16', 'admin', '2019-08-14 20:50:10', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (49, '立即执行自动任务', 'CoreAutoTaskApi-immediateExecutionTask', '/coreAutoTaskApi/immediateExecutionTask', '5', '立即执行自动任务', b'1', '2019-08-14 21:18:56', 'admin', '2019-08-14 21:18:56', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (50, '校验自动任务添加、修改时参数的设置', 'CoreAutoTaskApi-checkAutoTask', '/coreAutoTaskApi/checkAutoTask', '5', '校验自动任务添加、修改时参数的设置', b'1', '2019-08-15 10:53:31', 'admin', '2019-08-15 10:53:31', 'admin', NULL, NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '2019-04-23 15:40:22', 'admin', '2019-04-23 15:40:25', 'admin', b'1', 'admin', 'ADMIN', 'admin');
INSERT INTO `sys_role` VALUES (2, '2019-04-23 15:40:22', 'admin', '2019-04-23 15:40:25', 'admin', b'1', 'custom', 'custom', 'custom');
INSERT INTO `sys_role` VALUES (3, '2019-07-16 17:08:53', 'admin', '2019-08-07 10:45:04', 'admin', b'1', '测试使用', 'test588', 'test588');
INSERT INTO `sys_role` VALUES (8, '2019-08-08 18:07:43', 'admin', '2019-08-08 18:11:55', 'admin', b'1', '112', 'ww', 'ww');

-- ----------------------------
-- Table structure for sys_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_schedule_job`;
CREATE TABLE `sys_schedule_job`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `bean_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_concurrent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spring_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_stop` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_schedule_job
-- ----------------------------
INSERT INTO `sys_schedule_job` VALUES (2, NULL, NULL, NULL, NULL, b'0', '1231', '', '123123', '1', '5', '123', '0', '123', '', b'0');
INSERT INTO `sys_schedule_job` VALUES (4, NULL, NULL, NULL, NULL, b'1', '123131', '123', '123123', '1', '2', '123', '0', '123', '23123', b'0');
INSERT INTO `sys_schedule_job` VALUES (5, NULL, NULL, NULL, NULL, b'1', 'com.billow.job.autoTask.TestAutoTask', '*/10 * * * * ?', '234234', '1', '5', '234', '0', 'test', '', b'1');

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
  `icon_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `descritpion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_user
-- ----------------------------
INSERT INTO `u_user` VALUES (1, 'liuyongtao', 'liuyongtao', '1', '$2a$10$R5.R.ld6K4O5EU9qdlKXyOF7Tz3UtSqQ9UROgj5jh7EMH5SwVRQ8a', '150000,150600,150627', '2019-08-15 00:00:00', '13432345654', 'admin-system/displayImag/usericon/liuyongtao.png', NULL, b'1', '2019-04-23 15:26:47', 'admin', '2019-08-09 23:28:16', 'liuyongtao');
INSERT INTO `u_user` VALUES (2, 'admin', 'admin', '3', '$2a$10$seb.F5SucYtqqhNJqFhvu.91M5Ja8v2d2g0BVJ3y12f5fPgZRTtce', '555', '2019-08-02 17:44:09', '1111', 'admin-system/displayImag/usericon/admin.png', NULL, b'1', '2019-04-23 15:26:47', 'admin', '2019-08-09 23:28:48', 'admin');
INSERT INTO `u_user` VALUES (3, '1212', '1212', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', NULL, NULL, NULL, NULL, NULL, b'0', '2019-07-30 15:35:18', 'admin', '2019-07-30 15:54:56', 'admin');
INSERT INTO `u_user` VALUES (5, '345', '34', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', '150000,150400,150421', NULL, NULL, NULL, NULL, b'1', '2019-07-31 10:45:43', 'admin', '2019-08-07 09:50:16', 'admin');
INSERT INTO `u_user` VALUES (6, '678', '6786', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', NULL, NULL, NULL, NULL, NULL, b'1', '2019-07-31 10:47:29', 'admin', '2019-07-31 10:47:29', 'admin');
INSERT INTO `u_user` VALUES (7, '123XX', '123XXX', NULL, '$2a$10$S8iKAT6DLZ4aK54HoR2DN.o5r1Io4LZEpoq3onFD7hp4/Hjv4iQ8W', NULL, NULL, NULL, NULL, NULL, b'1', '2019-07-31 10:48:07', 'admin', '2019-07-31 10:59:44', 'admin');
INSERT INTO `u_user` VALUES (8, '1231AAA', '123www', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', NULL, NULL, NULL, NULL, NULL, b'1', '2019-07-31 10:49:49', 'admin', '2019-07-31 15:40:25', 'admin');
INSERT INTO `u_user` VALUES (9, '12AAA', '12AAA', '1', '$2a$10$rR4JP/smtel2Vd1VWn0Gie7vUWl8.cRWhx3siHDNcOPRDFFtZPevC', '150000,152500,152526', '2019-08-05 14:04:54', '18578757845', NULL, '123', b'1', '2019-07-31 10:53:59', 'admin', '2019-08-06 10:20:48', 'admin');
INSERT INTO `u_user` VALUES (10, '124', '123234', NULL, '$2a$10$XI2CS.KB/BV6SMV/vrUMhekF.OA/WmyLNSlGganjlW33I9ep6hI7G', '130000,130300,130321', NULL, '13425875445', NULL, NULL, b'1', '2019-08-01 18:41:25', 'admin', '2019-08-06 10:18:17', 'admin');
INSERT INTO `u_user` VALUES (12, '567', '5467', '2', '$2a$10$wwi1IkMUwtCX3HCAfaB5YuJpw.KMrShtvIOhusIIB1HQZA/ugJ0um', '140000,140400,140423', '2019-08-06 15:44:02', '15545487445', NULL, '123123', b'1', '2019-08-06 15:44:16', 'admin', '2019-08-07 10:54:57', 'admin');
INSERT INTO `u_user` VALUES (13, '45', '45', '3', '$2a$10$hPB0APphoJ.rcLU8wKJsouaDxq9zMwZw2MGmGCG4ar.wXWy4Oiily', '140000,140100,140105', '2019-08-06 15:44:42', '18925454145', NULL, '4564', b'1', '2019-08-06 15:44:59', 'admin', '2019-08-06 15:44:59', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
