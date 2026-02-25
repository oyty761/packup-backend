package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.*;
import com.example.packupbackend.mapper.*;
import com.example.packupbackend.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {


    @Override
    public Trip createTrip(Trip trip) {
        return null;
    }

    @Override
    public Trip getTripById(Long id) {
        return null;
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
    public Trip updateTrip(Trip trip) {
        return null;
    }

    @Override
    public boolean deleteTrip(Long id) {
        return false;
    }

    @Override
    public int getTripCountByUserId(Long userId) {
        return 0;
    }
}
