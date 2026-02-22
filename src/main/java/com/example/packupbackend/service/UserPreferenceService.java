package com.example.packupbackend.service;

import com.example.packupbackend.entity.UserPreference;

public interface UserPreferenceService {
    UserPreference createUserPreference(UserPreference preference);
    UserPreference getUserPreferenceByUserId(Long userId);
    UserPreference updateUserPreference(UserPreference preference);
    boolean deleteUserPreference(Long userId);
    boolean userPreferenceExists(Long userId);
}