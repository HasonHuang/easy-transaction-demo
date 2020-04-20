# 创建订单库
-- drop database if exists `easytrans-order`;
create database `easytrans-order` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
# 创建账户库
-- drop database if exists `easytrans-account`;
create database `easytrans-account` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;


# 创建订单表
CREATE TABLE `easytrans-order`.`order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_number` varchar(36) NOT NULL COMMENT '订单号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 创建账户表
CREATE TABLE `easytrans-account`.`account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '账户余额',
  `freeze_amount` decimal(10,2) DEFAULT '0.00' COMMENT '冻结金额',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_userid` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

# 创建积分表
CREATE TABLE `easytrans-account`.`integral` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `amount` bigint(20) unsigned DEFAULT NULL COMMENT '积分余额',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
  UNIQUE KEY `uk_userid` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;