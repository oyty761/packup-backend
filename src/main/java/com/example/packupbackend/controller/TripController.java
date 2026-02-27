package com.example.packupbackend.controller;

import com.example.packupbackend.common.ApiResponse;
import com.example.packupbackend.entity.Trip;
import com.example.packupbackend.mapper.TripMapper;
import com.example.packupbackend.service.DeepSeekPackingService;
import com.example.packupbackend.service.TripService;
import com.example.packupbackend.service.WeatherBasedPackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import com.example.packupbackend.dto.trip.TripCreateDTO;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
@Slf4j
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private DeepSeekPackingService deepSeekPackingService;

    @Autowired
    private TripMapper tripMapper;

    @Autowired
    private WeatherBasedPackingService weatherBasedPackingService;

    /**
     * 创建一个新的行程
     * @param request 包含行程信息的请求体
     * @return 创建的行程对象
     */
    @PostMapping
    public ApiResponse<Trip> createTrip(@Valid @RequestBody TripCreateDTO request) {
        log.info("创建行程请求: userId={}, tripName={}", request.getUserId(), request.getTripName());

        try {
            // 创建 Trip 实体对象
            Trip newTrip = new Trip();
            newTrip.setUserId(request.getUserId());
            newTrip.setTripName(request.getTripName());
            newTrip.setStartDate(request.getStartDate());
            newTrip.setEndDate(request.getEndDate());
            newTrip.setDestinations(request.getDestinations());

            // 调用服务层创建行程
            Trip createdTrip = tripService.createTrip(newTrip);

            log.info("行程创建成功，ID: {}", createdTrip.getId());
            return ApiResponse.success("行程创建成功", createdTrip);

        } catch (Exception e) {
            log.error("创建行程失败", e);
            return ApiResponse.error("创建行程失败: " + e.getMessage());
        }
    }

    /**
     * 查询所有行程
     */
    @GetMapping
    public ApiResponse<List<Trip>> getAllTrips() {
        List<Trip> trips = tripMapper.selectAll();
        return ApiResponse.success("查询成功", trips);
    }

    /**
     * 根据ID查询单个行程
     */
    @GetMapping("/{tripId}")
    public ApiResponse<Trip> getTripById(@PathVariable Long tripId) {
        Trip trip = tripMapper.selectById(tripId);
        if (trip == null) {
            return ApiResponse.error("行程不存在");
        }
        return ApiResponse.success("查询成功", trip);
    }

    /**
     * 根据用户ID查询行程
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Trip>> getTripsByUserId(@PathVariable Long userId) {
        List<Trip> trips = tripMapper.selectByUserId(userId);
        return ApiResponse.success("查询成功", trips);
    }

    /**
     * 更新行程
     */
    @PutMapping("/{tripId}")
    public ApiResponse<Trip> updateTrip(@PathVariable Long tripId, @Valid @RequestBody TripCreateDTO request) {
        Trip existingTrip = tripMapper.selectById(tripId);
        if (existingTrip == null) {
            return ApiResponse.error("行程不存在");
        }

        existingTrip.setTripName(request.getTripName());
        existingTrip.setStartDate(request.getStartDate());
        existingTrip.setEndDate(request.getEndDate());
        existingTrip.setDestinations(request.getDestinations());

        Trip updatedTrip = tripService.updateTrip(existingTrip);
        return ApiResponse.success("行程更新成功", updatedTrip);
    }

    /**
     * 删除行程
     */
    @DeleteMapping("/{tripId}")
    public ApiResponse<Void> deleteTrip(@PathVariable Long tripId) {
        boolean result = tripService.deleteTrip(tripId);
        if (result) {
            return ApiResponse.success("行程删除成功");
        } else {
            return ApiResponse.error("行程删除失败");
        }
    }

    /**
     * 基于天气生成打包物品建议
     */
    @PostMapping("/{tripId}/generate-weather-items")
    public ApiResponse<Void> generateWeatherItems(@PathVariable Long tripId) {
        Trip trip = tripService.getTripById(tripId);
        if (trip == null) {
            return ApiResponse.error("行程不存在");
        }

        weatherBasedPackingService.generateItemsFromWeather(trip);
        return ApiResponse.success(null);
    }

    /**
     * 基于AI生成打包物品建议
     */
    @PostMapping("/{tripId}/generate-ai-items")
    public ApiResponse<Void> generateAiItems(@PathVariable Long tripId) {
        Trip trip = tripMapper.selectById(tripId);
        if (trip == null) {
            return ApiResponse.error("行程不存在");
        }

        deepSeekPackingService.generateItemsForTrip(trip);
        return ApiResponse.success(null);
    }
}

