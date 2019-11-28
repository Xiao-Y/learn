/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : 127.0.0.1:3306
 Source Schema         : learn

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 07/11/2019 16:24:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cp_product
-- ----------------------------
DROP TABLE IF EXISTS `cp_product`;
CREATE TABLE `cp_product`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `creator_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updater_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `valid_ind` bit(1) NULL DEFAULT NULL COMMENT '有效标志',
  `dele_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除标志，0-删除，1-正常',
  `commodity_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品信息',
  `grade` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '等级',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `locality_growth` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产地',
  `packing` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '包装',
  `spec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格',
  `commodity_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态：0-无货，1-有货',
  `quantity` int(255) NULL DEFAULT NULL COMMENT '销售数量',
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cp_product
-- ----------------------------
INSERT INTO `cp_product` VALUES ('1190094653702959106', 'admin', 'admin', '2019-11-01 10:34:11', '2019-11-01 11:01:32', b'1', '0', '123', '一级', 1.00, '232', '23', '23', '123', '1', 1000, NULL);
INSERT INTO `cp_product` VALUES ('1190095071354974210', 'admin', NULL, '2019-11-01 10:35:51', NULL, b'1', '0', '23', '一级', 22.00, '23', '23', '12', '23', '1', 1000, NULL);
INSERT INTO `cp_product` VALUES ('1190095968122310658', 'admin', 'admin', '2019-11-01 10:39:24', '2019-11-01 10:39:24', b'1', '0', '55', '一级', 5.00, '57', '55', '5', '55', '1', 1000, NULL);
INSERT INTO `cp_product` VALUES ('1190103030382432258', 'admin', 'admin', '2019-11-01 11:07:28', '2019-11-01 11:07:28', b'1', '1', '888', '一级', 88.00, '88', '8', '88', '88', '1', 1000, NULL);

-- ----------------------------
-- Table structure for mq_publisher
-- ----------------------------
DROP TABLE IF EXISTS `mq_publisher`;
CREATE TABLE `mq_publisher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `correlation_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `exchange_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `rabbit_template_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `routing_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `next_retry` datetime(0) NULL DEFAULT NULL,
  `try_count` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 155 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mq_publisher
