package com.ibas.brta.vehims.repository;

import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.StatusGroup;

@Repository
public interface StatusGroupRepository extends JpaRepository<StatusGroup, Long> {
        StatusGroup findByStatusGroupCode(String statusGroupCode);

        boolean existsByStatusGroupCode(String statusGroupCode);

        boolean existsByStatusGroupCodeAndIdNot(String statusGroupCode, Long id);

        boolean existsByStatusGroupCodeAndIdNotAndIsActive(String statusGroupCode, Long id, boolean isActive);

        boolean existsByStatusGroupCodeAndIsActive(String statusGroupCode, boolean isActive);

        boolean existsByStatusGroupCodeAndIdNotAndIsActiveAndNameEn(String statusGroupCode, Long id, boolean isActive,
                        String nameEn);

        List<StatusGroup> findByIsActive(Boolean isActive);

        void deleteByStatusGroupCode(String statusGroupCode);

        List<StatusGroup> findAllByOrderByNameEnAsc();

        List<StatusGroup> findByIsActiveTrueOrderByNameEnAsc();

        // Complex query with JPQL and named parameters
        @Query("SELECT s FROM StatusGroup s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.statusGroupCode) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
        Page<StatusGroup> findStatusGroupBySearch(
                        @Param("nameEn") String nameEn,
                        @Param("isActive") Boolean isActive,
                        Pageable pageable);

        @Query("SELECT sg FROM StatusGroup sg WHERE sg.isActive = true ORDER BY sg.nameEn ASC")
        List<StatusGroup> getActiveStatusGroups();
}
