package com.ibas.brta.vehims.configurations.repository;

import com.ibas.brta.vehims.configurations.model.Designation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

    // Search by name containing keyword (case-insensitive)
    Page<Designation> findByNameEnContainingIgnoreCase(String nameEn, Pageable pageable);

    Page<Designation> findByNameBnContainingIgnoreCase(String nameEn, Pageable pageable);

    List<Designation> findByIsActive(Boolean isActive);

    List<Designation> findAll(Sort sort);

    // Simple query (by method naming convention)
    List<Designation> findByNameEn(String nameEn);

    List<Designation> findByNameBn(String nameBn);

    List<Designation> findByParentDesignationIdIsNullOrderByLevelNumberAsc();

    List<Designation> findAllByOrderByLevelNumberAsc();

    List<Designation> findByIsActiveTrueOrderByNameEnAsc();

    List<Designation> findByIsActiveTrueOrderByLevelNumberAsc();

    // Complex query with JPQL and named parameters
    // @Query("SELECT d FROM Designation d WHERE " +
    // "(:nameEn IS NULL OR LOWER(d.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR
    // LOWER(d.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) "
    // +
    // "AND (:isActive IS NULL OR d.isActive = :isActive)")
    // @Query("SELECT d FROM Designation d WHERE (:nameEn IS NULL OR LOWER(d.nameEn)
    // LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(d.nameBn) LIKE
    // LOWER(CONCAT('%', :nameEn, '%'))) AND (:isActive IS NULL OR d.isActive =
    // :isActive)")
    // List<Designation> getDesignationBySearch(
    // @Param("nameEn") String nameEn,
    // @Param("isActive") Boolean isActive);
    @Query("SELECT d FROM Designation d WHERE (:nameEn IS NULL OR LOWER(d.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(d.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:isActive IS NULL OR d.isActive = :isActive) ORDER BY d.levelNumber ASC")
    Page<Designation> getDesignationBySearch(
            @Param("nameEn") String nameEn,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}
