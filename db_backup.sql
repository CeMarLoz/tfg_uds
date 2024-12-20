CREATE DATABASE  IF NOT EXISTS `tfg` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tfg`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tfg
-- ------------------------------------------------------
-- Server version	8.4.3

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
-- Table structure for table `calle`
--

DROP TABLE IF EXISTS `calle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calle` (
  `calcodigo` int NOT NULL,
  `calnombre` varchar(65) NOT NULL,
  PRIMARY KEY (`calcodigo`),
  UNIQUE KEY `calnombre_UNIQUE` (`calnombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calle`
--

LOCK TABLES `calle` WRITE;
/*!40000 ALTER TABLE `calle` DISABLE KEYS */;
INSERT INTO `calle` VALUES (4,'Avenida Brasil'),(1,'Avenida Mariscal Lopez'),(7,'Calle 14 de Mayo'),(10,'Calle Cerro Pero'),(5,'Calle Chile'),(9,'Calle Doctor Francia'),(2,'Calle Independencia'),(6,'Calle Presidente Franco'),(8,'Calle San Roque de Santa Cruz'),(3,'Ruta 2');
/*!40000 ALTER TABLE `calle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriaconexion`
--

DROP TABLE IF EXISTS `categoriaconexion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoriaconexion` (
  `cccodigo` int NOT NULL,
  `ccnombre` varchar(55) NOT NULL,
  `ccmonto` double NOT NULL,
  `ccmtcubico` int NOT NULL,
  `ccexcedetne` double NOT NULL,
  `ccmora` double NOT NULL,
  PRIMARY KEY (`cccodigo`),
  UNIQUE KEY `ccnombre_UNIQUE` (`ccnombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriaconexion`
--

LOCK TABLES `categoriaconexion` WRITE;
/*!40000 ALTER TABLE `categoriaconexion` DISABLE KEYS */;
INSERT INTO `categoriaconexion` VALUES (1,'Casa Familiar',2500,10,3500,4500),(2,'Edificio',3500,12,4500,5500),(3,'Institucion Educativa',2500,10,3500,4500);
/*!40000 ALTER TABLE `categoriaconexion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `clicodigo` int NOT NULL,
  `clinombre` varchar(65) NOT NULL,
  `cliapellido` varchar(65) NOT NULL,
  `cliruc` varchar(15) NOT NULL,
  `clitelefono` varchar(15) NOT NULL,
  PRIMARY KEY (`clicodigo`),
  UNIQUE KEY `ruc_UNIQUE` (`cliruc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Cesar','Martinez','1002200','0971123456'),(2,'Francisco','Ramirez','5234521','0971412365'),(3,'Carlos ','Benitez','1000060','971001210'),(4,'Alberto','Troche','1000080','971001260'),(5,'Getrudis','Rivarola','1000180','971001280'),(6,'Balbina','Flores','1000330','971001300'),(7,'Sonia','Morel','1000400','971001340'),(8,'Nilsa','Verza','1000410','971001350'),(9,'Beatriz','Vega','1000420','971001380'),(10,'Domingo','Duarte','1000440','971001390'),(11,'Juana del Carmen','Cañete ','1000000','971001180'),(12,'Juan','Perez','5231424','092425368'),(13,'Amelia','Alonso','10111213','0971425365');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conexion`
--

DROP TABLE IF EXISTS `conexion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conexion` (
  `connro` int NOT NULL,
  `confecha` date NOT NULL,
  `conlecturaact` int NOT NULL,
  `solnro` int NOT NULL,
  `medcodigo` int NOT NULL,
  `cccodigo` int NOT NULL,
  PRIMARY KEY (`connro`),
  KEY `fk_conexion_medidor1_idx` (`medcodigo`),
  KEY `fk_conexion_categoriaconexion1_idx` (`cccodigo`),
  KEY `fk_conexion_solicitud1_idx` (`solnro`),
  CONSTRAINT `fk_conexion_categoriaconexion1` FOREIGN KEY (`cccodigo`) REFERENCES `categoriaconexion` (`cccodigo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_conexion_medidor1` FOREIGN KEY (`medcodigo`) REFERENCES `medidor` (`medcodigo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_conexion_solicitud1` FOREIGN KEY (`solnro`) REFERENCES `solicitud` (`solnro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conexion`
--

LOCK TABLES `conexion` WRITE;
/*!40000 ALTER TABLE `conexion` DISABLE KEYS */;
INSERT INTO `conexion` VALUES (1,'2024-12-18',1024,1,1,1),(2,'2024-12-19',2024,2,2,1),(3,'2024-12-19',2024,3,3,2),(4,'2024-12-19',2024,22,21,1),(5,'2024-12-19',10,23,18,1);
/*!40000 ALTER TABLE `conexion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta` (
  `cuenro` int NOT NULL,
  `cuefecha` date NOT NULL,
  `lmcodigo` int NOT NULL,
  `cueanulado` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`cuenro`),
  KEY `fk_cuenta_lecturamedidor1_idx` (`lmcodigo`),
  CONSTRAINT `fk_cuenta_lecturamedidor1` FOREIGN KEY (`lmcodigo`) REFERENCES `lecturamedidor` (`lmcodigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (1,'2024-12-18',1,0),(2,'2024-12-19',2,0),(3,'2024-12-19',4,0),(4,'2024-12-19',5,0),(5,'2024-12-19',6,0);
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuentadet`
--

DROP TABLE IF EXISTS `cuentadet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuentadet` (
  `cuenro` int NOT NULL,
  `sercodigo` int NOT NULL,
  `cuedetcantidad` int NOT NULL,
  `cuedetprecio` double NOT NULL,
  PRIMARY KEY (`cuenro`,`sercodigo`),
  KEY `fk_cuentaservicio_servicio1_idx` (`sercodigo`),
  KEY `fk_cuentaservicio_cuenta1_idx` (`cuenro`),
  CONSTRAINT `fk_cuentaservicio_cuenta1` FOREIGN KEY (`cuenro`) REFERENCES `cuenta` (`cuenro`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_cuentaservicio_servicio1` FOREIGN KEY (`sercodigo`) REFERENCES `servicio` (`sercodigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentadet`
--

LOCK TABLES `cuentadet` WRITE;
/*!40000 ALTER TABLE `cuentadet` DISABLE KEYS */;
INSERT INTO `cuentadet` VALUES (1,1,10,2500),(2,1,10,2500),(2,2,2,3500),(3,1,10,2500),(4,1,10,2500),(4,2,5,3500),(5,1,10,2500);
/*!40000 ALTER TABLE `cuentadet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `direccion`
--

DROP TABLE IF EXISTS `direccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direccion` (
  `connro` int NOT NULL,
  `calcodigo` int NOT NULL,
  PRIMARY KEY (`connro`,`calcodigo`),
  KEY `fk_conexioncalle_calle1_idx` (`calcodigo`),
  KEY `fk_conexioncalle_conexion1_idx` (`connro`),
  CONSTRAINT `fk_conexioncalle_calle1` FOREIGN KEY (`calcodigo`) REFERENCES `calle` (`calcodigo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_conexioncalle_conexion1` FOREIGN KEY (`connro`) REFERENCES `conexion` (`connro`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direccion`
--

LOCK TABLES `direccion` WRITE;
/*!40000 ALTER TABLE `direccion` DISABLE KEYS */;
INSERT INTO `direccion` VALUES (1,1),(4,1),(1,2),(3,3),(2,5),(2,7),(4,7),(5,7),(3,9),(5,9),(2,10);
/*!40000 ALTER TABLE `direccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estadosolicitud`
--

DROP TABLE IF EXISTS `estadosolicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estadosolicitud` (
  `escodigo` int NOT NULL,
  `esdescripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`escodigo`),
  UNIQUE KEY `esoldescripcion_UNIQUE` (`esdescripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadosolicitud`
--

LOCK TABLES `estadosolicitud` WRITE;
/*!40000 ALTER TABLE `estadosolicitud` DISABLE KEYS */;
INSERT INTO `estadosolicitud` VALUES (3,'Aprobado'),(1,'Pendiente'),(2,'Rechazado');
/*!40000 ALTER TABLE `estadosolicitud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturaventa`
--

DROP TABLE IF EXISTS `facturaventa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facturaventa` (
  `vencodigo` int NOT NULL AUTO_INCREMENT,
  `vennro` int NOT NULL,
  `venfecha` date NOT NULL,
  `vencontado` tinyint NOT NULL,
  `timcodigo` int NOT NULL,
  `clicodigo` int NOT NULL,
  `usucodigo` int NOT NULL,
  PRIMARY KEY (`vencodigo`),
  KEY `fk_venta_timbrado1_idx` (`timcodigo`),
  KEY `fk_venta_cliente1_idx` (`clicodigo`),
  KEY `fk_facturaventa_usuario1_idx` (`usucodigo`),
  CONSTRAINT `fk_facturaventa_usuario1` FOREIGN KEY (`usucodigo`) REFERENCES `usuario` (`usucodigo`),
  CONSTRAINT `fk_venta_cliente1` FOREIGN KEY (`clicodigo`) REFERENCES `cliente` (`clicodigo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_venta_timbrado1` FOREIGN KEY (`timcodigo`) REFERENCES `timbrado` (`timcodigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturaventa`
--

LOCK TABLES `facturaventa` WRITE;
/*!40000 ALTER TABLE `facturaventa` DISABLE KEYS */;
INSERT INTO `facturaventa` VALUES (3,1,'2024-12-18',1,1,1,1),(4,2,'2024-12-18',1,1,1,1),(5,3,'2024-12-18',1,1,1,1),(6,4,'2024-12-18',1,1,1,1),(7,5,'2024-12-19',1,1,2,1),(8,6,'2024-12-19',1,1,2,1),(9,7,'2024-12-19',1,1,12,1),(10,8,'2024-12-19',1,1,12,1),(11,9,'2024-12-19',1,1,13,4);
/*!40000 ALTER TABLE `facturaventa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `impuesto`
--

DROP TABLE IF EXISTS `impuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `impuesto` (
  `impcodigo` int NOT NULL,
  `impnombre` varchar(55) NOT NULL,
  `impvalor` int NOT NULL,
  PRIMARY KEY (`impcodigo`),
  UNIQUE KEY `impnombre_UNIQUE` (`impnombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `impuesto`
--

LOCK TABLES `impuesto` WRITE;
/*!40000 ALTER TABLE `impuesto` DISABLE KEYS */;
INSERT INTO `impuesto` VALUES (1,'Iva 10',10),(2,'Iva 5',5),(3,'Exenta',0),(4,'Iva + Ersan',12);
/*!40000 ALTER TABLE `impuesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemcategoria`
--

DROP TABLE IF EXISTS `itemcategoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemcategoria` (
  `iccodigo` int NOT NULL,
  `icdescripcion` varchar(55) NOT NULL,
  `impcodigo` int NOT NULL,
  PRIMARY KEY (`iccodigo`),
  UNIQUE KEY `icdescripcion_UNIQUE` (`icdescripcion`),
  KEY `fk_itemcategoria_impuesto1_idx` (`impcodigo`),
  CONSTRAINT `fk_itemcategoria_impuesto1` FOREIGN KEY (`impcodigo`) REFERENCES `impuesto` (`impcodigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemcategoria`
--

LOCK TABLES `itemcategoria` WRITE;
/*!40000 ALTER TABLE `itemcategoria` DISABLE KEYS */;
INSERT INTO `itemcategoria` VALUES (1,'Material',1),(2,'Consumo',4),(3,'Servicio',1);
/*!40000 ALTER TABLE `itemcategoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturamedidor`
--

DROP TABLE IF EXISTS `lecturamedidor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecturamedidor` (
  `lmcodigo` int NOT NULL,
  `lmfecha` date NOT NULL,
  `lmlectura` double NOT NULL,
  `connro` int NOT NULL,
  PRIMARY KEY (`lmcodigo`),
  KEY `fk_lecturamedidor_conexion1_idx` (`connro`),
  CONSTRAINT `fk_lecturamedidor_conexion1` FOREIGN KEY (`connro`) REFERENCES `conexion` (`connro`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturamedidor`
--

LOCK TABLES `lecturamedidor` WRITE;
/*!40000 ALTER TABLE `lecturamedidor` DISABLE KEYS */;
INSERT INTO `lecturamedidor` VALUES (1,'2024-12-18',1034,1),(2,'2024-12-19',2036,1),(3,'2024-12-19',2040,2),(4,'2024-12-19',2030,4),(5,'2024-12-19',2045,4),(6,'2024-12-19',20,5);
/*!40000 ALTER TABLE `lecturamedidor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medidor`
--

DROP TABLE IF EXISTS `medidor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medidor` (
  `medcodigo` int NOT NULL,
  `mednro` int NOT NULL,
  PRIMARY KEY (`medcodigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medidor`
--

LOCK TABLES `medidor` WRITE;
/*!40000 ALTER TABLE `medidor` DISABLE KEYS */;
INSERT INTO `medidor` VALUES (1,566701),(2,392556),(3,277457),(4,435471),(5,861599),(6,747310),(7,637205),(8,450784),(9,814539),(10,422198),(11,664026),(12,520150),(13,583238),(14,817005),(15,750498),(16,423109),(17,982390),(18,160295),(19,174629),(20,277556),(21,0);
/*!40000 ALTER TABLE `medidor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presupuesto`
--

DROP TABLE IF EXISTS `presupuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `presupuesto` (
  `prenro` int NOT NULL,
  `prefecha` date NOT NULL,
  `cuenro` int NOT NULL,
  PRIMARY KEY (`prenro`),
  KEY `fk_presupuesto_cuenta1_idx` (`cuenro`),
  CONSTRAINT `fk_presupuesto_cuenta1` FOREIGN KEY (`cuenro`) REFERENCES `cuenta` (`cuenro`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presupuesto`
--

LOCK TABLES `presupuesto` WRITE;
/*!40000 ALTER TABLE `presupuesto` DISABLE KEYS */;
INSERT INTO `presupuesto` VALUES (1,'2024-12-18',1),(2,'2024-12-18',1),(3,'2024-12-19',2),(4,'2024-12-19',3),(5,'2024-12-19',4),(6,'2024-12-19',5);
/*!40000 ALTER TABLE `presupuesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presupuestodet`
--

DROP TABLE IF EXISTS `presupuestodet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `presupuestodet` (
  `prenro` int NOT NULL,
  `sercodigo` int NOT NULL,
  `predetcantidad` int NOT NULL,
  `predetprecio` double NOT NULL,
  PRIMARY KEY (`prenro`,`sercodigo`),
  KEY `fk_presupuestoservicio_servicio1_idx` (`sercodigo`),
  KEY `fk_presupuestoservicio_presupuesto1_idx` (`prenro`),
  CONSTRAINT `fk_presupuestoservicio_presupuesto1` FOREIGN KEY (`prenro`) REFERENCES `presupuesto` (`prenro`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_presupuestoservicio_servicio1` FOREIGN KEY (`sercodigo`) REFERENCES `servicio` (`sercodigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presupuestodet`
--

LOCK TABLES `presupuestodet` WRITE;
/*!40000 ALTER TABLE `presupuestodet` DISABLE KEYS */;
INSERT INTO `presupuestodet` VALUES (1,1,10,2500),(2,1,10,2500),(3,1,10,2500),(3,2,2,3500),(4,1,10,2500),(5,1,10,2500),(5,2,5,3500),(6,1,10,2500);
/*!40000 ALTER TABLE `presupuestodet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reclamo`
--

DROP TABLE IF EXISTS `reclamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reclamo` (
  `recnro` int NOT NULL,
  `recfecha` date NOT NULL,
  `recdescripcion` varchar(65) NOT NULL,
  `clicodigo` int NOT NULL,
  PRIMARY KEY (`recnro`),
  KEY `fk_reclamo_cliente1_idx` (`clicodigo`),
  CONSTRAINT `fk_reclamo_cliente1` FOREIGN KEY (`clicodigo`) REFERENCES `cliente` (`clicodigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reclamo`
--

LOCK TABLES `reclamo` WRITE;
/*!40000 ALTER TABLE `reclamo` DISABLE KEYS */;
INSERT INTO `reclamo` VALUES (1,'2024-12-18','Falla con el agua, se rompio el caño',1),(2,'2024-12-19','No funciona la canilla',2),(3,'2024-12-19','Se rompio el caño',13);
/*!40000 ALTER TABLE `reclamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `rolcodigo` int NOT NULL,
  `roldescripcion` varchar(55) NOT NULL,
  PRIMARY KEY (`rolcodigo`),
  UNIQUE KEY `roldescripcion_UNIQUE` (`roldescripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'Administrador');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicio`
--

DROP TABLE IF EXISTS `servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicio` (
  `sercodigo` int NOT NULL,
  `serdescripcion` varchar(85) NOT NULL,
  `serprecio` double NOT NULL,
  `iccodigo` int NOT NULL,
  PRIMARY KEY (`sercodigo`),
  UNIQUE KEY `serdescripcion_UNIQUE` (`serdescripcion`),
  KEY `fk_servicio_itemcategoria1_idx` (`iccodigo`),
  CONSTRAINT `fk_servicio_itemcategoria1` FOREIGN KEY (`iccodigo`) REFERENCES `itemcategoria` (`iccodigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicio`
--

LOCK TABLES `servicio` WRITE;
/*!40000 ALTER TABLE `servicio` DISABLE KEYS */;
INSERT INTO `servicio` VALUES (1,'Conusmo Mensual',0,2),(2,'Consumo Excedente',0,2),(3,'Multa por Mora',0,3),(4,'Consumo Anterior',0,3),(5,'Derecho de Conexión',550000,3),(6,'Derecho de Reconexión',330000,3);
/*!40000 ALTER TABLE `servicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitud`
--

DROP TABLE IF EXISTS `solicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitud` (
  `solnro` int NOT NULL,
  `solfecha` date NOT NULL,
  `soldescripcion` varchar(65) DEFAULT NULL,
  `clicodigo` int NOT NULL,
  `escodigo` int NOT NULL,
  PRIMARY KEY (`solnro`),
  KEY `fk_solicitud_cliente_idx` (`clicodigo`),
  KEY `fk_solicitud_estadosolicitud1_idx` (`escodigo`),
  CONSTRAINT `fk_solicitud_cliente` FOREIGN KEY (`clicodigo`) REFERENCES `cliente` (`clicodigo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_solicitud_estadosolicitud1` FOREIGN KEY (`escodigo`) REFERENCES `estadosolicitud` (`escodigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitud`
--

LOCK TABLES `solicitud` WRITE;
/*!40000 ALTER TABLE `solicitud` DISABLE KEYS */;
INSERT INTO `solicitud` VALUES (1,'2024-12-01','Casa particular',1,3),(2,'2024-12-01','Quiero solicitar para su casa',2,3),(3,'2024-12-01','Casa particular',10,3),(4,'2024-12-02','Quiero solicitar para su lavadero',2,2),(5,'2024-12-03','Para un quinta',3,3),(6,'2024-12-04','Para un quinta',4,3),(7,'2024-12-05','Casa particular',1,3),(8,'2024-12-06','Quiero solicitar para su lavadero',6,2),(9,'2024-12-07','Para un quinta',7,3),(10,'2024-12-08','Quiero solicitar para su lavadero',8,2),(11,'2024-12-09','Casa particular',9,1),(12,'2024-12-10','Para un quinta',10,1),(13,'2024-12-11','Casa particular',9,1),(14,'2024-12-12','Quiero solicitar para su lavadero',8,2),(15,'2024-12-13','Casa particular',7,1),(16,'2024-12-14','Casa particular',6,1),(17,'2024-12-15','Para un quinta',5,1),(18,'2024-12-16','Casa particular',4,1),(19,'2024-12-17','Casa particular',3,1),(20,'2024-12-18','Para un quinta',2,1),(21,'2024-12-19','Casa particular',1,1),(22,'2024-12-19','Para su casa',12,1),(23,'2024-12-19','Para la casa',13,3);
/*!40000 ALTER TABLE `solicitud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timbrado`
--

DROP TABLE IF EXISTS `timbrado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timbrado` (
  `timcodigo` int NOT NULL AUTO_INCREMENT,
  `timnro` int NOT NULL,
  `timvencimiento` date NOT NULL,
  `timnroinicial` int NOT NULL,
  `timnrofinal` int NOT NULL,
  `timest` int NOT NULL,
  `timexp` int NOT NULL,
  `timactivo` tinyint NOT NULL,
  PRIMARY KEY (`timcodigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timbrado`
--

LOCK TABLES `timbrado` WRITE;
/*!40000 ALTER TABLE `timbrado` DISABLE KEYS */;
INSERT INTO `timbrado` VALUES (1,1234578,'2025-11-10',1,100,1,1,1);
/*!40000 ALTER TABLE `timbrado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `usucodigo` int NOT NULL,
  `nombre` varchar(65) NOT NULL,
  `apellido` varchar(65) NOT NULL,
  `alias` varchar(45) NOT NULL,
  `clave` varchar(85) NOT NULL,
  `rolcodigo` int NOT NULL,
  PRIMARY KEY (`usucodigo`),
  UNIQUE KEY `alias_UNIQUE` (`alias`),
  KEY `fk_usuario_rol1_idx` (`rolcodigo`),
  CONSTRAINT `fk_usuario_rol1` FOREIGN KEY (`rolcodigo`) REFERENCES `rol` (`rolcodigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin','admin','admin','12345',1),(2,'prof','prof','prof','123',1),(3,'Mirtha','Lopez','mirtha','0000',1),(4,'Amelia','Alonso','amelia','101112',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventadet`
--

DROP TABLE IF EXISTS `ventadet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventadet` (
  `vencodigo` int NOT NULL,
  `sercodigo` int NOT NULL,
  `vdetcantidad` int NOT NULL,
  `vdetprecio` double NOT NULL,
  `vdetimpuesto` int NOT NULL,
  PRIMARY KEY (`vencodigo`,`sercodigo`),
  KEY `fk_ventaitem_venta1_idx` (`vencodigo`),
  KEY `fk_ventadet_servicio1_idx` (`sercodigo`),
  CONSTRAINT `fk_ventadet_servicio1` FOREIGN KEY (`sercodigo`) REFERENCES `servicio` (`sercodigo`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_ventaitem_venta1` FOREIGN KEY (`vencodigo`) REFERENCES `facturaventa` (`vencodigo`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventadet`
--

LOCK TABLES `ventadet` WRITE;
/*!40000 ALTER TABLE `ventadet` DISABLE KEYS */;
INSERT INTO `ventadet` VALUES (3,1,10,2500,12),(4,1,10,2500,12),(5,1,10,2500,12),(6,1,10,2500,12),(7,1,10,2500,12),(8,1,10,2500,12),(8,2,2,3500,12),(9,1,10,2500,12),(10,1,10,2500,12),(10,2,5,3500,12),(11,1,10,2500,12);
/*!40000 ALTER TABLE `ventadet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'tfg'
--
/*!50003 DROP PROCEDURE IF EXISTS `pCalle` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pCalle`(IN criterio VARCHAR(45))
BEGIN
	SELECT 
		calcodigo 'Código', calnombre 'Nombre'
	FROM
		calle
	WHERE
		calcodigo LIKE CONCAT('%', criterio, '%')
			OR calnombre LIKE CONCAT('%', criterio, '%')
	ORDER BY calcodigo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pCiudad` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pCiudad`(IN criterio VARCHAR(45))
BEGIN
	SELECT 
		ciucodigo 'Código', ciudescripcion 'Descripción'
	FROM
		ciudad
	WHERE
		ciucodigo LIKE CONCAT('%', criterio, '%')
			OR ciudescripcion LIKE CONCAT('%', criterio, '%')
	ORDER BY ciucodigo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pCliente`(IN criterio VARCHAR(120))
BEGIN
	SELECT 
		clicodigo 'Código',
		cliruc 'RUC / CINº',
		CONCAT(clinombre, ' ', cliapellido) 'Nombres y Apellidos',
        clinombre,
        cliapellido,
        clitelefono
	FROM
		cliente
	WHERE
		clicodigo LIKE CONCAT('%', criterio, '%')
			OR cliruc LIKE CONCAT('%', criterio, '%')
            OR clinombre LIKE CONCAT('%', criterio, '%')
            OR cliapellido LIKE CONCAT('%', criterio, '%')
	ORDER BY clicodigo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pConexion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pConexion`(IN criterio VARCHAR(120))
BEGIN
	SELECT 
    connro 'Número',
    CONCAT(mednro,
            ' - ',
            clinombre,
            ' ',
            cliapellido) Medidor,
    DATE_FORMAT(confecha, '%d-%m-%Y') Fecha,
    conlecturaact,
    clinombre,
    cliapellido,
    c.solnro,
    ca.cccodigo,
    ca.ccnombre,
    m.medcodigo,
    m.mednro
FROM
    conexion c
        INNER JOIN
    medidor m ON c.medcodigo = m.medcodigo
        INNER JOIN
    categoriaconexion ca ON c.cccodigo = ca.cccodigo
        INNER JOIN
    solicitud s ON c.solnro = s.solnro
        INNER JOIN
    cliente cl ON s.clicodigo = cl.clicodigo
	WHERE
		connro LIKE CONCAT('%', criterio, '%')
			OR cliruc LIKE CONCAT('%', criterio, '%')
			OR clinombre LIKE CONCAT('%', criterio, '%')
			OR cliapellido LIKE CONCAT('%', criterio, '%')
	ORDER BY connro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pCuenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pCuenta`(IN criterio VARCHAR(120))
BEGIN
	SELECT 
		cuenro 'Número',
		mednro Medidor,
		DATE_FORMAT(cuefecha, '%d-%m-%Y') 'Fecha',
        l.lmcodigo,
		m.medcodigo,
        cueanulado
	FROM
		cuenta c
			INNER JOIN
		lecturamedidor l ON c.lmcodigo = l.lmcodigo
			INNER JOIN
		conexion x ON l.connro = x.connro
			INNER JOIN
		medidor m ON x.medcodigo = m.medcodigo
	WHERE
		cuenro LIKE CONCAT('%', criterio, '%')
			OR mednro LIKE CONCAT('%', criterio, '%')
	ORDER BY cuenro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pLecturaAnt` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pLecturaAnt`(in conexion int, in lectura int)
BEGIN
    -- Variable 
	DECLARE v_lectura_act int;
    
    -- Consulta de lecturas
	SELECT 
		lmlectura into v_lectura_act
	FROM
		lecturamedidor
	WHERE
		lmcodigo = (SELECT 
				IFNULL(MAX(la.lmcodigo), 0)
			FROM
				lecturamedidor la
			WHERE
				la.connro = conexion AND la.lmcodigo != lectura);
	-- En caso de la lectura retorna null buscamos el que se habia registro en la conexion
    -- se entiendo que se esta tratando el primer registro de lectura
	if v_lectura_act is null then
		select conlecturaact into v_lectura_act from conexion where connro = conexion;
	end if;
    
    -- para retornar la consulta
    select v_lectura_act;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pLecturaMedidor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pLecturaMedidor`(IN criterio VARCHAR(120))
BEGIN
	SELECT 
		lmcodigo 'Código',
		m.mednro Medidor,
		DATE_FORMAT(lmfecha, '%d-%m-%Y') 'Fecha',
		lmlectura,
		l.connro,
		m.medcodigo,
		ccmonto,
		ccmtcubico,
		ccexcedetne
	FROM
		lecturamedidor l
			INNER JOIN
		conexion c ON l.connro = c.connro
			INNER JOIN
		medidor m ON c.medcodigo = m.medcodigo
			INNER JOIN
		categoriaconexion t ON c.cccodigo = t.cccodigo
	WHERE
		lmcodigo LIKE CONCAT('%', criterio, '%')
			OR mednro LIKE CONCAT('%', criterio, '%')
	ORDER BY lmcodigo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pPresupuesto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pPresupuesto`(IN criterio VARCHAR(120))
BEGIN
	SELECT 
		prenro 'Número',
		mednro Medidor,
		DATE_FORMAT(prefecha, '%d-%m-%Y') 'Fecha',
		p.cuenro
	FROM
		presupuesto p
			INNER JOIN
		cuenta c ON p.cuenro = c.cuenro
			INNER JOIN
		lecturamedidor l ON c.lmcodigo = l.lmcodigo
			INNER JOIN
		conexion x ON l.connro = x.connro
			INNER JOIN
		medidor m ON x.medcodigo = m.medcodigo
	WHERE
		prenro LIKE CONCAT('%', criterio, '%')
			OR mednro LIKE CONCAT('%', criterio, '%')
	ORDER BY prenro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pServicio` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pServicio`(IN criterio VARCHAR(45))
BEGIN
	SELECT 
		sercodigo 'Código',
		serdescripcion 'Descripción',
		serprecio,
		c.iccodigo,
		c.icdescripcion
	FROM
		servicio s
			INNER JOIN
		itemcategoria c ON s.iccodigo = c.iccodigo
	WHERE
		sercodigo LIKE CONCAT('%', criterio, '%')
			OR serdescripcion LIKE CONCAT('%', criterio, '%')
	ORDER BY sercodigo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pSolicitud` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pSolicitud`(IN criterio VARCHAR(120))
BEGIN
	SELECT 
		solnro 'Número',
		CONCAT(clinombre, ' ', cliapellido) Cliente,
		DATE_FORMAT(solfecha, '%d-%m-%Y') 'Fecha',
		esdescripcion Estado,
		e.escodigo,
		c.clicodigo,
		clinombre,
		cliapellido,
        cliruc,
        soldescripcion
	FROM
		solicitud s
			INNER JOIN
		cliente c ON s.clicodigo = c.clicodigo
			INNER JOIN
		estadosolicitud e ON s.escodigo = e.escodigo
	WHERE
		solnro LIKE CONCAT('%', criterio, '%')
			OR cliruc LIKE CONCAT('%', criterio, '%')
			OR clinombre LIKE CONCAT('%', criterio, '%')
			OR cliapellido LIKE CONCAT('%', criterio, '%')
	ORDER BY solnro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pVenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pVenta`(IN criterio VARCHAR(120), in establecimiento int, in puntoexp int)
BEGIN
	SELECT 
		CONCAT(LPAD(a.estnro, 3, 0), '-', LPAD(a.pexpnro, 3, 0), '-', LPAD(v.vennro, 7, 0)) Factura,
		IF(v.vencontado = 1, 'Contado', 'Crédito') Condicion,
		c.cliruc RUC,
		CONCAT(c.clinombre, ' ', c.cliapellido) Cliente,
        v.vennro,
        c.clinombre,
        c.cliapellido,
        v.vencodigo,
        v.vencontado
	FROM
		facturaventa v
			INNER JOIN
		apertura a ON v.apernro = a.apernro
			INNER JOIN
		cliente c ON v.clicodigo = c.clicodigo
	WHERE
		v.vennro LIKE CONCAT('%', criterio, '%')
			OR c.cliruc LIKE CONCAT('%', criterio, '%')
			OR c.clinombre LIKE CONCAT('%', criterio, '%')
			OR c.cliapellido LIKE CONCAT('%', criterio, '%')
            AND a.estnro = establecimiento
			AND a.pexpnro = puntoexp
	ORDER BY v.vennro DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-19 23:02:30
