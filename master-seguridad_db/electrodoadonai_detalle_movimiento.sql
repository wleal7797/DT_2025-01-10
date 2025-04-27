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
-- Table structure for table `detalle_movimiento`
--

DROP TABLE IF EXISTS `detalle_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_movimiento` (
  `ID_DETALLE_MOVIMIENTO` int NOT NULL AUTO_INCREMENT,
  `CNT_PRODUCTO_MOVIMIENTO` int NOT NULL,
  `ID_MOVIMIENTO_BODEGA` int NOT NULL,
  `ID_BODEGA_ORIGEN` int NOT NULL,
  `ID_BODEGA_DESTINO` int NOT NULL,
  `ID_DETALLE_BODEGA` int NOT NULL,
  `id_producto` int DEFAULT NULL,
  PRIMARY KEY (`ID_DETALLE_MOVIMIENTO`),
  KEY `ID_MOVIMIENTO_BODEGA` (`ID_MOVIMIENTO_BODEGA`),
  KEY `ID_BODEGA_ORIGEN` (`ID_BODEGA_ORIGEN`),
  KEY `ID_BODEGA_DESTINO` (`ID_BODEGA_DESTINO`),
  KEY `detalle_movimiento___fk` (`ID_DETALLE_BODEGA`),
  KEY `FK3qy73mfcwc6muxvky9scoq4bc` (`id_producto`),
  CONSTRAINT `detalle_movimiento___fk` FOREIGN KEY (`ID_DETALLE_BODEGA`) REFERENCES `detalle_bodega` (`ID_DETALLE_BODEGA`),
  CONSTRAINT `detalle_movimiento_ibfk_1` FOREIGN KEY (`ID_MOVIMIENTO_BODEGA`) REFERENCES `movimiento_bodega` (`ID_MOVIMIENTO`),
  CONSTRAINT `detalle_movimiento_ibfk_3` FOREIGN KEY (`ID_BODEGA_ORIGEN`) REFERENCES `bodega` (`ID_BODEGA`),
  CONSTRAINT `detalle_movimiento_ibfk_4` FOREIGN KEY (`ID_BODEGA_DESTINO`) REFERENCES `bodega` (`ID_BODEGA`),
  CONSTRAINT `FK3qy73mfcwc6muxvky9scoq4bc` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`ID_PRODUCTO`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_movimiento`
--

LOCK TABLES `detalle_movimiento` WRITE;
/*!40000 ALTER TABLE `detalle_movimiento` DISABLE KEYS */;
INSERT INTO `detalle_movimiento` VALUES (3,1,3,2,6,2,NULL),(4,1,1,1,5,1,NULL);
/*!40000 ALTER TABLE `detalle_movimiento` ENABLE KEYS */;
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
