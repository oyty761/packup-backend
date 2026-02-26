package com.example.packupbackend.entity;

import lombok.Data;

@Data
/**
 * 模板物品明细实体类
 * 对应数据库表: packing_template_item
 * 
 * 存储打包模板中的具体物品信息。
 * 
 * 主要功能:
 * - 模板物品基本信息管理
 * - 默认数量设置
 * - 物品排序控制
 * - 与模板表多对一关联
 */
public class PackingTemplateItem {
    private Long id; // 模板物品唯一标识符，主键
    private Long templateId; // 关联的模板ID，外键
    private String name; // 物品名称
    private Integer defaultQuantity; // 默认数量
    private String category; // 物品分类
    private String subCategory; // 物品子分类
    private String notes; // 物品备注
    private Integer orderIndex; // 显示顺序
}