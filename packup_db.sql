-- 创建数据库
DROP DATABASE IF EXISTS packup_db;
CREATE DATABASE IF NOT EXISTS packup_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE packup_db;

-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
    -- 微信相关字段
                        `open_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '微信小程序用户唯一标识',
                        `union_id` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信开放平台唯一标识（同一用户多平台）',
                        `session_key` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信会话密钥',

    -- 用户基本信息
                        `nickname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信昵称',
                        `avatar_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信头像URL',
                        `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号（需用户授权）',

    -- 原用户名密码字段（可选，如果只做微信登录可以去掉）
                        `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,

    -- 状态字段
                        `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
                        `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
                        `last_login_ip` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最后登录IP',

    -- 时间字段
                        `created_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                        `updated_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),

                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UK_open_id` (`open_id`),  -- open_id必须唯一
                        UNIQUE KEY `UK_username` (`username`),  -- 如果使用用户名的话
                        KEY `idx_nickname` (`nickname`),
                        KEY `idx_phone` (`phone`),
                        KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 用户偏好表
DROP TABLE IF EXISTS `user_preference`;
CREATE TABLE `user_preference` (
                                   `user_id` bigint NOT NULL,
                                   `age` int DEFAULT NULL,
                                   `gender` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                   `travel_companions` int DEFAULT '1' COMMENT '出行人数',
                                   `cold_sensitivity` int DEFAULT NULL COMMENT '怕冷程度 1-5级',
                                   `heat_sensitivity` int DEFAULT NULL COMMENT '怕热程度 1-5级',
                                   `health_issues` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '健康问题，多个用逗号分隔',
                                   `packing_style` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '精简/完整',
                                   `updated_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                                   PRIMARY KEY (`user_id`),
                                   CONSTRAINT `FK_user_preference_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 行程表
DROP TABLE IF EXISTS `trip`;
CREATE TABLE `trip` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `user_id` bigint NOT NULL,
                        `trip_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `start_date` date NOT NULL,
                        `end_date` date NOT NULL,
                        `travel_days` int GENERATED ALWAYS AS (DATEDIFF(end_date, start_date) + 1) STORED,
                        `created_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                        `updated_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                        PRIMARY KEY (`id`),
                        KEY `FK_trip_user` (`user_id`),
                        CONSTRAINT `FK_trip_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 行程目的地表（支持多城市）
DROP TABLE IF EXISTS `trip_destination`;
CREATE TABLE `trip_destination` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `trip_id` bigint NOT NULL,
                                    `city_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                    `country` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                    `poi_name` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '景点/场所名称',
                                    `arrival_date` date NOT NULL,
                                    `departure_date` date NOT NULL,
                                    `order_index` int DEFAULT '0' COMMENT '行程顺序',
                                    PRIMARY KEY (`id`),
                                    KEY `FK_trip_destination_trip` (`trip_id`),
                                    CONSTRAINT `FK_trip_destination_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 行程活动表
DROP TABLE IF EXISTS `trip_activity`;
CREATE TABLE `trip_activity` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `trip_id` bigint NOT NULL,
                                 `activity_category` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '观光类/户外类/商务类/休闲类',
                                 `activity_detail` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '具体活动，如：徒步、滑雪',
                                 `poi_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的景点/场所ID',
                                 PRIMARY KEY (`id`),
                                 KEY `FK_trip_activity_trip` (`trip_id`),
                                 CONSTRAINT `FK_trip_activity_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 打包物品表
DROP TABLE IF EXISTS `packing_item`;
CREATE TABLE `packing_item` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `trip_id` bigint NOT NULL,
                                `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                `quantity` int DEFAULT '1',
                                `category` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '衣物鞋包/洗漱护肤/电子设备/药品健康/重要文件/其他物品',
                                `sub_category` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '子分类，如：上衣、裤子',
                                `source` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '系统推荐/模板导入/手动添加',
                                `notes` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                `is_packed` bit(1) NOT NULL DEFAULT b'0',
                                `created_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                `updated_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                                PRIMARY KEY (`id`),
                                KEY `FK_packing_item_trip` (`trip_id`),
                                KEY `idx_category` (`category`),
                                CONSTRAINT `FK_packing_item_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 天气预报表
DROP TABLE IF EXISTS `weather_forecast`;
CREATE TABLE `weather_forecast` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `trip_id` bigint NOT NULL,
                                    `forecast_date` date NOT NULL,
                                    `city` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                    `min_temp` float DEFAULT NULL,
                                    `max_temp` float DEFAULT NULL,
                                    `precipitation` float DEFAULT NULL COMMENT '降水量(mm)',
                                    `humidity` int DEFAULT NULL COMMENT '湿度百分比',
                                    `uv_index` int DEFAULT NULL,
                                    `weather_desc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                    `fetch_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`id`),
                                    KEY `FK_weather_trip` (`trip_id`),
                                    KEY `idx_forecast_date` (`forecast_date`),
                                    CONSTRAINT `FK_weather_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 外部攻略数据表
DROP TABLE IF EXISTS `crowd_source_data`;
CREATE TABLE `crowd_source_data` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `trip_id` bigint NOT NULL,
                                     `keyword` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                     `source_platform` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '小红书/抖音等',
                                     `extracted_item` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '提取的物品名称',
                                     `poi_relation` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的景点/场所',
                                     `mention_count` int DEFAULT '1' COMMENT '提及次数',
                                     `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`id`),
                                     KEY `FK_crowd_trip` (`trip_id`),
                                     KEY `idx_keyword` (`keyword`),
                                     CONSTRAINT `FK_crowd_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 打包模板表
DROP TABLE IF EXISTS `packing_template`;
CREATE TABLE `packing_template` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `user_id` bigint NOT NULL,
                                    `template_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                    `description` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                    `visibility` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT 'private' COMMENT 'private/public',
                                    `created_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                    `updated_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                                    PRIMARY KEY (`id`),
                                    KEY `FK_template_user` (`user_id`),
                                    CONSTRAINT `FK_template_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 模板物品明细表
DROP TABLE IF EXISTS `packing_template_item`;
CREATE TABLE `packing_template_item` (
                                         `id` bigint NOT NULL AUTO_INCREMENT,
                                         `template_id` bigint NOT NULL,
                                         `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                         `default_quantity` int DEFAULT '1',
                                         `category` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
                                         `sub_category` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                         `notes` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                         `order_index` int DEFAULT '0',
                                         PRIMARY KEY (`id`),
                                         KEY `FK_template_item_template` (`template_id`),
                                         CONSTRAINT `FK_template_item_template` FOREIGN KEY (`template_id`) REFERENCES `packing_template` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 模板标签表
