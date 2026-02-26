package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.TripDestination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TripDestinationMapper {
    int insert(TripDestination destination);//插入行程目的地
    TripDestination selectById(Long id);//根据id查询行程目的地
    List<TripDestination> selectByTripId(Long tripId);//根据行程id查询行程目的地
    List<TripDestination> findByTripId(@Param("tripId") Long tripId);//根据行程id查询行程目的地
    List<TripDestination> selectAll();//查询所有行程目的地
    int update(TripDestination destination);//更新行程目的地
    int deleteById(Long id);//根据id删除行程目的地
    int deleteByTripId(Long tripId);//根据行程id删除行程目的地
    int countByTripId(Long tripId);//根据行程id统计行程目的地数量
}//行程目的地创建，修改，删除，查找
