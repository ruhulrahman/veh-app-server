package com.ibas.brta.vehims.repository;

import com.ibas.brta.vehims.model.Designation;

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

    List<Designation> findByParentDesignationIdIsNull();

    // Complex query with JPQL and named parameters
    @Query("SELECT d FROM Designation d WHERE (:nameEn is null or d.nameEn LIKE %:nameEn%) AND (:nameEn is null or d.nameBn LIKE %:nameEn%) AND (:isActive is null or d.isActive = :isActive)")
    // @Query("SELECT d FROM Designation d WHERE d.nameEn LIKE %:nameEn%")
    Page<Designation> getDesignationBySearch(
            @Param("nameEn") String nameEn,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    // @Query("select c from VehicleData c where UPPER(c.registrationNo) like
    // %:filter%")
    // Page<VehicleData> searchByRegistrationNo(@Param("filter") String filter,
    // Pageable pageable);
}
