package com.example.packupbackend.entity;

import lombok.Data;

@Data
/**
 * 行程活动实体类
 * 对应数据库表: trip_activity
 * 
 * 存储行程中的具体活动信息，支持不同类型的活动分类。
 * 
 * 主要功能:
 * - 活动分类管理
 * - 具体活动详情记录
 * - 与行程表多对一关联
 */
public class TripActivity {
    private Long id; // 活动唯一标识符，主键
    private Long tripId; // 关联的行程ID，外键
    private String activityCategory; // 活动分类（观光类/户外类/商务类/休闲类）
    private String activityDetail; // 具体活动详情
    private String poiId; // 关联的景点/场所ID
}