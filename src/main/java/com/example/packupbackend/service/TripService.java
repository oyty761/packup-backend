package com.example.packupbackend.service;

import com.example.packupbackend.entity.Trip;
import java.time.LocalDate;
import java.util.List;

public interface TripService {
    Trip createTrip(Trip trip);
    Trip getTripById(Long id);
    List<Trip> getTripsByUserId(Long userId);
    List<Trip> getTripsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    List<Trip> getAllTrips();
    Trip updateTrip(Trip trip);
    boolean deleteTrip(Long id);
    int getTripCountByUserId(Long userId);
}