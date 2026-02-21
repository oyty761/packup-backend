package com.example.packupbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String gender;

    @Column
    private Integer age;

    @Column(name = "is_heat_sensitive")
    private Integer isHeatSensitive;

    @Column(name = "is_cold_sensitive")
    private Integer isColdSensitive;

    @Column(name = "style_preference")
    private String stylePreference;

    @Column(name = "health_notes", length = 1000)
    private String healthNotes;
}
