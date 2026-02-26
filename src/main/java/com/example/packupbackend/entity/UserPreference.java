package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
/**
 * 用户偏好实体类
 * 对应数据库表: user_preference
 * 
 * 存储用户的个人偏好设置，用于个性化打包建议。
 * 包括用户的生理特征、旅行习惯和健康状况等信息。
 * 
 * 主要功能:
 * - 用户基本属性记录（年龄、性别等）
 * - 环境适应性评估（怕冷/怕热程度）
 * - 旅行偏好设置（精简/完整打包风格）
 * - 健康状况记录
 * * 与用户表一对一关联
 */
public class UserPreference {
    private Long userId; // 关联的用户ID，主键
    private Integer age; // 用户年龄
    private String gender; // 用户性别
    private Integer travelCompanions; // 出行人数，默认1人
    private Integer coldSensitivity; // 怕冷程度 1-5级
    private Integer heatSensitivity; // 怕热程度 1-5级
    private String healthIssues; // 健康问题，多个用逗号分隔
    private String packingStyle; // 打包风格（精简/完整）
    private LocalDateTime updatedTime; // 更新时间
}