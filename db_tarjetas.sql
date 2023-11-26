CREATE DATABASE  IF NOT EXISTS `tarjetas` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tarjetas`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: tarjetas
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `documentos`
--

DROP TABLE IF EXISTS `documentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documentos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero` varchar(45) COLLATE utf8_bin NOT NULL,
  `documento` longblob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documentos`
--

LOCK TABLES `documentos` WRITE;
/*!40000 ALTER TABLE `documentos` DISABLE KEYS */;
/*!40000 ALTER TABLE `documentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estados`
--

DROP TABLE IF EXISTS `estados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estados` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estados`
--

LOCK TABLES `estados` WRITE;
/*!40000 ALTER TABLE `estados` DISABLE KEYS */;
INSERT INTO `estados` VALUES (1,'Impresa'),(2,'En Distribución'),(3,'Devuelta'),(4,'En Sucursal'),(5,'Reenvío'),(6,'Enviada a Sucursal'),(7,'Entregada'),(8,'Destruida');
/*!40000 ALTER TABLE `estados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `motivos`
--

DROP TABLE IF EXISTS `motivos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `motivos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `motivos`
--

LOCK TABLES `motivos` WRITE;
/*!40000 ALTER TABLE `motivos` DISABLE KEYS */;
INSERT INTO `motivos` VALUES (1,'Faltan datos'),(2,'Zona no atendida'),(3,'No existe número'),(4,'No existe calle'),(5,'Se mudó'),(6,'Titular desconocido'),(7,'Se negó a recibirlo'),(8,'Tiene 3 visitas'),(9,'Está de vacaciones'),(10,'Falleció el titular');
/*!40000 ALTER TABLE `motivos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos`
--

DROP TABLE IF EXISTS `movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimientos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `tarjeta` int NOT NULL,
  `estado` int NOT NULL,
  `ubicacion` int DEFAULT NULL,
  `operador` int NOT NULL,
  `motivo` int DEFAULT NULL,
  `documento` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tarjeta_idx` (`tarjeta`),
  KEY `fk_ubicacion_idx` (`ubicacion`),
  KEY `fk_operador_idx` (`operador`),
  KEY `fk_motivo_idx` (`motivo`),
  KEY `fk_documento_idx` (`documento`),
  CONSTRAINT `fk_documento` FOREIGN KEY (`documento`) REFERENCES `documentos` (`id`),
  CONSTRAINT `fk_motivo` FOREIGN KEY (`motivo`) REFERENCES `motivos` (`id`),
  CONSTRAINT `fk_operador` FOREIGN KEY (`operador`) REFERENCES `operadores` (`id`),
  CONSTRAINT `fk_ubicacion` FOREIGN KEY (`ubicacion`) REFERENCES `ubicaciones` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos`
--

LOCK TABLES `movimientos` WRITE;
/*!40000 ALTER TABLE `movimientos` DISABLE KEYS */;
INSERT INTO `movimientos` VALUES (6,'2023-11-16',821659,1,NULL,11,NULL,NULL),(7,'2023-11-17',821660,1,NULL,11,NULL,NULL),(12,'2023-11-17',333333,1,NULL,11,NULL,NULL),(13,'2023-11-16',222222,1,NULL,11,NULL,NULL),(14,'2023-11-17',444444,1,NULL,11,NULL,NULL),(15,'2023-11-15',111111,1,NULL,11,NULL,NULL),(16,'2023-11-18',123456,1,NULL,11,NULL,NULL),(17,'2023-11-18',654321,1,NULL,11,NULL,NULL),(20,'2023-11-22',888888,1,NULL,11,NULL,NULL),(21,'2023-11-22',222222,2,29,11,NULL,NULL),(22,'2023-11-22',888888,2,29,11,NULL,NULL),(23,'2023-11-22',821659,2,30,11,NULL,NULL),(34,'2023-11-26',222222,7,NULL,11,NULL,NULL),(35,'2023-11-26',821659,3,NULL,11,8,NULL);
/*!40000 ALTER TABLE `movimientos` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_insert_movimiento` AFTER INSERT ON `movimientos` FOR EACH ROW BEGIN
	DECLARE estado_anterior INT;
    IF NEW.estado = 1 THEN
        INSERT INTO tarjetas(fecha_emision, cliente, estado, fecha_estado)
        VALUES (NEW.fecha, NEW.tarjeta, 1, NEW.fecha);
	ELSEIF NEW.estado =2 THEN
		UPDATE tarjetas 
		SET 
		estado = NEW.estado, 
		fecha_estado = NEW.fecha, 
		ubicacion = NEW.ubicacion,
		motivo = NEW.motivo,
        fecha_imposicion = NEW.fecha
		WHERE cliente = NEW.tarjeta;		
	ELSE
		SELECT estado INTO estado_anterior FROM tarjetas WHERE cliente = NEW.tarjeta;
        IF estado_anterior = 2 AND (NEW.estado = 3 OR NEW.estado= 9) THEN        			
			UPDATE tarjetas 
			SET 
			estado = NEW.estado, 
			fecha_estado = NEW.fecha, 
			ubicacion = NEW.ubicacion,
			motivo = NEW.motivo,
            fecha_rendicion = NEW.fecha
			WHERE cliente = NEW.tarjeta;		
		ELSE
			UPDATE tarjetas 
			SET 
			estado = NEW.estado, 
			fecha_estado = NEW.fecha, 
			ubicacion = NEW.ubicacion,
			motivo = NEW.motivo
			WHERE cliente = NEW.tarjeta;
		END IF;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `operadores`
--

DROP TABLE IF EXISTS `operadores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operadores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) COLLATE utf8_bin NOT NULL,
  `email` varchar(150) COLLATE utf8_bin NOT NULL,
  `rol` int NOT NULL,
  `password` varchar(12) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_roles` (`rol`),
  CONSTRAINT `fk_roles` FOREIGN KEY (`rol`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operadores`
--

LOCK TABLES `operadores` WRITE;
/*!40000 ALTER TABLE `operadores` DISABLE KEYS */;
INSERT INTO `operadores` VALUES (6,'Rossana Torres','rossana@credinas.com.ar',1,'123'),(7,'Guillermo Gambini','guillermo@credimas.com.ar',1,'123'),(8,'Luz Gutierrez Leone','gluz@credimas.com.ar',1,'123'),(9,'Ivana Amaya','ivana@credimas.com.ar',2,'123'),(10,'Mercedes Gonzalez','mercedes@credimas.com.ar',3,'123'),(11,'Leonardo Papa','leonardo@credimas.com.ar',1,'123');
/*!40000 ALTER TABLE `operadores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `rapidez_x_fecha_imposicion`
--

DROP TABLE IF EXISTS `rapidez_x_fecha_imposicion`;
/*!50001 DROP VIEW IF EXISTS `rapidez_x_fecha_imposicion`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `rapidez_x_fecha_imposicion` AS SELECT 
 1 AS `mes`,
 1 AS `rapidez`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `rapidez_x_fecha_rendicion`
--

DROP TABLE IF EXISTS `rapidez_x_fecha_rendicion`;
/*!50001 DROP VIEW IF EXISTS `rapidez_x_fecha_rendicion`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `rapidez_x_fecha_rendicion` AS SELECT 
 1 AS `mes`,
 1 AS `rapidez`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'CAR'),(2,'Contact Center'),(3,'Sucursal');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjetas`
--

DROP TABLE IF EXISTS `tarjetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarjetas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente` int NOT NULL,
  `fecha_emision` date NOT NULL,
  `fecha_imposicion` date DEFAULT NULL,
  `fecha_rendicion` date DEFAULT NULL,
  `estado` int NOT NULL,
  `fecha_estado` date NOT NULL,
  `motivo` int DEFAULT NULL,
  `ubicacion` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjetas`
--

LOCK TABLES `tarjetas` WRITE;
/*!40000 ALTER TABLE `tarjetas` DISABLE KEYS */;
INSERT INTO `tarjetas` VALUES (1,821659,'2023-11-16','2023-11-22','2023-11-26',3,'2023-11-26',8,NULL),(2,821660,'2023-11-17',NULL,NULL,1,'2023-11-17',NULL,NULL),(3,333333,'2023-11-17',NULL,NULL,1,'2023-11-17',NULL,NULL),(4,222222,'2023-11-16','2023-11-22',NULL,7,'2023-11-26',NULL,NULL),(5,444444,'2023-11-17',NULL,NULL,1,'2023-11-17',NULL,NULL),(6,111111,'2023-11-15',NULL,NULL,1,'2023-11-15',NULL,NULL),(7,123456,'2023-11-18',NULL,NULL,1,'2023-11-18',NULL,NULL),(8,654321,'2023-11-18',NULL,NULL,1,'2023-11-18',NULL,NULL),(11,888888,'2023-11-22','2023-11-22',NULL,2,'2023-11-22',NULL,29);
/*!40000 ALTER TABLE `tarjetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos`
--

DROP TABLE IF EXISTS `tipos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos`
--

LOCK TABLES `tipos` WRITE;
/*!40000 ALTER TABLE `tipos` DISABLE KEYS */;
INSERT INTO `tipos` VALUES (1,'Sucursal'),(2,'Correo');
/*!40000 ALTER TABLE `tipos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ubicaciones`
--

DROP TABLE IF EXISTS `ubicaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ubicaciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) COLLATE utf8_bin NOT NULL,
  `tipo` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tipos` (`tipo`),
  CONSTRAINT `fk_tipos` FOREIGN KEY (`tipo`) REFERENCES `tipos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ubicaciones`
--

LOCK TABLES `ubicaciones` WRITE;
/*!40000 ALTER TABLE `ubicaciones` DISABLE KEYS */;
INSERT INTO `ubicaciones` VALUES (17,'Tucuman',1),(18,'Banda Rio Sali',1),(19,'Concepcion',1),(20,'Santiago',1),(21,'La Banda',1),(22,'Salta',1),(23,'Orán',1),(24,'Tartagal',1),(25,'Jujuy',1),(26,'San Pedro',1),(27,'Libretador',1),(28,'Yerba Buena',1),(29,'Servicios Modernos',2),(30,'Flash',2),(31,'Coprisa',2),(32,'Dago',2),(33,'La Veloz',2);
/*!40000 ALTER TABLE `ubicaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vista_motivos`
--

DROP TABLE IF EXISTS `vista_motivos`;
/*!50001 DROP VIEW IF EXISTS `vista_motivos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_motivos` AS SELECT 
 1 AS `causa`,
 1 AS `cantidad`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_movimientos`
--

DROP TABLE IF EXISTS `vista_movimientos`;
/*!50001 DROP VIEW IF EXISTS `vista_movimientos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_movimientos` AS SELECT 
 1 AS `cuenta`,
 1 AS `fecha`,
 1 AS `estado`,
 1 AS `ubicacion`,
 1 AS `motivo`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_tarjetas`
--

DROP TABLE IF EXISTS `vista_tarjetas`;
/*!50001 DROP VIEW IF EXISTS `vista_tarjetas`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_tarjetas` AS SELECT 
 1 AS `cliente`,
 1 AS `fecha_emision`,
 1 AS `estado`,
 1 AS `fecha_estado`,
 1 AS `motivo`,
 1 AS `ubicacion`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'tarjetas'
--

--
-- Dumping routines for database 'tarjetas'
--
/*!50003 DROP PROCEDURE IF EXISTS `motivos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `motivos`(
    IN fecha_inicial DATE,
    IN fecha_final DATE
)
BEGIN
    select motivos.nombre causa, count(*) as cantidad from movimientos
	inner join motivos on movimientos.motivo = motivos.id
    WHERE movimientos.fecha BETWEEN fecha_inicial AND fecha_final
    GROUP BY causa
order by cantidad;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `tarjetas_x_estado_impos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `tarjetas_x_estado_impos`(
    IN fecha_inicial DATE,
    IN fecha_final DATE
)
BEGIN
    SELECT estados.nombre AS estado, COUNT(*) AS cantidad
    FROM tarjetas.tarjetas
    INNER JOIN tarjetas.estados ON tarjetas.estado = estados.id
    WHERE tarjetas.fecha_imposicion BETWEEN fecha_inicial AND fecha_final
    GROUP BY estados.nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `tarjetas_x_estado_rend` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `tarjetas_x_estado_rend`(
    IN fecha_inicial DATE,
    IN fecha_final DATE
)
BEGIN
    SELECT estados.nombre AS estado, COUNT(*) AS cantidad
    FROM tarjetas.tarjetas
    INNER JOIN tarjetas.estados ON tarjetas.estado = estados.id
    WHERE tarjetas.fecha_rendicion BETWEEN fecha_inicial AND fecha_final
    GROUP BY estados.nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `rapidez_x_fecha_imposicion`
--

/*!50001 DROP VIEW IF EXISTS `rapidez_x_fecha_imposicion`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `rapidez_x_fecha_imposicion` AS select concat(year(`tarjetas`.`fecha_imposicion`),'-',lpad(month(`tarjetas`.`fecha_imposicion`),2,'0')) AS `mes`,avg((`tarjetas`.`fecha_rendicion` - `tarjetas`.`fecha_imposicion`)) AS `rapidez` from `tarjetas` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `rapidez_x_fecha_rendicion`
--

/*!50001 DROP VIEW IF EXISTS `rapidez_x_fecha_rendicion`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `rapidez_x_fecha_rendicion` AS select concat(year(`tarjetas`.`fecha_rendicion`),'-',lpad(month(`tarjetas`.`fecha_rendicion`),2,'0')) AS `mes`,avg((`tarjetas`.`fecha_rendicion` - `tarjetas`.`fecha_imposicion`)) AS `rapidez` from `tarjetas` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_motivos`
--

/*!50001 DROP VIEW IF EXISTS `vista_motivos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_motivos` AS select `motivos`.`nombre` AS `causa`,count(0) AS `cantidad` from (`movimientos` join `motivos` on((`movimientos`.`motivo` = `motivos`.`id`))) group by `causa` order by `cantidad` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_movimientos`
--

/*!50001 DROP VIEW IF EXISTS `vista_movimientos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_movimientos` AS select `m`.`tarjeta` AS `cuenta`,`m`.`fecha` AS `fecha`,`e`.`nombre` AS `estado`,`u`.`nombre` AS `ubicacion`,`m2`.`nombre` AS `motivo` from (((`movimientos` `m` left join `estados` `e` on((`m`.`estado` = `e`.`id`))) left join `ubicaciones` `u` on((`m`.`ubicacion` = `u`.`id`))) left join `motivos` `m2` on((`m`.`motivo` = `m2`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_tarjetas`
--

/*!50001 DROP VIEW IF EXISTS `vista_tarjetas`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_tarjetas` AS select `t`.`cliente` AS `cliente`,`t`.`fecha_emision` AS `fecha_emision`,`e`.`nombre` AS `estado`,`t`.`fecha_estado` AS `fecha_estado`,`m`.`nombre` AS `motivo`,`u`.`nombre` AS `ubicacion` from (((`tarjetas` `t` left join `estados` `e` on((`t`.`estado` = `e`.`id`))) left join `motivos` `m` on((`t`.`motivo` = `m`.`id`))) left join `ubicaciones` `u` on((`t`.`ubicacion` = `u`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-26 18:29:45
