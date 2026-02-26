package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PackingListSnapshot {
    private Long id;//主键ID
    private Long originalTripId;//原始行程ID
    private Long sourceTemplateId;//源模板ID
    private String snapshotName;//快照名称？
    private LocalDateTime createdAt;//创建时间
}