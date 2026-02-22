package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    
    // 微信相关字段
    private String openId;
    private String unionId;
    private String sessionKey;
    
    // 用户基本信息
    private String nickname;
    private String avatarUrl;
    private String phone;
    
    // 原用户名密码字段
    private String username;
    private String password;
    
    // 状态字段
    private Integer status;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
    
    // 时间字段
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}

