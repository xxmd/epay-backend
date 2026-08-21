/*
SQLyog Community
MySQL - 9.4.0 : Database - admin-pay
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`admin-pay` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

/*Table structure for table `pay_application` */

CREATE TABLE `pay_application` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `icon_file_id` bigint DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `platform` enum('ANDROID','IOS','WINDOWS') DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `remark` text,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `pay_application` */

insert  into `pay_application`(`id`,`icon_file_id`,`name`,`platform`,`enabled`,`remark`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,2,'佛跳墙','ANDROID',1,NULL,'2026-08-20 16:06:53','xxmd3720@gmail.com','2026-08-20 16:06:53','xxmd3720@gmail.com'),
(2,3,'测试应用','ANDROID',1,NULL,'2026-08-20 17:02:12','guest01','2026-08-20 17:02:12','guest01');

/*Table structure for table `pay_merchant` */

CREATE TABLE `pay_merchant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `platform_id` bigint DEFAULT NULL,
  `merchant_id` bigint DEFAULT NULL,
  `md5_secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sort` int DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `pay_merchant` */

insert  into `pay_merchant`(`id`,`platform_id`,`merchant_id`,`md5_secret_key`,`sort`,`enabled`,`remark`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,1,1031,'6OuEX7E7diz6R4070IUWOO0d06r001e1',NULL,0,NULL,'2026-08-19 18:38:47','admin','2026-08-21 18:24:19','admin'),
(2,2,2025071018032699,'kp5GG9c0HLPCmipoBH89e5INTPzR9IUO',1,1,NULL,'2026-08-21 18:17:57','admin','2026-08-21 18:30:47','admin');

/*Table structure for table `pay_merchant_method` */

CREATE TABLE `pay_merchant_method` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `merchant_id` bigint DEFAULT NULL,
  `method_id` bigint DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `pay_merchant_method` */

insert  into `pay_merchant_method`(`id`,`merchant_id`,`method_id`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(6,1,2,NULL,NULL,NULL,NULL),
(7,2,2,NULL,NULL,NULL,NULL);

/*Table structure for table `pay_method` */

CREATE TABLE `pay_method` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `remark` text,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `pay_method` */

insert  into `pay_method`(`id`,`label`,`value`,`enabled`,`remark`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,'微信支付','WX_PAY',1,NULL,'2026-08-19 16:34:01','admin','2026-08-19 16:34:01','admin'),
(2,'支付宝','ALI_PAY',1,NULL,'2026-08-19 16:34:14','admin','2026-08-19 16:34:14','admin');

/*Table structure for table `pay_order` */

CREATE TABLE `pay_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `merchant_id` bigint DEFAULT NULL,
  `application_id` bigint DEFAULT NULL,
  `method_id` bigint DEFAULT NULL,
  `order_number` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `product_price` decimal(10,2) DEFAULT NULL,
  `product_quantity` int DEFAULT NULL,
  `pay_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `pay_status` enum('UNPAID','PAID') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `pay_date` datetime DEFAULT NULL,
  `notify_param` text,
  `remark` text,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `pay_order` */

insert  into `pay_order`(`id`,`merchant_id`,`application_id`,`method_id`,`order_number`,`product_name`,`product_price`,`product_quantity`,`pay_url`,`pay_status`,`pay_date`,`notify_param`,`remark`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,1,1,2,'20260821103255000004','一元包天套餐',1.00,1,'https://66.hm659.org/submit.php?money=1&name=%E4%B8%80%E5%85%83%E5%8C%85%E5%A4%A9%E5%A5%97%E9%A4%90x1&notify_url=http%3A%2F%2F127.0.0.1%3A8080%2Fpay%2Forder%2Fnotify&out_trade_no=20260821103255000004&pid=1031&return_url=http%3A%2F%2F127.0.0.1%3A8080%2Fpay%2Forder%2Fnotify&sign=8039164280535926b226e6d158076956&sign_type=MD5&type=alipay','UNPAID',NULL,NULL,NULL,'2026-08-21 10:32:55','xxmd3720@gmail.com','2026-08-21 10:32:55','xxmd3720@gmail.com'),
(2,1,2,1,'20260821172852000005','测试商品',0.01,1,'https://66.hm659.org/submit.php?money=0.01&name=%E6%B5%8B%E8%AF%95%E5%95%86%E5%93%81x1&notify_url=http%3A%2F%2F127.0.0.1%3A8080%2Fpay%2Forder%2Fnotify&out_trade_no=20260821172852000005&pid=1031&return_url=http%3A%2F%2F127.0.0.1%3A8080%2Fpay%2Forder%2Fnotify&sign=8399e0cf90c669c05d7176dd9b5ce4e1&sign_type=MD5&type=wxpay','UNPAID',NULL,NULL,NULL,'2026-08-21 17:28:52','guest01','2026-08-21 17:28:52','guest01'),
(3,1,2,2,'20260821173059000006','测试商品',0.01,1,'https://66.hm659.org/submit.php?money=0.01&name=%E6%B5%8B%E8%AF%95%E5%95%86%E5%93%81x1&notify_url=http%3A%2F%2F127.0.0.1%3A8080%2Fpay%2Forder%2Fnotify&out_trade_no=20260821173059000006&pid=1031&return_url=http%3A%2F%2F127.0.0.1%3A8080%2Fpay%2Forder%2Fnotify&sign=d28c86f6c283861f9a751674fd2a6a01&sign_type=MD5&type=alipay','PAID','2026-08-21 17:42:08','{\"pid\":1031,\"type\":\"alipay\",\"name\":\"product\",\"money\":\"0.01\",\"sign\":\"89fc2c8fd8cf5d9e2db1924dbb4d172d\",\"valid\":true,\"trade_no\":\"2026082117310195328\",\"out_trade_no\":\"20260821173059000006\",\"trade_status\":\"TRADE_SUCCESS\",\"sign_type\":\"MD5\"}',NULL,'2026-08-21 17:30:59','guest01','2026-08-21 17:42:08','system'),
(4,2,2,2,'20260821182440000007','Zpay测试商品',0.01,1,'https://zpayz.cn/submit.php?money=0.01&name=Zpay%E6%B5%8B%E8%AF%95%E5%95%86%E5%93%81x1&notify_url=http%3A%2F%2F127.0.0.1%3A8080%2Fpay%2Forder%2Fnotify&out_trade_no=20260821182440000007&pid=2088522095883080&return_url=http%3A%2F%2F127.0.0.1%3A8080%2Fpay%2Forder%2Fnotify&sign=02734767676f14d3aca0485f636c00d4&sign_type=MD5&type=alipay','UNPAID',NULL,NULL,NULL,'2026-08-21 18:24:40','guest01','2026-08-21 18:24:40','guest01'),
(5,2,2,2,'20260821183109000008','Zpay测试商品',0.01,1,'https://zpayz.cn/submit.php?money=0.01&name=Zpay%E6%B5%8B%E8%AF%95%E5%95%86%E5%93%81x1&notify_url=http%3A%2F%2F127.0.0.1%3A8080%2Fpay%2Forder%2Fnotify&out_trade_no=20260821183109000008&pid=2025071018032699&return_url=http%3A%2F%2F127.0.0.1%3A8080%2Fpay%2Forder%2Fnotify&sign=3bcd810950557d207d79949fb24cc833&sign_type=MD5&type=alipay','UNPAID',NULL,NULL,NULL,'2026-08-21 18:31:13','guest01','2026-08-21 18:31:13','guest01');

/*Table structure for table `pay_platform` */

CREATE TABLE `pay_platform` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `domain_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sort` int DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `pay_platform` */

insert  into `pay_platform`(`id`,`name`,`domain_name`,`contact`,`sort`,`enabled`,`remark`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,'飞飞支付','66.hm659.org','Telegram@aoaozf1bot',NULL,1,NULL,'2026-08-19 18:27:06','admin','2026-08-21 18:09:10','admin'),
(2,'ZPay','zpayz.cn','https://member.z-pay.cn/member/',NULL,1,NULL,'2026-08-21 18:09:05','admin','2026-08-21 18:09:05','admin');

/*Table structure for table `res_local_file` */

CREATE TABLE `res_local_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `res_local_file` */

