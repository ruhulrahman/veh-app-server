package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByNameEn(String nameEn);

    Country findByNameBn(String nameBn);

    List<Country> findAllByOrderByNameEnAsc();

    List<Country> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM Country s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<Country> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    boolean existsByNameEn(String nameEn);

    boolean existsByNameBn(String nameBn);

    boolean existsByNameEnAndIdNot(String nameEn, Long id);

    boolean existsByNameBnAndIdNot(String nameBn, Long id);
}