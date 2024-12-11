package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.ExamCenter;

@Repository
public interface ExamCenterRepository extends JpaRepository<ExamCenter, Long> {

    List<ExamCenter> findByIsActiveTrueOrderByCreatedAtAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM ExamCenter s WHERE (:orgId IS NULL OR s.orgId = :orgId) AND (:thanaId IS NULL OR s.thanaId = :thanaId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<ExamCenter> findListWithPaginationBySearch(
            @Param("orgId") Long orgId,
            @Param("thanaId") Long thanaId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}