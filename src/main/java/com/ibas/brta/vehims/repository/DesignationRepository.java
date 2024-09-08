package com.ibas.brta.vehims.repository;

import com.ibas.brta.vehims.model.Designation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

    // Search by name containing keyword (case-insensitive)
    Page<Designation> findByNameEnContainingIgnoreCase(String nameEn, Pageable pageable);

    Page<Designation> findByNameBnContainingIgnoreCase(String nameEn, Pageable pageable);

    Page<Designation> findByIsActive(String isActive, Pageable pageable);
}
