package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.CrowdSourceData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CrowdSourceDataMapper {
    int insert(CrowdSourceData data);//数据插入
    CrowdSourceData selectById(Long id);//根据ID查询记录
    List<CrowdSourceData> selectByTripId(Long tripId);//根据行程ID查询行程信息
    List<CrowdSourceData> selectByKeyword(String keyword);//关键词模糊搜索
    List<CrowdSourceData> selectTopItemsByTripId(Long tripId, int limit);//根据行程ID查询TopN条数据（估计用不上）
    List<CrowdSourceData> selectAll();//查询所有数据
    int update(CrowdSourceData data);//更新现有数据
    int deleteById(Long id);//据主键ID删除单条记录
    int deleteByTripId(Long tripId);//级联删除指定行程相关的所有数据
    int countByTripId(Long tripId);//统计行程数据量
}