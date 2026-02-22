package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SharedList {
    private Long id;
    private String shareCode;
    private Long snapshotId;
    private Long ownerUserId;
    private Long recipientUserId;
    private String shareChannel;
    private String permission;
    private LocalDateTime expireTime;
    private LocalDateTime sharedAt;
    private LocalDateTime accessedAt;
}