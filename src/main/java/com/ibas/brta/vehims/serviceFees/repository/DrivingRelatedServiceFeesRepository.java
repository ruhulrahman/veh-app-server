package com.ibas.brta.vehims.serviceFees.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.ServiceEntity;
import com.ibas.brta.vehims.serviceFees.model.DrivingRelatedServiceFees;
import com.ibas.brta.vehims.serviceFees.payload.response.DrivingRelatedServiceFeesResponse;

@Repository
public interface DrivingRelatedServiceFeesRepository extends JpaRepository<DrivingRelatedServiceFees, Long> {

  @Query("""
      SELECT new com.ibas.brta.vehims.serviceFees.payload.response.DrivingRelatedServiceFeesResponse(
          s.id,
          s.serviceId,
          cs.nameEn,
          cs.nameBn,
          s.isYearlyFee,
          s.mainFee,
          s.lateFee,
          s.vatPercentage,
          s.sdPercentage,
          s.effectiveStartDate,
          s.effectiveEndDate,
          s.isActive
      )
      FROM DrivingRelatedServiceFees s
      LEFT JOIN ServiceEntity cs ON s.serviceId = cs.id
      WHERE (:serviceNameEn IS NULL OR LOWER(cs.nameEn) LIKE LOWER(CONCAT('%', :serviceNameEn, '%')))
        AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC, s.effectiveStartDate ASC
      """)
  Page<DrivingRelatedServiceFeesResponse> findListWithPaginationBySearch(
      @Param("serviceNameEn") String serviceNameEn,
      @Param("isActive") Boolean isActive,
      Pageable pageable);

  @Query("SELECT s.serviceId FROM DrivingRelatedServiceFees s WHERE s.isActive = true")
  List<Long> getServicesIdsIsActiveTrue();

  @Query("""
          SELECT new com.ibas.brta.vehims.serviceFees.payload.response.DrivingRelatedServiceFeesResponse(
              s.id,
              s.serviceId,
              cs.nameEn,
              cs.nameBn,
              s.isYearlyFee,
              s.mainFee,
              s.lateFee,
              s.vatPercentage,
              s.sdPercentage,
              s.effectiveStartDate,
              s.effectiveEndDate,
              s.isActive
          )
          FROM DrivingRelatedServiceFees s
          LEFT JOIN ServiceEntity cs ON s.serviceId = cs.id
          WHERE s.serviceId IN :serviceIds
            AND (s.isActive = true OR (s.effectiveStartDate <= CURRENT_TIMESTAMP AND s.effectiveEndDate >= CURRENT_TIMESTAMP))
            ORDER BY cs.priority ASC
      """)
  List<DrivingRelatedServiceFeesResponse> getServiceWithFeesByServiceIds(@Param("serviceIds") List<Long> serviceIds);

}
