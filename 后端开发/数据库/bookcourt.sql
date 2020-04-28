-- MySQL dump 10.13  Distrib 5.6.10, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bookcourt
-- ------------------------------------------------------
-- Server version	5.6.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `apply_change_table_8`
--

DROP TABLE IF EXISTS `apply_change_table_8`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apply_change_table_8` (
  `change_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `court_id` int(11) NOT NULL,
  `time_id` int(11) NOT NULL,
  `book_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `apply_state` int(11) NOT NULL,
  PRIMARY KEY (`change_id`),
  KEY `_id_8__idx` (`user_id`),
  KEY `_id_8__idx1` (`book_id`),
  KEY `_id_8__idx2` (`court_id`),
  KEY `_id_8__idx3` (`time_id`),
  CONSTRAINT `user_id_8_2` FOREIGN KEY (`user_id`) REFERENCES `user_table_2` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `book_id_8_4` FOREIGN KEY (`book_id`) REFERENCES `book_history_table_4` (`book_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `court_id_8_0` FOREIGN KEY (`court_id`) REFERENCES `court_table_0` (`court_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `time_id_8_1` FOREIGN KEY (`time_id`) REFERENCES `scheduled_time_table_1` (`time_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply_change_table_8`
--

LOCK TABLES `apply_change_table_8` WRITE;
/*!40000 ALTER TABLE `apply_change_table_8` DISABLE KEYS */;
/*!40000 ALTER TABLE `apply_change_table_8` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apply_fixed_table_7`
--

DROP TABLE IF EXISTS `apply_fixed_table_7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apply_fixed_table_7` (
  `apply_fixed_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `apply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `time_id` int(11) NOT NULL,
  `day_of_week` int(11) NOT NULL,
  `apply_state` int(11) NOT NULL,
  PRIMARY KEY (`apply_fixed_id`),
  KEY `_id_7_1_idx` (`time_id`),
  KEY `_id_7_2_idx` (`user_id`),
  CONSTRAINT `user_id_7_2` FOREIGN KEY (`user_id`) REFERENCES `user_table_2` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `time_id_7_1` FOREIGN KEY (`time_id`) REFERENCES `scheduled_time_table_1` (`time_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply_fixed_table_7`
--

LOCK TABLES `apply_fixed_table_7` WRITE;
/*!40000 ALTER TABLE `apply_fixed_table_7` DISABLE KEYS */;
/*!40000 ALTER TABLE `apply_fixed_table_7` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apply_unsubscribe_table_6`
--

DROP TABLE IF EXISTS `apply_unsubscribe_table_6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apply_unsubscribe_table_6` (
  `unsubscribe_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `apply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reason` varchar(255) NOT NULL,
  `unsubscribe_state` int(11) NOT NULL,
  PRIMARY KEY (`unsubscribe_id`),
  KEY `_id_6__idx` (`book_id`),
  KEY `_id_6__idx1` (`user_id`),
  CONSTRAINT `user_id_6_2` FOREIGN KEY (`user_id`) REFERENCES `user_table_2` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `book_id_6_4` FOREIGN KEY (`book_id`) REFERENCES `book_history_table_4` (`book_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply_unsubscribe_table_6`
--

LOCK TABLES `apply_unsubscribe_table_6` WRITE;
/*!40000 ALTER TABLE `apply_unsubscribe_table_6` DISABLE KEYS */;
/*!40000 ALTER TABLE `apply_unsubscribe_table_6` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_history_table_4`
--

DROP TABLE IF EXISTS `book_history_table_4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_history_table_4` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `court_id` int(11) NOT NULL,
  `time_id` int(11) NOT NULL,
  `book_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `book_state` int(11) NOT NULL,
  PRIMARY KEY (`book_id`),
  KEY `user_id_idx` (`user_id`),
  KEY `court_id_idx` (`court_id`),
  KEY `time_id_idx` (`time_id`),
  CONSTRAINT `user_id_4_2` FOREIGN KEY (`user_id`) REFERENCES `user_table_2` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `court_id_4_0` FOREIGN KEY (`court_id`) REFERENCES `court_table_0` (`court_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `time_id_4_1` FOREIGN KEY (`time_id`) REFERENCES `scheduled_time_table_1` (`time_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_history_table_4`
--

LOCK TABLES `book_history_table_4` WRITE;
/*!40000 ALTER TABLE `book_history_table_4` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_history_table_4` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `court_table_0`
--

DROP TABLE IF EXISTS `court_table_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `court_table_0` (
  `court_id` int(11) NOT NULL AUTO_INCREMENT,
  `court_material` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`court_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `court_table_0`
--

LOCK TABLES `court_table_0` WRITE;
/*!40000 ALTER TABLE `court_table_0` DISABLE KEYS */;
INSERT INTO `court_table_0` VALUES (1,'1号塑料羽毛球场地'),(2,'2号塑料羽毛球场地'),(3,'3号塑料羽毛球场地'),(4,'4号塑料羽毛球场地'),(5,'5号水泥羽毛球场地'),(6,'6号水泥羽毛球场地'),(7,'7号水泥羽毛球场地'),(8,'8号水泥羽毛球场地');
/*!40000 ALTER TABLE `court_table_0` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduled_time_table_1`
--

DROP TABLE IF EXISTS `scheduled_time_table_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduled_time_table_1` (
  `time_id` int(11) NOT NULL AUTO_INCREMENT,
  `starttime` time NOT NULL,
  `endtime` time NOT NULL,
  PRIMARY KEY (`time_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduled_time_table_1`
--

LOCK TABLES `scheduled_time_table_1` WRITE;
/*!40000 ALTER TABLE `scheduled_time_table_1` DISABLE KEYS */;
INSERT INTO `scheduled_time_table_1` VALUES (1,'17:30:00','19:30:00'),(2,'19:30:00','22:00:00');
/*!40000 ALTER TABLE `scheduled_time_table_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_table_3`
--

DROP TABLE IF EXISTS `team_table_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_table_3` (
  `team_id` int(11) NOT NULL AUTO_INCREMENT,
  `college` varchar(45) NOT NULL,
  `apply_fixed_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`team_id`),
  KEY `apply_fixed_id_idx` (`apply_fixed_id`),
  CONSTRAINT `apply_fixed_id_3_7` FOREIGN KEY (`apply_fixed_id`) REFERENCES `apply_fixed_table_7` (`apply_fixed_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_table_3`
--

LOCK TABLES `team_table_3` WRITE;
/*!40000 ALTER TABLE `team_table_3` DISABLE KEYS */;
INSERT INTO `team_table_3` VALUES (1,'计算机与信息工程学院',NULL);
/*!40000 ALTER TABLE `team_table_3` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `today_court_table_5`
--

DROP TABLE IF EXISTS `today_court_table_5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `today_court_table_5` (
  `today_court_id` int(11) NOT NULL AUTO_INCREMENT,
  `court_id` int(11) NOT NULL,
  `time_id` int(11) NOT NULL,
  `court_state` int(11) NOT NULL,
  PRIMARY KEY (`today_court_id`),
  KEY `court_id_idx` (`court_id`),
  KEY `time_id_idx` (`time_id`),
  CONSTRAINT `court_id_5_0` FOREIGN KEY (`court_id`) REFERENCES `court_table_0` (`court_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `time_id_5_1` FOREIGN KEY (`time_id`) REFERENCES `scheduled_time_table_1` (`time_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `today_court_table_5`
--

LOCK TABLES `today_court_table_5` WRITE;
/*!40000 ALTER TABLE `today_court_table_5` DISABLE KEYS */;
INSERT INTO `today_court_table_5` VALUES (1,1,1,0),(2,2,1,0),(3,3,1,0),(4,4,1,0),(5,5,1,0),(6,6,1,0),(7,7,1,0),(8,8,1,0),(9,1,2,0),(10,2,2,0),(11,3,2,0),(12,4,2,0),(13,5,2,0),(14,6,2,0),(15,7,2,0),(16,8,2,0);
/*!40000 ALTER TABLE `today_court_table_5` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_table_2`
--

DROP TABLE IF EXISTS `user_table_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_table_2` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `identity_num` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `level` double NOT NULL,
  `property` varchar(45) NOT NULL,
  `team_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `team_id_idx` (`team_id`),
  CONSTRAINT `team_id_2_3` FOREIGN KEY (`team_id`) REFERENCES `team_table_3` (`team_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_table_2`
--

LOCK TABLES `user_table_2` WRITE;
/*!40000 ALTER TABLE `user_table_2` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_table_2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bookcourt'
--

--
-- Dumping routines for database 'bookcourt'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-05  0:20:00
