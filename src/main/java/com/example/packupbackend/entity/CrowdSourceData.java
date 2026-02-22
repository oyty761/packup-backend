package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CrowdSourceData {
    private Long id;
    private Long tripId;
    private String keyword;
    private String sourcePlatform;
    private String extractedItem;
    private String poiRelation;
    private Integer mentionCount;
    private LocalDateTime createdTime;
}