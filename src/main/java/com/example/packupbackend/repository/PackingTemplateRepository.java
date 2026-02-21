package com.example.packupbackend.repository;

import com.example.packupbackend.entity.PackingTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PackingTemplateRepository extends JpaRepository<PackingTemplate, Long> {
    List<PackingTemplate> findByUserId(Long userId);
    List<PackingTemplate> findByUserIdAndTemplateNameContaining(Long userId, String templateName);
    List<PackingTemplate> findByUserIdOrderByTemplateNameAsc(Long userId);
    boolean existsByUserIdAndTemplateName(Long userId, String templateName);
}