insert  into `res_local_file`(`id`,`name`,`size`,`path`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,'Screenshot_20260720_142614.png','119323','2026/08/20/Screenshot_20260720_142614.png','2026-08-20 15:59:24','xxmd3720@gmail.com','2026-08-20 15:59:24','xxmd3720@gmail.com'),
(2,'Screenshot_20260720_142614.png','119323','2026/08/20/Screenshot_20260720_142614_160507921.png','2026-08-20 16:05:08','xxmd3720@gmail.com','2026-08-20 16:05:08','xxmd3720@gmail.com'),
(3,'Snipaste_2026-08-20_16-18-03.png','72886','2026/08/20/Snipaste_2026-08-20_16-18-03.png','2026-08-20 17:02:05','guest01','2026-08-20 17:02:05','guest01');

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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
(13,10,'BUTTON','删除菜单',NULL,NULL,'system:menu:delete',NULL,0,'2026-08-03 02:38:03','admin','2026-08-03 02:38:06','admin'),
(15,31,'MENU','支付方式','method','pay/method/index','pay:method:read',1,0,'2026-08-19 15:42:05','admin','2026-08-20 17:22:58','admin'),
(16,31,'MENU','平台管理','platform','pay/platform/index','pay:platform:read',2,0,'2026-08-19 15:42:44','admin','2026-08-20 17:24:10','admin'),
(17,31,'MENU','商户管理','merchant','pay/merchant/index','pay:merchant:read',3,0,'2026-08-19 15:43:40','admin','2026-08-20 17:23:06','admin'),
(18,15,'BUTTON','新增',NULL,NULL,'pay:method:create',NULL,0,'2026-08-19 15:50:16','admin','2026-08-19 16:33:43','admin'),
(19,15,'BUTTON','修改',NULL,NULL,'pay:method:update',NULL,0,'2026-08-19 15:50:57','admin','2026-08-19 15:51:35','admin'),
(20,15,'BUTTON','删除',NULL,NULL,'pay:method:delete',NULL,0,'2026-08-19 15:51:15','admin','2026-08-19 15:51:15','admin'),
(21,16,'BUTTON','新增',NULL,NULL,'pay:platform:create',NULL,0,'2026-08-19 15:52:08','admin','2026-08-20 10:17:15','admin'),
(22,16,'BUTTON','修改',NULL,NULL,'pay:platform:update',NULL,0,'2026-08-19 15:52:29','admin','2026-08-20 10:17:19','admin'),
(23,16,'BUTTON','删除',NULL,NULL,'pay:platform:delete',NULL,0,'2026-08-19 15:52:56','admin','2026-08-20 10:17:23','admin'),
(24,17,'BUTTON','新增',NULL,NULL,'pay:merchant:create',NULL,0,'2026-08-19 15:53:41','admin','2026-08-20 10:17:29','admin'),
(25,17,'BUTTON','修改',NULL,NULL,'pay:merchant:update',NULL,0,'2026-08-19 15:54:03','admin','2026-08-20 10:17:38','admin'),
(26,17,'BUTTON','删除',NULL,NULL,'pay:merchant:delete',NULL,0,'2026-08-19 15:54:35','admin','2026-08-20 10:17:42','admin'),
(27,1,'MENU','租户管理','merchant','system/merchant/index','system:merchant:read',4,0,'2026-08-20 10:00:30','admin','2026-08-20 10:00:30','admin'),
(29,31,'MENU','应用管理','application','pay/application/index','pay:application:read',4,0,'2026-08-20 10:03:40','admin','2026-08-20 17:23:22','admin'),
(30,31,'MENU','订单管理','order','pay/order/index','pay:order:read',5,0,'2026-08-20 10:04:28','admin','2026-08-20 17:23:29','admin'),
(31,NULL,'CATEGORY','支付管理','pay',NULL,NULL,3,0,'2026-08-20 10:11:20','admin','2026-08-20 15:20:37','admin'),
(32,NULL,'CATEGORY','资源管理','resource',NULL,NULL,2,0,'2026-08-20 15:08:43','admin','2026-08-20 15:20:34','admin'),
(33,32,'MENU','文件管理','file','resource/file/index','resource:file:read',NULL,0,'2026-08-20 15:09:22','admin','2026-08-20 15:09:22','admin'),
(34,29,'BUTTON','新增',NULL,NULL,'pay:application:create',NULL,0,'2026-08-20 15:40:58','admin','2026-08-20 15:40:58','admin'),
(35,29,'BUTTON','修改',NULL,NULL,'pay:application:update',NULL,0,'2026-08-20 15:41:14','admin','2026-08-20 15:41:14','admin'),
(36,29,'BUTTON','删除',NULL,NULL,'pay:application:delete',NULL,0,'2026-08-20 15:41:27','admin','2026-08-20 15:41:27','admin'),
(37,30,'BUTTON','新增',NULL,NULL,'pay:order:create',NULL,0,'2026-08-21 10:08:29','admin','2026-08-21 10:08:29','admin'),
(38,30,'BUTTON','修改',NULL,NULL,'pay:order:update',NULL,0,'2026-08-21 10:08:44','admin','2026-08-21 10:08:44','admin'),
(39,30,'BUTTON','删除',NULL,NULL,'pay:order:delete',NULL,0,'2026-08-21 10:09:00','admin','2026-08-21 10:09:00','admin');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`label`,`value`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,'系统管理员','admin','2026-08-13 08:18:00','admin','2026-08-20 15:20:09','admin'),
(2,'租户','tenant','2026-08-13 17:11:51','admin','2026-08-21 10:09:08','admin');

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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
(16,1,26,NULL,NULL,NULL,NULL),
(17,1,16,NULL,NULL,NULL,NULL),
(18,1,17,NULL,NULL,NULL,NULL),
(19,1,21,NULL,NULL,NULL,NULL),
(20,1,25,NULL,NULL,NULL,NULL),
(21,1,20,NULL,NULL,NULL,NULL),
(22,1,24,NULL,NULL,NULL,NULL),
(23,1,23,NULL,NULL,NULL,NULL),
(24,1,14,NULL,NULL,NULL,NULL),
(25,1,19,NULL,NULL,NULL,NULL),
(26,1,22,NULL,NULL,NULL,NULL),
(27,1,15,NULL,NULL,NULL,NULL),
(28,1,18,NULL,NULL,NULL,NULL),
(29,1,31,NULL,NULL,NULL,NULL),
(30,1,33,NULL,NULL,NULL,NULL),
(31,1,32,NULL,NULL,NULL,NULL),
(33,2,30,NULL,NULL,NULL,NULL),
(34,2,29,NULL,NULL,NULL,NULL),
(35,2,34,NULL,NULL,NULL,NULL),
(36,2,35,NULL,NULL,NULL,NULL),
(37,2,36,NULL,NULL,NULL,NULL),
(38,2,31,NULL,NULL,NULL,NULL),
(39,2,37,NULL,NULL,NULL,NULL),
(40,2,38,NULL,NULL,NULL,NULL);

/*Table structure for table `sys_user` */

CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`nickname`,`email`,`enabled`,`remark`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,'admin','$2a$10$bPk0MMz0DnwpEsX6u1OUs.NbuJ/5WFQEbp/tHcy..OT4uuHIgIJoO','admin','admin@123.com',1,'系统管理员','2026-08-13 07:34:40','admin','2026-08-20 09:52:22','admin'),
(2,'guest01','$2a$10$bPk0MMz0DnwpEsX6u1OUs.NbuJ/5WFQEbp/tHcy..OT4uuHIgIJoO','guest01','guest01@gmail.com',1,NULL,'2026-08-14 10:57:37','admin','2026-08-20 10:48:30','admin'),
(3,'xxmd3720@gmail.com','$2a$10$bPk0MMz0DnwpEsX6u1OUs.NbuJ/5WFQEbp/tHcy..OT4uuHIgIJoO','xxmd3720','xxmd3720@gmail.com',1,NULL,'2026-08-19 18:09:29','system','2026-08-20 09:52:03','admin');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`,`created_date`,`created_by`,`modified_date`,`modified_by`) values 
(1,1,1,NULL,NULL,NULL,NULL),
(2,2,2,NULL,NULL,NULL,NULL),
(3,3,2,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
