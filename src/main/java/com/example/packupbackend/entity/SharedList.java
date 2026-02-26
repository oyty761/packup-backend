package com.example.packupbackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
/**
 * 分享记录实体类
 * 对应数据库表: shared_list
 * 
 * 存储打包清单的分享信息，支持通过分享码或指定用户的方式分享。
 * 可以设置查看或编辑权限，以及分享有效期。
 * 
 * 主要功能:
 * - 分享码生成和管理
 * - 权限控制（查看/编辑）
 * - 有效期设置
 * - 分享渠道记录
 * * 与快照表多对一关联
 * - 访问记录追踪
 */
public class SharedList {
    private Long id; // 分享记录唯一标识符，主键
    private String shareCode; // 唯一分享码
    private Long snapshotId; // 关联的快照ID，外键
    private Long ownerUserId; // 分享者用户ID
    private Long recipientUserId; // 接收者用户ID（可为空）
    private String shareChannel; // 分享渠道（链接/二维码/微信等）
    private String permission; // 权限设置（view/edit）
    private LocalDateTime expireTime; // 过期时间
    private LocalDateTime sharedAt; // 分享时间
    private LocalDateTime accessedAt; // 最后访问时间
}