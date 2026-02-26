package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.PackingTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PackingTemplateMapper {
    int insert(PackingTemplate packingTemplate);//插入新的打包模板记录
    PackingTemplate selectById(Long id);//根据ID查询打包模板记录
    List<PackingTemplate> selectByUserId(Long userId);//根据用户ID查询打包模板记录
    List<PackingTemplate> selectByUserIdAndTemplateNameContaining(@Param("userId") Long userId, @Param("templateName") String templateName);//根据用户ID和模板名称模糊查询打包模板记录
    List<PackingTemplate> selectByUserIdOrderByTemplateNameAsc(Long userId);//根据用户ID和模板名称排序查询打包模板记录
    int existsByUserIdAndTemplateName(@Param("userId") Long userId, @Param("templateName") String templateName);//判断用户ID和模板名称是否存在
    List<PackingTemplate> selectAll();//查询所有打包模板记录
    int update(PackingTemplate packingTemplate);//更新打包模板记录
    int deleteById(Long id);//根据ID删除打包模板记录
}//可能暂时用不到

