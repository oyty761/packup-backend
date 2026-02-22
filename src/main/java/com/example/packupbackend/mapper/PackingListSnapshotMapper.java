package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.PackingListSnapshot;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PackingListSnapshotMapper {
    int insert(PackingListSnapshot snapshot);
    PackingListSnapshot selectById(Long id);
    List<PackingListSnapshot> selectByOriginalTripId(Long originalTripId);
    List<PackingListSnapshot> selectBySourceTemplateId(Long sourceTemplateId);
    List<PackingListSnapshot> selectAll();
    int update(PackingListSnapshot snapshot);
    int deleteById(Long id);
    int count();
}