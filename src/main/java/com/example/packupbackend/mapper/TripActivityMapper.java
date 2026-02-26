package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.TripActivity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TripActivityMapper {
    int insert(TripActivity activity);//插入行程内容
    TripActivity selectById(Long id);//根据ID查询行程内容
    List<TripActivity> selectByTripId(Long tripId);//根据行程ID查询所有行程内容
    List<TripActivity> selectAll();//查询所有行程内容
    int update(TripActivity activity);//更新行程内容
    int deleteById(Long id);//根据ID删除行程内容
    int deleteByTripId(Long tripId);//根据行程ID删除所有行程内容
    int countByTripId(Long tripId);//根据行程ID统计行程内容数量
}//行程内容相关，暂时还没有用上