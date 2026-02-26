package com.example.packupbackend.entity;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
/**
 * 用户实体类
 * 对应数据库表: user
 * 
 * 存储用户基本信息，支持微信小程序登录和传统用户名密码登录两种方式。
 * 用户可以通过微信授权获取基本信息，也可以选择传统的注册登录方式。
 * 
 * 主要功能:
 * - 微信第三方登录支持
 * - 用户状态管理
 * - 登录记录追踪
 * - 与用户偏好表一对一关联
 */
public class User {
    private Long id; // 用户唯一标识符，主键
    
    // 微信相关字段
    private String openId; // 微信小程序用户唯一标识
    private String unionId; // 微信开放平台唯一标识（同一用户多平台）
    private String sessionKey; // 微信会话密钥，用于解密用户信息
    
    // 用户基本信息
    private String nickname; // 微信昵称
    private String avatarUrl; // 微信头像URL
    private String phone; // 手机号（需用户授权）
    
    // 原用户名密码字段
    private String username; // 用户名（可选，传统登录方式）
    private String password; // 密码（可选，传统登录方式）
    
    // 状态字段
    private Integer status; // 用户状态：1-正常，0-禁用
    private LocalDateTime lastLoginTime; // 最后登录时间
    private String lastLoginIp; // 最后登录IP地址
    
    // 时间字段
    private LocalDateTime createdTime; // 创建时间
    private LocalDateTime updatedTime; // 更新时间
}

