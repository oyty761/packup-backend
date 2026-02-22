package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.TripActivity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TripActivityMapper {
    int insert(TripActivity activity);
    TripActivity selectById(Long id);
    List<TripActivity> selectByTripId(Long tripId);
    List<TripActivity> selectAll();
    int update(TripActivity activity);
    int deleteById(Long id);
    int deleteByTripId(Long tripId);
    int countByTripId(Long tripId);
}