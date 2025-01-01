package com.ibas.brta.vehims.configurations.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.ElementData;
import com.ibas.brta.vehims.configurations.model.Location;

@Repository
public interface ElementDataRepository extends JpaRepository<ElementData, Long> {

    ElementData findByNameEn(String nameEn);

    // ElementData findByParentId(Long parentId);

    // List<ElementData> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    // @Query("SELECT s FROM ElementData s WHERE (:nameEn IS NULL OR LOWER(s.nameEn)
    // LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE
    // LOWER(CONCAT('%', :nameEn, '%'))) AND (:locationTypeId IS NULL OR
    // s.locationTypeId = :locationTypeId) AND (:isActive IS NULL OR s.isActive =
    // :isActive) ORDER BY s.createdAt DESC")
    // Page<ElementData> findListWithPaginationBySearch(
    // @Param("nameEn") String nameEn,
    // @Param("locationTypeId") Long locationTypeId,
    // @Param("isActive") Boolean isActive,
    // Pageable pageable);

    // @Query("SELECT s.parentId FROM ElementData s WHERE s.id IN :locationIds")
    // Optional<Long> findFirstParentIdByLocationIds(@Param("locationIds")
    // List<Long> locationIds);
}