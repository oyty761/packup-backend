package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.SnapshotItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SnapshotItemMapper {
    int insert(SnapshotItem item);
    List<SnapshotItem> selectBySnapshotId(Long snapshotId);
    List<SnapshotItem> selectAll();
    int update(SnapshotItem item);
    int updateCheckedStatus(Long snapshotId, String itemName, Boolean isChecked);
    int deleteBySnapshotId(Long snapshotId);
    int deleteBySnapshotIdAndItemName(Long snapshotId, String itemName);
    int countBySnapshotId(Long snapshotId);
}//物品快照功能，暂时用不到