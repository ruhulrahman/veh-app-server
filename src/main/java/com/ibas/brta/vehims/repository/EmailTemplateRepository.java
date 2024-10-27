package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.configurations.EmailTemplate;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    EmailTemplate findByTemplateName(String templateName);

    List<EmailTemplate> findByIsActiveTrueOrderByTemplateNameAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM EmailTemplate s WHERE (:templateName IS NULL OR LOWER(s.templateName) LIKE LOWER(CONCAT('%', :templateName, '%')) OR LOWER(s.subjectEn) LIKE LOWER(CONCAT('%', :templateName, '%')) OR LOWER(s.subjectBn) LIKE LOWER(CONCAT('%', :templateName, '%'))) AND (:serviceId IS NULL OR s.serviceId = :serviceId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<EmailTemplate> findListWithPaginationBySearch(
            @Param("templateName") String templateName,
            @Param("serviceId") Long serviceId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}