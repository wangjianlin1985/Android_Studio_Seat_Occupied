/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : seat_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-04-21 00:40:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `classinfo`
-- ----------------------------
DROP TABLE IF EXISTS `classinfo`;
CREATE TABLE `classinfo` (
  `classNo` varchar(20) NOT NULL,
  `className` varchar(20) default NULL,
  `bornDate` datetime default NULL,
  `mainTeacher` varchar(20) default NULL,
  `classMemo` longtext,
  PRIMARY KEY  (`classNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classinfo
-- ----------------------------
INSERT INTO `classinfo` VALUES ('BJ001', '计算机3班', '2018-04-10 00:00:00', 'a', 'b');
INSERT INTO `classinfo` VALUES ('BJ002', '电子技术4班', '2018-04-10 00:00:00', '王明刚', '测试班级');

-- ----------------------------
-- Table structure for `jc`
-- ----------------------------
DROP TABLE IF EXISTS `jc`;
CREATE TABLE `jc` (
  `jcId` int(11) NOT NULL auto_increment,
  `jcType` varchar(20) default NULL,
  `title` varchar(60) default NULL,
  `content` longtext,
  `userObj` varchar(20) default NULL,
  `creditScore` float default NULL,
  `jcTime` varchar(20) default NULL,
  PRIMARY KEY  (`jcId`),
  KEY `FK959C80FC67` (`userObj`),
  CONSTRAINT `FK959C80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jc
-- ----------------------------
INSERT INTO `jc` VALUES ('1', '信用奖励', '积极举报不文明行为', '对于你上次举报其他人不文明的行为，我们给你增加2个信用积分', 'STU001', '2', '2018-04-12 15:10:33');
INSERT INTO `jc` VALUES ('2', '信用惩罚', '随地乱吐痰', '不讲究图书室的卫生，扣除你的信用分', 'STU001', '-2', '2018-04-12 15:09:23');

-- ----------------------------
-- Table structure for `jubao`
-- ----------------------------
DROP TABLE IF EXISTS `jubao`;
CREATE TABLE `jubao` (
  `jubaoId` int(11) NOT NULL auto_increment,
  `title` varchar(60) default NULL,
  `content` longtext,
  `userObj` varchar(20) default NULL,
  `jubaoTime` varchar(20) default NULL,
  `replyContent` longtext,
  PRIMARY KEY  (`jubaoId`),
  KEY `FK44976C5C80FC67` (`userObj`),
  CONSTRAINT `FK44976C5C80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jubao
-- ----------------------------
INSERT INTO `jubao` VALUES ('1', '1-1座位的人乱吐痰', '今天在阅览室看见这个人大声讲话，还乱吐痰！', 'STU001', '2018-04-10 14:12:48', '已经严肃处理');
INSERT INTO `jubao` VALUES ('2', '1-3座的人今天上午很吵', '今天上午这个座位的人吵死了，请处理！', 'STU001', '2018-04-21 00:17:09', '--');

-- ----------------------------
-- Table structure for `room`
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `roomId` int(11) NOT NULL auto_increment,
  `roomName` varchar(20) default NULL,
  `roomPhoto` varchar(50) default NULL,
  `roomPlace` varchar(50) default NULL,
  `seatNum` int(11) default NULL,
  PRIMARY KEY  (`roomId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('1', '1号阅览室', 'upload/74758B5251F553EFF8C776493F720FFA.jpg', '图书馆1楼', '100');
INSERT INTO `room` VALUES ('2', '2号阅览室', 'upload/E3F4F17DE76DF7101A0FA0E22C4231C7.jpg', '图书馆2楼', '80');

-- ----------------------------
-- Table structure for `seat`
-- ----------------------------
DROP TABLE IF EXISTS `seat`;
CREATE TABLE `seat` (
  `seatId` int(11) NOT NULL auto_increment,
  `roomObj` int(11) default NULL,
  `seatCode` varchar(20) default NULL,
  `seatStateObj` int(11) default NULL,
  PRIMARY KEY  (`seatId`),
  KEY `FK2742256F6C7819` (`roomObj`),
  KEY `FK2742252D6BE195` (`seatStateObj`),
  CONSTRAINT `FK2742252D6BE195` FOREIGN KEY (`seatStateObj`) REFERENCES `seatstate` (`stateId`),
  CONSTRAINT `FK2742256F6C7819` FOREIGN KEY (`roomObj`) REFERENCES `room` (`roomId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seat
-- ----------------------------
INSERT INTO `seat` VALUES ('1', '1', '1-1', '1');
INSERT INTO `seat` VALUES ('2', '1', '1-2', '1');
INSERT INTO `seat` VALUES ('3', '1', '1-3', '1');
INSERT INTO `seat` VALUES ('4', '1', '1-4', '1');
INSERT INTO `seat` VALUES ('5', '1', '1-5', '1');
INSERT INTO `seat` VALUES ('6', '1', '1-6', '1');
INSERT INTO `seat` VALUES ('7', '1', '1-7', '1');
INSERT INTO `seat` VALUES ('8', '1', '1-8', '1');
INSERT INTO `seat` VALUES ('9', '1', '1-9', '1');
INSERT INTO `seat` VALUES ('10', '1', '1-10', '1');
INSERT INTO `seat` VALUES ('11', '2', '2-1', '1');
INSERT INTO `seat` VALUES ('12', '2', '2-2', '1');
INSERT INTO `seat` VALUES ('13', '2', '2-3', '1');
INSERT INTO `seat` VALUES ('14', '2', '2-4', '1');
INSERT INTO `seat` VALUES ('15', '2', '2-5', '1');
INSERT INTO `seat` VALUES ('16', '2', '2-6', '1');
INSERT INTO `seat` VALUES ('17', '2', '2-7', '2');
INSERT INTO `seat` VALUES ('18', '2', '2-8', '1');
INSERT INTO `seat` VALUES ('19', '2', '2-9', '1');
INSERT INTO `seat` VALUES ('20', '2', '2-10', '1');

-- ----------------------------
-- Table structure for `seatorder`
-- ----------------------------
DROP TABLE IF EXISTS `seatorder`;
CREATE TABLE `seatorder` (
  `orderId` int(11) NOT NULL auto_increment,
  `seatObj` int(11) default NULL,
  `orderDate` datetime default NULL,
  `startTime` varchar(20) default NULL,
  `endTime` varchar(20) default NULL,
  `addTime` varchar(20) default NULL,
  `userObj` varchar(20) default NULL,
  `orderState` varchar(20) default NULL,
  `replyContent` longtext,
  `orderMemo` longtext,
  PRIMARY KEY  (`orderId`),
  KEY `FKE3C3E42992806FD9` (`seatObj`),
  KEY `FKE3C3E429C80FC67` (`userObj`),
  CONSTRAINT `FKE3C3E42992806FD9` FOREIGN KEY (`seatObj`) REFERENCES `seat` (`seatId`),
  CONSTRAINT `FKE3C3E429C80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seatorder
-- ----------------------------
INSERT INTO `seatorder` VALUES ('1', '1', '2018-04-12 00:00:00', '14:20', '11:20', '2018-04-10 14:12:12', 'STU001', '已审核', '可以早点来哈', '测试');
INSERT INTO `seatorder` VALUES ('2', '5', '2018-04-26 00:00:00', '14:20', '15:30', '2018-04-20 23:25:09', 'STU001', '已审核', '还可以来', '我要来');

-- ----------------------------
-- Table structure for `seatstate`
-- ----------------------------
DROP TABLE IF EXISTS `seatstate`;
CREATE TABLE `seatstate` (
  `stateId` int(11) NOT NULL auto_increment,
  `stateName` varchar(20) default NULL,
  PRIMARY KEY  (`stateId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seatstate
-- ----------------------------
INSERT INTO `seatstate` VALUES ('1', '空闲');
INSERT INTO `seatstate` VALUES ('2', '有人');

-- ----------------------------
-- Table structure for `selectseat`
-- ----------------------------
DROP TABLE IF EXISTS `selectseat`;
CREATE TABLE `selectseat` (
  `selectId` int(11) NOT NULL auto_increment,
  `seatObj` int(11) default NULL,
  `userObj` varchar(20) default NULL,
  `startTime` varchar(20) default NULL,
  `endTime` varchar(20) default NULL,
  `seatState` varchar(20) default NULL,
  PRIMARY KEY  (`selectId`),
  KEY `FK141D754192806FD9` (`seatObj`),
  KEY `FK141D7541C80FC67` (`userObj`),
  CONSTRAINT `FK141D754192806FD9` FOREIGN KEY (`seatObj`) REFERENCES `seat` (`seatId`),
  CONSTRAINT `FK141D7541C80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of selectseat
-- ----------------------------
INSERT INTO `selectseat` VALUES ('1', '1', 'STU001', '2018-04-10 13:10:56', '2018-04-20 19:04:38', '已离开');
INSERT INTO `selectseat` VALUES ('2', '2', 'STU001', '2018-04-20 18:33:07', '2018-04-20 19:03:18', '已离开');
INSERT INTO `selectseat` VALUES ('3', '4', 'STU001', '2018-04-20 19:04:56', '2018-04-21 00:31:09', '已离开');
INSERT INTO `selectseat` VALUES ('4', '17', 'STU001', '2018-04-21 00:31:19', '--', '占用中');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_name` varchar(20) NOT NULL,
  `password` varchar(30) default NULL,
  `userTypeObj` int(11) default NULL,
  `classObj` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `gender` varchar(4) default NULL,
  `birthDate` datetime default NULL,
  `userPhoto` varchar(50) default NULL,
  `telephone` varchar(20) default NULL,
  `email` varchar(50) default NULL,
  `address` varchar(80) default NULL,
  `blackFlag` varchar(20) default NULL,
  `creditScore` float default NULL,
  `regTime` varchar(30) default NULL,
  PRIMARY KEY  (`user_name`),
  KEY `FKF3F34B396D3B62F9` (`userTypeObj`),
  KEY `FKF3F34B39A91D8B03` (`classObj`),
  CONSTRAINT `FKF3F34B396D3B62F9` FOREIGN KEY (`userTypeObj`) REFERENCES `usertype` (`userTypeId`),
  CONSTRAINT `FKF3F34B39A91D8B03` FOREIGN KEY (`classObj`) REFERENCES `classinfo` (`classNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('STU001', '123', '1', 'BJ001', '王兆国', '男', '2018-04-03 00:00:00', 'upload/B72D1158F61A5A64D81F69B4E05225CB.jpg', '13573598343', 'zhaoguo@163.com', '四川成都红星路13号', '否', '12', '2018-04-12 14:15:15');
INSERT INTO `userinfo` VALUES ('TH001', '123', '2', 'BJ001', '杨刚明', '男', '2018-04-21 00:00:00', 'upload/5B44BE87CC2876C6E01897E470907C5F.jpg', '13908035081', 'gangming@163.com', '四川成都清光中路', '否', '10', '2018-04-21 00:06:47');

-- ----------------------------
-- Table structure for `usertype`
-- ----------------------------
DROP TABLE IF EXISTS `usertype`;
CREATE TABLE `usertype` (
  `userTypeId` int(11) NOT NULL auto_increment,
  `userTypeName` varchar(20) default NULL,
  PRIMARY KEY  (`userTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usertype
-- ----------------------------
INSERT INTO `usertype` VALUES ('1', '学生');
INSERT INTO `usertype` VALUES ('2', '老师');
