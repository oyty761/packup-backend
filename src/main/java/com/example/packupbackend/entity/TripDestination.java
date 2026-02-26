package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
/**
 * 行程目的地实体类
 * 对应数据库表: trip_destination
 * 
 * 存储行程中的具体目的地信息，支持多城市行程规划。
 * 每个目的地包含具体的到达离开时间和顺序安排。
 * 
 * 主要功能:
 * - 目的地基本信息管理（城市、国家、景点）
 * - 时间安排（到达/离开日期）
 * - 行程顺序控制
 * - 与行程表多对一关联
 * - 为天气获取和智能推荐提供地理信息
 */
public class TripDestination {
    private Long id; // 目的地唯一标识符，主键
    private Long tripId; // 关联的行程ID，外键
    private String cityName; // 城市名称
    private String country; // 国家名称
    private String poiName; // 景点/场所名称
    private LocalDate arrivalDate; // 到达日期
    private LocalDate departureDate; // 离开日期
    private Integer orderIndex; // 行程顺序
}