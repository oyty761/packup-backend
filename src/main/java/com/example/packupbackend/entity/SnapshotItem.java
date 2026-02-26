package com.example.packupbackend.entity;

import lombok.Data;

@Data
public class SnapshotItem {
    private Long snapshotId;//快照ID
    private String itemName;//物品名称
    private Integer quantity;//物品数量
    private String category;//物品分类
    private Boolean isChecked;//是否已经打包
    private String notes;//物品备注
    private Integer orderIndex;//显示顺序
}