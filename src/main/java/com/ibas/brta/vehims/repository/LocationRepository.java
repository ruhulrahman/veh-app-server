package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.configurations.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByNameEn(String nameEn);
    
    Location findByParentId(Long parentId);

    List<Location> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM Location s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:locationTypeId IS NULL OR s.locationTypeId = :locationTypeId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<Location> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("locationTypeId") Long locationTypeId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}