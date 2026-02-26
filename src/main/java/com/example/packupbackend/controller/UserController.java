package com.example.packupbackend.controller;

import com.example.packupbackend.common.ApiResponse;
import com.example.packupbackend.dto.UserRegisterDTO;
import com.example.packupbackend.entity.User;
import com.example.packupbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //注册
    @PostMapping("/register")
    public ApiResponse<User> register(@Valid @RequestBody UserRegisterDTO request) {
        try {
            User user = userService.register(
                request.getUsername(), 
                request.getPassword()
            );
            return ApiResponse.success("注册成功", user);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ApiResponse<User> login(@Valid @RequestBody UserRegisterDTO request) {
        try {
            User user = userService.login(request.getUsername(), request.getPassword());
            return ApiResponse.success("登录成功", user);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
/*
    @PostMapping("/wechat/register")
    public ApiResponse<User> registerWithWeChat(@Valid @RequestBody WeChatRegisterRequest request) {
        try {
            User user = userService.registerWithWeChat(
                request.getOpenId(),
                request.getNickname(),
                request.getAvatarUrl()
            );
            return ApiResponse.success("微信注册成功", user);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/wechat/login")
    public ApiResponse<User> loginWithWeChat(@Valid @RequestBody WeChatLoginRequest request) {
        try {
            User user = userService.loginWithWeChat(request.getOpenId());
            return ApiResponse.success("微信登录成功", user);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }*/

    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ApiResponse.error("用户不存在");
        }
        return ApiResponse.success(user);
    }

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ApiResponse.success(users);
    }

    @GetMapping("/status/{status}")
    public ApiResponse<List<User>> getUsersByStatus(@PathVariable Integer status) {
        List<User> users = userService.getUsersByStatus(status);
        return ApiResponse.success(users);
    }

    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ApiResponse.success("用户信息更新成功", updatedUser);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        boolean result = userService.deleteUser(id);
        if (result) {
            return ApiResponse.success("用户删除成功");
        } else {
            return ApiResponse.error("用户删除失败");
        }
    }

    @GetMapping("/check/username")
    public ApiResponse<Boolean> checkUsernameExists(@RequestParam String username) {
        boolean exists = userService.userExistsByUsername(username);
        return ApiResponse.success(exists);
    }

    @GetMapping("/check/openid")
    public ApiResponse<Boolean> checkOpenIdExists(@RequestParam String openId) {
        boolean exists = userService.userExistsByOpenId(openId);
        return ApiResponse.success(exists);
    }

    @GetMapping("/count")
    public ApiResponse<Integer> getUserCount() {
        int count = userService.getUserCount();
        return ApiResponse.success(count);
    }


}
