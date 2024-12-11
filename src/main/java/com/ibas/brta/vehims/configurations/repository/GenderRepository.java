package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
    Gender findByNameEn(String nameEn);

    Gender findByNameBn(String nameBn);

    List<Gender> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM Gender s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<Gender> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    // Complex query with JPQL and named parameters
    @Query("SELECT g FROM Gender g WHERE g.isActive = true ORDER BY g.createdAt ASC")
    List<Gender> getActiveGengerList();
}