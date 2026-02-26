package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.PackingItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PackingItemMapper {
    int insert(PackingItem packingItem);//插入打包物品
    PackingItem selectById(Long id);//根据ID查询打包物品
    List<PackingItem> selectByTripId(Long tripId);//根据行程ID查询打包物品
    List<PackingItem> selectByTripIdAndIsPacked(@Param("tripId") Long tripId, @Param("isPacked") Boolean isPacked);//根据行程ID和是否打包查询打包物品（暂时没有用）
    List<PackingItem> selectByTripIdAndCategory(@Param("tripId") Long tripId, @Param("category") String category);//根据行程ID和物品分类查询打包物品
    int countByTripIdAndIsPacked(@Param("tripId") Long tripId, @Param("isPacked") Boolean isPacked);//根据行程ID和是否打包查询打包物品数量
    List<PackingItem> selectAll();//查询所有打包物品
    int update(PackingItem packingItem);//更新打包物品
    int deleteById(Long id);//根据ID删除打包物品
    int deleteByTripId(Long tripId);//根据行程ID删除打包物品
    PackingItem findByTripIdAndNameAndSource(@Param("tripId") Long tripId,//查询指定行程ID、物品名称和来源的打包物品
                                              @Param("name") String name,
                                              @Param("source") String source);
}

