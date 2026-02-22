package com.example.packupbackend.entity;

import lombok.Data;

@Data
public class PackingItem {
    private Long id;
    private String name;
    private String category;
    private Integer quantity;
    private String notes;
    private Boolean isPacked = false;
    private String source;
    private Trip trip;
}
