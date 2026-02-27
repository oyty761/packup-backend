package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.*;
import com.example.packupbackend.mapper.*;
import com.example.packupbackend.service.DeepSeekPackingService;
import com.example.packupbackend.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripServiceImpl implements TripService {

    private final TripMapper tripMapper;
    private final TripDestinationMapper tripDestinationMapper;
    private final DeepSeekPackingService deepSeekPackingService;

    @Override
    @Transactional
    public void createTrip(Trip trip, List<TripDestination> destinations) {
        // 设置创建时间和更新时间
        trip.setCreatedTime(LocalDateTime.now());
        trip.setUpdatedTime(LocalDateTime.now());

        tripMapper.insert(trip);
        for (TripDestination dest : destinations) {
            dest.setTripId(trip.getId());
            tripDestinationMapper.insert(dest);
        }
        // 异步调用 AI 生成物品
        CompletableFuture.runAsync(() -> {
            try {
                deepSeekPackingService.generateItemsForTrip(trip);
            } catch (Exception e) {
                log.error("AI 生成物品失败", e);
            }
        });
    }

    @Override
    public Trip createTrip(Trip trip) {
        // 设置创建时间和更新时间
        trip.setCreatedTime(LocalDateTime.now());
        trip.setUpdatedTime(LocalDateTime.now());

        // 插入行程
        tripMapper.insert(trip);

        log.info("创建行程成功，ID: {}", trip.getId());
        return trip;
    }

    @Override
    public Trip getTripById(Long id) {
        if (id == null) {
            return null;
        }
        return tripMapper.selectById(id);
    }

    @Override
    public List<Trip> getTripsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<Trip> getTripsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<Trip> getAllTrips() {
        return List.of();
    }

    @Override
    @Transactional
    public Trip updateTrip(Trip trip) {
        try {
            log.debug("开始更新行程，ID: {}, 名称: {}", trip.getId(), trip.getTripName());

            // 设置更新时间
            trip.setUpdatedTime(LocalDateTime.now());
            log.debug("设置更新时间为: {}", trip.getUpdatedTime());

            // 调用Mapper更新数据库
            log.debug("准备执行SQL更新");
            int result = tripMapper.update(trip);
            log.debug("SQL执行结果，影响行数: {}", result);

            if (result > 0) {
                log.info("行程更新成功，ID: {}", trip.getId());
                return trip;
            } else {
                log.warn("行程更新失败，ID: {}", trip.getId());
                return null;
            }
        } catch (Exception e) {
            log.error("更新行程时发生异常，ID: {}", trip.getId(), e);
            throw new RuntimeException("更新行程失败: " + e.getMessage(), e);
        }
    }


    @Override
    @Transactional
    public boolean deleteTrip(Long id) {
        if (id == null) {
            return false;
        }

        int result = tripMapper.deleteById(id);

        if (result > 0) {
            log.info("行程删除成功，ID: {}", id);
            return true;
        } else {
            log.warn("行程删除失败，ID: {}", id);
            return false;
        }
    }

    @Override
    public int getTripCountByUserId(Long userId) {
        return 0;
    }
}


