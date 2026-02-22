package com.example.packupbackend.entity;

import lombok.Data;
import java.util.List;

@Data
public class PackingTemplate {
    private Long id;
    private String templateName;
    private String description;
    private List<String> tags;
    private String itemsJson;
    private User user;
}

