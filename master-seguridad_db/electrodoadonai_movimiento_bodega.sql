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
-- Table structure for table `movimiento_bodega`
--

DROP TABLE IF EXISTS `movimiento_bodega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento_bodega` (
  `ID_MOVIMIENTO` int NOT NULL AUTO_INCREMENT,
  `FECHA_MOVIMIENTO` date NOT NULL,
  `ID_TIPO_MOVIMIENTO` int NOT NULL,
  `ID_EMPLEADO` int NOT NULL,
  PRIMARY KEY (`ID_MOVIMIENTO`),
  KEY `ID_TIPO_MOVIMIENTO` (`ID_TIPO_MOVIMIENTO`),
  KEY `ID_EMPLEADO` (`ID_EMPLEADO`),
  CONSTRAINT `movimiento_bodega_ibfk_1` FOREIGN KEY (`ID_TIPO_MOVIMIENTO`) REFERENCES `tipo_movimiento` (`ID_TIPO_MOVIMIENTO`),
  CONSTRAINT `movimiento_bodega_ibfk_2` FOREIGN KEY (`ID_EMPLEADO`) REFERENCES `empleado` (`ID_EMPLEADO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento_bodega`
--

LOCK TABLES `movimiento_bodega` WRITE;
/*!40000 ALTER TABLE `movimiento_bodega` DISABLE KEYS */;
INSERT INTO `movimiento_bodega` VALUES (1,'2025-02-10',1,1),(2,'2025-02-14',1,1),(3,'2025-02-17',2,1);
/*!40000 ALTER TABLE `movimiento_bodega` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-27  0:23:52
