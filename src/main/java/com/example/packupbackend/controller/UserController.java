package com.example.packupbackend.controller;

import com.example.packupbackend.common.ApiResponse;
import com.example.packupbackend.dto.user.UserLoginDTO;
import com.example.packupbackend.dto.user.UserRegisterDTO;
import com.example.packupbackend.dto.user.UserUpdateDTO;
import com.example.packupbackend.entity.User;
import com.example.packupbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //用户注册
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
//用户登录
    @PostMapping("/login")
    public ApiResponse<User> login(@Valid @RequestBody UserLoginDTO request) {
        try {
            User user = userService.login(request.getUsername(), request.getPassword());
            return ApiResponse.success("登录成功", user);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
/*//微信登陆相关（暂时写不到）
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
//根据ID获取用户信息
    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) {//似乎不需要专门的DTO文件，因为用户信息已经封装在User类中
        User user = userService.getUserById(id);
        if (user == null) {
            return ApiResponse.error("用户不存在");
        }
        return ApiResponse.success(user);
    }
//获取用户列表
    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ApiResponse.success(users);
    }
//根据状态查找用户
    @GetMapping("/status/{status}")
    public ApiResponse<List<User>> getUsersByStatus(@PathVariable Integer status) {
        List<User> users = userService.getUsersByStatus(status);
        return ApiResponse.success(users);
    }
//更新，修改用户信息
@PutMapping("/{id}")
public ApiResponse<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO request) {
    // 创建用户对象并设置允许更新的字段
    User user = new User();
    user.setId(id);
    user.setNickname(request.getNickname());
    user.setAvatarUrl(request.getAvatarUrl());
    user.setPhone(request.getPhone());

    User updatedUser = userService.updateUser(user);
    return ApiResponse.success("用户信息更新成功", updatedUser);
}
//删除用户
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        boolean result = userService.deleteUser(id);
        if (result) {
            return ApiResponse.success("用户删除成功");
        } else {
            return ApiResponse.error("用户删除失败");
        }
    }
//检查用户名是否已存在（我昨天晚上做到这，为上面需要DTO的功能创建了DTO,但还没有为任何功能创建接口文档）
    @GetMapping("/check/username")
    public ApiResponse<Boolean> checkUsernameExists(@RequestParam String username) {
        boolean exists = userService.userExistsByUsername(username);
        return ApiResponse.success(exists);
    }
//检查微信OpenId
    @GetMapping("/check/openid")
    public ApiResponse<Boolean> checkOpenIdExists(@RequestParam String openId) {
        boolean exists = userService.userExistsByOpenId(openId);
        return ApiResponse.success(exists);
    }
//统计用户总数
    @GetMapping("/count")
    public ApiResponse<Integer> getUserCount() {
        int count = userService.getUserCount();
        return ApiResponse.success(count);
    }


}
