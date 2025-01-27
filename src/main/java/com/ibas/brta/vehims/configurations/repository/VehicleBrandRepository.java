package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.VehicleBrand;

@Repository
public interface VehicleBrandRepository extends JpaRepository<VehicleBrand, Long> {
    VehicleBrand findByNameEn(String nameEn);

    List<VehicleBrand> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM VehicleBrand s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:makerId IS NULL OR s.makerId = :makerId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<VehicleBrand> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("makerId") Long makerId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    boolean existsByNameEn(String nameEn);

    boolean existsByNameBn(String nameBn);

    boolean existsByNameEnAndIdNot(String nameEn, Long id);

    boolean existsByNameBnAndIdNot(String nameBn, Long id);
}