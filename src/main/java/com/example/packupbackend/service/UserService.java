package com.example.packupbackend.service;

import com.example.packupbackend.entity.User;

public interface UserService {
    User register(String username, String password, String gender, Integer age);
    User login(String username, String password);
}
