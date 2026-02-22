package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.PackingTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PackingTemplateMapper {
    int insert(PackingTemplate packingTemplate);
    PackingTemplate selectById(Long id);
    List<PackingTemplate> selectByUserId(Long userId);
    List<PackingTemplate> selectByUserIdAndTemplateNameContaining(@Param("userId") Long userId, @Param("templateName") String templateName);
    List<PackingTemplate> selectByUserIdOrderByTemplateNameAsc(Long userId);
    int existsByUserIdAndTemplateName(@Param("userId") Long userId, @Param("templateName") String templateName);
    List<PackingTemplate> selectAll();
    int update(PackingTemplate packingTemplate);
    int deleteById(Long id);
}

