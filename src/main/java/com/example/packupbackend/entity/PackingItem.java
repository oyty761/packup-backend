package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PackingItem {
    private Long id;
    private Long tripId;
    private String name;
    private Integer quantity;
    private String category;
    private String subCategory;
    private String notes;
    private Boolean isPacked = false;
    private String source;
    private Trip trip;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

