/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : fund

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 27/04/2024 18:14:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS `fund`;
USE `fund`;
-- ----------------------------
-- Table structure for fund_purchases
-- ----------------------------
DROP TABLE IF EXISTS `fund_purchases`;
CREATE TABLE `fund_purchases` (
  `id` int NOT NULL AUTO_INCREMENT,
  `purchase_id` int NOT NULL,
  `user_id` int NOT NULL,
  `purchase_time` varchar(255) NOT NULL,
  `investment` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of fund_purchases
-- ----------------------------
BEGIN;
INSERT INTO `fund_purchases` (`id`, `purchase_id`, `user_id`, `purchase_time`, `investment`) VALUES (2, 3, 1, '2024-04-27T15:47:41.423117', 1000000);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
