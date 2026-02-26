package com.example.packupbackend.service;
import com.example.packupbackend.entity.PackingItem;
import com.example.packupbackend.entity.PackingTemplate;
import com.example.packupbackend.entity.Trip;
import com.example.packupbackend.entity.User;
import java.util.List;
import java.util.Optional;

public interface PackingService {
    // 基础打包物品管理
    PackingItem createPackingItem(PackingItem packingItem);
    Optional<PackingItem> getPackingItem(Long id);
    List<PackingItem> getPackingItemsByTrip(Long tripId);
    PackingItem updatePackingItem(Long id, PackingItem packingItem);
    void deletePackingItem(Long id);
    
    // 模板相关功能
    PackingTemplate saveAsTemplate(String name, String category, Long tripId);
    List<PackingItem> applyTemplate(Long templateId, Long tripId);
    List<PackingTemplate> getAllTemplates();
    
    // 原有方法保持兼容
    List<PackingItem> generatePackingList(Trip trip, User user);
}