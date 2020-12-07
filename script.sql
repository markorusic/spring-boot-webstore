-- MySQL dump 10.13  Distrib 5.7.32, for osx10.15 (x86_64)
--
-- Host: localhost    Database: spring_boot_webstore
-- ------------------------------------------------------
-- Server version	5.7.31

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
-- Table structure for table `admin_actions`
--

DROP TABLE IF EXISTS `admin_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_actions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `admin_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKy7gulxlmtetegatmc7xovrgd` (`admin_id`),
  CONSTRAINT `FKy7gulxlmtetegatmc7xovrgd` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_actions`
--

LOCK TABLES `admin_actions` WRITE;
/*!40000 ALTER TABLE `admin_actions` DISABLE KEYS */;
INSERT INTO `admin_actions` VALUES (1,'Created product with id 1','2020-12-07 15:40:17',1),(2,'Created product with id 2','2020-12-07 15:40:23',1),(3,'Created product with id 3','2020-12-07 15:40:25',1),(4,'Created product with id 4','2020-12-07 15:40:25',1),(5,'Created product with id 5','2020-12-07 15:40:26',1),(6,'Created product with id 6','2020-12-07 15:40:26',1),(7,'Created product with id 7','2020-12-07 15:40:26',1),(8,'Created product with id 8','2020-12-07 15:40:26',1),(9,'Created product with id 9','2020-12-07 15:40:26',1),(10,'Created product with id 10','2020-12-07 15:40:27',1),(11,'Created product with id 11','2020-12-07 15:40:27',1),(12,'Created product with id 12','2020-12-07 15:40:27',1),(13,'Created product with id 13','2020-12-07 15:40:27',1),(14,'Created product with id 14','2020-12-07 15:40:29',1),(15,'Created product with id 15','2020-12-07 15:40:30',1),(16,'Created product with id 16','2020-12-07 15:40:30',1),(17,'Created product with id 17','2020-12-07 15:40:30',1),(18,'Created product with id 18','2020-12-07 15:40:30',1),(19,'Created product with id 19','2020-12-07 15:40:30',1),(20,'Created product with id 20','2020-12-07 15:40:30',1),(21,'Created product with id 21','2020-12-07 15:40:30',1),(22,'Created product with id 22','2020-12-07 15:40:31',1),(23,'Created product with id 23','2020-12-07 15:40:31',1),(24,'Created product with id 24','2020-12-07 15:40:31',1),(25,'Created product with id 25','2020-12-07 15:40:31',1),(26,'Created product with id 26','2020-12-07 15:40:31',1),(27,'Created product with id 27','2020-12-07 15:40:31',1),(28,'Created product with id 28','2020-12-07 15:40:32',1),(29,'Created product with id 29','2020-12-07 15:40:32',1),(30,'Created product with id 30','2020-12-07 15:40:32',1),(31,'Created product with id 31','2020-12-07 15:40:32',1),(32,'Created product with id 32','2020-12-07 15:40:32',1),(33,'Created product with id 33','2020-12-07 15:40:32',1),(34,'Created product with id 34','2020-12-07 15:40:32',1),(35,'Created product with id 35','2020-12-07 15:40:37',1),(36,'Created product with id 36','2020-12-07 15:40:37',1),(37,'Created product with id 37','2020-12-07 15:40:37',1),(38,'Created product with id 38','2020-12-07 15:40:37',1),(39,'Created product with id 39','2020-12-07 15:40:37',1),(40,'Created product with id 40','2020-12-07 15:40:38',1),(41,'Created product with id 41','2020-12-07 15:40:38',1),(42,'Created product with id 42','2020-12-07 15:40:40',1),(43,'Created product with id 43','2020-12-07 15:40:40',1),(44,'Created product with id 44','2020-12-07 15:40:40',1),(45,'Created product with id 45','2020-12-07 15:40:40',1),(46,'Created product with id 46','2020-12-07 15:40:40',1),(47,'Created product with id 47','2020-12-07 15:40:40',1),(48,'Created product with id 48','2020-12-07 15:40:40',1),(49,'Created product with id 49','2020-12-07 15:40:41',1),(50,'Created product with id 50','2020-12-07 15:40:41',1),(51,'Created product with id 51','2020-12-07 15:40:43',1),(52,'Created product with id 52','2020-12-07 15:40:43',1),(53,'Created product with id 53','2020-12-07 15:40:43',1),(54,'Created product with id 54','2020-12-07 15:40:44',1),(55,'Created product with id 55','2020-12-07 15:40:44',1),(56,'Created product with id 56','2020-12-07 15:40:44',1),(57,'Created product with id 57','2020-12-07 15:40:46',1),(58,'Created product with id 58','2020-12-07 15:40:46',1),(59,'Created product with id 59','2020-12-07 15:40:46',1),(60,'Created product with id 60','2020-12-07 15:40:46',1),(61,'Created product with id 61','2020-12-07 15:40:47',1),(62,'Created product with id 62','2020-12-07 15:40:47',1),(63,'Created product with id 63','2020-12-07 15:40:47',1),(64,'Created product with id 64','2020-12-07 15:40:50',1),(65,'Created product with id 65','2020-12-07 15:40:50',1),(66,'Created product with id 66','2020-12-07 15:40:50',1),(67,'Created product with id 67','2020-12-07 15:40:50',1),(68,'Created product with id 68','2020-12-07 15:40:51',1),(69,'Created product with id 69','2020-12-07 15:40:51',1),(70,'Created product with id 70','2020-12-07 15:40:51',1),(71,'Created product with id 71','2020-12-07 15:40:51',1),(72,'Created product with id 72','2020-12-07 15:40:51',1),(73,'Created product with id 73','2020-12-07 15:40:51',1),(74,'Created product with id 74','2020-12-07 15:40:52',1),(75,'Created product with id 75','2020-12-07 15:40:52',1),(76,'Created product with id 76','2020-12-07 15:40:52',1),(77,'Created product with id 77','2020-12-07 15:40:52',1);
/*!40000 ALTER TABLE `admin_actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admins` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_47bvqemyk6vlm0w7crc3opdd4` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1,'admin@gmail.com','Pera','Peric','$2y$12$yeIXPy2JmTL5nc884mrqP.OYFdPsmXn/v9cStlESXFrPNSiJJBHbO');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'test'),(2,'test'),(3,'test'),(4,'test'),(5,'test');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_actions`
--

DROP TABLE IF EXISTS `customer_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_actions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKswrgilswcnkjfb55yrbandbo1` (`customer_id`),
  CONSTRAINT `FKswrgilswcnkjfb55yrbandbo1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_actions`
--

LOCK TABLES `customer_actions` WRITE;
/*!40000 ALTER TABLE `customer_actions` DISABLE KEYS */;
INSERT INTO `customer_actions` VALUES (1,'Created order with id q1','2020-12-07 15:41:53',2),(2,'Created order with id q2','2020-12-07 15:41:54',2),(3,'Created order with id q3','2020-12-07 15:41:54',2),(4,'Created order with id q4','2020-12-07 15:41:54',2),(5,'Created order with id q5','2020-12-07 15:41:54',2),(6,'Created order with id q6','2020-12-07 15:41:55',2),(7,'Created order with id q7','2020-12-07 15:41:55',2),(8,'Canceled order with id 1','2020-12-07 15:41:59',2);
/*!40000 ALTER TABLE `customer_actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rfbvkrffamfql7cjmen8v976v` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (2,'marko.rusic@gmail.com','Makro','Rusic','$2a$10$o2FiXD2hb41D74DEyWDxpOlX3GOIa7MsKumb4vn.yhZZLX3CHLvJC');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (1,'test product','https://picsum.photos/200/300',100,3,5,1),(2,'test product','https://picsum.photos/200/300',100,2,2,1),(3,'test product','https://picsum.photos/200/300',100,3,5,2),(4,'test product','https://picsum.photos/200/300',100,2,2,2),(5,'test product','https://picsum.photos/200/300',100,3,5,3),(6,'test product','https://picsum.photos/200/300',100,2,2,3),(7,'test product','https://picsum.photos/200/300',100,3,5,4),(8,'test product','https://picsum.photos/200/300',100,2,2,4),(9,'test product','https://picsum.photos/200/300',100,3,5,5),(10,'test product','https://picsum.photos/200/300',100,2,2,5),(11,'test product','https://picsum.photos/200/300',100,3,5,6),(12,'test product','https://picsum.photos/200/300',100,2,2,6),(13,'test product','https://picsum.photos/200/300',100,3,5,7),(14,'test product','https://picsum.photos/200/300',100,2,2,7);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `note` varchar(255) DEFAULT NULL,
  `shipping_address` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpxtb8awmi0dk6smoh2vp1litg` (`customer_id`),
  CONSTRAINT `FKpxtb8awmi0dk6smoh2vp1litg` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'opis','adresa 1',1,2),(2,'opis','adresa 1',0,2),(3,'opis','adresa 1',0,2),(4,'opis','adresa 1',0,2),(5,'opis','adresa 1',0,2),(6,'opis','adresa 1',0,2),(7,'opis','adresa 1',0,2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_photos`
--

DROP TABLE IF EXISTS `product_photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_photos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk6euo1c1uosxm44vy24qbw05j` (`product_id`),
  CONSTRAINT `FKk6euo1c1uosxm44vy24qbw05j` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_photos`
--

LOCK TABLES `product_photos` WRITE;
/*!40000 ALTER TABLE `product_photos` DISABLE KEYS */;
INSERT INTO `product_photos` VALUES (1,'https://picsum.photos/200/300',1),(2,'https://picsum.photos/200/300',1),(3,'https://picsum.photos/200/300',1),(4,'https://picsum.photos/200/300',2),(5,'https://picsum.photos/200/300',2),(6,'https://picsum.photos/200/300',2),(7,'https://picsum.photos/200/300',3),(8,'https://picsum.photos/200/300',3),(9,'https://picsum.photos/200/300',3),(10,'https://picsum.photos/200/300',4),(11,'https://picsum.photos/200/300',4),(12,'https://picsum.photos/200/300',4),(13,'https://picsum.photos/200/300',5),(14,'https://picsum.photos/200/300',5),(15,'https://picsum.photos/200/300',5),(16,'https://picsum.photos/200/300',6),(17,'https://picsum.photos/200/300',6),(18,'https://picsum.photos/200/300',6),(19,'https://picsum.photos/200/300',7),(20,'https://picsum.photos/200/300',7),(21,'https://picsum.photos/200/300',7),(22,'https://picsum.photos/200/300',8),(23,'https://picsum.photos/200/300',8),(24,'https://picsum.photos/200/300',8),(25,'https://picsum.photos/200/300',9),(26,'https://picsum.photos/200/300',9),(27,'https://picsum.photos/200/300',9),(28,'https://picsum.photos/200/300',10),(29,'https://picsum.photos/200/300',10),(30,'https://picsum.photos/200/300',10),(31,'https://picsum.photos/200/300',11),(32,'https://picsum.photos/200/300',11),(33,'https://picsum.photos/200/300',11),(34,'https://picsum.photos/200/300',12),(35,'https://picsum.photos/200/300',12),(36,'https://picsum.photos/200/300',12),(37,'https://picsum.photos/200/300',13),(38,'https://picsum.photos/200/300',13),(39,'https://picsum.photos/200/300',13),(40,'https://picsum.photos/200/300',14),(41,'https://picsum.photos/200/300',14),(42,'https://picsum.photos/200/300',14),(43,'https://picsum.photos/200/300',15),(44,'https://picsum.photos/200/300',15),(45,'https://picsum.photos/200/300',15),(46,'https://picsum.photos/200/300',16),(47,'https://picsum.photos/200/300',16),(48,'https://picsum.photos/200/300',16),(49,'https://picsum.photos/200/300',17),(50,'https://picsum.photos/200/300',17),(51,'https://picsum.photos/200/300',17),(52,'https://picsum.photos/200/300',18),(53,'https://picsum.photos/200/300',18),(54,'https://picsum.photos/200/300',18),(55,'https://picsum.photos/200/300',19),(56,'https://picsum.photos/200/300',19),(57,'https://picsum.photos/200/300',19),(58,'https://picsum.photos/200/300',20),(59,'https://picsum.photos/200/300',20),(60,'https://picsum.photos/200/300',20),(61,'https://picsum.photos/200/300',21),(62,'https://picsum.photos/200/300',21),(63,'https://picsum.photos/200/300',21),(64,'https://picsum.photos/200/300',22),(65,'https://picsum.photos/200/300',22),(66,'https://picsum.photos/200/300',22),(67,'https://picsum.photos/200/300',23),(68,'https://picsum.photos/200/300',23),(69,'https://picsum.photos/200/300',23),(70,'https://picsum.photos/200/300',24),(71,'https://picsum.photos/200/300',24),(72,'https://picsum.photos/200/300',24),(73,'https://picsum.photos/200/300',25),(74,'https://picsum.photos/200/300',25),(75,'https://picsum.photos/200/300',25),(76,'https://picsum.photos/200/300',26),(77,'https://picsum.photos/200/300',26),(78,'https://picsum.photos/200/300',26),(79,'https://picsum.photos/200/300',27),(80,'https://picsum.photos/200/300',27),(81,'https://picsum.photos/200/300',27),(82,'https://picsum.photos/200/300',28),(83,'https://picsum.photos/200/300',28),(84,'https://picsum.photos/200/300',28),(85,'https://picsum.photos/200/300',29),(86,'https://picsum.photos/200/300',29),(87,'https://picsum.photos/200/300',29),(88,'https://picsum.photos/200/300',30),(89,'https://picsum.photos/200/300',30),(90,'https://picsum.photos/200/300',30),(91,'https://picsum.photos/200/300',31),(92,'https://picsum.photos/200/300',31),(93,'https://picsum.photos/200/300',31),(94,'https://picsum.photos/200/300',32),(95,'https://picsum.photos/200/300',32),(96,'https://picsum.photos/200/300',32),(97,'https://picsum.photos/200/300',33),(98,'https://picsum.photos/200/300',33),(99,'https://picsum.photos/200/300',33),(100,'https://picsum.photos/200/300',34),(101,'https://picsum.photos/200/300',34),(102,'https://picsum.photos/200/300',34),(103,'https://picsum.photos/200/300',35),(104,'https://picsum.photos/200/300',35),(105,'https://picsum.photos/200/300',35),(106,'https://picsum.photos/200/300',36),(107,'https://picsum.photos/200/300',36),(108,'https://picsum.photos/200/300',36),(109,'https://picsum.photos/200/300',37),(110,'https://picsum.photos/200/300',37),(111,'https://picsum.photos/200/300',37),(112,'https://picsum.photos/200/300',38),(113,'https://picsum.photos/200/300',38),(114,'https://picsum.photos/200/300',38),(115,'https://picsum.photos/200/300',39),(116,'https://picsum.photos/200/300',39),(117,'https://picsum.photos/200/300',39),(118,'https://picsum.photos/200/300',40),(119,'https://picsum.photos/200/300',40),(120,'https://picsum.photos/200/300',40),(121,'https://picsum.photos/200/300',41),(122,'https://picsum.photos/200/300',41),(123,'https://picsum.photos/200/300',41),(124,'https://picsum.photos/200/300',43),(125,'https://picsum.photos/200/300',42),(126,'https://picsum.photos/200/300',43),(127,'https://picsum.photos/200/300',42),(128,'https://picsum.photos/200/300',42),(129,'https://picsum.photos/200/300',43),(130,'https://picsum.photos/200/300',44),(131,'https://picsum.photos/200/300',44),(132,'https://picsum.photos/200/300',44),(133,'https://picsum.photos/200/300',45),(134,'https://picsum.photos/200/300',45),(135,'https://picsum.photos/200/300',45),(136,'https://picsum.photos/200/300',46),(137,'https://picsum.photos/200/300',46),(138,'https://picsum.photos/200/300',46),(139,'https://picsum.photos/200/300',47),(140,'https://picsum.photos/200/300',47),(141,'https://picsum.photos/200/300',47),(142,'https://picsum.photos/200/300',48),(143,'https://picsum.photos/200/300',48),(144,'https://picsum.photos/200/300',48),(145,'https://picsum.photos/200/300',49),(146,'https://picsum.photos/200/300',49),(147,'https://picsum.photos/200/300',49),(148,'https://picsum.photos/200/300',50),(149,'https://picsum.photos/200/300',50),(150,'https://picsum.photos/200/300',50),(151,'https://picsum.photos/200/300',51),(152,'https://picsum.photos/200/300',51),(153,'https://picsum.photos/200/300',51),(154,'https://picsum.photos/200/300',52),(155,'https://picsum.photos/200/300',52),(156,'https://picsum.photos/200/300',52),(157,'https://picsum.photos/200/300',53),(158,'https://picsum.photos/200/300',53),(159,'https://picsum.photos/200/300',53),(160,'https://picsum.photos/200/300',54),(161,'https://picsum.photos/200/300',54),(162,'https://picsum.photos/200/300',54),(163,'https://picsum.photos/200/300',55),(164,'https://picsum.photos/200/300',55),(165,'https://picsum.photos/200/300',55),(166,'https://picsum.photos/200/300',56),(167,'https://picsum.photos/200/300',56),(168,'https://picsum.photos/200/300',56),(169,'https://picsum.photos/200/300',57),(170,'https://picsum.photos/200/300',57),(171,'https://picsum.photos/200/300',57),(172,'https://picsum.photos/200/300',58),(173,'https://picsum.photos/200/300',58),(174,'https://picsum.photos/200/300',58),(175,'https://picsum.photos/200/300',59),(176,'https://picsum.photos/200/300',59),(177,'https://picsum.photos/200/300',59),(178,'https://picsum.photos/200/300',60),(179,'https://picsum.photos/200/300',60),(180,'https://picsum.photos/200/300',60),(181,'https://picsum.photos/200/300',61),(182,'https://picsum.photos/200/300',61),(183,'https://picsum.photos/200/300',61),(184,'https://picsum.photos/200/300',62),(185,'https://picsum.photos/200/300',62),(186,'https://picsum.photos/200/300',62),(187,'https://picsum.photos/200/300',63),(188,'https://picsum.photos/200/300',63),(189,'https://picsum.photos/200/300',63),(190,'https://picsum.photos/200/300',64),(191,'https://picsum.photos/200/300',64),(192,'https://picsum.photos/200/300',64),(193,'https://picsum.photos/200/300',65),(194,'https://picsum.photos/200/300',65),(195,'https://picsum.photos/200/300',65),(196,'https://picsum.photos/200/300',66),(197,'https://picsum.photos/200/300',66),(198,'https://picsum.photos/200/300',66),(199,'https://picsum.photos/200/300',67),(200,'https://picsum.photos/200/300',67),(201,'https://picsum.photos/200/300',67),(202,'https://picsum.photos/200/300',68),(203,'https://picsum.photos/200/300',68),(204,'https://picsum.photos/200/300',68),(205,'https://picsum.photos/200/300',69),(206,'https://picsum.photos/200/300',69),(207,'https://picsum.photos/200/300',69),(208,'https://picsum.photos/200/300',70),(209,'https://picsum.photos/200/300',70),(210,'https://picsum.photos/200/300',70),(211,'https://picsum.photos/200/300',71),(212,'https://picsum.photos/200/300',71),(213,'https://picsum.photos/200/300',71),(214,'https://picsum.photos/200/300',72),(215,'https://picsum.photos/200/300',72),(216,'https://picsum.photos/200/300',72),(217,'https://picsum.photos/200/300',73),(218,'https://picsum.photos/200/300',73),(219,'https://picsum.photos/200/300',73),(220,'https://picsum.photos/200/300',74),(221,'https://picsum.photos/200/300',74),(222,'https://picsum.photos/200/300',74),(223,'https://picsum.photos/200/300',75),(224,'https://picsum.photos/200/300',75),(225,'https://picsum.photos/200/300',75),(226,'https://picsum.photos/200/300',76),(227,'https://picsum.photos/200/300',76),(228,'https://picsum.photos/200/300',76),(229,'https://picsum.photos/200/300',77),(230,'https://picsum.photos/200/300',77),(231,'https://picsum.photos/200/300',77);
/*!40000 ALTER TABLE `product_photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_reviews`
--

DROP TABLE IF EXISTS `product_reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_reviews` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `rate` float NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK23003sau3i9tq86acj378o7u9` (`customer_id`),
  KEY `FK35kxxqe2g9r4mww80w9e3tnw9` (`product_id`),
  CONSTRAINT `FK23003sau3i9tq86acj378o7u9` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK35kxxqe2g9r4mww80w9e3tnw9` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_reviews`
--

LOCK TABLES `product_reviews` WRITE;
/*!40000 ALTER TABLE `product_reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'test product','https://picsum.photos/200/300',100,1),(2,'test product','https://picsum.photos/200/300',100,2),(3,'test product','https://picsum.photos/200/300',100,2),(4,'test product','https://picsum.photos/200/300',100,2),(5,'test product','https://picsum.photos/200/300',100,2),(6,'test product','https://picsum.photos/200/300',100,2),(7,'test product','https://picsum.photos/200/300',100,2),(8,'test product','https://picsum.photos/200/300',100,2),(9,'test product','https://picsum.photos/200/300',100,2),(10,'test product','https://picsum.photos/200/300',100,2),(11,'test product','https://picsum.photos/200/300',100,2),(12,'test product','https://picsum.photos/200/300',100,2),(13,'test product','https://picsum.photos/200/300',100,2),(14,'test product','https://picsum.photos/200/300',100,3),(15,'test product','https://picsum.photos/200/300',100,3),(16,'test product','https://picsum.photos/200/300',100,3),(17,'test product','https://picsum.photos/200/300',100,3),(18,'test product','https://picsum.photos/200/300',100,3),(19,'test product','https://picsum.photos/200/300',100,3),(20,'test product','https://picsum.photos/200/300',100,3),(21,'test product','https://picsum.photos/200/300',100,3),(22,'test product','https://picsum.photos/200/300',100,3),(23,'test product','https://picsum.photos/200/300',100,3),(24,'test product','https://picsum.photos/200/300',100,3),(25,'test product','https://picsum.photos/200/300',100,3),(26,'test product','https://picsum.photos/200/300',100,3),(27,'test product','https://picsum.photos/200/300',100,3),(28,'test product','https://picsum.photos/200/300',100,3),(29,'test product','https://picsum.photos/200/300',100,3),(30,'test product','https://picsum.photos/200/300',100,3),(31,'test product','https://picsum.photos/200/300',100,3),(32,'test product','https://picsum.photos/200/300',100,3),(33,'test product','https://picsum.photos/200/300',100,3),(34,'test product','https://picsum.photos/200/300',100,3),(35,'test product','https://picsum.photos/200/300',100,5),(36,'test product','https://picsum.photos/200/300',100,5),(37,'test product','https://picsum.photos/200/300',100,5),(38,'test product','https://picsum.photos/200/300',100,5),(39,'test product','https://picsum.photos/200/300',100,5),(40,'test product','https://picsum.photos/200/300',100,5),(41,'test product','https://picsum.photos/200/300',100,5),(42,'test product','https://picsum.photos/200/300',100,4),(43,'test product','https://picsum.photos/200/300',100,4),(44,'test product','https://picsum.photos/200/300',100,4),(45,'test product','https://picsum.photos/200/300',100,4),(46,'test product','https://picsum.photos/200/300',100,4),(47,'test product','https://picsum.photos/200/300',100,4),(48,'test product','https://picsum.photos/200/300',100,4),(49,'test product','https://picsum.photos/200/300',100,4),(50,'test product','https://picsum.photos/200/300',100,4),(51,'test product','https://picsum.photos/200/300',100,1),(52,'test product','https://picsum.photos/200/300',100,1),(53,'test product','https://picsum.photos/200/300',100,1),(54,'test product','https://picsum.photos/200/300',100,1),(55,'test product','https://picsum.photos/200/300',100,1),(56,'test product','https://picsum.photos/200/300',100,1),(57,'test product','https://picsum.photos/200/300',100,2),(58,'test product','https://picsum.photos/200/300',100,2),(59,'test product','https://picsum.photos/200/300',100,2),(60,'test product','https://picsum.photos/200/300',100,2),(61,'test product','https://picsum.photos/200/300',100,2),(62,'test product','https://picsum.photos/200/300',100,2),(63,'test product','https://picsum.photos/200/300',100,2),(64,'test product','https://picsum.photos/200/300',100,4),(65,'test product','https://picsum.photos/200/300',100,4),(66,'test product','https://picsum.photos/200/300',100,4),(67,'test product','https://picsum.photos/200/300',100,4),(68,'test product','https://picsum.photos/200/300',100,4),(69,'test product','https://picsum.photos/200/300',100,4),(70,'test product','https://picsum.photos/200/300',100,4),(71,'test product','https://picsum.photos/200/300',100,4),(72,'test product','https://picsum.photos/200/300',100,4),(73,'test product','https://picsum.photos/200/300',100,4),(74,'test product','https://picsum.photos/200/300',100,4),(75,'test product','https://picsum.photos/200/300',100,4),(76,'test product','https://picsum.photos/200/300',100,4),(77,'test product','https://picsum.photos/200/300',100,4);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-07 15:43:34
