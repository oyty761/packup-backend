package com.example.packupbackend.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String gender;
    private Integer age;
    private Integer isHeatSensitive;
    private Integer isColdSensitive;
    private String stylePreference;
    private String healthNotes;
}

