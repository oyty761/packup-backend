package com.example.packupbackend.controller;

import com.example.packupbackend.common.ApiResponse;
import com.example.packupbackend.entity.UserPreference;
import com.example.packupbackend.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user-preferences")
@CrossOrigin(origins = "*")
public class UserPreferenceController {

    @Autowired
    private UserPreferenceService userPreferenceService;
//创建用户偏好
    @PostMapping
    public ApiResponse<UserPreference> createUserPreference(@Valid @RequestBody UserPreference preference) {
        UserPreference createdPreference = userPreferenceService.createUserPreference(preference);
        return ApiResponse.success("用户偏好创建成功", createdPreference);
    }
//根据ID获取用户偏好
    @GetMapping("/{userId}")
    public ApiResponse<UserPreference> getUserPreference(@PathVariable Long userId) {
        UserPreference preference = userPreferenceService.getUserPreferenceByUserId(userId);
        if (preference == null) {
            return ApiResponse.error("用户偏好不存在");
        }
        return ApiResponse.success(preference);
    }
//修改更新用户偏好
    @PutMapping("/{userId}")
    public ApiResponse<UserPreference> updateUserPreference(@PathVariable Long userId, 
                                                           @Valid @RequestBody UserPreference preference) {
        preference.setUserId(userId);
        UserPreference updatedPreference = userPreferenceService.updateUserPreference(preference);
        return ApiResponse.success("用户偏好更新成功", updatedPreference);
    }
//删除用户偏好
    @DeleteMapping("/{userId}")
    public ApiResponse<Void> deleteUserPreference(@PathVariable Long userId) {
        boolean result = userPreferenceService.deleteUserPreference(userId);
        if (result) {
            return ApiResponse.success("用户偏好删除成功");
        } else {
            return ApiResponse.error("用户偏好删除失败");
        }
    }
//检查用户偏好是否存在
    @GetMapping("/{userId}/exists")
    public ApiResponse<Boolean> checkUserPreferenceExists(@PathVariable Long userId) {
        boolean exists = userPreferenceService.userPreferenceExists(userId);
        return ApiResponse.success(exists);
    }
}