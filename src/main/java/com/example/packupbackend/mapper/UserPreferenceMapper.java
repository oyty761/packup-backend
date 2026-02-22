package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.UserPreference;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPreferenceMapper {
    int insert(UserPreference preference);
    UserPreference selectByUserId(Long userId);
    int update(UserPreference preference);
    int deleteByUserId(Long userId);
    int existsByUserId(Long userId);
}