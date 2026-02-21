package com.example.packupbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "packing_template")
@Data
public class PackingTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_name", nullable = false)
    private String templateName;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "packing_template_tags", joinColumns = @JoinColumn(name = "template_id"))
    @Column(name = "tag")
    private List<String> tags;

    @Column(name = "items_json", length = 5000)
    private String itemsJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
