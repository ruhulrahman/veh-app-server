package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.configurations.VehicleMaker;

@Repository
public interface VehicleMakerRepository extends JpaRepository<VehicleMaker, Long> {
    VehicleMaker findByNameEn(String nameEn);

    List<VehicleMaker> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM VehicleMaker s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:countryId IS NULL OR s.countryId = :countryId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<VehicleMaker> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("countryId") Long countryId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}