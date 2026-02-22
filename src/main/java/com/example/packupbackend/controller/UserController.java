package com.example.packupbackend.controller;

import com.example.packupbackend.common.ApiResponse;
import com.example.packupbackend.entity.User;
import com.example.packupbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<User> register(@Valid @RequestBody UserRegistrationRequest request) {
        User user = userService.register(
            request.getUsername(), 
            request.getPassword(), 
            request.getGender(), 
            request.getAge()
        );
        return ApiResponse.success("注册成功", user);
    }

    @PostMapping("/login")
    public ApiResponse<User> login(@Valid @RequestBody UserLoginRequest request) {
        User user = userService.login(request.getUsername(), request.getPassword());
        return ApiResponse.success("登录成功", user);
    }

    @PostMapping("/wechat/register")
    public ApiResponse<User> registerWithWeChat(@Valid @RequestBody WeChatRegisterRequest request) {
        User user = userService.registerWithWeChat(
            request.getOpenId(), 
            request.getNickname(), 
            request.getAvatarUrl()
        );
        return ApiResponse.success("微信注册成功", user);
    }

    @PostMapping("/wechat/login")
    public ApiResponse<User> loginWithWeChat(@Valid @RequestBody WeChatLoginRequest request) {
        User user = userService.loginWithWeChat(request.getOpenId());
        return ApiResponse.success("微信登录成功", user);
    }

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

    // 请求体类
    public static class UserRegistrationRequest {
        private String username;
        private String password;
        private String gender;
        private Integer age;

        // getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
    }

    public static class UserLoginRequest {
        private String username;
        private String password;

        // getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class WeChatRegisterRequest {
        private String openId;
        private String nickname;
        private String avatarUrl;

        // getters and setters
        public String getOpenId() { return openId; }
        public void setOpenId(String openId) { this.openId = openId; }
        public String getNickname() { return nickname; }
        public void setNickname(String nickname) { this.nickname = nickname; }
        public String getAvatarUrl() { return avatarUrl; }
        public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    }

    public static class WeChatLoginRequest {
        private String openId;

        // getters and setters
        public String getOpenId() { return openId; }
        public void setOpenId(String openId) { this.openId = openId; }
    }
}