package com.example.packupbackend.controller;

import com.example.packupbackend.common.ApiResponse;
import com.example.packupbackend.entity.Trip;
import com.example.packupbackend.service.TripService;
import com.example.packupbackend.service.WeatherBasedPackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private WeatherBasedPackingService weatherBasedPackingService;

    /**
     * 创建一个新的行程
     * @param request 包含行程信息的请求体
     * @return 创建的行程对象
     */
    @PostMapping
    public ApiResponse<Trip> createTrip(@Valid @RequestBody TripCreationRequest request) {
        // 1. 创建一个新的 Trip 实体对象
        Trip newTrip = new Trip();

        // 2. 从请求中获取数据并设置到新对象中
        newTrip.setUserId(request.getUserId());
        newTrip.setTripName(request.getTripName());
        newTrip.setStartDate(request.getStartDate());
        newTrip.setEndDate(request.getEndDate());
        newTrip.setDestinations(request.getDestinations());

        // 3. 调用 Service 层的方法，传入构建好的 Trip 对象
        Trip createdTrip = tripService.createTrip(newTrip);

        // 4. 返回成功的响应
        return ApiResponse.success("行程创建成功", createdTrip);
    }

    /**
     * 用于接收创建行程请求的数据传输对象 (DTO)
     */
    public static class TripCreationRequest {

        @NotNull(message = "用户ID不能为空")
        private Long userId;

        @NotEmpty(message = "行程名称不能为空")
        private String tripName;

        @NotEmpty(message = "目的地不能为空")
        private List<String> destinations;

        @NotNull(message = "出发日期不能为空")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate startDate;

        @NotNull(message = "返回日期不能为空")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate endDate;

        // --- Getters and Setters ---

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getTripName() {
            return tripName;
        }

        public void setTripName(String tripName) {
            this.tripName = tripName;
        }

        public List<String> getDestinations() {
            return destinations;
        }

        public void setDestinations(List<String> destinations) {
            this.destinations = destinations;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }
    }

    @PostMapping("/{tripId}/generate-weather-items")
    public ApiResponse<Void> generateWeatherItems(@PathVariable Long tripId) {
        Trip trip = tripService.getTripById(tripId);
        if (trip == null) {
            return ApiResponse.error("行程不存在");
        }
        weatherBasedPackingService.generateItemsFromWeather(trip);
        return ApiResponse.success(null);
    }
}

