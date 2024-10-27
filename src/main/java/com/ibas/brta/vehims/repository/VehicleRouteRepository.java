package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.configurations.VehicleRoute;

@Repository
public interface VehicleRouteRepository extends JpaRepository<VehicleRoute, Long> {
    VehicleRoute findByNameEn(String nameEn);

    List<VehicleRoute> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM VehicleRoute s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:routePermitTypeId IS NULL OR s.routePermitTypeId = :routePermitTypeId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<VehicleRoute> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("routePermitTypeId") Long routePermitTypeId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}