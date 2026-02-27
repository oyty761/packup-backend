package com.example.packupbackend.dto.trip;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.packupbackend.entity.TripDestination;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

public class TripCreateDTO {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotEmpty(message = "行程名称不能为空")
    private String tripName;

    @NotEmpty(message = "目的地不能为空")
    private List<TripDestination> destinations;

    @NotNull(message = "出发日期不能为空")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "返回日期不能为空")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    // getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTripName() { return tripName; }
    public void setTripName(String tripName) { this.tripName = tripName; }

    public List<TripDestination> getDestinations() { return destinations; }
    public void setDestinations(List<TripDestination> destinations) { this.destinations = destinations; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}


