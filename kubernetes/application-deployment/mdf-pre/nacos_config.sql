/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.32 : Database - nacos_config
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`nacos_config` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `nacos_config`;

/*Table structure for table `config_info` */

DROP TABLE IF EXISTS `config_info`;

CREATE TABLE `config_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) COLLATE utf8mb3_bin DEFAULT NULL,
  `c_use` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
  `effect` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
  `type` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL,
  `c_schema` text COLLATE utf8mb3_bin,
  `encrypted_data_key` text COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info';

/*Data for the table `config_info` */

insert  into `config_info`(`id`,`data_id`,`group_id`,`content`,`md5`,`gmt_create`,`gmt_modified`,`src_user`,`src_ip`,`app_name`,`tenant_id`,`c_desc`,`c_use`,`effect`,`type`,`c_schema`,`encrypted_data_key`) values 
(3,'application.yaml','matrix-jsp-demo','server:\r\n  port: 8080\r\n  servlet: \r\n    context-path: /matrix-jsp-demo\r\nspring:\r\n  application:\r\n    name: matrix-jsp-demo\r\n  profiles:\r\n    active: pre\r\n  jackson:\r\n    time-zone: \"GMT+8\"\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    serialization:\r\n      FAIL_ON_EMPTY_BEANS: false\r\n  http:\r\n    encoding:\r\n      charset: UTF-8\r\n      force: true\r\n  mvc:\r\n    pathmatch:\r\n      # springboot2.6版本Spring MVC 处理映射匹配的默认策略已从AntPathMatcher更改为PathPatternParser\r\n      matching-strategy: ant-path-matcher\r\n      # springboot2.0x中，默认不支持带后缀.do，此处如果关闭*.do请求将全部失效\r\n      use-suffix-pattern: true\r\n# server.jsp-servlet.init-parameters.development=true # 修改JSP文件，页面动态更新，但不起作用。','4c32ff68c0c2989aeae958e1d741deff','2023-06-09 06:44:30','2023-06-09 06:44:30',NULL,'10.233.97.0','','mdf-pre',NULL,NULL,NULL,'yaml',NULL,''),
(4,'matrix-jsp-demo-pre.yaml','matrix-jsp-demo','#日常环境\r\n\r\n##配置sql打印日志\r\nspring:\r\n  datasource:\r\n    matrix:  # 数据源1\r\n      type: com.alibaba.druid.pool.DruidDataSource\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      url: jdbc:mysql://mdf-pre-mysql-service-inner.mdf-pre:3306/matrix?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&serverTimezone=Hongkong\r\n      username: root\r\n      password: XianYue1998@\r\n  redis:\r\n    model: standalone\r\n    host: mdf-pre-redis-service-inner.mdf-pre\r\n    port: 6379\r\n    name: \r\n    password: matrix\r\n    sentinelPassword: \r\n    sentinelMasterId: \r\n    database: 10 								# redis数据库索引(默认为0)，我们使用索引为3的数据库，避免和其他数据库冲突\r\n    timeout: 0   									# redis连接超时时间（单位毫秒）\r\n    pool:\r\n      max-active: 8					 # 最大可用连接数（默认为8，负数表示无限）\r\n      max-idle: 8					 	 # 最大空闲连接数（默认为8，负数表示无限）\r\n      min-idle: 0						 # 最小空闲连接数（默认为0，该值只有为正数才有用）\r\n      max-wait: -1					 # 从连接池中获取连接最大等待时间（默认为-1，单位为毫秒，负数表示无限）\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n','f2a6cff915f595c546b86c40c570198b','2023-06-09 06:45:37','2023-06-09 06:45:37',NULL,'10.233.97.0','','mdf-pre',NULL,NULL,NULL,'yaml',NULL,'');

/*Table structure for table `config_info_aggr` */

DROP TABLE IF EXISTS `config_info_aggr`;

CREATE TABLE `config_info_aggr` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='增加租户字段';

/*Data for the table `config_info_aggr` */

/*Table structure for table `config_info_beta` */

DROP TABLE IF EXISTS `config_info_beta`;

CREATE TABLE `config_info_beta` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info_beta';

/*Data for the table `config_info_beta` */

/*Table structure for table `config_info_tag` */

DROP TABLE IF EXISTS `config_info_tag`;

CREATE TABLE `config_info_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info_tag';

/*Data for the table `config_info_tag` */

/*Table structure for table `config_tags_relation` */

DROP TABLE IF EXISTS `config_tags_relation`;

