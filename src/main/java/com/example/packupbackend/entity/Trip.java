package com.example.packupbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trip")
@Data
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trip_name", nullable = false)
    private String tripName;

    @ElementCollection
    @CollectionTable(name = "trip_destinations", joinColumns = @JoinColumn(name = "trip_id"))
    @Column(name = "destination")
    private List<String> destinations;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "travel_days")
    private Integer travelDays;

    @ElementCollection
    @CollectionTable(name = "trip_activities", joinColumns = @JoinColumn(name = "trip_id"))
    @Column(name = "activity")
    private List<String> activities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;
}
