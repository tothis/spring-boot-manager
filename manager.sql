/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : 192.168.1.128:3306
 Source Schema         : manager

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 03/06/2021 20:30:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`
(
    `id`          bigint UNSIGNED  NOT NULL AUTO_INCREMENT,
    `type`        char(20)         NULL DEFAULT '' COMMENT '类型',
    `name`        char(20)         NULL DEFAULT '' COMMENT '字典名称',
    `value`       char(10)         NULL DEFAULT '' COMMENT '字典值',
    `create_by`   bigint UNSIGNED  NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime(0)      NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   bigint UNSIGNED  NULL DEFAULT NULL COMMENT '修改者',
    `update_time` datetime(0)      NULL DEFAULT NULL COMMENT '修改时间',
    `state`       tinyint UNSIGNED NULL DEFAULT NULL COMMENT '状态 0:正常 1:已删除',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = '字典';

-- ----------------------------
-- Records of dict
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
