
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` char(11) DEFAULT NULL,
  `password` char(64) NOT NULL COMMENT 'SHA256处理后密码',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `latest_login` datetime NOT NULL COMMENT '最近登陆时间',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`),
  KEY `idx_email` (`email`),
  KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` int NOT NULL COMMENT '品类：用于按目录进行商品展示、检索',
  `name` varchar(45) NOT NULL COMMENT '商品名，品牌等信息',
  `style` varchar(45) DEFAULT NULL COMMENT '款式，型号，颜色等信息',
  `price` decimal(10,2) unsigned NOT NULL COMMENT '单价',
  `stock` int unsigned NOT NULL COMMENT '库存',
  `create_at` datetime NOT NULL,
  `latest_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_category_update` (`category`,`latest_update` DESC) COMMENT '按最近更新时间查询某个品类'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `count` int unsigned NOT NULL,
  `amount` decimal(10,2) unsigned NOT NULL COMMENT '总金额',
  `shipping` varchar(200) DEFAULT NULL COMMENT '收货信息',
  `status` int unsigned NOT NULL COMMENT '订单状态：未支付、已支付、配送中、已收货...',
  `created_at` datetime NOT NULL COMMENT '订单创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_time` (`user_id`,`created_at` DESC) COMMENT '按用户最近时间查询',
  KEY `idx_product_time` (`product_id`,`created_at` DESC) COMMENT '按商品最近时间查询'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;






