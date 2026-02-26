package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.*;
import com.example.packupbackend.mapper.*;
import com.example.packupbackend.service.PackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PackingServiceImpl implements PackingService {

    @Autowired
    private PackingItemMapper packingItemMapper;
    
    @Autowired
    private PackingTemplateMapper packingTemplateMapper;
    
    @Autowired
    private PackingTemplateItemMapper packingTemplateItemMapper;
    
    @Autowired
    private TripMapper tripMapper;

    // 基础打包物品管理
    @Override
    @Transactional
    public PackingItem createPackingItem(PackingItem packingItem) {
        packingItem.setCreatedTime(LocalDateTime.now());
        packingItem.setUpdatedTime(LocalDateTime.now());
        packingItemMapper.insert(packingItem);
        return packingItem;
    }

    @Override
    public Optional<PackingItem> getPackingItem(Long id) {
        PackingItem item = packingItemMapper.selectById(id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<PackingItem> getPackingItemsByTrip(Long tripId) {
        return packingItemMapper.selectByTripId(tripId);
    }

    @Override
    @Transactional
    public PackingItem updatePackingItem(Long id, PackingItem packingItem) {
        packingItem.setId(id);
        packingItem.setUpdatedTime(LocalDateTime.now());
        packingItemMapper.update(packingItem);
        return packingItem;
    }

    @Override
    @Transactional
    public void deletePackingItem(Long id) {
        packingItemMapper.deleteById(id);
    }

    // 模板相关功能
    @Override
    @Transactional
    public PackingTemplate saveAsTemplate(String name, String category, Long tripId) {
        // 创建模板
        PackingTemplate template = new PackingTemplate();
        template.setTemplateName(name);
        template.setDescription(category);
        User user = new User();
        user.setId(tripMapper.selectById(tripId).getUserId());
        template.setUser(user);
        
        packingTemplateMapper.insert(template);
        
        // 获取行程的所有物品并复制到模板
        List<PackingItem> items = packingItemMapper.selectByTripId(tripId);
        for (PackingItem item : items) {
            PackingTemplateItem templateItem = new PackingTemplateItem();
            templateItem.setTemplateId(template.getId());
            templateItem.setName(item.getName());
            templateItem.setDefaultQuantity(item.getQuantity());
            templateItem.setCategory(item.getCategory());
            templateItem.setSubCategory(item.getSubCategory());
            templateItem.setNotes(item.getNotes());
            templateItem.setOrderIndex(items.indexOf(item));
            
            packingTemplateItemMapper.insert(templateItem);
        }
        
        return template;
    }

    @Override
    @Transactional
    public List<PackingItem> applyTemplate(Long templateId, Long tripId) {
        List<PackingItem> createdItems = new ArrayList<>();
        
        // 获取模板物品
        List<PackingTemplateItem> templateItems = packingTemplateItemMapper.selectByTemplateId(templateId);
        Trip trip = tripMapper.selectById(tripId);
        
        // 为每个模板物品创建打包物品
        for (PackingTemplateItem templateItem : templateItems) {
            PackingItem packingItem = new PackingItem();
            packingItem.setTripId(tripId);
            packingItem.setName(templateItem.getName());
            packingItem.setQuantity(templateItem.getDefaultQuantity());
            packingItem.setCategory(templateItem.getCategory());
            packingItem.setSubCategory(templateItem.getSubCategory());
            packingItem.setNotes(templateItem.getNotes());
            packingItem.setSource("template");
            packingItem.setTrip(trip);
            packingItem.setCreatedTime(LocalDateTime.now());
            packingItem.setUpdatedTime(LocalDateTime.now());
            
            packingItemMapper.insert(packingItem);
            createdItems.add(packingItem);
        }
        
        return createdItems;
    }

    @Override
    public List<PackingTemplate> getAllTemplates() {
        return packingTemplateMapper.selectAll();
    }

    // 原有方法保持兼容
    @Override
    public List<PackingItem> generatePackingList(Trip trip, User user) {
        return List.of();
    }
}