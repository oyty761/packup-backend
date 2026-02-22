package com.example.packupbackend.entity;

import lombok.Data;

@Data
public class TripActivity {
    private Long id;
    private Long tripId;
    private String activityCategory;
    private String activityDetail;
    private String poiId;
}