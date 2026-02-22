package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PackingListSnapshot {
    private Long id;
    private Long originalTripId;
    private Long sourceTemplateId;
    private String snapshotName;
    private LocalDateTime createdAt;
}