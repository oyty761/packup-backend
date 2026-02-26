package com.example.packupbackend.mapper;

import com.example.packupbackend.entity.PackingTemplateTag;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PackingTemplateTagMapper {
    int insert(PackingTemplateTag tag);//插入模板标签
    List<PackingTemplateTag> selectByTemplateId(Long templateId);//根据模板id查询标签
    List<String> selectTagsByTemplateId(Long templateId);//根据模板id查询标签
    List<PackingTemplateTag> selectAll();//查询所有标签
    int deleteByTemplateId(Long templateId);//根据模板id删除标签
    int deleteByTemplateIdAndTag(Long templateId, String tag);//根据模板id和标签删除标签
}//可能暂时用不到