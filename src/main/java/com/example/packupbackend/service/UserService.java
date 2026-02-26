package com.example.packupbackend.service;

import com.example.packupbackend.entity.User;
import java.util.List;

public interface UserService {
    User register(String username, String password);
    User login(String username, String password);
    User registerWithWeChat(String openId, String nickname, String avatarUrl);
    User loginWithWeChat(String openId);
    User getUserById(Long id);
    User getUserByOpenId(String openId);
    List<User> getAllUsers();
    List<User> getUsersByStatus(Integer status);
    User updateUser(User user);
    boolean deleteUser(Long id);
    boolean userExistsByUsername(String username);
    boolean userExistsByOpenId(String openId);
    int getUserCount();
}
