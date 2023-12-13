-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: iscweb
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organizer_id` int DEFAULT NULL,
  `name` char(64) DEFAULT NULL,
  `theme` char(32) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `location` varchar(128) DEFAULT NULL,
  `volunteer_min` int DEFAULT '0' COMMENT '所需最小的志愿者数量',
  `volunteer_max` int DEFAULT NULL COMMENT '所需志愿者最大数',
  `volunteer_current_number` int NOT NULL DEFAULT '0',
  `status` int NOT NULL DEFAULT '0' COMMENT '活动状态',
  `description` text COMMENT '活动描述',
  `deleted` int DEFAULT '0',
  `comment` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `activity_organizer_fk` (`organizer_id`),
  CONSTRAINT `activity_organizer_fk` FOREIGN KEY (`organizer_id`) REFERENCES `organizer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (4,3,'三下乡','乡村振兴','2023-08-08 09:40:00','西安',3,7,1,0,'自愿的乡村振兴青年服务活动',0,''),(6,3,'学雷锋','好人好事','2023-08-09 09:40:10','西安',2,8,0,1,'参与社会热心青年服务活动',0,NULL),(7,5,'返家乡','社区服务','2023-08-12 09:40:00','岳阳',2,8,2,5,'进行一次对家乡走访的活动实践',0,NULL);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_volunteer_relation`
--

DROP TABLE IF EXISTS `activity_volunteer_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_volunteer_relation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `activity_id` int DEFAULT NULL,
  `volunteer_id` int DEFAULT NULL,
  `deleted` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk1` (`activity_id`),
  KEY `fk2` (`volunteer_id`),
  CONSTRAINT `fk1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `fk2` FOREIGN KEY (`volunteer_id`) REFERENCES `volunteer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='记录志愿者参与活动情况';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_volunteer_relation`
--

LOCK TABLES `activity_volunteer_relation` WRITE;
/*!40000 ALTER TABLE `activity_volunteer_relation` DISABLE KEYS */;
INSERT INTO `activity_volunteer_relation` VALUES (2,4,3,1),(3,7,3,0),(5,4,3,0),(6,6,3,1);
/*!40000 ALTER TABLE `activity_volunteer_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizer`
--

DROP TABLE IF EXISTS `organizer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` char(32) NOT NULL,
  `password` varchar(64) NOT NULL,
  `phone` char(12) NOT NULL,
  `email` varchar(32) DEFAULT NULL,
  `avatar` varchar(128) DEFAULT NULL,
  `activity_max` int DEFAULT '0',
  `deleted` int DEFAULT '0',
  `current_activity_number` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `organizer_u` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='组织者表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizer`
--

LOCK TABLES `organizer` WRITE;
/*!40000 ALTER TABLE `organizer` DISABLE KEYS */;
INSERT INTO `organizer` VALUES (2,'caixukun','$2a$10$7NlxF5lQT24uuQWlgWPYJusb0.s1Q1SgW9ZUONf4SpIzSix9R3zXm','111',NULL,'organizer-default.png',2,0,0),(3,'ikun','$2a$10$A58GycC5NCDnCcn7lIVq9eraaOjho5oflvJGkY8sWXNLYpsVU/R72','111',NULL,'organizer-default.png',2,0,0),(4,'kunkun','$2a$10$wesMQ5dzEOBO.QLjFpu5BOYOQ5WrlThqk3IpUNgaM5qpqg0eHrwfK','111111',NULL,'organizer-default.png',2,0,0),(5,'zyy','$2a$10$Z6Bi644RoYXUpeHMBrYqxuCvTibNZkXsUEp6Plz.HYgBx3HBMxZ4W','153','929570291@qq.com','organizer-default.png',2,0,1),(6,'zy','$2a$10$d/7XVASLUL.TYRRMthZoTO02kJ.FmOMvQ1hAbfq9qfAPJeaLCacKG','1','9295@q.com','organizer-default.png',2,0,0);
/*!40000 ALTER TABLE `organizer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regulator`
--

DROP TABLE IF EXISTS `regulator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regulator` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` char(32) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `phone` char(12) NOT NULL,
  `email` varchar(32) DEFAULT NULL,
  `avatar` varchar(128) DEFAULT NULL,
  `rate` int DEFAULT '1' COMMENT '管理等级',
  `deleted` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `regulator_pk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regulator`
--

LOCK TABLES `regulator` WRITE;
/*!40000 ALTER TABLE `regulator` DISABLE KEYS */;
INSERT INTO `regulator` VALUES (1,'ikun','$2a$10$Shl56n5frhOt68Dbkq3xheYDNWsOIWjmuo5JLilGl/G.eckGXy3se','11111','444@qq.com','regulator-default.png',1,0),(2,'kun','$2a$10$OYLKxCr7ENIWp.2.xWpWp.6jH8J1a7wRfDFWG3.MNwzb4ZoWQrvNK','11111','444@qq.com','regulator-default.png',1,0),(6,'xiaohei','$2a$10$OxcQHfEoA5KduoOB0xJ1M.8FcddWFrEBDx9lABmTxl5HJ2DOyTnKG','1111','444@qq.com','regulator-default.png',1,0),(7,'daheizi','$2a$10$f57wR9/EG/yuWhgTr1wMLOqx1UD3vhfi0104E3V3pPSMth765bRJK','444','444@qq.com','regulator-default.png',1,0),(8,'heii','$2a$10$PfctwyFSXDyaaBT8aYIUJOudmRMay88evc7rW.NI2SA6MDfVwaa9W','1111','444@qq.com','regulator-default.png',1,0);
/*!40000 ALTER TABLE `regulator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volunteer`
--

DROP TABLE IF EXISTS `volunteer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volunteer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` char(32) NOT NULL,
  `password` varchar(64) NOT NULL,
  `phone` char(12) NOT NULL,
  `email` varchar(32) DEFAULT NULL,
  `avatar` varchar(128) DEFAULT NULL,
  `score` int DEFAULT '0',
  `activity_count` int DEFAULT '0',
  `activity_max` int DEFAULT '0' COMMENT '限制最大参与活动数',
  `status` int NOT NULL DEFAULT '0',
  `deleted` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `volunteer_u` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='志愿者表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteer`
--

LOCK TABLES `volunteer` WRITE;
/*!40000 ALTER TABLE `volunteer` DISABLE KEYS */;
INSERT INTO `volunteer` VALUES (1,'jige','$2a$10$raC3kuO/DsMVgQIoNpVBTO2HGQ0dvCaSAWFZFmIXCfEl2yf.NiHdq','111','123@qq.com','volunteer-default.png',0,0,4,3,0),(2,'kunkun','$2a$10$QS4/LZKAMLJRvo7tcd1FyOfXgBlItuB0i5u9ec9ZtRpAL3sxWx9b2','111','123@qq.com','volunteer-default.png',0,0,4,3,0),(3,'ikun','123456','123','123@qq.com','volunteer-default.png',2,1,4,3,0),(12,'m','$2a$10$hIEhpCsAzgyklgRs0dis.uykIYbnunMICrbE1G7MovAjJf9qCXQKe','1','123@qq.com','volunteer-default.png',0,0,4,3,0);
/*!40000 ALTER TABLE `volunteer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-13 19:22:00
