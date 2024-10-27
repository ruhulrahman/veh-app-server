package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.configurations.AppointmentTimeSlot;

@Repository
public interface AppointmentTimeSlotRepository extends JpaRepository<AppointmentTimeSlot, Long> {
    AppointmentTimeSlot findBySlotNameEn(String slotNameEn);

    List<AppointmentTimeSlot> findByIsActiveTrueOrderBySlotNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM AppointmentTimeSlot s WHERE (:slotNameEn IS NULL OR LOWER(s.slotNameEn) LIKE LOWER(CONCAT('%', :slotNameEn, '%')) OR LOWER(s.slotNameBn) LIKE LOWER(CONCAT('%', :slotNameEn, '%'))) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<AppointmentTimeSlot> findListWithPaginationBySearch(
            @Param("slotNameEn") String slotNameEn,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}