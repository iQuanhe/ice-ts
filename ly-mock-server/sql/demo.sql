# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.25-log)
# Database: admin-template
# Generation Time: 2019-05-23 03:14:57 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table apis
# ------------------------------------------------------------

CREATE TABLE `apis` (
  `id` varchar(36) NOT NULL,
  `path` varchar(1000) DEFAULT NULL,
  `desc` varchar(400) DEFAULT NULL,
  `result` longtext,
  `nums` int(100) DEFAULT NULL,
  `isRandom` int(1) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `isExtend` int(1) DEFAULT NULL,
  `syscode` varchar(100) DEFAULT NULL,
  `sysname` varchar(100) DEFAULT NULL,
  `isStrict` int(1) DEFAULT NULL,
  `firstPath` varchar(100) DEFAULT NULL,
  `secondPath` varchar(100) DEFAULT NULL,
  `thirdPath` varchar(100) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL,
  `forthPath` varchar(100) DEFAULT NULL,
  `order` int(255) DEFAULT NULL,
  `times` int(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table date_flows
# ------------------------------------------------------------

CREATE TABLE `date_flows` (
  `id` varchar(20) NOT NULL,
  `date` date NOT NULL,
  `times` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table demo_category
# ------------------------------------------------------------

CREATE TABLE `demo_category` (
  `id` varchar(36) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `detail` varchar(400) DEFAULT NULL,
  `delFlg` int(1) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table demo_item
# ------------------------------------------------------------

CREATE TABLE `demo_item` (
  `id` varchar(36) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `detail` varchar(400) DEFAULT NULL,
  `img` varchar(1000) DEFAULT NULL,
  `categoryId` varchar(36) DEFAULT NULL,
  `delFlg` int(1) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table flows
# ------------------------------------------------------------

CREATE TABLE `flows` (
  `id` varchar(100) NOT NULL,
  `apiId` varchar(100) NOT NULL,
  `time` datetime NOT NULL,
  `syscode` varchar(100) NOT NULL,
  `apiPath` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table mycols
# ------------------------------------------------------------

CREATE TABLE `mycols` (
  `id` varchar(36) NOT NULL,
  `path` varchar(100) DEFAULT NULL,
  `cols` longtext,
  `serialNumber` int(100) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table news
# ------------------------------------------------------------

CREATE TABLE `news` (
  `id` varchar(36) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `desc` varchar(400) DEFAULT NULL,
  `link` varchar(100) DEFAULT NULL,
  `weblink` varchar(100) DEFAULT NULL,
  `logolink` varchar(100) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table system
# ------------------------------------------------------------

CREATE TABLE `system` (
  `id` varchar(100) NOT NULL,
  `sysCode` varchar(100) NOT NULL,
  `context` varchar(100) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `sysName` varchar(100) NOT NULL,
  `order` int(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
