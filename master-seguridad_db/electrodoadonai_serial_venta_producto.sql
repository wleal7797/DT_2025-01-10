-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: electrodoadonai
-- ------------------------------------------------------
-- Server version	8.0.41

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

--
-- Table structure for table `serial_venta_producto`
--

DROP TABLE IF EXISTS `serial_venta_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serial_venta_producto` (
  `ID_SERIAL_PRODUCTO` int NOT NULL,
  `serial` varchar(255) DEFAULT NULL,
  `ID_PRODUCTO` int NOT NULL,
  `ID_VENTA` int NOT NULL,
  PRIMARY KEY (`ID_SERIAL_PRODUCTO`),
  UNIQUE KEY `ID_SERIAL_PRODUCTO` (`ID_SERIAL_PRODUCTO`),
  KEY `ID_VENTAfk_idx` (`ID_VENTA`),
  KEY `ID_PRODUCTOfk_idx` (`ID_PRODUCTO`),
  CONSTRAINT `ID_PRODUCTOfk` FOREIGN KEY (`ID_PRODUCTO`) REFERENCES `producto` (`ID_PRODUCTO`),
  CONSTRAINT `ID_VENTAfk` FOREIGN KEY (`ID_VENTA`) REFERENCES `venta` (`ID_VENTA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serial_venta_producto`
--

LOCK TABLES `serial_venta_producto` WRITE;
/*!40000 ALTER TABLE `serial_venta_producto` DISABLE KEYS */;
INSERT INTO `serial_venta_producto` VALUES (1,'Abckjedi2032',1,1),(2,'iidedenu3282',1,3);
/*!40000 ALTER TABLE `serial_venta_producto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-27  0:23:53
