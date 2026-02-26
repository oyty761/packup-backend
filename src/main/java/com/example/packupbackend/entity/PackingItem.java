package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
/**
 * 打包物品实体类
 * 对应数据库表: packing_item
 * 
 * 存储行程中需要携带的物品信息，支持分类管理和打包状态跟踪。
 * 物品可以来自系统推荐、模板导入或用户手动添加。
 * 
 * 主要功能:
 * - 物品基本信息管理（名称、数量、分类等）
 * - 打包状态跟踪（是否已打包）
 * - 多种来源标识（系统/模板/手动）
 * - 与行程表多对一关联
 * - 支持详细的物品备注信息
 */
public class PackingItem {
    private Long id; // 打包物品唯一标识符，主键
    private Long tripId; // 关联的行程ID，外键
    private String name; // 物品名称
    private Integer quantity; // 物品数量
    private String category; // 物品分类（衣物鞋包/洗漱护肤/电子设备等）
    private String subCategory; // 物品子分类
    private String notes; // 物品备注信息
    private Boolean isPacked = false; // 是否已打包状态
    private String source; // 物品来源（系统推荐/模板导入/手动添加）
    private Trip trip; // 关联的行程对象
    private LocalDateTime createdTime; // 创建时间
    private LocalDateTime updatedTime; // 更新时间
}

