/*
Navicat MySQL Data Transfer

Source Server         : lhdb
Source Server Version : 50726
Source Host           : 127.0.0.1:3306
Source Database       : sbl

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-10-12 12:36:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(30) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '', '$2a$10$1jx9ECqmFV6cyN4c/du7resG9UmMdj9g0ttx5u4Ni7VgY7e9r8.ZG', '', '0');
INSERT INTO `user` VALUES ('2', '2', '$2a$10$DIVPgyrBFt/jNBQzaV9DT.cy9.QRDqFzcpPiTiSww6nySIl56FG36', '2', '0');
INSERT INTO `user` VALUES ('3', '3', '$2a$10$9eZaRXJLzl89gxlKDQOdjeq06tnxcefzlzd17dDQp8P3EsI1CV/ci', '3', '0');
INSERT INTO `user` VALUES ('4', '1', '$2a$10$BktNqzvkYBOVyAilWuCKIeoEL2qJWHFZEPZKwVq.GagJ1jxgMp7/K', '1', '0');
INSERT INTO `user` VALUES ('5', '4', '$2a$10$./qiUdeqE2D5Vtdg/obd2uN1PYJaOCA6MsGqoCeD8JNCQUBj2gVam', '4', '0');
INSERT INTO `user` VALUES ('6', '5', '$2a$10$RIYtNGPzbg7iQHfYGKPfyekAk0gCm90Q3xo49DZU9rtgvEcP3W/06', '5', '0');
INSERT INTO `user` VALUES ('7', 'root00', '$2a$10$Ipq6aKIL.A.imh5KG2wiF.0iedHPe7AvviAq1mbna..Kn5ga0SnQS', 'test@test.com', '0');
INSERT INTO `user` VALUES ('8', 'root', '$2a$10$oWT.TJil7GNpcWZyBQyJsOG75xhIQnGN9XoENWVSs6Sqgp4elFpdS', 'test@test.com', '0');
INSERT INTO `user` VALUES ('9', 'bot2', '$2a$10$YYhvjtQlbs0x7pb51H0vaO/aMQEU/407cqC6YaGB/f3FLjdltliDm', '2@qq.com', '0');
INSERT INTO `user` VALUES ('10', '5678', '$2a$10$9lYymlXuwba6itfJfN2xh.rLVjEmB5KIdRTHwArjsxL4FmBZqw7Uq', '1234@123.com', '0');
INSERT INTO `user` VALUES ('11', 'cccp', '$2a$10$sOCgQK7xyHNUrlpQXTG20epwM1yQwjy2cuVxLjCplaQ9CY51bkKqS', 'g@su.com', '0');
INSERT INTO `user` VALUES ('12', 'cccp0', '$2a$10$wTNPCDxC2fi7c1/wG36BYe3iXQsJf0g4.e8DbQ75LgYI3vmg5ZNNK', 'a@su.com', '0');
INSERT INTO `user` VALUES ('13', 'cccp1', '$2a$10$bWYPKrBm.cbXzFdFQPlZCOQwHBQ468xrYm.oi3ZsECOdzxHOCUe16', 'a@su.co', '0');
INSERT INTO `user` VALUES ('14', 'sdfvg', '$2a$10$HnZYE2wWhxk5uveUcGl8zu833pEJtAJxPF7N7cvO1p9OVCoviNDyG', '23er@wesdf.com', '0');
INSERT INTO `user` VALUES ('15', 'rootA', '$2a$10$uYidQ6ZRD13Pw0Tckp6g0uqI9VMEXJk7CieUfiZbPQm/S7VPoPZey', '123@qw.com', '0');
INSERT INTO `user` VALUES ('16', 'asdfadsf', '$2a$10$9ABSOdQ9rp.qxH7WpdVC5eCogBNWew3egW20l9MQoGDMieLmT2T22', 'asds@dq.com', '0');
INSERT INTO `user` VALUES ('17', 'sadf', '$2a$10$dZ1GFSHQ5nwy0Q7QlZsmB.p2WKJQYT5sbVZ9cm4mAvICYjZOPRXVK', 'edf@as.com', '0');
INSERT INTO `user` VALUES ('18', 'asdf', '$2a$10$QnZJxu5OF6d9JyvSD0Dn4.dtNvJPlelZiflyo1cv06os.i9UooziG', 'asd@asd.com', '0');

-- ----------------------------
-- Table structure for want
-- ----------------------------
DROP TABLE IF EXISTS `want`;
CREATE TABLE `want` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `amount` int(11) NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `remark` varchar(255) NOT NULL DEFAULT '',
  `date` datetime NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of want
-- ----------------------------
INSERT INTO `want` VALUES ('1', '59式中型坦克', '2', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('2', '59式中型坦克', '3', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('3', '59式中型坦克', '173', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('4', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('5', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('6', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('7', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('8', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('9', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('10', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('11', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('12', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('13', '59式中型坦克', '2', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('14', '59式中型坦克', '3', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('15', '59式中型坦克', '173', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('16', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('17', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('18', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('19', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('20', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('21', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('22', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('23', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
INSERT INTO `want` VALUES ('24', '59式中型坦克', '1', '4800000.00', '无', '2019-10-09 10:23:56', '1');
