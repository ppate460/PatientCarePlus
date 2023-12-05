-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dev
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

USE PatientCarePlus;
SELECT * FROM doctors;
SELECT * FROM doctor_users;
-- SELECT * FROM logger;
-- SELECT * FROM messages;
-- SELECT * FROM patients;
-- SELECT * FROM patient_users;
-- SELECT * FROM users;
-- SELECT * FROM connections;

drop table connections;

create table connections(
                            connection_id int not null auto_increment,
                            patient_id int,
                            doctor_id int,
                            primary key(connection_id)
);

create table vitals(
                       patient_id int not null,
                       num int not null,
                       primary key(patient_id)
);

-- CREATE TABLE connections(
-- 	patient_id int not null,
--     doctor_id int not null
-- );

SELECT DISTINCT * FROM connections
                           join doctors on connections.doctor_id = doctors.doctor_id
where connections.patient_id = 2;

--
-- Table structure for table `doctor_users`
--

DROP TABLE IF EXISTS `doctor_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_users` (
                                `user_id` int NOT NULL AUTO_INCREMENT,
                                `username` varchar(45) DEFAULT NULL,
                                `password` varchar(45) DEFAULT NULL,
                                PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_users`
--

LOCK TABLES `doctor_users` WRITE;
/*!40000 ALTER TABLE `doctor_users` DISABLE KEYS */;
INSERT INTO `doctor_users` VALUES (2,'marcus','Test1234'),(3,'marcus','Test1234');
/*!40000 ALTER TABLE `doctor_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
                           `doctor_id` int NOT NULL AUTO_INCREMENT,
                           `username` varchar(45) DEFAULT NULL,
                           `password` varchar(45) DEFAULT NULL,
                           `email` varchar(45) DEFAULT NULL,
                           `patient_id` int DEFAULT NULL,
                           `last_name` varchar(45) DEFAULT NULL,
                           `first_name` varchar(45) DEFAULT NULL,
                           `work_address` varchar(45) DEFAULT NULL,
                           `certification` varchar(45) DEFAULT NULL,
                           `phone` varchar(45) DEFAULT NULL,
                           PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,'marcus','Update4Me','marcus@welby.com',2,'Marcus','Welby',NULL,NULL,'333-4444');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logger`
--

DROP TABLE IF EXISTS `logger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logger` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `message` varchar(4000) DEFAULT NULL,
                          `log_date` datetime DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logger`
--

LOCK TABLES `logger` WRITE;
/*!40000 ALTER TABLE `logger` DISABLE KEYS */;
INSERT INTO `logger` VALUES (1,'Happy Sunday','2023-08-06 12:46:59'),(2,'Have a great day.','2023-08-06 12:47:41');
/*!40000 ALTER TABLE `logger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
                            `message_id` int NOT NULL AUTO_INCREMENT,
                            `from_id` int DEFAULT NULL,
                            `to_id` int DEFAULT NULL,
                            `sender_type` int DEFAULT NULL,
                            `subject` varchar(45) DEFAULT NULL,
                            `message` varchar(45) DEFAULT NULL,
                            `sent_date` datetime DEFAULT NULL,
                            `root_message_id` int DEFAULT NULL,
                            PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,2,2,1,'Tummy ache','can I get an appointment?','2023-10-31 22:48:08',1),(2,1,2,2,'annual check up','It\'s that time of the year again.','2023-10-31 22:55:26',2),(3,2,1,1,'feeling ill','Can I see you today?','2023-10-31 22:57:03',3),(7,2,1,1,'Re: annual check up','How about Wednesday?','2023-11-12 23:09:59',2),(8,1,2,2,'Re: Re: annual check up','Sounds great!','2023-11-12 23:10:36',2),(9,2,1,1,'Re: Re: Re: annual check up','See you then.','2023-11-12 23:13:38',2);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_users`
--

DROP TABLE IF EXISTS `patient_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_users`
--

LOCK TABLES `patient_users` WRITE;
/*!40000 ALTER TABLE `patient_users` DISABLE KEYS */;
INSERT INTO `patient_users` VALUES (1,'tester','Test1234');
/*!40000 ALTER TABLE `patient_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `emergency_phone` varchar(45) DEFAULT NULL,
  `emergency_contact` varchar(45) DEFAULT NULL,
  `doctors_notes` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (2,'bob@test.com','tester','Update123','Tester','Bob','555-1212','Ms. Tester','fit');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `createDt` datetime DEFAULT NULL,
  `updateDt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'bob','Test1234',1,1,'2023-08-06 12:31:42',NULL),(2,'steri','Test1234',NULL,NULL,NULL,NULL),(3,'mickey','Test1234',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-12 15:18:32
