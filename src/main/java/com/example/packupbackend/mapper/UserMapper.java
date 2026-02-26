package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    int insert(User user);
    User selectById(Long id);
    User selectByOpenId(String openId);
    User selectByUsername(String username);
    List<User> selectAll();
    List<User> selectByStatus(Integer status);
    int update(User user);
    int updateLastLogin(@Param("id") Long id, @Param("ip") String ip);
    int deleteById(Long id);
    int existsByUsername(String username);
    int existsByOpenId(String openId);
    int count();
}//用户的创建，修改，删除，查找
