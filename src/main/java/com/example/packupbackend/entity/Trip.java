package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
/**
 * 行程实体类
 * 对应数据库表: trip
 *
 * 存储用户的旅行行程信息，包括行程基本信息、时间安排等。
 * 一个行程可以包含多个目的地和多种活动类型。
 *
 * 主要功能:
 * - 行程基本信息管理
 * - 时间范围计算（自动生成旅行天数）
 * - 与用户表多对一关联
 * - 与行程目的地、活动等子表一对多关联
 * - 作为打包物品的核心关联实体
 */
public class Trip {
    private Long id; // 行程唯一标识符，主键
    private Long userId; // 关联的用户ID，外键
    private String tripName; // 行程名称
    private LocalDate startDate; // 行程开始日期
    private LocalDate endDate; // 行程结束日期
    private Integer travelDays; // 旅行天数（数据库生成字段）
    private User user; // 关联的用户对象
    private LocalDateTime createdTime; // 创建时间
    private LocalDateTime updatedTime; // 更新时间

    // 关联的子实体列表（非数据库字段）
    private List<TripDestination> destinations; // 目的地列表
    private List<TripActivity> activities; // 行程活动列表
}


