package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.PackingListSnapshot;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
//可能暂时用不上
@Mapper
public interface PackingListSnapshotMapper {
    int insert(PackingListSnapshot snapshot);//插入新的打包清单快照记录
    PackingListSnapshot selectById(Long id);//根据ID查询打包清单快照记录
    List<PackingListSnapshot> selectByOriginalTripId(Long originalTripId);//根据原始行程ID查询打包清单快照记录
    List<PackingListSnapshot> selectBySourceTemplateId(Long sourceTemplateId);//根据源模板ID查询打包清单快照记录
    List<PackingListSnapshot> selectAll();//查询所有打包清单快照记录
    int update(PackingListSnapshot snapshot);//更新打包清单快照记录
    int deleteById(Long id);//根据ID删除打包清单快照记录
    int count();//获取打包清单快照记录总数
}