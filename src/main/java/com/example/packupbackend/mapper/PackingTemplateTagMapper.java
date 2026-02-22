package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.PackingTemplateTag;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PackingTemplateTagMapper {
    int insert(PackingTemplateTag tag);
    List<PackingTemplateTag> selectByTemplateId(Long templateId);
    List<String> selectTagsByTemplateId(Long templateId);
    List<PackingTemplateTag> selectAll();
    int deleteByTemplateId(Long templateId);
    int deleteByTemplateIdAndTag(Long templateId, String tag);
}