CREATE TABLE `config_tags_relation` (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_tag_relation';

/*Data for the table `config_tags_relation` */

/*Table structure for table `group_capacity` */

DROP TABLE IF EXISTS `group_capacity`;

CREATE TABLE `group_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='集群、各Group容量信息表';

/*Data for the table `group_capacity` */

/*Table structure for table `his_config_info` */

DROP TABLE IF EXISTS `his_config_info`;

CREATE TABLE `his_config_info` (
  `id` bigint unsigned NOT NULL,
  `nid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text COLLATE utf8mb3_bin,
  `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL,
  `op_type` char(10) COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='多租户改造';

/*Data for the table `his_config_info` */

insert  into `his_config_info`(`id`,`nid`,`data_id`,`group_id`,`app_name`,`content`,`md5`,`gmt_create`,`gmt_modified`,`src_user`,`src_ip`,`op_type`,`tenant_id`,`encrypted_data_key`) values 
(0,1,'application.yaml','leader','','server:\r\n  port: 8080\r\n  servlet: \r\n    context-path: /leader\r\nspring:\r\n  application:\r\n    name: leader\r\n  profiles:\r\n    active: dev\r\n  jackson:\r\n    time-zone: \"GMT+8\"\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    serialization:\r\n      FAIL_ON_EMPTY_BEANS: false\r\n  http:\r\n    encoding:\r\n      charset: UTF-8\r\n      force: true\r\n  mvc:\r\n    pathmatch:\r\n      # springboot2.6版本Spring MVC 处理映射匹配的默认策略已从AntPathMatcher更改为PathPatternParser\r\n      matching-strategy: ant-path-matcher\r\n      # springboot2.0x中，默认不支持带后缀.do，此处如果关闭*.do请求将全部失效\r\n      use-suffix-pattern: true\r\n# server.jsp-servlet.init-parameters.development=true # 修改JSP文件，页面动态更新，但不起作用。','1f2b163d9d7aa463670bab6d09cdddbe','2023-06-09 10:51:20','2023-06-09 02:51:21',NULL,'10.233.97.0','I','mdf-pre',''),
(0,2,'application-pre.yaml','leader','','#日常环境\r\n\r\n##配置sql打印日志\r\nspring:\r\n  datasource:\r\n    matrix:  # 数据源1\r\n      type: com.alibaba.druid.pool.DruidDataSource\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      url: jdbc:mysql://mdf-pre-mysql-service-inner.mdf-pre:3306/matrix?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&serverTimezone=Hongkong\r\n      username: root\r\n      password: XianYue1998@\r\n  redis:\r\n    model: standalone\r\n    host: mdf-pre-redis-service-inner.mdf-pre\r\n    port: 6379\r\n    name: \r\n    password: matrix\r\n    sentinelPassword: \r\n    sentinelMasterId: \r\n    database: 10 								# redis数据库索引(默认为0)，我们使用索引为3的数据库，避免和其他数据库冲突\r\n    timeout: 0   									# redis连接超时时间（单位毫秒）\r\n    pool:\r\n      max-active: 8					 # 最大可用连接数（默认为8，负数表示无限）\r\n      max-idle: 8					 	 # 最大空闲连接数（默认为8，负数表示无限）\r\n      min-idle: 0						 # 最小空闲连接数（默认为0，该值只有为正数才有用）\r\n      max-wait: -1					 # 从连接池中获取连接最大等待时间（默认为-1，单位为毫秒，负数表示无限）\r\n','39160c2b4a1eb0a4580096177e0fab1f','2023-06-09 10:52:03','2023-06-09 02:52:04',NULL,'10.233.97.0','I','mdf-pre',''),
(0,3,'application.yaml','matrix-jsp-demo','','server:\r\n  port: 8080\r\n  servlet: \r\n    context-path: /matrix-jsp-demo\r\nspring:\r\n  application:\r\n    name: matrix-jsp-demo\r\n  profiles:\r\n    active: pre\r\n  jackson:\r\n    time-zone: \"GMT+8\"\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    serialization:\r\n      FAIL_ON_EMPTY_BEANS: false\r\n  http:\r\n    encoding:\r\n      charset: UTF-8\r\n      force: true\r\n  mvc:\r\n    pathmatch:\r\n      # springboot2.6版本Spring MVC 处理映射匹配的默认策略已从AntPathMatcher更改为PathPatternParser\r\n      matching-strategy: ant-path-matcher\r\n      # springboot2.0x中，默认不支持带后缀.do，此处如果关闭*.do请求将全部失效\r\n      use-suffix-pattern: true\r\n# server.jsp-servlet.init-parameters.development=true # 修改JSP文件，页面动态更新，但不起作用。','4c32ff68c0c2989aeae958e1d741deff','2023-06-09 14:44:30','2023-06-09 06:44:30',NULL,'10.233.97.0','I','mdf-pre',''),
(1,4,'application.yaml','leader','','server:\r\n  port: 8080\r\n  servlet: \r\n    context-path: /leader\r\nspring:\r\n  application:\r\n    name: leader\r\n  profiles:\r\n    active: dev\r\n  jackson:\r\n    time-zone: \"GMT+8\"\r\n    date-format: yyyy-MM-dd HH:mm:ss\r\n    serialization:\r\n      FAIL_ON_EMPTY_BEANS: false\r\n  http:\r\n    encoding:\r\n      charset: UTF-8\r\n      force: true\r\n  mvc:\r\n    pathmatch:\r\n      # springboot2.6版本Spring MVC 处理映射匹配的默认策略已从AntPathMatcher更改为PathPatternParser\r\n      matching-strategy: ant-path-matcher\r\n      # springboot2.0x中，默认不支持带后缀.do，此处如果关闭*.do请求将全部失效\r\n      use-suffix-pattern: true\r\n# server.jsp-servlet.init-parameters.development=true # 修改JSP文件，页面动态更新，但不起作用。','1f2b163d9d7aa463670bab6d09cdddbe','2023-06-09 14:44:41','2023-06-09 06:44:41',NULL,'10.233.97.0','D','mdf-pre',''),
(2,5,'application-pre.yaml','leader','','#日常环境\r\n\r\n##配置sql打印日志\r\nspring:\r\n  datasource:\r\n    matrix:  # 数据源1\r\n      type: com.alibaba.druid.pool.DruidDataSource\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      url: jdbc:mysql://mdf-pre-mysql-service-inner.mdf-pre:3306/matrix?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&serverTimezone=Hongkong\r\n      username: root\r\n      password: XianYue1998@\r\n  redis:\r\n    model: standalone\r\n    host: mdf-pre-redis-service-inner.mdf-pre\r\n    port: 6379\r\n    name: \r\n    password: matrix\r\n    sentinelPassword: \r\n    sentinelMasterId: \r\n    database: 10 								# redis数据库索引(默认为0)，我们使用索引为3的数据库，避免和其他数据库冲突\r\n    timeout: 0   									# redis连接超时时间（单位毫秒）\r\n    pool:\r\n      max-active: 8					 # 最大可用连接数（默认为8，负数表示无限）\r\n      max-idle: 8					 	 # 最大空闲连接数（默认为8，负数表示无限）\r\n      min-idle: 0						 # 最小空闲连接数（默认为0，该值只有为正数才有用）\r\n      max-wait: -1					 # 从连接池中获取连接最大等待时间（默认为-1，单位为毫秒，负数表示无限）\r\n','39160c2b4a1eb0a4580096177e0fab1f','2023-06-09 14:44:44','2023-06-09 06:44:45',NULL,'10.233.97.0','D','mdf-pre',''),
(0,6,'matrix-jsp-demo-pre.yaml','matrix-jsp-demo','','#日常环境\r\n\r\n##配置sql打印日志\r\nspring:\r\n  datasource:\r\n    matrix:  # 数据源1\r\n      type: com.alibaba.druid.pool.DruidDataSource\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      url: jdbc:mysql://mdf-pre-mysql-service-inner.mdf-pre:3306/matrix?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&serverTimezone=Hongkong\r\n      username: root\r\n      password: XianYue1998@\r\n  redis:\r\n    model: standalone\r\n    host: mdf-pre-redis-service-inner.mdf-pre\r\n    port: 6379\r\n    name: \r\n    password: matrix\r\n    sentinelPassword: \r\n    sentinelMasterId: \r\n    database: 10 								# redis数据库索引(默认为0)，我们使用索引为3的数据库，避免和其他数据库冲突\r\n    timeout: 0   									# redis连接超时时间（单位毫秒）\r\n    pool:\r\n      max-active: 8					 # 最大可用连接数（默认为8，负数表示无限）\r\n      max-idle: 8					 	 # 最大空闲连接数（默认为8，负数表示无限）\r\n      min-idle: 0						 # 最小空闲连接数（默认为0，该值只有为正数才有用）\r\n      max-wait: -1					 # 从连接池中获取连接最大等待时间（默认为-1，单位为毫秒，负数表示无限）\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n','f2a6cff915f595c546b86c40c570198b','2023-06-09 14:45:36','2023-06-09 06:45:37',NULL,'10.233.97.0','I','mdf-pre','');

/*Table structure for table `permissions` */

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `role` varchar(50) COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `resource` varchar(255) COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `action` varchar(8) COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs;

/*Data for the table `permissions` */

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `username` varchar(50) COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `role` varchar(50) COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs;

/*Data for the table `roles` */

insert  into `roles`(`username`,`role`) values 
('nacos','ROLE_ADMIN');

/*Table structure for table `tenant_capacity` */

DROP TABLE IF EXISTS `tenant_capacity`;

CREATE TABLE `tenant_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='租户容量信息表';

/*Data for the table `tenant_capacity` */

/*Table structure for table `tenant_info` */

DROP TABLE IF EXISTS `tenant_info`;

CREATE TABLE `tenant_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='tenant_info';

/*Data for the table `tenant_info` */

insert  into `tenant_info`(`id`,`kp`,`tenant_id`,`tenant_name`,`tenant_desc`,`create_source`,`gmt_create`,`gmt_modified`) values 
(1,'1','mdf-pre','pre','mdf pre env','nacos',1686278982794,1686278982794);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `password` varchar(500) COLLATE utf8mb4_zh_0900_as_cs NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs;

/*Data for the table `users` */

insert  into `users`(`username`,`password`,`enabled`) values 
('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
