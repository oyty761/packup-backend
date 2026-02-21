package com.example.packupbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "packing_item")
@Data
public class PackingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String category;

    @Column
    private Integer quantity;

    @Column(length = 500)
    private String notes;

    @Column(name = "is_packed", nullable = false)
    private Boolean isPacked = false;

    @Column
    private String source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}
