package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.PackingItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PackingItemMapper {
    int insert(PackingItem packingItem);
    PackingItem selectById(Long id);
    List<PackingItem> selectByTripId(Long tripId);
    List<PackingItem> selectByTripIdAndIsPacked(@Param("tripId") Long tripId, @Param("isPacked") Boolean isPacked);
    List<PackingItem> selectByTripIdAndCategory(@Param("tripId") Long tripId, @Param("category") String category);
    int countByTripIdAndIsPacked(@Param("tripId") Long tripId, @Param("isPacked") Boolean isPacked);
    List<PackingItem> selectAll();
    int update(PackingItem packingItem);
    int deleteById(Long id);
    int deleteByTripId(Long tripId);
    PackingItem findByTripIdAndNameAndSource(@Param("tripId") Long tripId,
                                              @Param("name") String name,
                                              @Param("source") String source);
}

