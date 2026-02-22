package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.SharedList;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SharedListMapper {
    int insert(SharedList sharedList);
    SharedList selectById(Long id);
    SharedList selectByShareCode(String shareCode);
    List<SharedList> selectByOwnerUserId(Long ownerUserId);
    List<SharedList> selectByRecipientUserId(Long recipientUserId);
    List<SharedList> selectBySnapshotId(Long snapshotId);
    List<SharedList> selectExpiredLists(LocalDateTime currentTime);
    List<SharedList> selectAll();
    int update(SharedList sharedList);
    int updateAccessedAt(Long id, LocalDateTime accessedAt);
    int deleteById(Long id);
    int deleteByShareCode(String shareCode);
    int count();
    int countActiveByOwnerUserId(Long ownerUserId);
}