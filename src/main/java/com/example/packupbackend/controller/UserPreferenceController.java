package com.example.packupbackend.controller;

import com.example.packupbackend.common.ApiResponse;
import com.example.packupbackend.dto.userpreference.UserPreferenceCreateDTO;
import com.example.packupbackend.entity.UserPreference;
import com.example.packupbackend.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import com.example.packupbackend.dto.userpreference.UserPreferenceUpdateDTO;
@RestController
@RequestMapping("/api/user-preferences")
@CrossOrigin(origins = "*")
public class UserPreferenceController {

    @Autowired
    private UserPreferenceService userPreferenceService;
    //创建用户偏好（因为在用户注册时会自动创建默认偏好，所以此处只在偏好不存在时才允许创建）
    @PostMapping
    public ApiResponse<UserPreference> createUserPreference(@Valid @RequestBody UserPreferenceCreateDTO preferenceDTO) {
        // 检查用户偏好是否已存在
        UserPreference existingPreference = userPreferenceService.getUserPreferenceByUserId(preferenceDTO.getUserId());
        if (existingPreference != null) {
            return ApiResponse.error("用户偏好已存在，如需修改请使用更新接口");
        }

        UserPreference preference = new UserPreference();
        preference.setUserId(preferenceDTO.getUserId());
        preference.setAge(preferenceDTO.getAge());
        preference.setGender(preferenceDTO.getGender());
        preference.setTravelCompanions(preferenceDTO.getTravelCompanions());
        preference.setColdSensitivity(preferenceDTO.getColdSensitivity());
        preference.setHeatSensitivity(preferenceDTO.getHeatSensitivity());
        preference.setHealthIssues(preferenceDTO.getHealthIssues());
        preference.setPackingStyle(preferenceDTO.getPackingStyle());

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
                                                        @Valid @RequestBody UserPreferenceUpdateDTO preferenceDTO) {
    UserPreference preference = userPreferenceService.getUserPreferenceByUserId(userId);
    if (preference == null) {
        return ApiResponse.error("用户偏好不存在");
    }

    // 只更新非空字段
    if (preferenceDTO.getAge() != null) {
        preference.setAge(preferenceDTO.getAge());
    }
    if (preferenceDTO.getGender() != null) {
        preference.setGender(preferenceDTO.getGender());
    }
    if (preferenceDTO.getTravelCompanions() != null) {
        preference.setTravelCompanions(preferenceDTO.getTravelCompanions());
    }
    if (preferenceDTO.getColdSensitivity() != null) {
        preference.setColdSensitivity(preferenceDTO.getColdSensitivity());
    }
    if (preferenceDTO.getHeatSensitivity() != null) {
        preference.setHeatSensitivity(preferenceDTO.getHeatSensitivity());
    }
    if (preferenceDTO.getHealthIssues() != null) {
        preference.setHealthIssues(preferenceDTO.getHealthIssues());
    }
    if (preferenceDTO.getPackingStyle() != null) {
        preference.setPackingStyle(preferenceDTO.getPackingStyle());
    }

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