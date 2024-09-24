package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ibas.brta.vehims.model.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    ServiceEntity findByNameEn(String nameEn);

    ServiceEntity findByNameBn(String nameBn);

    ServiceEntity findByServiceCode(String serviceCode);

    boolean existsByNameEn(String nameEn);

    boolean existsByNameBn(String nameBn);

    boolean existsByServiceCode(String serviceCode);

    List<ServiceEntity> findAllByOrderByNameEnAsc();

    List<ServiceEntity> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM ServiceEntity s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.serviceCode) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<ServiceEntity> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}