DROP TABLE IF EXISTS `packing_template_tag`;
CREATE TABLE `packing_template_tag` (
                                        `template_id` bigint NOT NULL,
                                        `tag` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
                                        PRIMARY KEY (`template_id`, `tag`),
                                        CONSTRAINT `FK_template_tag_template` FOREIGN KEY (`template_id`) REFERENCES `packing_template` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 清单快照表（用于分享）
DROP TABLE IF EXISTS `packing_list_snapshot`;
CREATE TABLE `packing_list_snapshot` (
                                         `id` bigint NOT NULL AUTO_INCREMENT,
                                         `original_trip_id` bigint DEFAULT NULL COMMENT '原始行程ID，可为空（如果是从模板分享）',
                                         `source_template_id` bigint DEFAULT NULL COMMENT '来源模板ID',
                                         `snapshot_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                         `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                                         PRIMARY KEY (`id`),
                                         KEY `FK_snapshot_trip` (`original_trip_id`),
                                         KEY `FK_snapshot_template` (`source_template_id`),
                                         CONSTRAINT `FK_snapshot_trip` FOREIGN KEY (`original_trip_id`) REFERENCES `trip` (`id`) ON DELETE SET NULL,
                                         CONSTRAINT `FK_snapshot_template` FOREIGN KEY (`source_template_id`) REFERENCES `packing_template` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 快照物品表
DROP TABLE IF EXISTS `snapshot_item`;
CREATE TABLE `snapshot_item` (
                                 `snapshot_id` bigint NOT NULL,
                                 `item_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `quantity` int DEFAULT '1',
                                 `category` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `is_checked` bit(1) DEFAULT b'0' COMMENT '接收方是否已打包',
                                 `notes` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 `order_index` int DEFAULT '0',
                                 PRIMARY KEY (`snapshot_id`, `item_name`),
                                 CONSTRAINT `FK_snapshot_item_snapshot` FOREIGN KEY (`snapshot_id`) REFERENCES `packing_list_snapshot` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 分享记录表
DROP TABLE IF EXISTS `shared_list`;
CREATE TABLE `shared_list` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `share_code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '唯一分享码',
                               `snapshot_id` bigint NOT NULL,
                               `owner_user_id` bigint NOT NULL COMMENT '分享者',
                               `recipient_user_id` bigint DEFAULT NULL COMMENT '接收者（如果是指定用户分享）',
                               `share_channel` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '链接/二维码/微信等',
                               `permission` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT 'view' COMMENT 'view/edit',
                               `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
                               `shared_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                               `accessed_at` datetime DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `UK_share_code` (`share_code`),
                               KEY `FK_shared_snapshot` (`snapshot_id`),
                               KEY `FK_shared_owner` (`owner_user_id`),
                               KEY `FK_shared_recipient` (`recipient_user_id`),
                               CONSTRAINT `FK_shared_snapshot` FOREIGN KEY (`snapshot_id`) REFERENCES `packing_list_snapshot` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `FK_shared_owner` FOREIGN KEY (`owner_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `FK_shared_recipient` FOREIGN KEY (`recipient_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建索引以提高查询性能
CREATE INDEX idx_trip_user_dates ON trip(user_id, start_date, end_date);
CREATE INDEX idx_packing_item_trip_category ON packing_item(trip_id, category);
CREATE INDEX idx_shared_list_code_status ON shared_list(share_code, expire_time);
CREATE INDEX idx_crowd_source_item ON crowd_source_data(extracted_item, mention_count);