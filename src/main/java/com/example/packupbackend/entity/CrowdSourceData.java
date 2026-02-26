package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
/**
 * 外部攻略数据实体类（暂时用不上）
 * 对应数据库表: crowd_source_data
 * 
 * 存储从社交媒体平台（如小红书、抖音）抓取的旅行攻略数据。
 * 通过关键词提取有用的物品推荐信息，丰富系统的物品库。
 * 
 * 主要功能:
 * - 社交媒体数据采集
 * - 关键词和物品提取
 * - 提及频次统计
 * - 与行程表多对一关联
 * - 为智能推荐提供数据支撑
 */
public class CrowdSourceData {
    private Long id; // 数据唯一标识符，主键
    private Long tripId; // 关联的行程ID，外键
    private String keyword; // 搜索关键词
    private String sourcePlatform; // 来源平台（小红书/抖音等）
    private String extractedItem; // 提取的物品名称
    private String poiRelation; // 关联的景点/场所
    private Integer mentionCount; // 提及次数，默认1
    private LocalDateTime createdTime; // 创建时间
}