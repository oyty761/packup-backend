package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Trip {
    private Long id;
    private Long userId;
    private String tripName;
    private List<String> destinations;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer travelDays;
    private List<String> activities;
    private User user;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

