package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.PackingTemplateItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PackingTemplateItemMapper {
    int insert(PackingTemplateItem item);// 插入新的模板物品记录
    PackingTemplateItem selectById(Long id);// 根据ID查询模板物品记录
    List<PackingTemplateItem> selectByTemplateId(Long templateId);// 根据模板ID查询模板物品记录
    List<PackingTemplateItem> selectAll();// 查询所有模板物品记录
    int update(PackingTemplateItem item);// 更新模板物品记录
    int deleteById(Long id);// 根据ID删除模板物品记录
    int deleteByTemplateId(Long templateId);// 根据模板ID删除模板物品记录
    int countByTemplateId(Long templateId);// 根据模板ID统计模板物品记录数
}//可能暂时用不到