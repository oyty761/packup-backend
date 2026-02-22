package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    int insert(User user);
    User selectById(Long id);
    User selectByUsername(String username);
    List<User> selectAll();
    int update(User user);
    int deleteById(Long id);
    int existsByUsername(String username);
}
