package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.NotificationTemplate;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    NotificationTemplate findByTitleEn(String titleEn);

    NotificationTemplate findByTitleBn(String titleBn);

    List<NotificationTemplate> findByIsActiveTrueOrderByTitleEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM NotificationTemplate s WHERE (:titleEn IS NULL OR LOWER(s.titleEn) LIKE LOWER(CONCAT('%', :titleEn, '%')) OR LOWER(s.titleBn) LIKE LOWER(CONCAT('%', :titleEn, '%'))) AND (:serviceId IS NULL OR s.serviceId = :serviceId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<NotificationTemplate> findListWithPaginationBySearch(
            @Param("titleEn") String titleEn,
            @Param("serviceId") Long serviceId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}