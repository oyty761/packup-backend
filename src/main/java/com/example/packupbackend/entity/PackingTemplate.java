package com.example.packupbackend.entity;

import lombok.Data;
import java.util.List;

@Data
/**
 * 打包模板实体类
 * 对应数据库表: packing_template
 * 
 * 存储可复用的打包清单模板，用户可以基于历史行程或他人分享创建模板。
 * 支持私有和公开两种可见性设置。
 * 
 * 主要功能:
 * - 模板基本信息管理（名称、描述、标签）
 * - 可见性控制（私有/公开）
 * - 与用户表多对一关联
 * - 与模板物品明细表一对多关联
 * - 支持模板的分享和复用
 */
public class PackingTemplate {
    private Long id; // 模板唯一标识符，主键
    private String templateName; // 模板名称
    private String description; // 模板描述
    private List<String> tags; // 模板标签列表
    private String itemsJson; // 物品JSON数据
    private User user; // 关联的用户对象
}

