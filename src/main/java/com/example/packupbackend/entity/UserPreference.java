package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserPreference {
    private Long userId;
    private Integer age;
    private String gender;
    private Integer travelCompanions;
    private Integer coldSensitivity;
    private Integer heatSensitivity;
    private String healthIssues;
    private String packingStyle;
    private LocalDateTime updatedTime;
}