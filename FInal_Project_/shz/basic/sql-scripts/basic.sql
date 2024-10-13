/*
 Navicat MySQL Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : basic

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 29/04/2024 02:54:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS `basic`;
USE `basic`;

-- ----------------------------
-- Table structure for finance_transactions
-- ----------------------------
DROP TABLE IF EXISTS `finance_transactions`;
CREATE TABLE `finance_transactions` (
                                        `transaction_id` int unsigned NOT NULL AUTO_INCREMENT,
                                        `user_id` int unsigned NOT NULL,
                                        `product` char(255) NOT NULL,
                                        PRIMARY KEY (`transaction_id`) USING BTREE,
                                        KEY `FK_finance_transactions_1` (`user_id`) USING BTREE,
                                        CONSTRAINT `FK_finance_transactions_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of finance_transactions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for transactions
-- ----------------------------
DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
                                `transaction_id` int unsigned NOT NULL AUTO_INCREMENT,
                                `user_id` int unsigned NOT NULL,
                                `transaction_type` varchar(10) NOT NULL,
                                `amount` double NOT NULL,
                                PRIMARY KEY (`transaction_id`) USING BTREE,
                                KEY `FK_transactions_1` (`user_id`) USING BTREE,
                                CONSTRAINT `FK_transactions_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of transactions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `id` int unsigned NOT NULL AUTO_INCREMENT,
                         `user_id` int unsigned NOT NULL UNIQUE,
                         `balance` double unsigned NOT NULL,
                         `credits` int unsigned NOT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE KEY `user_id_unique` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` (`id`, `user_id`, `balance`, `credits`) VALUES (9, 0, 0, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;








-- /*
--  Navicat MySQL Data Transfer
--
--  Source Server         : root
--  Source Server Type    : MySQL
--  Source Server Version : 80300 (8.3.0)
--  Source Host           : localhost:3306
--  Source Schema         : basic
--
--  Target Server Type    : MySQL
--  Target Server Version : 80300 (8.3.0)
--  File Encoding         : 65001
--
--  Date: 28/04/2024 21:56:17
-- */
--
-- SET NAMES utf8mb4;
-- SET FOREIGN_KEY_CHECKS = 0;
--
-- CREATE DATABASE IF NOT EXISTS `basic`;
-- USE `basic`;
-- -- ----------------------------
-- -- Table structure for finance_transactions
-- -- ----------------------------
-- DROP TABLE IF EXISTS `finance_transactions`;
-- CREATE TABLE `finance_transactions` (
--   `transaction_id` int unsigned NOT NULL AUTO_INCREMENT,
--   `user_id` int unsigned NOT NULL,
--   `product` char(255) NOT NULL,
--   PRIMARY KEY (`transaction_id`) USING BTREE,
--   KEY `FK_finance_trasactions_1` (`user_id`) USING BTREE,
--   KEY `FK_finance_trasactions_2` (`product`) USING BTREE
-- ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
--
-- -- ----------------------------
-- -- Records of finance_transactions
-- -- ----------------------------
-- BEGIN;
-- INSERT INTO `finance_transactions` (`transaction_id`, `user_id`, `product`) VALUES (1, 1, 'afiuenv ajdfhin');
-- INSERT INTO `finance_transactions` (`transaction_id`, `user_id`, `product`) VALUES (2, 1, 'bbbbbbbb');
-- INSERT INTO `finance_transactions` (`transaction_id`, `user_id`, `product`) VALUES (3, 2, 'cccccccc');
-- INSERT INTO `finance_transactions` (`transaction_id`, `user_id`, `product`) VALUES (4, 1, 'funds');
-- INSERT INTO `finance_transactions` (`transaction_id`, `user_id`, `product`) VALUES (5, 1, 'funds');
-- INSERT INTO `finance_transactions` (`transaction_id`, `user_id`, `product`) VALUES (6, 1, 'bonds');
-- COMMIT;
--
-- -- ----------------------------
-- -- Table structure for transactions
-- -- ----------------------------
-- DROP TABLE IF EXISTS `transactions`;
-- CREATE TABLE `transactions` (
--   `transaction_id` int unsigned NOT NULL AUTO_INCREMENT,
--   `user_id` int unsigned NOT NULL,
--   `transaction_type` varchar(10) NOT NULL,
--   `amount` double NOT NULL,
--   PRIMARY KEY (`transaction_id`) USING BTREE,
--   KEY `FK_transactions_1` (`user_id`) USING BTREE,
--   CONSTRAINT `FK_transactions_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
--
-- -- ----------------------------
-- -- Records of transactions
-- -- ----------------------------
-- BEGIN;
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (1, 1, 'IN', 10);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (2, 1, 'OUT', 10);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (3, 1, 'IN', 10);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (4, 1, 'IN', 1000);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (5, 1, 'OUT', 1000);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (6, 1, 'IN', 1000);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (7, 1, 'IN', 1000);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (8, 1, 'IN', 10000);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (9, 1, 'IN', 100000);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (10, 1, 'IN', 1);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (11, 1, 'IN', 1000);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (12, 1, 'IN', 10000);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (13, 1, 'OUT', 1);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (14, 1, 'OUT', 336933);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (15, 1, 'OUT', 235853.1);
-- INSERT INTO `transactions` (`transaction_id`, `user_id`, `transaction_type`, `amount`) VALUES (16, 1, 'OUT', 220129.56000000003);
-- COMMIT;
--
-- -- ----------------------------
-- -- Table structure for users
-- -- ----------------------------
-- DROP TABLE IF EXISTS `users`;
-- CREATE TABLE `users` (
--   `user_id` int unsigned NOT NULL AUTO_INCREMENT,
--   `balance` double unsigned NOT NULL,
--   `credits` int unsigned NOT NULL,
--   PRIMARY KEY (`user_id`) USING BTREE
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
--
-- -- ----------------------------
-- -- Records of users
-- -- ----------------------------
-- BEGIN;
-- INSERT INTO `users` (`user_id`, `balance`, `credits`) VALUES (1, 330194.33999999997, 1240);
-- INSERT INTO `users` (`user_id`, `balance`, `credits`) VALUES (2, 0.1, 0);
-- INSERT INTO `users` (`user_id`, `balance`, `credits`) VALUES (3, 10, 0);
-- COMMIT;
--
-- SET FOREIGN_KEY_CHECKS = 1;
