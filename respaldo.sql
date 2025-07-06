-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: simel
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `alumno`
--

DROP TABLE IF EXISTS `alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumno` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `id_grado_seccion` int DEFAULT NULL,
  `id_login` int DEFAULT NULL,
  `puntos_totales` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_login` (`id_login`),
  UNIQUE KEY `unique_alumno_grado` (`id_login`,`id_grado_seccion`),
  KEY `id_grado_seccion` (`id_grado_seccion`),
  CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`id_grado_seccion`) REFERENCES `grado_seccion` (`id`),
  CONSTRAINT `alumno_ibfk_2` FOREIGN KEY (`id_login`) REFERENCES `login` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumno`
--

LOCK TABLES `alumno` WRITE;
/*!40000 ALTER TABLE `alumno` DISABLE KEYS */;
INSERT INTO `alumno` VALUES (15,'Jose Campos',1,7,40),(16,'Neymar Urteaga',1,8,37),(17,'Sofia Gonzales',1,9,0),(18,'Piero Mendez',1,10,0),(19,'Carlos Zuñiga',1,11,0),(20,'Piero Silva',2,12,0),(21,'Josue Pisfil',2,13,0),(22,'Robert Cotrina',2,14,0),(23,'Selene Orteaga',2,15,0),(24,'Paola Lopez',2,16,0),(25,'Jose Pisfil',3,17,0),(26,'Mauricio Sosa',3,18,0),(27,'Angela Campos',3,19,0),(28,'Arturo Zuñiga',3,20,0),(29,'Alumno prueba',1,29,0),(31,'Alumno prueba2',2,31,0);
/*!40000 ALTER TABLE `alumno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alumno_simple`
--

DROP TABLE IF EXISTS `alumno_simple`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumno_simple` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `id_login` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_login` (`id_login`),
  CONSTRAINT `alumno_simple_ibfk_1` FOREIGN KEY (`id_login`) REFERENCES `login` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumno_simple`
--

LOCK TABLES `alumno_simple` WRITE;
/*!40000 ALTER TABLE `alumno_simple` DISABLE KEYS */;
INSERT INTO `alumno_simple` VALUES (1,'Jose Campos',7),(2,'Neymar Urteaga',8),(3,'Sofia Gonzales',9),(4,'Piero Mendez',10),(5,'Carlos Zuñiga',11),(6,'Piero Silva',12),(7,'Josue Pisfil',13),(8,'Robert Cotrina',14),(9,'Selene Orteaga',15),(10,'Paola Lopez',16),(11,'Jose Pisfil',17),(12,'Mauricio Sosa',18),(13,'Angela Campos',19),(14,'Arturo Zuñiga',20),(15,'Emperatriz Ordinola',28),(16,'Alumno prueba',29),(17,'Alumno prueba2',31);
/*!40000 ALTER TABLE `alumno_simple` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `canje_premio`
--

DROP TABLE IF EXISTS `canje_premio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `canje_premio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_alumno` int NOT NULL,
  `id_premio` int NOT NULL,
  `codigo_canje` varchar(20) NOT NULL,
  `estado` enum('Canje digital exitoso','Canje físico solicitado') NOT NULL,
  `fecha_canje` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_canje` (`codigo_canje`),
  KEY `id_alumno` (`id_alumno`),
  KEY `id_premio` (`id_premio`),
  CONSTRAINT `canje_premio_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `alumno` (`id`),
  CONSTRAINT `canje_premio_ibfk_2` FOREIGN KEY (`id_premio`) REFERENCES `premio` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `canje_premio`
--

LOCK TABLES `canje_premio` WRITE;
/*!40000 ALTER TABLE `canje_premio` DISABLE KEYS */;
INSERT INTO `canje_premio` VALUES (4,15,6,'87U25R8V','Canje físico solicitado','2025-07-06 03:46:18'),(5,15,9,'9TMW46KP','Canje digital exitoso','2025-07-06 03:48:31');
/*!40000 ALTER TABLE `canje_premio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` VALUES (1,'Matemáticas'),(2,'Comunicación'),(3,'Ciencias'),(4,'Historia'),(5,'Arte');
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso_grado_docente`
--

DROP TABLE IF EXISTS `curso_grado_docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curso_grado_docente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_grado_seccion` int DEFAULT NULL,
  `id_curso` int DEFAULT NULL,
  `id_docente` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_docente_curso_grado` (`id_docente`,`id_curso`,`id_grado_seccion`),
  KEY `id_grado_seccion` (`id_grado_seccion`),
  KEY `id_curso` (`id_curso`),
  CONSTRAINT `curso_grado_docente_ibfk_1` FOREIGN KEY (`id_grado_seccion`) REFERENCES `grado_seccion` (`id`),
  CONSTRAINT `curso_grado_docente_ibfk_2` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`),
  CONSTRAINT `curso_grado_docente_ibfk_3` FOREIGN KEY (`id_docente`) REFERENCES `docente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso_grado_docente`
--

LOCK TABLES `curso_grado_docente` WRITE;
/*!40000 ALTER TABLE `curso_grado_docente` DISABLE KEYS */;
INSERT INTO `curso_grado_docente` VALUES (1,1,1,1),(6,3,1,1),(2,1,2,2),(3,1,3,3),(9,2,3,3),(4,2,4,4),(8,3,4,4),(5,2,5,5),(7,3,5,5);
/*!40000 ALTER TABLE `curso_grado_docente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente`
--

DROP TABLE IF EXISTS `docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `id_login` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_login` (`id_login`),
  CONSTRAINT `docente_ibfk_1` FOREIGN KEY (`id_login`) REFERENCES `login` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docente`
--

LOCK TABLES `docente` WRITE;
/*!40000 ALTER TABLE `docente` DISABLE KEYS */;
INSERT INTO `docente` VALUES (1,'Manuel Estrada',2),(2,'Maria Lopez',3),(3,'Carlos Sosa',4),(4,'Luis Valdez',5),(5,'Edwin Alcantara',6),(6,'Docente prueba',30);
/*!40000 ALTER TABLE `docente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluacion_curso_alumno`
--

DROP TABLE IF EXISTS `evaluacion_curso_alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluacion_curso_alumno` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_alumno` int NOT NULL,
  `id_curso` int NOT NULL,
  `id_grado_seccion` int NOT NULL,
  `nota_eva1` decimal(5,2) DEFAULT NULL,
  `nota_eva2` decimal(5,2) DEFAULT NULL,
  `nota_eva3` decimal(5,2) DEFAULT NULL,
  `promedio` decimal(5,2) DEFAULT NULL,
  `comentario` text,
  `fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_evaluacion` (`id_alumno`,`id_curso`,`id_grado_seccion`),
  KEY `id_curso` (`id_curso`),
  KEY `id_grado_seccion` (`id_grado_seccion`),
  CONSTRAINT `evaluacion_curso_alumno_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `alumno` (`id`) ON DELETE CASCADE,
  CONSTRAINT `evaluacion_curso_alumno_ibfk_2` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`),
  CONSTRAINT `evaluacion_curso_alumno_ibfk_3` FOREIGN KEY (`id_grado_seccion`) REFERENCES `grado_seccion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluacion_curso_alumno`
--

LOCK TABLES `evaluacion_curso_alumno` WRITE;
/*!40000 ALTER TABLE `evaluacion_curso_alumno` DISABLE KEYS */;
INSERT INTO `evaluacion_curso_alumno` VALUES (1,15,3,1,20.00,20.00,20.00,20.00,'Excelente.','2025-07-06 02:13:58'),(4,16,3,1,18.00,18.00,20.00,18.67,'Muy bien.','2025-07-06 02:13:58'),(5,15,1,1,20.00,20.00,0.00,13.33,'Sigue adelante.','2025-06-23 22:44:29'),(7,16,1,1,15.00,15.00,18.00,16.00,'Tu puedes, adelante.','2025-06-23 22:44:29'),(8,16,2,1,20.00,12.00,18.00,16.67,'Adelante.','2025-06-23 21:49:13');
/*!40000 ALTER TABLE `evaluacion_curso_alumno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grado_seccion`
--

DROP TABLE IF EXISTS `grado_seccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grado_seccion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grado` int NOT NULL,
  `seccion` char(1) DEFAULT 'A',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grado_seccion`
--

LOCK TABLES `grado_seccion` WRITE;
/*!40000 ALTER TABLE `grado_seccion` DISABLE KEYS */;
INSERT INTO `grado_seccion` VALUES (1,4,'A'),(2,5,'A'),(3,6,'A');
/*!40000 ALTER TABLE `grado_seccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `rol` enum('alumno','docente','administrador') NOT NULL,
  `estado` enum('activo','inactivo') DEFAULT 'activo',
  `fecha_creacion` date DEFAULT (curdate()),
  PRIMARY KEY (`id`),
  UNIQUE KEY `usuario` (`usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (1,'Luis Chalan','lchalan','$2a$10$4eevJ.nZWB62hXqtQR0r5e9Jjggf87F8Zwh7ZX6vocbu.3YLWt4f6','administrador','activo','2025-05-28'),(2,'Manuel Estrada','mestrada','$2a$12$CJzR2Md8oxBLHDczh554auv.TWNF0vF4ikNrvCsXlJUVHevDQvOKy','docente','inactivo','2025-05-28'),(3,'Maria Lopez','mlopez','$2a$10$.8kyhcofNQbXfmsxTZkgS.2AcLjqE1H7v4mAviT0XR7VHiWbYNMlW','docente','activo','2025-05-28'),(4,'Carlos Sosa','csosa','$2a$10$7Kmm9krOOMxRyamjd3Jq7uU3Uo7jaUdxxsd5bTZ6qNWqKjiNnrX22','docente','activo','2025-05-28'),(5,'Luis Valdez','lvaldez','docente123','docente','activo','2025-05-28'),(6,'Edwin Alcantara','ealcantara','docente123','docente','activo','2025-05-28'),(7,'Jose Campos','josecampos','$2a$10$IhIkSkFneN82.sGVERBUUuONgYMmwxxkPh.iMEPpzdHdE9OqKYyPq','alumno','activo','2025-05-28'),(8,'Neymar Urteaga','neymarurteaga','$2a$10$XAdQDWwO/pVrYkUgTK1sOOzz7v9u8M5OoOs.2cQXoffLH/NF5OnRS','alumno','activo','2025-05-28'),(9,'Sofia Gonzales','sofiagonzales','alumno123','alumno','activo','2025-05-28'),(10,'Piero Mendez','pieromendez','alumno123','alumno','activo','2025-05-28'),(11,'Carlos Zuñiga','carloszuniga','alumno123','alumno','activo','2025-05-28'),(12,'Piero Silva','pierosilva','alumno123','alumno','activo','2025-05-28'),(13,'Josue Pisfil','josuepisfil','alumno123','alumno','activo','2025-05-28'),(14,'Robert Cotrina','robertcotrina','alumno123','alumno','activo','2025-05-28'),(15,'Selene Orteaga','seleneorteaga','alumno123','alumno','activo','2025-05-28'),(16,'Paola Lopez','paolalopez','alumno123','alumno','activo','2025-05-28'),(17,'Jose Pisfil','josepisfil','alumno123','alumno','activo','2025-05-28'),(18,'Mauricio Sosa','mauriciososa','alumno123','alumno','activo','2025-05-28'),(19,'Angela Campos','angelacampos','alumno123','alumno','activo','2025-05-28'),(20,'Arturo Zuñiga','arturozuniga','alumno123','alumno','activo','2025-05-28'),(28,'Emperatriz Ordinola','eordinola','$2a$12$K9km390s8UsVgmGODhPopu.3xWtDjNQw3Y9d0w9k5xdu4Xii7GGnG','alumno','activo','2025-06-25'),(29,'Alumno prueba','aprueba','$2a$12$zJ0Of2sjxSgEgy0vGmk33eJqqhI0aYyjnt1hD2nRwUwM77XJYAoMy','alumno','activo','2025-07-06'),(30,'Docente prueba','dprueba','$2a$12$ANi4Hifgzl432VM5jpsm1.O1KFroWtkmhIMlF85Va0e95YgeW27iG','docente','activo','2025-07-06'),(31,'Alumno prueba2','aprueba2','$2a$12$Bdgho/goMrYzOIavzPZxruu8RfXYZm/XJkqEWTzcRMhM2885mjOaS','alumno','activo','2025-07-06');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logro`
--

DROP TABLE IF EXISTS `logro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logro` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logro`
--

LOCK TABLES `logro` WRITE;
/*!40000 ALTER TABLE `logro` DISABLE KEYS */;
INSERT INTO `logro` VALUES (2,'Puntualidad','Reconocimiento por llegar siempre a tiempo.'),(3,'Trabajo en equipo','Se adapta correctamente al trabajar en equipo.'),(4,'Responsabilidad','Cumple con sus tareas en tiempo y forma.');
/*!40000 ALTER TABLE `logro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logro_asignado`
--

DROP TABLE IF EXISTS `logro_asignado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logro_asignado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_alumno` int NOT NULL,
  `id_curso` int NOT NULL,
  `id_grado_seccion` int NOT NULL,
  `id_logro` int NOT NULL,
  `puntos` int NOT NULL,
  `comentario` text,
  `fecha_asignacion` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id_alumno` (`id_alumno`),
  KEY `id_curso` (`id_curso`),
  KEY `id_grado_seccion` (`id_grado_seccion`),
  KEY `id_logro` (`id_logro`),
  CONSTRAINT `logro_asignado_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `alumno` (`id`) ON DELETE CASCADE,
  CONSTRAINT `logro_asignado_ibfk_2` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`),
  CONSTRAINT `logro_asignado_ibfk_3` FOREIGN KEY (`id_grado_seccion`) REFERENCES `grado_seccion` (`id`),
  CONSTRAINT `logro_asignado_ibfk_4` FOREIGN KEY (`id_logro`) REFERENCES `logro` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logro_asignado`
--

LOCK TABLES `logro_asignado` WRITE;
/*!40000 ALTER TABLE `logro_asignado` DISABLE KEYS */;
INSERT INTO `logro_asignado` VALUES (2,16,3,1,2,20,'Excelente.','2025-07-06 02:14:17'),(3,16,3,1,3,20,'Excelente','2025-07-06 02:22:13'),(5,15,3,1,2,10,'Excelente.','2025-07-06 02:31:32'),(6,15,3,1,3,50,'Excelente.','2025-07-06 03:46:00');
/*!40000 ALTER TABLE `logro_asignado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `premio`
--

DROP TABLE IF EXISTS `premio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `premio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text,
  `puntos_requeridos` int NOT NULL,
  `tipo` enum('físico','digital') NOT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `premio`
--

LOCK TABLES `premio` WRITE;
/*!40000 ALTER TABLE `premio` DISABLE KEYS */;
INSERT INTO `premio` VALUES (6,'Sesión de yoga','Clase grupal de relajación y meditación.',50,'físico','13da2726-275f-4cd2-a89c-54c71f2528c2.svg'),(7,'Entrada para Cine','Boleto para acceso a cine 2x1.',150,'digital','983407ab-4b01-4baa-ac2c-7c48dc137abe.svg'),(9,'Gift Card Sony','Tarjeta prepago para PlayStation Store (20 $).',10,'digital','ba07ef14-5cd6-437f-8281-2cfb282d621d.svg');
/*!40000 ALTER TABLE `premio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reto`
--

DROP TABLE IF EXISTS `reto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text,
  `puntos` int NOT NULL,
  `id_grado_seccion` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grado_seccion` (`id_grado_seccion`),
  CONSTRAINT `fk_grado_seccion` FOREIGN KEY (`id_grado_seccion`) REFERENCES `grado_seccion` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reto`
--

LOCK TABLES `reto` WRITE;
/*!40000 ALTER TABLE `reto` DISABLE KEYS */;
INSERT INTO `reto` VALUES (1,'Puntualidad Impecable','Llega puntualmente a todas tus clases durante 1 semana completa.',10,1),(2,'Espacio Limpio, Mente Clara','Mantén tu carpeta, útiles y espacio de trabajo ordenado durante toda la semana.',12,2),(3,'Excelencia en Evaluación','Obtén una nota mayor a 17 en la siguiente evaluación de matemática.',15,1),(4,'Aliado del Aula','Apoya voluntariamente a un compañero con dificultades académicas esta semana.',16,1);
/*!40000 ALTER TABLE `reto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reto_alumno`
--

DROP TABLE IF EXISTS `reto_alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reto_alumno` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_alumno` int NOT NULL,
  `id_reto` int NOT NULL,
  `estado` enum('pendiente','completado','rechazado') DEFAULT 'pendiente',
  `puntos_ganados` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_alumno` (`id_alumno`),
  KEY `id_reto` (`id_reto`),
  CONSTRAINT `reto_alumno_ibfk_1` FOREIGN KEY (`id_alumno`) REFERENCES `alumno` (`id`),
  CONSTRAINT `reto_alumno_ibfk_2` FOREIGN KEY (`id_reto`) REFERENCES `reto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reto_alumno`
--

LOCK TABLES `reto_alumno` WRITE;
/*!40000 ALTER TABLE `reto_alumno` DISABLE KEYS */;
INSERT INTO `reto_alumno` VALUES (2,16,1,'completado',0),(3,15,1,'completado',0),(8,16,2,'completado',0),(10,16,3,'completado',0);
/*!40000 ALTER TABLE `reto_alumno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reto_grado`
--

DROP TABLE IF EXISTS `reto_grado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reto_grado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_reto` int NOT NULL,
  `id_grado_seccion` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_reto` (`id_reto`,`id_grado_seccion`),
  KEY `id_grado_seccion` (`id_grado_seccion`),
  CONSTRAINT `reto_grado_ibfk_1` FOREIGN KEY (`id_reto`) REFERENCES `reto` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reto_grado_ibfk_2` FOREIGN KEY (`id_grado_seccion`) REFERENCES `grado_seccion` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reto_grado`
--

LOCK TABLES `reto_grado` WRITE;
/*!40000 ALTER TABLE `reto_grado` DISABLE KEYS */;
/*!40000 ALTER TABLE `reto_grado` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-06  4:01:48
