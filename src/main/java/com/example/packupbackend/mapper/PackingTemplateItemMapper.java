package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.PackingTemplateItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PackingTemplateItemMapper {
    int insert(PackingTemplateItem item);
    PackingTemplateItem selectById(Long id);
    List<PackingTemplateItem> selectByTemplateId(Long templateId);
    List<PackingTemplateItem> selectAll();
    int update(PackingTemplateItem item);
    int deleteById(Long id);
    int deleteByTemplateId(Long templateId);
    int countByTemplateId(Long templateId);
}