package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.CrowdSourceData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CrowdSourceDataMapper {
    int insert(CrowdSourceData data);
    CrowdSourceData selectById(Long id);
    List<CrowdSourceData> selectByTripId(Long tripId);
    List<CrowdSourceData> selectByKeyword(String keyword);
    List<CrowdSourceData> selectTopItemsByTripId(Long tripId, int limit);
    List<CrowdSourceData> selectAll();
    int update(CrowdSourceData data);
    int deleteById(Long id);
    int deleteByTripId(Long tripId);
    int countByTripId(Long tripId);
}