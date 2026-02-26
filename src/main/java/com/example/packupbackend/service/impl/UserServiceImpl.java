package com.example.packupbackend.service.impl;

import com.example.packupbackend.entity.User;
import com.example.packupbackend.entity.UserPreference;
import com.example.packupbackend.mapper.UserMapper;
import com.example.packupbackend.mapper.UserPreferenceMapper;
import com.example.packupbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserPreferenceMapper userPreferenceMapper;

    @Override
    @Transactional
    public User register(String username, String password) {
        // 检查用户名是否已存在
        if (userMapper.existsByUsername(username) > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptPassword(password));
        user.setStatus(1); // 正常状态
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());

        userMapper.insert(user);
        
        // 创建默认用户偏好
        UserPreference preference = new UserPreference();
        preference.setUserId(user.getId());
        preference.setTravelCompanions(1);
        preference.setColdSensitivity(3);
        preference.setHeatSensitivity(3);
        preference.setPackingStyle("comprehensive");
        preference.setUpdatedTime(LocalDateTime.now());
        
        userPreferenceMapper.insert(preference);
        
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!user.getPassword().equals(encryptPassword(password))) {
            throw new RuntimeException("密码错误");
        }

        // 更新最后登录时间
        userMapper.updateLastLogin(user.getId(), null);
        
        return user;
    }

    @Override
    @Transactional
    public User registerWithWeChat(String openId, String nickname, String avatarUrl) {
        // 检查微信用户是否已存在
        if (userMapper.existsByOpenId(openId) > 0) {
            throw new RuntimeException("该微信账号已注册");
        }

        // 创建新用户
        User user = new User();
        user.setOpenId(openId);
        user.setNickname(nickname);
        user.setAvatarUrl(avatarUrl);
        user.setStatus(1);
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());

        userMapper.insert(user);
        
        // 创建默认用户偏好
        UserPreference preference = new UserPreference();
        preference.setUserId(user.getId());
        preference.setTravelCompanions(1);
        preference.setColdSensitivity(3);
        preference.setHeatSensitivity(3);
        preference.setPackingStyle("comprehensive");
        preference.setUpdatedTime(LocalDateTime.now());
        
        userPreferenceMapper.insert(preference);
        
        return user;
    }

    @Override
    public User loginWithWeChat(String openId) {
        User user = userMapper.selectByOpenId(openId);
        if (user == null) {
            throw new RuntimeException("微信用户未注册");
        }

        // 更新最后登录时间
        userMapper.updateLastLogin(user.getId(), null);
        
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> getUsersByStatus(Integer status) {
        return userMapper.selectByStatus(status);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        user.setUpdatedTime(LocalDateTime.now());
        userMapper.update(user);
        return user;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public boolean userExistsByUsername(String username) {
        return userMapper.existsByUsername(username) > 0;
    }

    @Override
    public boolean userExistsByOpenId(String openId) {
        return userMapper.existsByOpenId(openId) > 0;
    }

    @Override
    public int getUserCount() {
        return userMapper.count();
    }

    // 密码加密方法
    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密失败", e);
        }
    }
}