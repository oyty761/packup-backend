package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.Trip;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TripMapper {
    int insert(Trip trip);
    Trip selectById(Long id);
    List<Trip> selectByUserId(Long userId);
    List<Trip> selectByUserIdOrderByCreatedTimeDesc(Long userId);
    List<Trip> selectAll();
    int update(Trip trip);
    int deleteById(Long id);
}

