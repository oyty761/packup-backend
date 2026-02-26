package com.example.packupbackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {//接收和传输用户注册时的基本信息
    private String username;
    private String password;
}
