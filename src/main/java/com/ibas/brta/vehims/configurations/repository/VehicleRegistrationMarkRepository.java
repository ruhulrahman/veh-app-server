package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ibas.brta.vehims.configurations.model.VehicleRegistrationMark;

@Repository
public interface VehicleRegistrationMarkRepository extends JpaRepository<VehicleRegistrationMark, Long> {

    VehicleRegistrationMark findByNameEn(String nameEn);

    VehicleRegistrationMark findByNameBn(String nameBn);

    List<VehicleRegistrationMark> findAllByOrderByNameEnAsc();

    List<VehicleRegistrationMark> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM VehicleRegistrationMark s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<VehicleRegistrationMark> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    @Query("SELECT s FROM VehicleRegistrationMark s WHERE id IN (:ids) AND s.isActive = true ORDER BY s.nameEn ASC")
    List<VehicleRegistrationMark> findByIdsIsActiveTrueOrderByNameEnAsc(@Param("ids") List<Long> ids);
}