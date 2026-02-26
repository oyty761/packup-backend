package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.Trip;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TripMapper {
    int insert(Trip trip);//插入行程
    Trip selectById(Long id);//根据id查询行程
    List<Trip> selectByUserId(Long userId);//根据用户ID查询行程信息
    List<Trip> selectByUserIdOrderByCreatedTimeDesc(Long userId);//根据用户ID查询行程信息，按照创建时间倒序排列
    List<Trip> selectAll();//查询所有行程信息
    int update(Trip trip);//更新行程信息
    int deleteById(Long id);//根据id删除行程
}//行程信息的创建，修改，删除，查找

