package com.ibas.brta.vehims.serviceFees.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFees;
import com.ibas.brta.vehims.serviceFees.payload.response.VehicleServiceFeesResponse;

import java.util.List;

@Repository
public interface VehicleServiceFeeRepository extends JpaRepository<VehicleServiceFees, Long> {
    VehicleServiceFees findByServiceId(Long serviceId);

    VehicleServiceFees findByServiceIdAndIsActive(Long serviceId, boolean isActive);

    @Query("""
                SELECT new com.ibas.brta.vehims.serviceFees.payload.response.VehicleServiceFeesResponse(
                    vsf.id,
                    vsf.serviceId,
                    ce.nameEn,
                    ce.nameBn,
                    vsf.isYearlyFee,
                    vsf.mainFee,
                    vsf.lateFee,
                    vsf.vatPercentage,
                    vsf.sdPercentage,
                    vsf.effectiveStartDate,
                    vsf.effectiveEndDate,
                    vsf.isActive,
                    vsf.ccMin,
                    vsf.ccMax,
                    vsf.seatMin,
                    vsf.seatMax,
                    vsf.weightMin,
                    vsf.weightMax,
                    vsf.kwMin,
                    vsf.kwMax,
                    vsf.isAirCondition,
                    vsf.isHire,
                    null,
                    null
                )
                FROM VehicleServiceFees vsf
                LEFT JOIN ServiceEntity ce ON vsf.serviceId = ce.id
                LEFT JOIN VehicleServiceFeesVehicleTypeMap fMap ON vsf.id = fMap.serviceFeesId
                WHERE (:serviceId IS NULL OR vsf.serviceId = :serviceId)
                   OR (:isAirCondition IS NULL OR vsf.isAirCondition = :isAirCondition)
                   OR (:isHire IS NULL OR vsf.isHire = :isHire)
                   OR (:vehicleTypeId IS NULL OR fMap.vehicleTypeId = :vehicleTypeId)
                  AND (:isActive IS NULL OR vsf.isActive = :isActive OR
                      (vsf.effectiveStartDate <= CURRENT_TIMESTAMP AND vsf.effectiveEndDate >= CURRENT_TIMESTAMP))
                GROUP BY vsf.id, vsf.serviceId, ce.nameEn, ce.nameBn, vsf.isYearlyFee, vsf.mainFee, vsf.lateFee,
                         vsf.vatPercentage, vsf.sdPercentage, vsf.effectiveStartDate, vsf.effectiveEndDate, vsf.isActive,
                         vsf.ccMin, vsf.ccMax, vsf.seatMin, vsf.seatMax, vsf.weightMin, vsf.weightMax, vsf.kwMin, vsf.kwMax,
                         vsf.isAirCondition, vsf.isHire
                ORDER BY vsf.createdAt DESC, vsf.effectiveStartDate ASC
            """)
    Page<VehicleServiceFeesResponse> findListWithPaginationBySearch(
            @Param("serviceId") Long serviceId,
            @Param("isAirCondition") Boolean isAirCondition,
            @Param("isHire") Boolean isHire,
            @Param("vehicleTypeId") Long vehicleTypeId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    @Query("""
            SELECT new com.ibas.brta.vehims.serviceFees.payload.response.VehicleServiceFeesResponse(
                vsf.id,
                vsf.serviceId,
                ce.nameEn,
                ce.nameBn,
                vsf.isYearlyFee,
                vsf.mainFee,
                vsf.lateFee,
                vsf.vatPercentage,
                vsf.sdPercentage,
                vsf.effectiveStartDate,
                vsf.effectiveEndDate,
                vsf.isActive,
                vsf.ccMin,
                vsf.ccMax,
                vsf.seatMin,
                vsf.seatMax,
                vsf.weightMin,
                vsf.weightMax,
                vsf.kwMin,
                vsf.kwMax,
                vsf.isAirCondition,
                vsf.isHire,
                null,
                null
            )
            FROM VehicleServiceFees vsf
            LEFT JOIN ServiceEntity ce ON vsf.serviceId = ce.id
            WHERE vsf.serviceId IN :serviceIds
            AND (vsf.isActive = true OR vsf.effectiveEndDate >= CURRENT_TIMESTAMP)
            GROUP BY vsf.serviceId, ce.nameEn, ce.nameBn
            ORDER BY ce.priority ASC
            """)
    List<VehicleServiceFeesResponse> getServiceWithFeesByServiceIds(List<Long> serviceIds);

    @Query("SELECT s.serviceId FROM VehicleServiceFees s WHERE s.isActive = true")
    List<Long> getServicesIdsIsActiveTrue();
}
