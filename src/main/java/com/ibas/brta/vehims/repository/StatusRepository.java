package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ibas.brta.vehims.model.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByStatusCode(String statusCode);

    boolean existsByStatusCode(String statusCode);

    boolean existsByStatusCodeAndIdNot(String statusCode, Long id);

    boolean existsByStatusCodeAndIdNotAndIsActive(String statusCode, Long id, boolean isActive);

    boolean existsByStatusCodeAndIdNotAndIsActiveAndNameEn(String statusCode, Long id, boolean isActive, String nameEn);

    boolean existsByStatusCodeAndIdNotAndIsActiveAndNameBn(String statusCode, Long id, boolean isActive, String nameBn);

    Status findByNameEn(String nameEn);

    Status findByNameBn(String nameBn);

    Status findByStatusCodeAndIsActive(String statusCode, boolean isActive);

    void deleteByStatusCode(String statusCode);

    List<Status> findAllByOrderByNameEnAsc();

    List<Status> findByIsActiveTrueOrderByNameEnAsc();

    List<Status> findByIsActiveTrueOrderByCreatedAtDescPriorityAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM Status s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.statusCode) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:statusGroupId IS NULL OR s.statusGroupId = :statusGroupId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.priority ASC, s.createdAt DESC")
    Page<Status> findStatusBySearch(
            @Param("nameEn") String nameEn,
            @Param("statusGroupId") Long statusGroupId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}
