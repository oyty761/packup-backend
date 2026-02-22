package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.TripDestination;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TripDestinationMapper {
    int insert(TripDestination destination);
    TripDestination selectById(Long id);
    List<TripDestination> selectByTripId(Long tripId);
    List<TripDestination> selectAll();
    int update(TripDestination destination);
    int deleteById(Long id);
    int deleteByTripId(Long tripId);
    int countByTripId(Long tripId);
}