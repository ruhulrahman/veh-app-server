package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ibas.brta.vehims.configurations.model.VehicleType;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

    VehicleType findByNameEn(String nameEn);

    VehicleType findByNameBn(String nameBn);

    List<VehicleType> findAllByOrderByNameEnAsc();

    List<VehicleType> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM VehicleType s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<VehicleType> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    @Query("SELECT s FROM VehicleType s WHERE id IN (:ids) AND s.isActive = true ORDER BY s.nameEn ASC")
    List<VehicleType> findByIdsIsActiveTrueOrderByNameEnAsc(@Param("ids") List<Long> ids);

    boolean existsByNameEn(String nameEn);

    boolean existsByNameBn(String nameBn);

    boolean existsByNameEnAndIdNot(String nameEn, Long id);

    boolean existsByNameBnAndIdNot(String nameBn, Long id);
}