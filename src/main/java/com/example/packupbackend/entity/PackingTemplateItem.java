package com.example.packupbackend.entity;

import lombok.Data;

@Data
public class PackingTemplateItem {
    private Long id;
    private Long templateId;
    private String name;
    private Integer defaultQuantity;
    private String category;
    private String subCategory;
    private String notes;
    private Integer orderIndex;
}