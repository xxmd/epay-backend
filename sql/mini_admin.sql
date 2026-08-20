/*
SQLyog Community
MySQL - 9.4.0 : Database - mini-admin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mini-admin` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

/*Table structure for table `sys_menu` */

CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL,
  `type` enum('CATEGORY','MENU','BUTTON') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `sort` int DEFAULT NULL,
  `hidden` tinyint(1) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`parent_id`,`type`,`title`,`path`,`component`,`permission`,`sort`,`hidden`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,NULL,'CATEGORY','系统管理','system',NULL,NULL,1,0,'2026-07-30 03:18:13','admin','2026-07-30 03:18:20','admin'),
(2,1,'MENU','用户管理','user','system/user/index','system:user:read',1,0,NULL,NULL,'2026-08-03 17:28:43','admin'),
(3,2,'BUTTON','新增用户',NULL,NULL,'system:user:create',NULL,0,'2026-08-03 17:54:49','admin','2026-08-03 17:54:49','admin'),
(4,2,'BUTTON','修改用户',NULL,NULL,'system:user:update',NULL,NULL,NULL,NULL,NULL,NULL),
(5,2,'BUTTON','删除用户',NULL,NULL,'system:user:delete',NULL,NULL,NULL,NULL,NULL,NULL),
(6,1,'MENU','角色管理','role','system/role/index','system:role:read',2,0,'2026-08-03 17:26:02','admin','2026-08-03 17:36:16','admin'),
(7,6,'BUTTON','新增角色',NULL,NULL,'system:role:create',NULL,0,'2026-08-13 13:52:54','admin','2026-08-13 13:52:54','admin'),
(8,6,'BUTTON','修改角色',NULL,NULL,'system:role:update',NULL,0,'2026-08-13 13:53:17','admin','2026-08-13 13:53:17','admin'),
(9,6,'BUTTON','删除角色',NULL,NULL,'system:role:delete',NULL,0,'2026-08-13 13:53:36','admin','2026-08-13 13:53:36','admin'),
(10,1,'MENU','菜单管理','menu','system/menu/index','system:menu:read',3,0,'2026-07-30 03:19:36','admin','2026-08-03 17:28:15','admin'),
(11,10,'BUTTON','新增菜单',NULL,NULL,'system:menu:create',NULL,0,'2026-07-30 03:20:27','admin','2026-07-30 03:20:28','admin'),
(12,10,'BUTTON','修改菜单',NULL,NULL,'system:menu:update',NULL,0,'2026-07-30 11:39:25','admin','2026-07-30 11:42:20','admin'),
(13,10,'BUTTON','删除菜单',NULL,NULL,'system:menu:delete',NULL,0,'2026-08-03 02:38:03','admin','2026-08-03 02:38:06','admin');

/*Table structure for table `sys_role` */

CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`label`,`value`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,'系统管理员','admin','2026-08-13 08:18:00','admin','2026-08-13 08:18:05','admin'),
(2,'访客','guest','2026-08-13 17:11:51','admin','2026-08-14 14:03:16','admin');

/*Table structure for table `sys_role_menu` */

CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL,
  `menu_id` bigint DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,1,1,NULL,NULL,NULL,NULL),
(2,1,2,NULL,NULL,NULL,NULL),
(3,1,3,NULL,NULL,NULL,NULL),
(4,1,4,NULL,NULL,NULL,NULL),
(5,1,5,NULL,NULL,NULL,NULL),
(6,1,6,NULL,NULL,NULL,NULL),
(7,1,7,NULL,NULL,NULL,NULL),
(8,1,8,NULL,NULL,NULL,NULL),
(9,1,9,NULL,NULL,NULL,NULL),
(10,1,10,NULL,NULL,NULL,NULL),
(11,1,11,NULL,NULL,NULL,NULL),
(12,1,12,NULL,NULL,NULL,NULL),
(13,1,13,NULL,NULL,NULL,NULL),
(14,2,1,NULL,NULL,NULL,NULL),
(15,2,10,NULL,NULL,NULL,NULL);

/*Table structure for table `sys_user` */

CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`nickname`,`enabled`,`remark`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,'admin','$2a$10$e9s3wq8TyMOoDrcYapiKoONouy.hbZl5iNrD1.ZdX0V.htcOvgLo.','admin',1,'系统管理员','2026-08-13 07:34:40','admin','2026-08-13 18:23:10','admin'),
(2,'guest01','$2a$10$bPk0MMz0DnwpEsX6u1OUs.NbuJ/5WFQEbp/tHcy..OT4uuHIgIJoO','guest01',1,NULL,'2026-08-14 10:57:37','admin','2026-08-14 14:02:43','guest01');

/*Table structure for table `sys_user_role` */

CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`,`created_date`,`created_by`,`modified_date`,`modified_by`) values
(1,1,1,NULL,NULL,NULL,NULL),
(2,2,2,NULL,NULL,NULL,NULL);

/*Table structure for table `sys_local_file` */

CREATE TABLE `sys_local_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `path` varchar(500) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
