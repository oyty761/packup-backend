package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.UserPreference;
import com.example.packupbackend.mapper.UserPreferenceMapper;
import com.example.packupbackend.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Autowired
    private UserPreferenceMapper userPreferenceMapper;

    @Override
    @Transactional
    public UserPreference createUserPreference(UserPreference preference) {
        preference.setUpdatedTime(LocalDateTime.now());
        userPreferenceMapper.insert(preference);
        return preference;
    }

    @Override
    public UserPreference getUserPreferenceByUserId(Long userId) {
        return userPreferenceMapper.selectByUserId(userId);
    }

    @Override
    @Transactional
    public UserPreference updateUserPreference(UserPreference preference) {
        preference.setUpdatedTime(LocalDateTime.now());
        userPreferenceMapper.update(preference);
        return preference;
    }

    @Override
    @Transactional
    public boolean deleteUserPreference(Long userId) {
        return userPreferenceMapper.deleteByUserId(userId) > 0;
    }

    @Override
    public boolean userPreferenceExists(Long userId) {
        return userPreferenceMapper.existsByUserId(userId) > 0;
    }
}