package com.example.packupbackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    @NotBlank(message = "昵称不能为空")
    @Size(max = 50, message = "昵称长度不能超过50个字符")
    private String nickname;

    @Size(max = 200, message = "头像URL长度不能超过200个字符")
    private String avatarUrl;

    @Size(max = 20, message = "手机号长度不能超过20个字符")
    private String phone;

    // 可以根据业务需求添加其他允许更新的字段
}

