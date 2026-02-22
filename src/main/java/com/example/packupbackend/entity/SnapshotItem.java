package com.example.packupbackend.entity;

import lombok.Data;

@Data
public class SnapshotItem {
    private Long snapshotId;
    private String itemName;
    private Integer quantity;
    private String category;
    private Boolean isChecked;
    private String notes;
    private Integer orderIndex;
}