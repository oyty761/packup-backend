package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.PackingItem;
import com.example.packupbackend.entity.Trip;
import com.example.packupbackend.entity.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.example.packupbackend.service.PackingListService;

@Service
public class PackingListServiceImpl implements PackingListService {

    @Override
    public List<PackingItem> generatePackingList(Trip trip, User user) {
        // 初始化空的物品列表
        List<PackingItem> items = new ArrayList<>();

        // 根据活动添加基础物品
        if (trip.getActivities() != null) {
            if (trip.getActivities().contains("hiking")) {
                PackingItem hikingPole = new PackingItem();
                hikingPole.setName("登山杖");
                hikingPole.setCategory("others");
                hikingPole.setQuantity(1);
                hikingPole.setNotes("徒步必备装备");
                hikingPole.setSource("system");
                hikingPole.setIsPacked(false);
                items.add(hikingPole);
            }

            // 可以继续添加其他活动的物品逻辑
            if (trip.getActivities().contains("swimming")) {
                PackingItem swimsuit = new PackingItem();
                swimsuit.setName("泳衣");
                swimsuit.setCategory("clothing");
                swimsuit.setQuantity(1);
                swimsuit.setNotes("游泳必备");
                swimsuit.setSource("system");
                swimsuit.setIsPacked(false);
                items.add(swimsuit);
            }
        }

        // 根据用户偏好调整
        if ("minimalist".equals(user.getStylePreference())) {
            for (PackingItem item : items) {
                if (item.getQuantity() != null && item.getQuantity() > 1) {
                    item.setQuantity(Math.max(1, item.getQuantity() / 2));
                }
            }
        }

        return items;
    }
}

