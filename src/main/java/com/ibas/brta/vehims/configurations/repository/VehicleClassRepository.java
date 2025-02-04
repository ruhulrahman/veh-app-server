package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.VehicleClass;

@Repository
public interface VehicleClassRepository extends JpaRepository<VehicleClass, Long> {
    VehicleClass findByNameEn(String nameEn);

    List<VehicleClass> findByIsActiveTrueOrderByNameEnAsc();

    @Query("SELECT vc FROM VehicleClass vc WHERE vc.id IN :vehicleClassIds AND vc.isActive = true ORDER BY vc.nameEn ASC")
    List<VehicleClass> findByIdsIsActiveTrueOrderByNameEnAsc(@Param("vehicleClassIds") List<Long> vehicleClassIds);

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM VehicleClass s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:vehicleTypeId IS NULL OR s.vehicleTypeId = :vehicleTypeId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<VehicleClass> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("vehicleTypeId") Long vehicleTypeId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    boolean existsByNameEn(String nameEn);

    boolean existsByNameBn(String nameBn);

    boolean existsByNameEnAndIdNot(String nameEn, Long id);

    boolean existsByNameBnAndIdNot(String nameBn, Long id);
}