-- ----------------------------
INSERT INTO `mq_publisher` VALUES (148, '发送测试消息：2019-10-18 03:58:47.139', '18fc80ae-98a8-45d8-862b-b642a3fd3e37', '2019-10-18 15:58:54', 'sendMailExchange22', '{\"body\":\"5Y+R6YCB5rWL6K+V5raI5oGv77yaMjAxOS0xMC0xOCAwMzo1ODo0Ny4xMzk=\",\"messageProperties\":{\"contentLength\":0,\"contentType\":\"application/octet-stream\",\"correlationIdString\":\"18fc80ae-98a8-45d8-862b-b642a3fd3e37\",\"deliveryMode\":\"NON_PERSISTENT\",\"deliveryTag\":0,\"headers\":{},\"priority\":0}}', 'publicRabbitTemplate', 'sendmailRouteKey', '2', '2019-10-18 15:59:35', '2019-10-18 15:59:35', 3);
INSERT INTO `mq_publisher` VALUES (149, '发送测试消息：2019-10-18 04:00:21.434', '21ccc9da-4ecd-48ea-ad13-20c6f14a8145', '2019-10-18 16:00:28', 'sendMailExchange22', '{\"body\":\"5Y+R6YCB5rWL6K+V5raI5oGv77yaMjAxOS0xMC0xOCAwNDowMDoyMS40MzQ=\",\"messageProperties\":{\"contentLength\":0,\"contentType\":\"application/octet-stream\",\"correlationIdString\":\"21ccc9da-4ecd-48ea-ad13-20c6f14a8145\",\"deliveryMode\":\"NON_PERSISTENT\",\"deliveryTag\":0,\"headers\":{},\"priority\":0}}', 'publicRabbitTemplate', 'sendmailRouteKey', '2', '2019-10-18 16:01:25', '2019-10-18 16:01:23', 3);
INSERT INTO `mq_publisher` VALUES (150, '发送测试消息：2019-10-18 04:01:12.326', 'cb2cf066-b178-4887-b04f-ac721e7c0e6c', '2019-10-18 16:01:12', 'sendMailExchange22', '{\"body\":\"5Y+R6YCB5rWL6K+V5raI5oGv77yaMjAxOS0xMC0xOCAwNDowMToxMi4zMjY=\",\"messageProperties\":{\"contentLength\":0,\"contentType\":\"application/octet-stream\",\"correlationIdString\":\"cb2cf066-b178-4887-b04f-ac721e7c0e6c\",\"deliveryMode\":\"NON_PERSISTENT\",\"deliveryTag\":0,\"headers\":{},\"priority\":0}}', 'publicRabbitTemplate', 'sendmailRouteKey', '2', '2019-10-18 16:02:10', '2019-10-18 16:02:08', 3);
INSERT INTO `mq_publisher` VALUES (151, '发送测试消息：2019-10-30 04:10:22.036', '8e0465ab-39a3-4c04-b286-3cbb21d820aa', '2019-10-30 16:10:27', 'sendMailExchange22', '{\"body\":\"5Y+R6YCB5rWL6K+V5raI5oGv77yaMjAxOS0xMC0zMCAwNDoxMDoyMi4wMzY=\",\"messageProperties\":{\"contentLength\":0,\"contentType\":\"application/octet-stream\",\"correlationIdString\":\"8e0465ab-39a3-4c04-b286-3cbb21d820aa\",\"deliveryMode\":\"NON_PERSISTENT\",\"deliveryTag\":0,\"headers\":{},\"priority\":0}}', 'publicRabbitTemplate', 'sendmailRouteKey', '2', '2019-10-30 16:11:25', '2019-10-30 16:11:23', 3);
INSERT INTO `mq_publisher` VALUES (152, '发送测试消息：2019-10-30 04:10:39.851', '90ddf788-9830-4e7d-84ea-a38f16e4bb76', '2019-10-30 16:10:40', 'sendMailExchange22', '{\"body\":\"5Y+R6YCB5rWL6K+V5raI5oGv77yaMjAxOS0xMC0zMCAwNDoxMDozOS44NTE=\",\"messageProperties\":{\"contentLength\":0,\"contentType\":\"application/octet-stream\",\"correlationIdString\":\"90ddf788-9830-4e7d-84ea-a38f16e4bb76\",\"deliveryMode\":\"NON_PERSISTENT\",\"deliveryTag\":0,\"headers\":{},\"priority\":0}}', 'publicRabbitTemplate', 'sendmailRouteKey', '2', '2019-10-30 16:11:35', '2019-10-30 16:11:33', 3);
INSERT INTO `mq_publisher` VALUES (153, '发送测试消息：2019-10-30 04:11:42.596', 'fc960ac3-4dad-4ddb-8123-f2a33996ecfe', '2019-10-30 16:11:43', 'sendMailExchange', '{\"body\":\"5Y+R6YCB5rWL6K+V5raI5oGv77yaMjAxOS0xMC0zMCAwNDoxMTo0Mi41OTY=\",\"messageProperties\":{\"contentLength\":0,\"contentType\":\"application/octet-stream\",\"correlationIdString\":\"fc960ac3-4dad-4ddb-8123-f2a33996ecfe\",\"deliveryMode\":\"NON_PERSISTENT\",\"deliveryTag\":0,\"headers\":{},\"priority\":0}}', 'publicRabbitTemplate', 'sendmailRouteKey', '1', '2019-10-30 16:11:43', '2019-10-30 16:11:53', 0);
INSERT INTO `mq_publisher` VALUES (154, '发送测试消息：2019-10-30 04:12:20.085', '719831d9-d605-48fb-80b8-b11bafe5795c', '2019-10-30 16:12:20', 'sendMailExchange', '{\"body\":\"5Y+R6YCB5rWL6K+V5raI5oGv77yaMjAxOS0xMC0zMCAwNDoxMjoyMC4wODU=\",\"messageProperties\":{\"contentLength\":0,\"contentType\":\"application/octet-stream\",\"correlationIdString\":\"719831d9-d605-48fb-80b8-b11bafe5795c\",\"deliveryMode\":\"NON_PERSISTENT\",\"deliveryTag\":0,\"headers\":{},\"priority\":0}}', 'publicRabbitTemplate', 'sendmailRouteKey', '1', '2019-10-30 16:12:20', '2019-10-30 16:12:30', 0);
INSERT INTO `mq_publisher` VALUES (155, '发送测试消息：2019-10-31 10:13:33.717', '11266413-b96c-424b-aa11-593720e72639', '2019-10-31 10:13:34', 'sendMailExchange22', '{\"body\":\"5Y+R6YCB5rWL6K+V5raI5oGv77yaMjAxOS0xMC0zMSAxMDoxMzozMy43MTc=\",\"messageProperties\":{\"contentLength\":0,\"contentType\":\"application/octet-stream\",\"correlationIdString\":\"11266413-b96c-424b-aa11-593720e72639\",\"deliveryMode\":\"NON_PERSISTENT\",\"deliveryTag\":0,\"headers\":{},\"priority\":0}}', 'publicRabbitTemplate', 'sendmailRouteKey', '2', '2019-10-31 10:14:30', '2019-10-31 10:14:28', 3);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1647 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_role_menu
-- ----------------------------
INSERT INTO `r_role_menu` VALUES (1381, 1, 1, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1382, 1, 25, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1383, 1, 26, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1384, 1, 27, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1385, 1, 7, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1386, 1, 8, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1387, 1, 9, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1388, 1, 10, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1389, 1, 36, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1390, 1, 38, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1391, 1, 39, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1392, 1, 37, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1393, 1, 5, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1394, 1, 33, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1395, 1, 6, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1396, 1, 32, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1397, 1, 2, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1398, 1, 34, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1399, 1, 35, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1400, 1, 4, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1401, 1, 11, '2019-08-30 23:03:35', 'admin', '2019-08-30 23:03:35', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1402, 1, 14, '2019-08-30 23:03:36', 'admin', '2019-08-30 23:03:36', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1403, 1, 12, '2019-08-30 23:03:36', 'admin', '2019-08-30 23:03:36', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1404, 1, 13, '2019-08-30 23:03:36', 'admin', '2019-08-30 23:03:36', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1405, 1, 3, '2019-08-30 23:03:36', 'admin', '2019-08-30 23:03:36', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1406, 1, 31, '2019-08-30 23:03:36', 'admin', '2019-08-30 23:03:36', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1407, 1, 20, '2019-08-30 23:03:36', 'admin', '2019-08-30 23:03:36', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1408, 1, 24, '2019-08-30 23:03:36', 'admin', '2019-08-30 23:03:36', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1633, 2, 25, '2019-08-31 13:38:31', 'admin', '2019-08-31 13:38:31', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1634, 2, 26, '2019-08-31 13:38:31', 'admin', '2019-08-31 13:38:31', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1635, 2, 27, '2019-08-31 13:38:31', 'admin', '2019-08-31 13:38:31', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1636, 2, 25, '2019-08-31 13:39:15', 'admin', '2019-08-31 13:39:15', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1637, 2, 26, '2019-08-31 13:39:15', 'admin', '2019-08-31 13:39:15', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1638, 2, 27, '2019-08-31 13:39:15', 'admin', '2019-08-31 13:39:15', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1641, 1, 42, '2019-09-03 21:21:45', 'admin', '2019-09-03 21:21:45', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1642, 1, 43, '2019-09-03 21:21:45', 'admin', '2019-09-03 21:21:45', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1643, 1, 44, '2019-09-07 10:33:38', 'admin', '2019-09-07 10:33:38', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1644, 1, 42, '2019-09-07 10:45:03', 'admin', '2019-09-07 10:45:03', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1645, 1, 44, '2019-09-07 10:45:03', 'admin', '2019-09-07 10:45:03', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1647, 1, 46, '2019-09-07 11:06:04', 'admin', '2019-09-07 11:06:04', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1648, 1, 47, '2019-11-07 11:12:43', 'admin', '2019-11-07 11:12:43', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1649, 1, 2, '2019-11-07 16:07:47', 'admin', '2019-11-07 16:07:47', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1650, 1, 47, '2019-11-07 16:07:47', 'admin', '2019-11-07 16:07:47', 'admin', b'1');
INSERT INTO `r_role_menu` VALUES (1651, 1, 48, '2019-11-07 16:07:47', 'admin', '2019-11-07 16:07:47', 'admin', b'1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 2779 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of r_role_permission
-- ----------------------------
INSERT INTO `r_role_permission` VALUES (2594, 1, 1, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2595, 1, 2, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2596, 1, 3, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2597, 1, 4, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2598, 1, 5, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2599, 1, 6, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2600, 1, 7, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2601, 1, 8, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2602, 1, 9, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2603, 1, 12, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2604, 1, 13, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2605, 1, 14, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2606, 1, 15, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2607, 1, 16, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2608, 1, 17, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2609, 1, 18, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2610, 1, 19, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2611, 1, 20, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2612, 1, 21, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2613, 1, 22, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2614, 1, 23, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2615, 1, 24, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2616, 1, 25, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2617, 1, 26, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2618, 1, 27, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2619, 1, 28, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2620, 1, 29, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2621, 1, 30, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2622, 1, 31, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2623, 1, 32, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2624, 1, 33, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2625, 1, 34, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2626, 1, 35, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2627, 1, 37, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2628, 1, 38, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2629, 1, 39, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2630, 1, 42, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2631, 1, 41, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2632, 1, 43, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2633, 1, 44, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2634, 1, 45, '2019-08-30 23:03:37', 'admin', '2019-08-30 23:03:37', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2635, 1, 46, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2636, 1, 47, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2637, 1, 48, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2638, 1, 49, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2639, 1, 50, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2640, 1, 51, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2641, 1, 52, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2642, 1, 53, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2643, 1, 54, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2644, 1, 55, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2645, 1, 56, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2646, 1, 57, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2647, 1, 58, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2648, 1, 59, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2649, 1, 60, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2650, 1, 65, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2651, 1, 61, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2652, 1, 62, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2653, 1, 63, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2654, 1, 64, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2655, 1, 66, '2019-08-30 23:03:38', 'admin', '2019-08-30 23:03:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2740, 2, 5, '2019-08-31 13:57:43', 'admin', '2019-08-31 13:57:43', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2741, 2, 5, '2019-08-31 14:00:17', 'admin', '2019-08-31 14:00:17', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2743, 2, 5, '2019-08-31 14:01:11', 'admin', '2019-08-31 14:01:11', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2749, 2, 3, '2019-08-31 14:08:10', 'admin', '2019-08-31 14:08:10', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2750, 2, 1, '2019-08-31 14:08:21', 'admin', '2019-08-31 14:08:21', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2751, 2, 2, '2019-08-31 14:08:21', 'admin', '2019-08-31 14:08:21', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2752, 2, 4, '2019-08-31 14:08:21', 'admin', '2019-08-31 14:08:21', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2753, 2, 6, '2019-08-31 14:08:21', 'admin', '2019-08-31 14:08:21', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2754, 2, 7, '2019-08-31 14:08:21', 'admin', '2019-08-31 14:08:21', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2755, 2, 8, '2019-08-31 14:08:21', 'admin', '2019-08-31 14:08:21', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2756, 2, 9, '2019-08-31 14:08:21', 'admin', '2019-08-31 14:08:21', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2757, 2, 12, '2019-08-31 14:08:21', 'admin', '2019-08-31 14:08:21', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2760, 1, 67, '2019-08-31 17:16:28', 'admin', '2019-08-31 17:16:28', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2761, 1, 68, '2019-08-31 21:24:18', 'admin', '2019-08-31 21:24:18', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2762, 1, 69, '2019-08-31 21:35:31', 'admin', '2019-08-31 21:35:31', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2763, 1, 70, '2019-09-01 14:36:38', 'admin', '2019-09-01 14:36:38', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2764, 1, 71, '2019-09-03 21:24:39', 'admin', '2019-09-03 21:24:39', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2765, 1, 72, '2019-09-04 21:07:10', 'admin', '2019-09-04 21:07:10', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2766, 1, 73, '2019-09-04 21:17:06', 'admin', '2019-09-04 21:17:06', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2767, 1, 75, '2019-09-06 20:15:13', 'admin', '2019-09-06 20:15:13', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2768, 1, 77, '2019-09-06 20:15:13', 'admin', '2019-09-06 20:15:13', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2769, 1, 76, '2019-09-06 20:15:13', 'admin', '2019-09-06 20:15:13', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2770, 1, 74, '2019-09-06 20:15:13', 'admin', '2019-09-06 20:15:13', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2771, 1, 78, '2019-09-07 12:14:15', 'admin', '2019-09-07 12:14:15', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2772, 1, 79, '2019-09-07 15:10:45', 'admin', '2019-09-07 15:10:45', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2773, 1, 80, '2019-09-08 12:02:54', 'admin', '2019-09-08 12:02:54', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2774, 1, 81, '2019-11-01 10:04:23', 'admin', '2019-11-01 10:04:23', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2775, 1, 82, '2019-11-01 10:04:23', 'admin', '2019-11-01 10:04:23', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2776, 1, 83, '2019-11-01 10:04:23', 'admin', '2019-11-01 10:04:23', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2777, 1, 84, '2019-11-01 10:04:23', 'admin', '2019-11-01 10:04:23', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2778, 1, 85, '2019-11-01 10:51:27', 'admin', '2019-11-01 10:51:27', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2779, 1, 94, '2019-11-07 08:48:46', 'admin', '2019-11-07 08:48:46', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2780, 1, 93, '2019-11-07 08:48:46', 'admin', '2019-11-07 08:48:46', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2781, 1, 92, '2019-11-07 08:48:46', 'admin', '2019-11-07 08:48:46', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2782, 1, 91, '2019-11-07 08:48:46', 'admin', '2019-11-07 08:48:46', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2783, 1, 90, '2019-11-07 08:48:46', 'admin', '2019-11-07 08:48:46', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2784, 1, 89, '2019-11-07 08:48:46', 'admin', '2019-11-07 08:48:46', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2785, 1, 88, '2019-11-07 08:48:46', 'admin', '2019-11-07 08:48:46', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2786, 1, 87, '2019-11-07 08:48:46', 'admin', '2019-11-07 08:48:46', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2787, 1, 86, '2019-11-07 08:48:46', 'admin', '2019-11-07 08:48:46', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2788, 1, 95, '2019-11-07 11:12:43', 'admin', '2019-11-07 11:12:43', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2789, 1, 96, '2019-11-07 11:12:43', 'admin', '2019-11-07 11:12:43', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2790, 1, 97, '2019-11-07 11:12:43', 'admin', '2019-11-07 11:12:43', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2791, 1, 98, '2019-11-07 11:12:43', 'admin', '2019-11-07 11:12:43', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2792, 1, 99, '2019-11-07 11:15:44', 'admin', '2019-11-07 11:15:44', 'admin', b'1');
INSERT INTO `r_role_permission` VALUES (2794, 1, 100, '2019-11-07 16:06:02', 'admin', '2019-11-07 16:06:02', 'admin', b'1');

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
-- Table structure for sys_apply_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_apply_info`;
CREATE TABLE `sys_apply_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apply_data` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `apply_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `apply_user_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_end` bit(1) NULL DEFAULT NULL,
  `proc_def_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `proc_inst_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `vo_clazz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_apply_info
-- ----------------------------
INSERT INTO `sys_apply_info` VALUES (1, '{\"endDate\":1569859200000,\"pageNo\":0,\"pageSize\":10,\"reason\":\"月阿斯达\",\"recordCount\":0,\"startDate\":1567510559000}', 'leave', 'admin', '2019-09-03 21:13:17', 'admin', b'0', NULL, '32501', '2019-09-03 21:13:18', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (2, '{\"endDate\":1567516561000,\"pageNo\":0,\"pageSize\":10,\"reason\":\"2312312312\",\"recordCount\":0,\"startDate\":1567516559000}', 'leave', 'admin', '2019-09-03 21:16:07', 'admin', b'0', 'process_pool:1:27513', '35001', '2019-09-03 21:16:09', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (3, '{\"reason\":\"1231123\",\"endDate\":1567948040000,\"startDate\":1567872000000}', 'leave', 'hr', '2019-09-08 21:07:24', 'hr', b'0', 'process_pool:1:27513', '47501', '2019-09-08 21:07:26', 'hr', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (4, '{\"reason\":\"123123123\",\"endDate\":1569859200000,\"startDate\":1567958400000}', 'leave', 'admin', '2019-09-08 21:30:20', 'admin', b'0', 'process_pool:1:27513', '47520', '2019-09-08 21:30:20', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (5, '{\"reason\":\"12312312\",\"endDate\":1568033684000,\"startDate\":1567958400000}', 'leave', 'admin', '2019-09-09 20:55:03', 'admin', b'0', 'process_pool:1:27513', '50005', '2019-09-09 20:55:06', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (6, '{\"reason\":\"1231123123\",\"endDate\":1570118400000,\"startDate\":1567958400000}', 'leave', 'admin', '2019-09-09 21:03:54', 'admin', b'0', 'process_pool:1:27513', '50028', '2019-09-09 21:03:54', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (7, '{\"reason\":\"56757567567\",\"endDate\":1571068800000,\"startDate\":1568034541000}', 'leave', 'admin', '2019-09-09 21:09:11', 'admin', b'0', 'leave:5:50004', '52501', '2019-09-09 21:09:13', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (8, '{\"reason\":\"123123123\",\"endDate\":1572451200000,\"startDate\":1568035201000}', 'leave', 'hr', '2019-09-09 21:20:08', 'hr', b'0', 'leave:6:52534', '52535', '2019-09-09 21:20:08', 'hr', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (9, '{\"reason\":\"3453535345\",\"endDate\":1572537600000,\"startDate\":1568035493000}', 'leave', 'admin', '2019-09-09 21:25:03', 'admin', b'0', 'leave:6:52534', '52561', '2019-09-09 21:25:03', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (10, '{\"approveStatus\":\"2\",\"reason\":\"1231123\",\"endDate\":1568119505000,\"startDate\":1568649600000}', 'leave', 'admin', '2019-09-10 20:45:23', 'admin', b'0', 'leave:6:52534', '55001', '2019-09-10 20:45:27', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (11, '{\"approveStatus\":\"2\",\"reason\":\"123112\",\"endDate\":1568120059000,\"startDate\":1568120056000}', 'leave', 'admin', '2019-09-10 20:54:22', 'admin', b'0', 'leave:6:52534', '55030', '2019-09-10 20:54:22', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (12, '{\"approveStatus\":\"0\",\"reason\":\"123123123\",\"endDate\":1569513600000,\"startDate\":1568121153000}', 'leave', 'admin', '2019-09-10 21:12:39', 'admin', b'0', 'leave:6:52534', '55053', '2019-09-10 21:12:39', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (13, '{\"approveStatus\":\"0\",\"reason\":\"0000000\",\"endDate\":1569772800000,\"startDate\":1568044800000}', 'leave', 'admin', '2019-09-10 21:19:16', 'admin', b'0', 'leave:6:52534', '57501', '2019-09-10 21:19:18', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (14, '{\"reason\":\"12123123\",\"submitType\":\"cancel\",\"endDate\":1569600000000,\"startDate\":1568205044000}', 'leave', 'admin', '2019-09-11 20:30:56', 'admin', b'0', 'leave:7:60004', '60005', '2019-09-11 20:33:10', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (15, '{\"reason\":\"123123\",\"submitType\":\"agree\",\"endDate\":1568205261000,\"startDate\":1568205259000}', 'leave', 'admin', '2019-09-11 20:34:24', 'admin', b'0', 'leave:7:60004', '60031', '2019-09-11 20:36:19', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (16, '{\"reason\":\"1231223\",\"submitType\":\"cancel\",\"endDate\":1568427288000,\"startDate\":1568427286000}', 'leave', 'admin', '2019-09-14 10:15:05', 'admin', b'0', 'leave:8:62504', '62505', '2019-09-14 10:17:47', 'admin', b'1', NULL);
INSERT INTO `sys_apply_info` VALUES (17, '{\"reason\":\"3123123\",\"submitType\":\"reject\",\"endDate\":1568431507000,\"startDate\":1568431505000}', 'leave', 'admin', '2019-09-14 11:25:10', 'admin', b'0', 'leave:8:62504', '62530', '2019-09-14 11:26:32', 'hr', b'1', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `sys_data_dictionary` VALUES (9, 'sendMailType', '0', '不发送', 'publicJob', 1, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (10, 'sendMailType', '1', '全发送', 'publicJob', 2, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (11, 'sendMailType', '2', '异常时发送', 'publicJob', 3, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (12, 'sendMailType', '3', '成功时发送', 'publicJob', 4, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (13, 'dataSourcesType', '1', '固定内容', 'adminSystem', 1, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (14, 'dataSourcesType', '2', 'SQL查询', 'adminSystem', 2, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (15, 'dataSourcesType', '3', '参数设置', 'adminSystem', 3, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (16, 'mailType', '1', '普通邮件', 'adminSystem', 1, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (17, 'mailType', '2', 'html邮件', 'adminSystem', 2, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (18, 'mailType', '3', '带附件邮件', 'adminSystem', 2, b'0', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (19, 'dataSourcesType', '4', '混合（参数和SQL）', 'adminSystem', 4, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (20, 'userGroupType', 'General', '普通用户', 'adminUser', 1, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (21, 'userGroupType', 'GroupLeader', '分管经理', 'adminUser', 2, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (22, 'userGroupType', 'HR', '人力资源', 'adminUser', 3, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (23, 'userGroupType', 'GeneralMg', '总经理', 'adminUser', 4, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (24, 'applyType', 'leave', '请假申请', 'adminSystem', 1, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (25, 'mailType', '4', 'FreeMarker邮件', 'adminSystem', 2, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');
INSERT INTO `sys_data_dictionary` VALUES (26, 'mailType', '5', 'Thymeleaf邮件', 'adminSystem', 2, b'1', '2019-07-11 14:06:47', 'admin', '2019-07-11 14:06:56', 'admin');

-- ----------------------------
-- Table structure for sys_mail_template
-- ----------------------------
DROP TABLE IF EXISTS `sys_mail_template`;
CREATE TABLE `sys_mail_template`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mail_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mail_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `data_sources` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `to_emails` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mail_temp` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `mail_markdown` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `run_sql` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `single_result` bit(1) NULL DEFAULT NULL,
  `template_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `descritpion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attachment` bit(1) NULL DEFAULT NULL,
  `template_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_mail_template
-- ----------------------------
INSERT INTO `sys_mail_template` VALUES (1, 'autoTask', '2', '2', NULL, '', '<p><strong>以下是自动任务运行情况</strong></p>\n<table>\n<thead>\n<tr>\n<th>日志ID</th>\n<th>任务ID</th>\n<th>任务分组</th>\n<th>任务名称</th>\n<th>是否成功</th>\n<th>运行时间</th>\n</tr>\n</thead>\n<tbody>\n<tr>\n<td>#{id}</td>\n<td>#{jobId}</td>\n<td>#{jobGroup}</td>\n<td>#{jobName}</td>\n<td>#{isSuccess}</td>\n<td>#{runTime}</td>\n</tr>\n</tbody>\n</table>\n<pre><div class=\"hljs\"><code class=\"lang-java\">#{info}\n</code></div></pre>\n', '**以下是自动任务运行情况**\n|日志ID|任务ID|任务分组|任务名称|是否成功|运行时间|\n|-|-|-|-|-|-|\n|#{id}|#{jobId}|#{jobGroup}|#{jobName}|#{isSuccess}|#{runTime}|\n\n```java\n#{info}\n```', 'SELECT r.id,r.job_id as jobId,\n(select d.field_display from sys_data_dictionary d where d.system_module = \'adminSystem\' and d.field_type = \'systemModule\' and d.field_value = r.job_group) as jobGroup,\nr.job_name as jobName,\n(CASE r.is_success\n	WHEN TRUE THEN\n		\'success\'\n	ELSE\n		\'error\'\nEND)\n as isSuccess,\nr.run_time as runTime,\nr.info \nFROM sys_schedule_job_log r \nwhere r.id = #{logId}', NULL, NULL, '自动任务', b'1', NULL, NULL, '2019-08-22 20:29:02', 'admin', NULL, NULL);
INSERT INTO `sys_mail_template` VALUES (2, 'testhtml', '2', '1', NULL, '', '<p><img src=\"admin-system/displayImag/markdown/201908220906457923880.jpg\" alt=\"8111d168a53456c63ed4864e9b65f50d.jpg\" /></p>\n<p><img src=\"admin-system/displayImag/markdown/201908220908311726470.jpg\" alt=\"dalog.jpg\" /></p>\n', '![8111d168a53456c63ed4864e9b65f50d.jpg](admin-system/displayImag/markdown/201908220906457923880.jpg)\n\n![dalog.jpg](admin-system/displayImag/markdown/201908220908311726470.jpg)', NULL, NULL, NULL, '测试', b'1', NULL, NULL, '2019-08-22 21:08:36', 'admin', NULL, NULL);
INSERT INTO `sys_mail_template` VALUES (3, 'messageParam-FreeMarker', '4', '3', NULL, '', '', '', NULL, NULL, 'messageParam.ftl', '测试', b'1', NULL, NULL, '2019-08-22 21:08:36', 'admin', NULL, NULL);
INSERT INTO `sys_mail_template` VALUES (4, 'messageSQL-FreeMarker', '4', '2', NULL, '', '', '', 'SELECT mail_code as mailCode,template_path as templatePath FROM sys_mail_template r where r.mail_code = \'#{mailCode}\'', b'1', 'messageSQL.ftl', '测试messageSQL.ftl', b'1', NULL, NULL, '2019-08-22 21:08:36', 'admin', NULL, NULL);
INSERT INTO `sys_mail_template` VALUES (5, 'messageParamSQL-FreeMarker', '4', '4', NULL, '', '', '', 'SELECT mail_code as mailCode,template_path as templatePath FROM sys_mail_template r where r.mail_code = \'#{mailCode}\'', b'1', 'messageParamSQL.ftl', '测试messageSQL.ftl', b'1', NULL, NULL, '2019-08-22 21:08:36', 'admin', NULL, NULL);
INSERT INTO `sys_mail_template` VALUES (6, 'messageParam-Thymeleaf', '5', '3', NULL, '', '', '', NULL, NULL, 'messageParam', '测试', b'1', NULL, NULL, '2019-08-22 21:08:36', 'admin', NULL, NULL);
INSERT INTO `sys_mail_template` VALUES (7, 'messageSQL2-FreeMarker', '4', '2', NULL, '', '', '', 'SELECT mail_code as mailCode,template_path as templatePath FROM sys_mail_template r', b'0', 'messageSQL2.ftl', '测试messageSQL.ftl', b'1', NULL, NULL, '2019-08-22 21:08:36', 'admin', NULL, NULL);
INSERT INTO `sys_mail_template` VALUES (8, 'messageParamSQL2-FreeMarker', '4', '4', NULL, '', '', '', 'SELECT mail_code as mailCode,template_path as templatePath FROM sys_mail_template r', b'0', 'messageParamSQL2.ftl', '测试messageSQL.ftl', b'1', NULL, NULL, '2019-08-22 21:08:36', 'admin', NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 'homeIndex', '首页', NULL, b'1', 'collection_fill', NULL, 0, '2019-04-23 15:41:51', 'admin', '2019-09-13 09:37:17', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (2, 'sys', '系统管理', NULL, b'1', 'setup', '', 100, '2019-04-23 15:41:51', 'admin', '2019-09-13 09:46:31', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (3, 'sysWhiteListIndex', '白名单', 2, b'1', 'barrage', '', 4, '2019-04-23 15:41:51', 'admin', '2019-09-15 13:06:35', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (4, 'sysMenuListIndex', '菜单管理', 2, b'1', 'barrage_fill', '', 1, '2019-04-23 15:41:51', 'admin', '2019-09-13 09:47:51', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (5, 'job', '自动任务', NULL, b'1', 'clock_fill', '', 99, '2019-04-23 15:41:51', 'admin', '2019-09-13 09:45:45', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (6, 'jobAutoTaskListIndex', '自动任务', 5, b'1', 'clock', '', 1, '2019-04-23 15:41:51', 'admin', '2019-09-13 09:46:04', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (7, 'pro', '产品系统', NULL, b'1', 'manage_fill', '', 10, '2019-04-23 15:41:51', 'admin', '2019-09-13 09:42:02', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (8, 'proProductListIndex', '产品管理', 7, b'1', 'manage', '', 2, '2019-04-23 15:41:51', 'admin', '2019-09-13 09:43:54', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (9, 'proProductEdit', '修改产品信息', 8, b'0', 'editor', '', 3, '2019-04-23 15:41:51', 'admin', '2019-09-13 09:44:08', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (10, 'proProductImageEdit', '修改产品图片', 8, b'0', 'brush_fill', '', 4, '2019-04-23 15:41:51', 'admin', '2019-09-13 09:44:21', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (11, 'sysRoleListIndex', '角色管理', 2, b'1', 'addressbook_fill', '', 2, '2019-04-23 15:41:51', 'admin', '2019-09-13 09:48:08', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (12, 'sysPermissionListIndex', '权限管理', 2, b'1', 'flashlight_fill', NULL, 3, '2019-07-10 15:22:05', 'admin', '2019-09-13 09:48:39', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (13, 'sysPermissionEdit', '修改权限信息', 12, b'0', 'brush_fill', NULL, 1, '2019-07-10 17:42:20', 'admin', '2019-09-13 09:48:50', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (14, 'sysRoleEdit', '修改角色信息', 11, b'0', 'editor', NULL, 2, '2019-07-11 18:00:41', 'admin', '2019-09-13 09:48:20', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (20, 'edit', '编辑', NULL, b'1', 'brush_fill', NULL, 120, '2019-07-23 14:13:26', 'admin', '2019-09-13 09:49:07', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (24, 'editMarkdown', 'Markdown', 20, b'1', 'brush_fill', NULL, 1, '2019-07-26 14:27:36', 'admin', '2019-09-11 21:22:43', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (25, 'user', '用户系统', NULL, b'1', 'people_fill', NULL, 3, '2019-07-30 14:29:20', 'admin', '2019-09-13 09:40:00', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (26, 'userUserListIndex', '用户管理', 25, b'1', 'addpeople_fill', NULL, 0, '2019-07-30 14:31:46', 'admin', '2019-09-13 09:40:25', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (27, 'userUserEdit', '用户修改/添加', 26, b'0', 'addressbook_fill', NULL, 1, '2019-07-30 15:30:22', 'admin', '2019-09-13 09:40:34', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (31, 'sysCacheMagIndex', '缓存管理', 2, b'1', 'budaidise', NULL, 99, '2019-08-10 11:43:37', 'admin', '2019-09-15 13:06:02', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (32, 'jobAutoTaskEdit', '修改自动任务', 6, b'0', 'editor', NULL, 0, '2019-08-12 17:41:49', 'admin', '2019-09-13 09:46:15', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (33, 'jobAutoTaskCharts', '任务概览', 5, b'1', 'browse_fill', NULL, 0, '2019-08-20 10:03:01', 'admin', '2019-09-13 09:45:53', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (34, 'sysMailTemplateListIndex', '邮件模板管理', 2, b'1', 'mail_fill', NULL, 0, '2019-08-21 10:27:20', 'admin', '2019-09-13 09:46:57', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (35, 'sysMailTemplateEdit', '邮件模板修改', 34, b'0', 'brush_fill', NULL, 0, '2019-08-21 15:35:19', 'admin', '2019-09-13 09:47:11', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (36, 'processMag', '流程管理', NULL, b'1', 'activity', NULL, 80, '2019-08-27 20:08:40', 'admin', '2019-09-13 09:44:44', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (37, 'procProcDefListIndex', '流程定义', 36, b'1', 'document_fill', NULL, 3, '2019-08-27 20:15:53', 'admin', '2019-09-13 09:45:29', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (38, 'procProcDeployListIndex', '流程部署', 36, b'1', 'qrcode_fill', NULL, 0, '2019-08-30 21:26:52', 'admin', '2019-09-13 09:45:01', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (39, 'procViewProcessImg', '查看流程图片', 36, b'0', 'browse_fill', NULL, 0, '2019-08-30 21:42:01', 'admin', '2019-09-13 09:45:12', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (42, 'workbench', '我的工作台', NULL, b'1', 'workbench', NULL, 1, '2019-09-03 21:20:32', 'admin', '2019-09-13 09:38:15', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (43, 'workbenchMyTaskListIndex', '我的任务', 42, b'1', 'task_fill', NULL, 1, '2019-09-03 21:21:33', 'admin', '2019-09-13 09:39:40', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (44, 'workbenchApplyList', '我要申请', 42, b'1', 'mail_fill', NULL, 0, '2019-09-07 10:32:33', 'admin', '2019-09-13 09:39:11', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (46, 'workbenchApplyInfo', '申请信息', 44, b'0', 'brush_fill', NULL, 1, '2019-09-07 11:05:44', 'admin', '2019-09-13 09:39:23', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (47, 'sysDataDictionaryList', '数据字典', 2, b'1', 'qrcode_fill', NULL, 95, '2019-11-07 08:51:33', 'admin', '2019-11-07 11:14:04', 'admin', b'1');
INSERT INTO `sys_menu` VALUES (48, 'sysDataDictionaryEdit', '修改数据字典', 47, b'0', 'brush_fill', NULL, 0, '2019-11-07 16:07:39', 'admin', '2019-11-07 16:16:37', 'admin', b'1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '查询省市区', 'CityApi-findCity', '/cityApi/findCity/**', '1', '查询省市区', b'1', '2019-04-23 15:41:51', 'admin', '2019-08-22 15:46:02', 'admin', NULL, NULL, NULL);
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
INSERT INTO `sys_permission` VALUES (51, '查询自动任务执行日志', 'CoreAutoTaskApi-findAutoTaskLog', '/coreAutoTaskApi/findAutoTaskLog', '5', '查询自动任务执行日志', b'1', '2019-08-20 10:01:30', 'admin', '2019-08-20 10:01:30', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (52, '根据条件查询邮件模板信息', 'MailTemplateApi-findMailTemplateList', '/mailTemplateApi/findMailTemplateList', '1', '根据条件查询邮件模板信息', b'1', '2019-08-21 11:21:03', 'admin', '2019-08-21 11:21:03', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (53, '根据id获取邮件模板信息', 'MailTemplateApi-findMailTemplateById', '/mailTemplateApi/findMailTemplateById/**', '1', '根据id获取邮件模板信息', b'1', '2019-08-21 15:43:00', 'admin', '2019-08-21 15:43:00', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (54, '根据ID删除邮件模板', 'MailTemplateApi-deleteMailTemplateById', '/mailTemplateApi/deleteMailTemplateById/**', '1', '根据ID删除邮件模板', b'1', '2019-08-21 15:59:45', 'admin', '2019-08-21 15:59:45', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (55, '根据ID禁用邮件模板', 'MailTemplateApi-prohibitMailTemplateById', '/mailTemplateApi/prohibitMailTemplateById/**', '1', '根据ID禁用邮件模板', b'1', '2019-08-21 16:00:40', 'admin', '2019-08-21 16:00:40', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (56, '添加邮件模板信息', 'MailTemplateApi-saveMailTemplate', '/mailTemplateApi/saveMailTemplate', '1', '添加邮件模板信息', b'1', '2019-08-21 16:01:35', 'admin', '2019-08-21 16:01:35', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (57, '更新邮件模板信息', 'MailTemplateApi-updateMailTemplate', '/mailTemplateApi/updateMailTemplate', '1', '更新邮件模板信息', b'1', '2019-08-21 16:02:12', 'admin', '2019-08-21 16:02:12', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (58, '查询 mailCode 的个数', 'MailTemplateApi-checkMailCode', '/mailTemplateApi/checkMailCode/**', '1', '查询 mailCode 的个数', b'1', '2019-08-21 18:35:53', 'admin', '2019-08-21 18:35:53', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (59, '查询流程定义列表', 'ActDeployApi-findProcDeployList', '/actDeployApi/findProcDeployList', '1', '查询流程定义列表', b'1', '2019-08-27 20:59:00', 'admin', '2019-08-30 21:16:20', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (60, '部署流程（多文件）', 'ActDeployApi-deploy-file', '/actDeployApi/deploy/file', '1', '部署流程（多文件）', b'1', '2019-08-28 20:57:37', 'admin', '2019-08-28 21:08:34', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (61, '查询流程定义列表', 'ActProcDefApi-findProcDefList', '/actProcDefApi/findProcDefList', '1', '查询流程定义列表', b'1', '2019-08-30 21:21:17', 'admin', '2019-08-30 21:21:17', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (62, '挂起流程定义', 'ActProcDefApi-suspendProcess', '/actProcDefApi/suspendProcess/**', '1', '挂起流程定义', b'1', '2019-08-30 21:22:17', 'admin', '2019-08-30 21:22:17', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (63, '激活流程定义', 'ActProcDefApi-activateProcess', '/actProcDefApi/activateProcess/**', '1', '激活流程定义', b'1', '2019-08-30 21:22:56', 'admin', '2019-08-30 21:22:56', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (64, '查询流程部署列表', 'ActDeployApi-findProcDeployList', '/actDeployApi/findProcDeployList', '1', '查询流程部署列表', b'1', '2019-08-30 21:23:52', 'admin', '2019-08-30 21:23:52', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (65, '根据id获取原始的流程图', 'ActDeployApi-viewDeployImgById', '/actDeployApi/viewDeployImgById/**', '1', '根据id获取原始的流程图', b'1', '2019-08-30 21:24:36', 'admin', '2019-08-30 21:24:36', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (66, '根据id删除流程部署,(cascade：是否级联删除)', 'ActDeployApi-delProceDeployById', '/actDeployApi/delProceDeployById/**', '1', '根据id删除流程部署,(cascade：是否级联删除)', b'1', '2019-08-30 23:03:15', 'admin', '2019-08-30 23:03:15', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (67, '查询个人任务数量', 'ApplyApi-queryAssigneeTaskCount', '/applyApi/queryAssigneeTaskCount', '1', '查询个人任务数量', b'1', '2019-08-31 16:23:57', 'admin', '2019-09-06 20:08:02', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (68, '我发起的流程数量（所有的）', 'ApplyApi-myStartProdeCount', '/applyApi/myStartProdeCount', '1', '我发起的流程数量（所有的）', b'1', '2019-08-31 21:23:26', 'admin', '2019-09-06 20:09:52', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (69, '运行中的的流程', 'ApplyApi-auditProgressProdeCount', '/applyApi/ongoingCount', '1', '运行中的的流程', b'1', '2019-08-31 21:35:19', 'admin', '2019-09-06 20:11:10', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (70, '提交请假申请', 'LeaveApi-submitLeave', '/leaveApi/submitLeave', '1', '提交请假申请', b'1', '2019-09-01 14:36:07', 'admin', '2019-09-01 14:36:07', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (71, '查询个人任务列表', 'ApplyApi-queryMyTaskList', '/applyApi/queryMyTaskList', '1', '查询个人任务列表', b'1', '2019-09-03 21:24:26', 'admin', '2019-09-06 20:07:34', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (72, '查看活动的流程图（显示运行轨迹）', 'ApplyApi-viewExecutionImgById', '/applyApi/viewExecutionImgById/**', '1', '查看活动的流程图（显示运行轨迹）', b'1', '2019-09-04 21:06:58', 'admin', '2019-09-06 20:11:59', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (73, '认领任务', 'ApplyApi-claimTask', '/applyApi/claimTask/**', '1', '认领任务', b'1', '2019-09-04 21:15:48', 'admin', '2019-09-06 20:11:33', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (74, '我发起的流程（所有的）', 'ApplyApi-myStartProdeList', '/applyApi/myStartProdeList', '1', '我发起的流程（所有的）', b'1', '2019-09-06 20:08:55', 'admin', '2019-09-06 20:08:55', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (75, '删除已经结束的申请', 'ApplyApi-deleteApplyInfoById', '/applyApi/deleteApplyInfoById/**', '1', '删除已经结束的申请', b'1', '2019-09-06 20:13:04', 'admin', '2019-09-06 20:13:04', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (76, '提交请假任务', 'ApplyApi-commitLeaveProcess', '/applyApi/commitLeaveProcess/**', '1', '提交请假任务', b'1', '2019-09-06 20:13:54', 'admin', '2019-09-06 20:14:49', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (77, '提交请假申请', 'ApplyApi-submitLeave', '/applyApi/submitLeave', '1', '提交请假申请', b'1', '2019-09-06 20:14:33', 'admin', '2019-09-06 20:14:33', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (78, '根据ID查询申请信息', 'ApplyApi-findApplyById', '/applyApi/findApplyById/**', '1', '根据ID查询申请信息', b'1', '2019-09-07 12:14:03', 'admin', '2019-09-07 12:14:03', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (79, '通过 key 查询到最新的一个流程定义', 'ActProcDefApi-findDefByKey', '/actProcDefApi/findDefByKey/**', '1', '通过 key 查询到最新的一个流程定义', b'1', '2019-09-07 15:10:32', 'admin', '2019-09-07 15:10:32', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (80, '通过流程实例id 查询批注信息', 'ApplyApi-findCommentListByProcInstId', '/applyApi/findCommentListByProcInstId/**', '1', '通过流程实例id 查询批注信息', b'1', '2019-09-08 12:02:42', 'admin', '2019-09-08 12:03:25', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (81, '根据条件查询商品信息', 'ProductApi-findProductList', '/productApi/findProductList', '2', '根据条件查询商品信息', b'1', '2019-11-01 10:02:34', 'admin', '2019-11-01 10:02:34', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (82, '保存商品信息', 'ProductApi-saveProduct', '/productApi/saveProduct', '2', '保存商品信息', b'1', '2019-11-01 10:03:11', 'admin', '2019-11-01 10:03:11', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (83, '更新商品信息', 'ProductApi-saveProduct', '/productApi/saveProduct', '2', '更新商品信息', b'1', '2019-11-01 10:03:46', 'admin', '2019-11-01 10:03:46', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (84, '更新商品信息', 'ProductApi-updateProduct', '/productApi/updateProduct', '2', '更新商品信息', b'1', '2019-11-01 10:04:13', 'admin', '2019-11-01 10:04:13', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (85, '根据id删除商品信息', 'ProductApi-deleteProductById', '/productApi/deleteProductById/*', '2', '根据id删除商品信息', b'1', '2019-11-01 10:51:17', 'admin', '2019-11-01 10:51:17', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (86, '查询自动任务列表', 'CoreAutoTaskApi-findAutoTask', '/coreAutoTaskApi/findAutoTask', '1', '查询自动任务列表', b'1', '2019-08-12 16:35:36', 'admin', '2019-08-12 16:35:36', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (87, '保存/修改自动任务', 'CoreAutoTaskApi-saveAutoTask', '/coreAutoTaskApi/saveAutoTask', '1', '保存/修改自动任务', b'1', '2019-08-12 18:52:51', 'admin', '2019-08-12 18:52:51', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (88, '测试Cron表达式下次运行的时间', 'CoreAutoTaskApi-testRunCron', '/coreAutoTaskApi/testRunCron/**', '1', '测试Cron表达式下次运行的时间', b'1', '2019-08-13 09:55:30', 'admin', '2019-08-13 09:55:30', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (89, '启用、停止自动任务', 'CoreAutoTaskApi-updateJobStatus', '/coreAutoTaskApi/updateJobStatus/**', '1', '启用、停止自动任务', b'1', '2019-08-14 09:00:10', 'admin', '2019-08-14 09:00:10', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (90, '禁用自动任务', 'CoreAutoTaskApi-updateJobValidInd', '/coreAutoTaskApi/updateJobValidInd/**', '1', '123123', b'1', '2019-08-14 20:40:53', 'admin', '2019-08-14 20:40:53', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (91, '根据任务id,删除自动任务', 'CoreAutoTaskApi-deleteAutoTask', '/coreAutoTaskApi/deleteAutoTask/**', '1', '根据任务id,删除自动任务', b'1', '2019-08-14 20:49:16', 'admin', '2019-08-14 20:50:10', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (92, '立即执行自动任务', 'CoreAutoTaskApi-immediateExecutionTask', '/coreAutoTaskApi/immediateExecutionTask', '1', '立即执行自动任务', b'1', '2019-08-14 21:18:56', 'admin', '2019-08-14 21:18:56', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (93, '校验自动任务添加、修改时参数的设置', 'CoreAutoTaskApi-checkAutoTask', '/coreAutoTaskApi/checkAutoTask', '1', '校验自动任务添加、修改时参数的设置', b'1', '2019-08-15 10:53:31', 'admin', '2019-08-15 10:53:31', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (94, '查询自动任务执行日志', 'CoreAutoTaskApi-findAutoTaskLog', '/coreAutoTaskApi/findAutoTaskLog', '1', '查询自动任务执行日志', b'1', '2019-08-20 10:01:30', 'admin', '2019-08-20 10:01:30', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (95, '根据条件查询数据字典信息', 'DataDictionaryApi-listByPage', '/dataDictionaryApi/list', '1', '根据条件查询数据字典信息', b'1', '2019-11-07 11:10:29', 'admin', '2019-11-07 11:10:29', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (96, '保存/更新数据字典', 'DataDictionaryApi-saveOrUpdate', '/dataDictionaryApi/saveOrUpdate', '1', '保存/更新数据字典', b'1', '2019-11-07 11:11:12', 'admin', '2019-11-07 11:11:12', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (97, '根据id删除数据字典', 'DataDictionaryApi-del', '/dataDictionaryApi/del/**', '1', '根据id删除数据字典', b'1', '2019-11-07 11:11:40', 'admin', '2019-11-07 11:12:21', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (98, '根据id禁用数据字典', 'DataDictionaryApi-prohibit', '/dataDictionaryApi/prohibit/**', '1', '根据id禁用数据字典', b'1', '2019-11-07 11:12:14', 'admin', '2019-11-07 11:12:26', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (99, '字典下拉字段分类', 'DataDictionaryApi-findFieldType', '/dataDictionaryApi/findFieldType', '1', '字典下拉字段分类', b'1', '2019-11-07 11:15:37', 'admin', '2019-11-07 11:15:37', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (100, '字典下拉系统模块', 'DataDictionaryApi-findSysModule', '/dataDictionaryApi/findSysModule', '1', '字典下拉系统模块', b'1', '2019-11-07 13:56:14', 'admin', '2019-11-07 13:56:14', 'admin', NULL, NULL, NULL);

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
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spring_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bean_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_concurrent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_exception_stop` bit(1) NULL DEFAULT NULL,
  `is_save_log` bit(1) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_send_mail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mail_receive` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `class_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `run_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `template_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_schedule_job
-- ----------------------------
INSERT INTO `sys_schedule_job` VALUES (2, '5', '123', '0', '', '1231', '123', '', '1', b'1', b'0', '123123', b'0', NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_schedule_job` VALUES (4, '2', '123', '0', '23123', '123131', '123', '123', '1', b'1', b'0', '123123', b'1', NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_schedule_job` VALUES (5, '5', '234', '0', '', 'com.billow.job.autoTask.TestAutoTask', 'test', '1/10 * * * * ? *', '1', b'1', b'1', '234234', b'1', NULL, NULL, NULL, NULL, '1', 'liuytsz@sinosoft.com.cn', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_schedule_job_log`;
CREATE TABLE `sys_schedule_job_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `is_success` bit(1) NULL DEFAULT NULL,
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_id` bigint(20) NULL DEFAULT NULL,
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `run_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_schedule_job_log
-- ----------------------------
INSERT INTO `sys_schedule_job_log` VALUES (1, '2019-09-27 17:38:55', 'JOB-AUTO', '2019-09-27 17:38:55', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (2, '2019-09-27 17:39:03', 'JOB-AUTO', '2019-09-27 17:39:03', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (3, '2019-09-27 17:39:13', 'JOB-AUTO', '2019-09-27 17:39:13', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (4, '2019-09-27 17:39:23', 'JOB-AUTO', '2019-09-27 17:39:23', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (5, '2019-09-27 17:39:33', 'JOB-AUTO', '2019-09-27 17:39:33', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (6, '2019-09-27 17:39:43', 'JOB-AUTO', '2019-09-27 17:39:43', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (7, '2019-09-27 17:39:53', 'JOB-AUTO', '2019-09-27 17:39:53', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (8, '2019-09-27 17:40:03', 'JOB-AUTO', '2019-09-27 17:40:03', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (9, '2019-09-27 17:52:44', 'JOB-AUTO', '2019-09-27 17:52:44', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (10, '2019-09-27 17:52:53', 'JOB-AUTO', '2019-09-27 17:52:53', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (11, '2019-09-27 17:53:03', 'JOB-AUTO', '2019-09-27 17:53:03', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (12, '2019-09-27 17:53:13', 'JOB-AUTO', '2019-09-27 17:53:13', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');
INSERT INTO `sys_schedule_job_log` VALUES (13, '2019-09-27 17:53:23', 'JOB-AUTO', '2019-09-27 17:53:23', 'JOB-AUTO', NULL, NULL, b'1', '5', 5, '234', '0天0小时0分2秒');

-- ----------------------------
-- Table structure for sys_white_list
-- ----------------------------
DROP TABLE IF EXISTS `sys_white_list`;
CREATE TABLE `sys_white_list`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `module` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for u_leave
-- ----------------------------
DROP TABLE IF EXISTS `u_leave`;
CREATE TABLE `u_leave`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valid_ind` bit(1) NULL DEFAULT NULL,
  `end_date` datetime(0) NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_leave
-- ----------------------------
INSERT INTO `u_leave` VALUES (1, '2019-09-01 14:38:17', 'admin', '2019-09-01 14:38:17', 'admin', NULL, '2019-09-27 00:00:00', '121212', '2019-09-08 00:00:00');
INSERT INTO `u_leave` VALUES (2, '2019-09-01 14:47:42', 'admin', '2019-09-01 14:47:42', 'admin', NULL, '2019-09-30 00:00:00', '12323', '2019-09-01 00:00:00');
INSERT INTO `u_leave` VALUES (3, '2019-09-01 14:56:10', 'admin', '2019-09-01 14:56:10', 'admin', NULL, '2019-09-28 00:00:00', '13123', '2019-09-15 00:00:00');
INSERT INTO `u_leave` VALUES (4, '2019-09-01 15:04:27', 'admin', '2019-09-01 15:04:27', 'admin', NULL, '2019-10-01 00:00:00', '13123', '2019-09-01 15:04:20');
INSERT INTO `u_leave` VALUES (5, '2019-09-01 20:03:48', 'admin', '2019-09-01 20:03:48', 'admin', NULL, '2019-09-01 20:03:18', '123123', '2019-09-01 20:03:16');

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
  `group_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_user
-- ----------------------------
INSERT INTO `u_user` VALUES (1, 'liuyongtao', 'liuyongtao', '1', '$2a$10$R5.R.ld6K4O5EU9qdlKXyOF7Tz3UtSqQ9UROgj5jh7EMH5SwVRQ8a', '150000,150600,150627', '2019-08-15 00:00:00', '13432345654', 'admin-system/displayImag/usericon/liuyongtao.png', NULL, b'1', '2019-04-23 15:26:47', 'admin', '2019-08-09 23:28:16', 'liuyongtao', 'hr');
INSERT INTO `u_user` VALUES (2, 'admin', 'admin', '3', '$2a$10$seb.F5SucYtqqhNJqFhvu.91M5Ja8v2d2g0BVJ3y12f5fPgZRTtce', '555', '2019-08-02 17:44:09', '1111', 'admin-system/displayImag/usericon/admin.png', NULL, b'1', '2019-04-23 15:26:47', 'admin', '2019-08-09 23:28:48', 'admin', 'deptLeader');
INSERT INTO `u_user` VALUES (3, 'hr', 'hr', '2', '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', NULL, NULL, NULL, NULL, NULL, b'1', '2019-07-30 15:35:18', 'admin', '2019-07-30 15:54:56', 'admin', 'hr');
INSERT INTO `u_user` VALUES (5, '345', '34', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', '150000,150400,150421', NULL, NULL, NULL, NULL, b'1', '2019-07-31 10:45:43', 'admin', '2019-08-07 09:50:16', 'admin', NULL);
INSERT INTO `u_user` VALUES (6, '678', '6786', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', NULL, NULL, NULL, NULL, NULL, b'1', '2019-07-31 10:47:29', 'admin', '2019-07-31 10:47:29', 'admin', NULL);
INSERT INTO `u_user` VALUES (7, '123XX', '123XXX', NULL, '$2a$10$S8iKAT6DLZ4aK54HoR2DN.o5r1Io4LZEpoq3onFD7hp4/Hjv4iQ8W', NULL, NULL, NULL, NULL, NULL, b'1', '2019-07-31 10:48:07', 'admin', '2019-07-31 10:59:44', 'admin', NULL);
INSERT INTO `u_user` VALUES (8, '1231AAA', '123www', NULL, '$2a$10$6fE91.rXBTCODjcPxaCpI.mBUQjH6pRTJhFm25QggVgP06CKiJX0u', NULL, NULL, NULL, NULL, NULL, b'1', '2019-07-31 10:49:49', 'admin', '2019-07-31 15:40:25', 'admin', NULL);
INSERT INTO `u_user` VALUES (9, '12AAA', '12AAA', '1', '$2a$10$rR4JP/smtel2Vd1VWn0Gie7vUWl8.cRWhx3siHDNcOPRDFFtZPevC', '150000,152500,152526', '2019-08-05 14:04:54', '18578757845', NULL, '123', b'1', '2019-07-31 10:53:59', 'admin', '2019-08-06 10:20:48', 'admin', NULL);
INSERT INTO `u_user` VALUES (10, '124', '123234', NULL, '$2a$10$XI2CS.KB/BV6SMV/vrUMhekF.OA/WmyLNSlGganjlW33I9ep6hI7G', '130000,130300,130321', NULL, '13425875445', NULL, NULL, b'1', '2019-08-01 18:41:25', 'admin', '2019-08-06 10:18:17', 'admin', NULL);
INSERT INTO `u_user` VALUES (12, '567', '5467', '2', '$2a$10$wwi1IkMUwtCX3HCAfaB5YuJpw.KMrShtvIOhusIIB1HQZA/ugJ0um', '140000,140400,140423', '2019-08-06 15:44:02', '15545487445', NULL, '123123', b'1', '2019-08-06 15:44:16', 'admin', '2019-08-07 10:54:57', 'admin', NULL);
INSERT INTO `u_user` VALUES (13, '45', '45', '3', '$2a$10$hPB0APphoJ.rcLU8wKJsouaDxq9zMwZw2MGmGCG4ar.wXWy4Oiily', '140000,140100,140105', '2019-08-06 15:44:42', '18925454145', NULL, '4564', b'1', '2019-08-06 15:44:59', 'admin', '2019-08-06 15:44:59', 'admin', NULL);


-- ----------------------------
-- Table structure for p_goods_brand
-- ----------------------------
DROP TABLE IF EXISTS `p_goods_brand`;
CREATE TABLE `p_goods_brand`
(
    `id`           varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `brand_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '品牌名称',
    `brand_sort`   bigint(20)                                              NOT NULL COMMENT '分类排序',
    `valid_ind`    bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '品牌表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_goods_brand
-- ----------------------------
INSERT INTO `p_goods_brand`
VALUES ('1', '品牌A', 1, b'1', '2019-11-27 15:32:15', 'billow', '2019-11-27 15:32:18', 'billow');

-- ----------------------------
-- Table structure for p_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `p_goods_category`;
CREATE TABLE `p_goods_category`
(
    `id`            varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '分类名称',
    `category_sort` bigint(20)                                              NOT NULL COMMENT '分类排序',
    `valid_ind`     bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`   datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`   datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '分类表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_goods_category
-- ----------------------------
INSERT INTO `p_goods_category`
VALUES ('1', '奢侈品牌', 1, b'1', '2019-11-27 15:32:48', 'billow', '2019-11-27 15:32:52', 'billow');

-- ----------------------------
-- Table structure for p_goods_safeguard
-- ----------------------------
DROP TABLE IF EXISTS `p_goods_safeguard`;
CREATE TABLE `p_goods_safeguard`
(
    `id`             varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `safeguard_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '保障名称',
    `price`          int(11)                                                 NOT NULL COMMENT '保障价格',
    `valid_ind`      bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`    datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`    datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '增值保障'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for p_goods_sku
-- ----------------------------
DROP TABLE IF EXISTS `p_goods_sku`;
CREATE TABLE `p_goods_sku`
(
    `id`           varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `sku_no`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'sku编号,唯一',
    `sku_name`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'sku名称(冗余spu_name)',
    `price`        int(11)                                                 NOT NULL COMMENT '售价',
    `stock`        int(11)                                                 NOT NULL COMMENT '库存',
    `shop_id`      varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '商铺id,为0表示自营',
    `spu_id`       varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'spu_id',
    `valid_ind`    bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = 'sku表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_goods_sku
-- ----------------------------
INSERT INTO `p_goods_sku`
VALUES ('1', '11', '黑色3G', 4500, 234, '1', '1', b'1', '2019-11-27 15:39:55', 'billow', '2019-11-27 15:40:00', 'billow');
INSERT INTO `p_goods_sku`
VALUES ('2', '22', '黑色6G', 5200, 123, '1', '1', b'1', '2019-11-27 15:40:20', 'billow', '2019-11-27 15:40:24', 'billow');
INSERT INTO `p_goods_sku`
VALUES ('3', '33', '白色3G', 4100, 213, '1', '1', b'1', '2019-11-27 15:40:52', 'billow', '2019-11-27 15:40:56', 'billow');
INSERT INTO `p_goods_sku`
VALUES ('4', '44', '白色6G', 5100, 342, '1', '1', b'1', '2019-11-27 15:41:18', 'billow', '2019-11-27 15:41:21', 'billow');

-- ----------------------------
-- Table structure for p_goods_sku_safeguard
-- ----------------------------
DROP TABLE IF EXISTS `p_goods_sku_safeguard`;
CREATE TABLE `p_goods_sku_safeguard`
(
    `id`           varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `sku_id`       varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'sku_id',
    `safeguard_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'safeguard_id',
    `valid_ind`    bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = 'sku增值保障'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for p_goods_sku_spec_value
-- ----------------------------
DROP TABLE IF EXISTS `p_goods_sku_spec_value`;
CREATE TABLE `p_goods_sku_spec_value`
(
    `id`            varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `sku_id`        varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'sku_id',
    `spec_key_id`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '规格id(冗余)',
    `spec_value_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '规格值id',
    `valid_ind`     bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`   datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`   datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = 'sku规格值'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_goods_sku_spec_value
-- ----------------------------
INSERT INTO `p_goods_sku_spec_value`
VALUES ('1', '1', '1', '1', b'1', '2019-11-27 15:41:50', 'billow', '2019-11-27 15:41:53', 'billow');
INSERT INTO `p_goods_sku_spec_value`
VALUES ('2', '1', '2', '3', b'1', '2019-11-27 15:42:24', 'billow', '2019-11-27 15:42:30', 'billow');
INSERT INTO `p_goods_sku_spec_value`
VALUES ('3', '2', '1', '1', b'1', '2019-11-27 15:41:50', 'billow', '2019-11-27 15:41:53', 'billow');
INSERT INTO `p_goods_sku_spec_value`
VALUES ('4', '2', '2', '4', b'1', '2019-11-27 15:41:50', 'billow', '2019-11-27 15:41:53', 'billow');
INSERT INTO `p_goods_sku_spec_value`
VALUES ('5', '3', '1', '2', b'1', '2019-11-27 15:41:50', 'billow', '2019-11-27 15:41:53', 'billow');
INSERT INTO `p_goods_sku_spec_value`
VALUES ('6', '3', '2', '3', b'1', '2019-11-27 15:41:50', 'billow', '2019-11-27 15:41:53', 'billow');
INSERT INTO `p_goods_sku_spec_value`
VALUES ('7', '4', '1', '2', b'1', '2019-11-27 15:41:50', 'billow', '2019-11-27 15:41:53', 'billow');
INSERT INTO `p_goods_sku_spec_value`
VALUES ('8', '4', '2', '4', b'1', '2019-11-27 15:41:50', 'billow', '2019-11-27 15:41:53', 'billow');

-- ----------------------------
-- Table structure for p_goods_spec_key
-- ----------------------------
DROP TABLE IF EXISTS `p_goods_spec_key`;
CREATE TABLE `p_goods_spec_key`
(
    `id`           varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `spec_no`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '规格编号',
    `spec_name`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '规格名称',
    `key_sort`     bigint(20)                                              NOT NULL COMMENT '规格排序',
    `valid_ind`    bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '规格表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_goods_spec_key
-- ----------------------------
INSERT INTO `p_goods_spec_key`
VALUES ('1', '11', '颜色', 1, b'1', '2019-11-27 15:35:07', 'billow', '2019-11-27 15:35:10', 'billow');
INSERT INTO `p_goods_spec_key`
VALUES ('2', '22', '内存', 2, b'1', '2019-11-27 15:35:25', 'billow', '2019-11-27 15:35:28', 'billow');

-- ----------------------------
-- Table structure for p_goods_spec_value
-- ----------------------------
DROP TABLE IF EXISTS `p_goods_spec_value`;
CREATE TABLE `p_goods_spec_value`
(
    `id`           varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `spec_key_id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '规格id',
    `spec_value`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '规格值',
    `value_sort`   bigint(20)                                              NOT NULL COMMENT '规格排序',
    `valid_ind`    bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '规格值表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_goods_spec_value
-- ----------------------------
INSERT INTO `p_goods_spec_value`
VALUES ('1', '1', '黑色', 1, b'1', '2019-11-27 15:35:53', 'billow', '2019-11-27 15:35:57', 'billow');
INSERT INTO `p_goods_spec_value`
VALUES ('2', '1', '白色', 2, b'1', '2019-11-27 15:36:26', 'billow', '2019-11-27 15:36:30', 'billow');
INSERT INTO `p_goods_spec_value`
VALUES ('3', '2', '3G', 1, b'1', '2019-11-27 15:37:43', 'billow', '2019-11-27 15:37:47', 'billow');
INSERT INTO `p_goods_spec_value`
VALUES ('4', '2', '6G', 2, b'1', '2019-11-27 15:38:04', 'billow', '2019-11-27 15:38:09', 'billow');

-- ----------------------------
-- Table structure for p_goods_spu
-- ----------------------------
DROP TABLE IF EXISTS `p_goods_spu`;
CREATE TABLE `p_goods_spu`
(
    `id`           varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `spu_no`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '商品编号，唯一',
    `goods_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '商品名称',
    `low_price`    int(11)                                                 NOT NULL COMMENT '最低售价',
    `stock`        bigint(20)                                              NULL DEFAULT NULL COMMENT '总库存量',
    `category_id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '分类id',
    `brand_id`     varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '品牌id',
    `spu_sort`     bigint(20)                                              NOT NULL COMMENT '商品排序',
    `valid_ind`    bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_spu_no` (`spu_no`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = 'spu表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_goods_spu
-- ----------------------------
INSERT INTO `p_goods_spu`
VALUES ('1', '11', 'iphone11', 5523, 9000, '1', '1', 1, b'1', '2019-11-27 15:34:06', 'billow', '2019-11-27 15:34:12',
        'billow');

-- ----------------------------
-- Table structure for p_goods_spu_spec
-- ----------------------------
DROP TABLE IF EXISTS `p_goods_spu_spec`;
CREATE TABLE `p_goods_spu_spec`
(
    `id`           varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `spu_id`       varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'spu_id',
    `spec_key_id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'spec_key_id',
    `valid_ind`    bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = 'spu规格表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_goods_spu_spec
-- ----------------------------
INSERT INTO `p_goods_spu_spec`
VALUES ('1', '1', '1', b'1', '2019-11-27 15:38:42', 'billow', '2019-11-27 15:38:46', 'billow');
INSERT INTO `p_goods_spu_spec`
VALUES ('2', '1', '2', b'1', '2019-11-27 15:38:56', 'billow', '2019-11-27 15:39:01', 'billow');

-- ----------------------------
-- Table structure for p_shop_info
-- ----------------------------
DROP TABLE IF EXISTS `p_shop_info`;
CREATE TABLE `p_shop_info`
(
    `id`           varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '主键',
    `shop_name`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '店铺名称',
    `shop_sort`    bigint(20)                                              NOT NULL COMMENT '店铺排序',
    `valid_ind`    bit(1)                                                  NULL DEFAULT NULL COMMENT '是否有效',
    `update_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '最后更新时间',
    `updater_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
    `create_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '店铺表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of p_shop_info
-- ----------------------------
INSERT INTO `p_shop_info`
VALUES ('1', 'bilow的门店', 1, b'1', '2019-11-27 15:31:32', 'billow', '2019-11-27 15:31:40', 'billow');

SET FOREIGN_KEY_CHECKS = 1;