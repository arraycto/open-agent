-- MySQL dump 10.13  Distrib 5.7.25, for macos10.14 (x86_64)
--
-- Host: localhost    Database: db_plat
-- ------------------------------------------------------
-- Server version	5.7.25

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
-- Table structure for table `schedule_job_client`
--

DROP TABLE IF EXISTS `schedule_job_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_job_client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `client_ip` varchar(100) DEFAULT NULL,
  `client_id` varchar(100) DEFAULT NULL,
  `browser_ip` varchar(100) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `job_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='定时任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_job_client`
--

LOCK TABLES `schedule_job_client` WRITE;
/*!40000 ALTER TABLE `schedule_job_client` DISABLE KEYS */;
INSERT INTO `schedule_job_client` VALUES (6,NULL,'Hessc:channel:10.107.1.160','127.0.0.1',0,NULL,NULL,4),(7,NULL,'Hessc:channel:10.10.40.27','127.0.0.1',0,NULL,NULL,4),(8,NULL,'Hessc:channel:10.10.40.40','127.0.0.1',0,NULL,NULL,4),(9,NULL,'Hessc:channel:10.107.1.160','127.0.0.1',0,NULL,NULL,1318483831269437442),(10,NULL,'Hessc:channel:10.107.1.160','127.0.0.1',0,NULL,NULL,1318531482337746945),(11,NULL,'Hessc:channel:10.107.1.160','127.0.0.1',0,NULL,NULL,1318535285850083329),(12,NULL,'Hessc:channel:10.107.1.160','127.0.0.1',0,NULL,NULL,1318535566801342465),(15,NULL,'Hessc:channel:10.10.40.40','127.0.0.1',0,NULL,NULL,3),(17,NULL,'Hessc:channel:10.4.96.61','127.0.0.1',0,NULL,NULL,2);
/*!40000 ALTER TABLE `schedule_job_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CALENDARS`
--

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CALENDARS`
--

LOCK TABLES `QRTZ_CALENDARS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_job_executor_detail_log`
--

DROP TABLE IF EXISTS `schedule_job_executor_detail_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_job_executor_detail_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `log_id` bigint(10) DEFAULT NULL COMMENT '日志id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `result` varchar(2000) DEFAULT NULL COMMENT '信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `executor_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `execute_time` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_job_executor_detail_log`
--

LOCK TABLES `schedule_job_executor_detail_log` WRITE;
/*!40000 ALTER TABLE `schedule_job_executor_detail_log` DISABLE KEYS */;
INSERT INTO `schedule_job_executor_detail_log` VALUES (1,1,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222554,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263222631,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:53:43',1603263222554,'2020-10-21 14:53:42',1603263222631),(2,1,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222554,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263222749,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:53:43',1603263222554,'2020-10-21 14:53:42',1603263222749),(3,1,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222554,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263222752,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:53:43',1603263222554,'2020-10-21 14:53:42',1603263222752),(4,1,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222554,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263222777,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:53:43',1603263222554,'2020-10-21 14:53:42',1603263222777),(5,1,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222554,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263222981,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:53:43',1603263222554,'2020-10-21 14:53:43',1603263222981),(6,1,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222554,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263223005,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行完成: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:53:43',1603263222554,'2020-10-21 14:53:43',1603263223005),(7,1,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222554,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263223006,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:53:43',1603263222554,'2020-10-21 14:53:43',1603263223006),(8,2,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222555,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263245447,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:54:05',1603263222555,'2020-10-21 14:54:05',1603263245447),(9,2,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222555,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263245447,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:54:05',1603263222555,'2020-10-21 14:54:05',1603263245447),(10,2,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222555,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263245450,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:54:06',1603263222555,'2020-10-21 14:54:05',1603263245450),(11,2,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222555,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263245453,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:54:06',1603263222555,'2020-10-21 14:54:05',1603263245453),(12,2,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222555,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263245477,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:54:06',1603263222555,'2020-10-21 14:54:05',1603263245477),(13,2,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222555,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263245477,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:54:06',1603263222555,'2020-10-21 14:54:06',1603263245477),(14,2,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222555,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263245483,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行完成: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:54:07',1603263222555,'2020-10-21 14:54:07',1603263245483),(15,2,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222555,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263245486,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:54:08',1603263222555,'2020-10-21 14:54:08',1603263245486),(16,3,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349953,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263349983,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:55:50',1603263349953,'2020-10-21 14:55:50',1603263349983),(17,3,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349953,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263350083,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:55:50',1603263349953,'2020-10-21 14:55:50',1603263350083),(18,3,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349953,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263350084,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:55:50',1603263349953,'2020-10-21 14:55:50',1603263350084),(19,3,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349953,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263350096,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:55:50',1603263349953,'2020-10-21 14:55:50',1603263350096),(20,3,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349953,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263350247,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:55:50',1603263349953,'2020-10-21 14:55:50',1603263350247),(21,3,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349953,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263350249,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行完成: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:55:50',1603263349953,'2020-10-21 14:55:50',1603263350249),(22,3,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349953,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263350250,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:55:51',1603263349953,'2020-10-21 14:55:50',1603263350250),(23,4,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349954,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263358732,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:55:59',1603263349954,'2020-10-21 14:55:58',1603263358732),(24,4,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349954,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263358733,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:55:59',1603263349954,'2020-10-21 14:55:58',1603263358733),(25,4,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349954,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263358736,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:55:59',1603263349954,'2020-10-21 14:55:58',1603263358736),(26,4,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349954,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263358742,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:00',1603263349954,'2020-10-21 14:55:59',1603263358742),(27,4,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349954,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263358808,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:01',1603263349954,'2020-10-21 14:56:00',1603263358808),(28,4,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349954,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263358808,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:02',1603263349954,'2020-10-21 14:56:01',1603263358808),(29,5,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349955,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263385361,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:56:25',1603263349955,'2020-10-21 14:56:25',1603263385361),(30,5,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349955,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263385360,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:56:25',1603263349955,'2020-10-21 14:56:25',1603263385360),(31,5,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349955,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263385362,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:25',1603263349955,'2020-10-21 14:56:25',1603263385362),(32,5,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349955,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263385366,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:25',1603263349955,'2020-10-21 14:56:25',1603263385366),(33,5,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349955,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263385410,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:25',1603263349955,'2020-10-21 14:56:25',1603263385410),(34,6,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349956,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263419233,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:56:59',1603263349956,'2020-10-21 14:56:59',1603263419233),(35,6,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349956,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263419235,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 14:56:59',1603263349956,'2020-10-21 14:56:59',1603263419235),(36,6,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349956,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263419240,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:59',1603263349956,'2020-10-21 14:56:59',1603263419240),(37,6,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349956,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263419246,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:59',1603263349956,'2020-10-21 14:56:59',1603263419246),(38,6,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349956,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263419273,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:59',1603263349956,'2020-10-21 14:56:59',1603263419273),(39,7,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349957,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263660209,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:01:00',1603263349957,'2020-10-21 15:01:00',1603263660209),(40,7,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349957,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263660238,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:01',1603263349957,'2020-10-21 15:01:01',1603263660238),(41,7,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349957,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263660263,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:01',1603263349957,'2020-10-21 15:01:01',1603263660263),(42,7,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349957,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263660198,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:01:01',1603263349957,'2020-10-21 15:01:01',1603263660198),(43,7,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349957,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263662351,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:02',1603263349957,'2020-10-21 15:01:02',1603263662351),(44,7,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349957,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263670006,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行完成: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:10',1603263349957,'2020-10-21 15:01:10',1603263670006),(45,7,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349957,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263671334,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:11',1603263349957,'2020-10-21 15:01:11',1603263671334),(46,8,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349958,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263679963,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:01:20',1603263349958,'2020-10-21 15:01:20',1603263679963),(47,8,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349958,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263679967,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:01:20',1603263349958,'2020-10-21 15:01:20',1603263679967),(48,8,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349958,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263679970,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:20',1603263349958,'2020-10-21 15:01:20',1603263679970),(49,8,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349958,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263679996,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:20',1603263349958,'2020-10-21 15:01:20',1603263679996),(50,8,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349958,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263681738,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:22',1603263349958,'2020-10-21 15:01:21',1603263681738),(51,8,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349958,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263681738,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:22',1603263349958,'2020-10-21 15:01:21',1603263681738),(52,9,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349959,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263696058,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:01:36',1603263349959,'2020-10-21 15:01:36',1603263696058),(53,9,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349959,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263696062,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:01:36',1603263349959,'2020-10-21 15:01:36',1603263696062),(54,9,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349959,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263696064,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:36',1603263349959,'2020-10-21 15:01:36',1603263696064),(55,9,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349959,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263696071,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:36',1603263349959,'2020-10-21 15:01:36',1603263696071),(56,9,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349959,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263697234,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:37',1603263349959,'2020-10-21 15:01:37',1603263697234),(57,9,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349959,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263697234,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:37',1603263349959,'2020-10-21 15:01:37',1603263697234),(58,10,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349960,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263709896,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:01:50',1603263349960,'2020-10-21 15:01:49',1603263709896),(59,10,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349960,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263709898,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:01:50',1603263349960,'2020-10-21 15:01:49',1603263709898),(60,10,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349960,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263709906,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:50',1603263349960,'2020-10-21 15:01:50',1603263709906),(61,10,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349960,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263709914,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:50',1603263349960,'2020-10-21 15:01:50',1603263709914),(62,10,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349960,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263709978,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:50',1603263349960,'2020-10-21 15:01:50',1603263709978),(63,10,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349960,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263709978,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:50',1603263349960,'2020-10-21 15:01:50',1603263709978),(64,10,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349960,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263713570,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行完成: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:54',1603263349960,'2020-10-21 15:01:53',1603263713570),(65,10,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349960,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263713573,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:54',1603263349960,'2020-10-21 15:01:53',1603263713573),(66,11,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349961,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263719247,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:01:59',1603263349961,'2020-10-21 15:01:59',1603263719247),(67,11,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349961,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263719248,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:01:59',1603263349961,'2020-10-21 15:01:59',1603263719248),(68,11,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349961,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263719249,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:59',1603263349961,'2020-10-21 15:01:59',1603263719249),(69,11,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349961,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263719257,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:59',1603263349961,'2020-10-21 15:01:59',1603263719257),(70,11,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349961,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263719318,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:59',1603263349961,'2020-10-21 15:01:59',1603263719318),(71,11,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349961,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263719318,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:59',1603263349961,'2020-10-21 15:01:59',1603263719318),(72,11,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349961,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263720387,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行完成: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:00',1603263349961,'2020-10-21 15:02:00',1603263720387),(73,11,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349961,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263720389,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:00',1603263349961,'2020-10-21 15:02:00',1603263720389),(74,12,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349962,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263726770,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:02:07',1603263349962,'2020-10-21 15:02:06',1603263726770),(75,12,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349962,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263726774,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:02:07',1603263349962,'2020-10-21 15:02:06',1603263726774),(76,12,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349962,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263726774,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:07',1603263349962,'2020-10-21 15:02:06',1603263726774),(77,12,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349962,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263726782,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:07',1603263349962,'2020-10-21 15:02:06',1603263726782),(78,12,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349962,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263726843,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:07',1603263349962,'2020-10-21 15:02:06',1603263726843),(79,12,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349962,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263726843,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:07',1603263349962,'2020-10-21 15:02:06',1603263726843),(80,12,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349962,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263727806,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行完成: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:08',1603263349962,'2020-10-21 15:02:07',1603263727806),(81,12,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349962,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263727808,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:08',1603263349962,'2020-10-21 15:02:07',1603263727808),(82,13,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349963,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263734599,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:02:15',1603263349963,'2020-10-21 15:02:14',1603263734599),(83,13,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349963,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263734604,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:02:15',1603263349963,'2020-10-21 15:02:14',1603263734604),(84,13,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349963,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263734616,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:15',1603263349963,'2020-10-21 15:02:14',1603263734616),(85,13,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349963,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263734629,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:15',1603263349963,'2020-10-21 15:02:14',1603263734629),(86,13,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349963,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263734653,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:15',1603263349963,'2020-10-21 15:02:14',1603263734653),(87,13,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349963,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263734653,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:15',1603263349963,'2020-10-21 15:02:14',1603263734653),(88,14,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349964,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263922511,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:05:23',1603263349964,'2020-10-21 15:05:22',1603263922511),(89,14,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349964,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263922514,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:05:23',1603263349964,'2020-10-21 15:05:22',1603263922514),(90,14,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349964,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263922518,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:23',1603263349964,'2020-10-21 15:05:22',1603263922518),(91,14,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349964,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263922526,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:23',1603263349964,'2020-10-21 15:05:22',1603263922526),(92,14,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349964,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263929947,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:30',1603263349964,'2020-10-21 15:05:29',1603263929947),(93,14,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349964,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263929947,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:30',1603263349964,'2020-10-21 15:05:30',1603263929947),(94,14,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349964,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263929959,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行完成: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:30',1603263349964,'2020-10-21 15:05:30',1603263929959),(95,14,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349964,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263929984,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:30',1603263349964,'2020-10-21 15:05:30',1603263929984),(96,15,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349965,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263934635,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:05:35',1603263349965,'2020-10-21 15:05:34',1603263934635),(97,15,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349965,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263934637,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:05:35',1603263349965,'2020-10-21 15:05:34',1603263934637),(98,15,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349965,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263934638,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:35',1603263349965,'2020-10-21 15:05:34',1603263934638),(99,15,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349965,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263934645,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:35',1603263349965,'2020-10-21 15:05:34',1603263934645),(100,15,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349965,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263934679,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:35',1603263349965,'2020-10-21 15:05:34',1603263934679),(101,15,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349965,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263934679,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:35',1603263349965,'2020-10-21 15:05:34',1603263934679),(102,15,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349965,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263934688,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行完成: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:35',1603263349965,'2020-10-21 15:05:34',1603263934688),(103,15,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349965,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263934692,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:35',1603263349965,'2020-10-21 15:05:34',1603263934692),(104,16,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349966,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263939422,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"等待转发channel服务器执行\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:05:39',1603263349966,'2020-10-21 15:05:39',1603263939422),(105,16,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349966,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL_WAIT_SUCCESS\",\n  \"agentChannelId\" : \"Hessc:channel:10.4.96.61\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263939425,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"转发channel服务器执行,成功\",\n  \"command\" : \"pwd\",\n  \"executor\" : false\n}',0,'2020-10-21 15:05:39',1603263349966,'2020-10-21 15:05:39',1603263939425),(106,16,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349966,\n  \"executeStatus\" : \"STATUS_SEND_CHANNEL\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263939427,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"请求已发送Channel服务器\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:40',1603263349966,'2020-10-21 15:05:39',1603263939427),(107,16,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349966,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263939434,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"开始执行: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:40',1603263349966,'2020-10-21 15:05:39',1603263939434),(108,16,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349966,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263939499,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:40',1603263349966,'2020-10-21 15:05:39',1603263939499),(109,16,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349966,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263995623,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:06:36',1603263349966,'2020-10-21 15:06:35',1603263995623),(110,16,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349966,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263995637,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行完成: pwd\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:06:36',1603263349966,'2020-10-21 15:06:35',1603263995637),(111,16,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349966,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263995642,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:06:36',1603263349966,'2020-10-21 15:06:35',1603263995642);
/*!40000 ALTER TABLE `schedule_job_executor_detail_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_LOCKS`
--

LOCK TABLES `QRTZ_LOCKS` WRITE;
/*!40000 ALTER TABLE `QRTZ_LOCKS` DISABLE KEYS */;
INSERT INTO `QRTZ_LOCKS` VALUES ('OpencloudScheduler','STATE_ACCESS'),('OpencloudScheduler','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `QRTZ_LOCKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

LOCK TABLES `QRTZ_PAUSED_TRIGGER_GRPS` WRITE;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_job`
--

DROP TABLE IF EXISTS `schedule_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='定时任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_job`
--

LOCK TABLES `schedule_job` WRITE;
/*!40000 ALTER TABLE `schedule_job` DISABLE KEYS */;
INSERT INTO `schedule_job` VALUES (2,'commandTask','pwd','*/5 * * * * ?',1,'测试任务','2020-10-20 20:55:16');
/*!40000 ALTER TABLE `schedule_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPROP_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPROP_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPROP_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_job_executor_log`
--

DROP TABLE IF EXISTS `schedule_job_executor_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_job_executor_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `log_id` bigint(10) DEFAULT NULL COMMENT '日志id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `result` varchar(2000) DEFAULT NULL COMMENT '信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `executor_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `execute_time` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_job_executor_log`
--

LOCK TABLES `schedule_job_executor_log` WRITE;
/*!40000 ALTER TABLE `schedule_job_executor_log` DISABLE KEYS */;
INSERT INTO `schedule_job_executor_log` VALUES (1,1,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222554,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263223006,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:53:43',1603263222554,'2020-10-21 14:53:43',1603263223006),(2,2,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263222555,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-00000008-db8490bc1fc9d4de-f11ab510\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-00000009-8195ce3107c853b6-9637d5ef\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263245486,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:54:08',1603263222555,'2020-10-21 14:54:08',1603263245486),(3,3,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349953,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263350250,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:55:51',1603263349953,'2020-10-21 14:55:50',1603263350250),(4,4,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349954,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263358808,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:02',1603263349954,'2020-10-21 14:56:01',1603263358808),(5,5,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349955,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263385410,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:25',1603263349955,'2020-10-21 14:56:25',1603263385410),(6,6,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349956,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263419273,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 14:56:59',1603263349956,'2020-10-21 14:56:59',1603263419273),(7,7,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349957,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263671334,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:11',1603263349957,'2020-10-21 15:01:11',1603263671334),(8,8,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349958,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263681738,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:22',1603263349958,'2020-10-21 15:01:21',1603263681738),(9,9,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349959,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263697234,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:37',1603263349959,'2020-10-21 15:01:37',1603263697234),(10,10,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349960,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263713573,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:01:54',1603263349960,'2020-10-21 15:01:53',1603263713573),(11,11,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349961,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263720389,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:00',1603263349961,'2020-10-21 15:02:00',1603263720389),(12,12,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349962,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263727808,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:08',1603263349962,'2020-10-21 15:02:07',1603263727808),(13,13,'commandTask','pwd',-1,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349963,\n  \"executeStatus\" : \"STATUS_DOING\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000c-fae3d76612d12f70-3fe5f914\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263734653,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"/Users/xielianjun/sensetime/workplace/open-agent/Users/xielianjun/sensetime/workplace/open-agent\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:02:15',1603263349963,'2020-10-21 15:02:14',1603263734653),(14,14,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349964,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000a-fc94325f58d7c816-0aacacf0\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263929984,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:30',1603263349964,'2020-10-21 15:05:30',1603263929984),(15,15,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349965,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263934692,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:05:35',1603263349965,'2020-10-21 15:05:34',1603263934692),(16,16,'commandTask','pwd',0,'{\n  \"type\" : 2,\n  \"executeId\" : 1603263349966,\n  \"executeStatus\" : \"STATUS_FINISH\",\n  \"agentChannelId\" : \"acde48fffe001122-000057d5-0000000d-2237ae466ed1d783-3216bafa\",\n  \"proxyChannelId\" : \"acde48fffe001122-000057d5-0000000b-9e294e66b8d64525-f014858a\",\n  \"clientId\" : \"Hessc:channel:10.4.96.61\",\n  \"executeTime\" : 1603263995642,\n  \"proxyIp\" : \"10.4.96.61\",\n  \"result\" : \"执行结束!\",\n  \"command\" : \"pwd\",\n  \"executor\" : true\n}',0,'2020-10-21 15:06:36',1603263349966,'2020-10-21 15:06:35',1603263995642);
/*!40000 ALTER TABLE `schedule_job_executor_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CRON_TRIGGERS`
--

LOCK TABLES `QRTZ_CRON_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` DISABLE KEYS */;
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('OpencloudScheduler','TASK_2','DEFAULT','*/5 * * * * ?','Asia/Shanghai');
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

LOCK TABLES `QRTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` DISABLE KEYS */;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('OpencloudScheduler','TASK_2','DEFAULT',NULL,'com.opencloud.agent.utils.ScheduleJob','0','0','0','0',0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002C636F6D2E6F70656E636C6F75642E6167656E742E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C0009636C69656E744964737400104C6A6176612F7574696C2F4C6973743B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B787074000B636F6D6D616E645461736B707372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001754613FB207874000D2A2F35202A202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000274000370776474000CE6B58BE8AF95E4BBBBE58AA1737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0014000000017800);
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

LOCK TABLES `QRTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` DISABLE KEYS */;
INSERT INTO `QRTZ_TRIGGERS` VALUES ('OpencloudScheduler','TASK_2','DEFAULT','TASK_2','DEFAULT',NULL,1603199000000,-1,5,'PAUSED','CRON',1603199000000,0,NULL,2,0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002C636F6D2E6F70656E636C6F75642E6167656E742E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C0009636C69656E744964737400104C6A6176612F7574696C2F4C6973743B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B787074000B636F6D6D616E645461736B707372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001754613FB207874000D2A2F35202A202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000274000370776474000CE6B58BE8AF95E4BBBBE58AA1737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0014000000017800);
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPLE_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_FIRED_TRIGGERS`
--

LOCK TABLES `QRTZ_FIRED_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_BLOB_TRIGGERS`
--

LOCK TABLES `QRTZ_BLOB_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_job_log`
--

DROP TABLE IF EXISTS `schedule_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `executor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_job_log`
--

LOCK TABLES `schedule_job_log` WRITE;
/*!40000 ALTER TABLE `schedule_job_log` DISABLE KEYS */;
INSERT INTO `schedule_job_log` VALUES (1,2,'commandTask','pwd',0,NULL,290,'2020-10-21 14:53:42',NULL),(2,2,'commandTask','pwd',0,NULL,29,'2020-10-21 14:54:05',NULL),(3,2,'commandTask','pwd',0,NULL,93,'2020-10-21 14:55:50',NULL),(4,2,'commandTask','pwd',0,NULL,23,'2020-10-21 14:55:59',NULL),(5,2,'commandTask','pwd',0,NULL,30,'2020-10-21 14:56:25',NULL),(6,2,'commandTask','pwd',0,NULL,33,'2020-10-21 14:56:59',NULL),(7,2,'commandTask','pwd',0,NULL,745,'2020-10-21 15:01:00',NULL),(8,2,'commandTask','pwd',0,NULL,33,'2020-10-21 15:01:20',NULL),(9,2,'commandTask','pwd',0,NULL,32,'2020-10-21 15:01:36',NULL),(10,2,'commandTask','pwd',0,NULL,47,'2020-10-21 15:01:50',NULL),(11,2,'commandTask','pwd',0,NULL,26,'2020-10-21 15:01:59',NULL),(12,2,'commandTask','pwd',0,NULL,30,'2020-10-21 15:02:07',NULL),(13,2,'commandTask','pwd',0,NULL,31,'2020-10-21 15:02:15',NULL),(14,2,'commandTask','pwd',0,NULL,40,'2020-10-21 15:05:22',NULL),(15,2,'commandTask','pwd',0,NULL,40,'2020-10-21 15:05:35',NULL),(16,2,'commandTask','pwd',0,NULL,69,'2020-10-21 15:05:39',NULL);
/*!40000 ALTER TABLE `schedule_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SCHEDULER_STATE`
--

LOCK TABLES `QRTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` DISABLE KEYS */;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('OpencloudScheduler','MacBook-Air.local1603271226216',1603272025700,15000);
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-21 17:24:45
