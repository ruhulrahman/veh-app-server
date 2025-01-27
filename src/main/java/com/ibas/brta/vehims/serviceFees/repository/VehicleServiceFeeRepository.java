package com.ibas.brta.vehims.serviceFees.repository;

import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFeesVehicleTypeMap;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFees;
import com.ibas.brta.vehims.serviceFees.payload.response.VehicleServiceFeesResponse;

import java.util.List;

@Repository
public interface VehicleServiceFeeRepository extends JpaRepository<VehicleServiceFees, Long>, JpaSpecificationExecutor<VehicleServiceFees> {
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
                    vsf.isApplicableForMultipleVehicleOwner,
                    vsf.feeForMultipleVehicle,
                    vsf.isElectricVehicle,
                    null,
                    null
                )
                FROM VehicleServiceFees vsf
                LEFT JOIN ServiceEntity ce ON vsf.serviceId = ce.id
                LEFT JOIN VehicleServiceFeesVehicleTypeMap fMap ON vsf.id = fMap.serviceFeesId
                WHERE
                    (:serviceId IS NULL OR vsf.serviceId = :serviceId)
                    AND (:isAirCondition IS NULL OR vsf.isAirCondition = :isAirCondition)
                    AND (:isElectricVehicle IS NULL OR vsf.isElectricVehicle = :isElectricVehicle)
                    AND (:isHire IS NULL OR vsf.isHire = :isHire)
                    AND (:isApplicableForMultipleVehicleOwner IS NULL OR vsf.isApplicableForMultipleVehicleOwner = :isApplicableForMultipleVehicleOwner)
                    AND (:vehicleTypeId IS NULL OR fMap.vehicleTypeId = :vehicleTypeId)
                    AND (
                        :isActive IS NULL OR vsf.isActive = :isActive OR
                        (vsf.effectiveStartDate <= CURRENT_TIMESTAMP AND vsf.effectiveEndDate >= CURRENT_TIMESTAMP)
                    )
                GROUP BY
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
                vsf.isApplicableForMultipleVehicleOwner,
                vsf.feeForMultipleVehicle,
                vsf.isElectricVehicle,
                ce.priority
                ORDER BY vsf.createdAt DESC, vsf.effectiveStartDate ASC
            """)
    Page<VehicleServiceFeesResponse> findListWithPaginationBySearch(
            @Param("serviceId") Long serviceId,
            @Param("isAirCondition") Boolean isAirCondition,
            @Param("isElectricVehicle") Boolean isElectricVehicle,
            @Param("isHire") Boolean isHire,
            @Param("isApplicableForMultipleVehicleOwner") Boolean isApplicableForMultipleVehicleOwner,
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
                vsf.isApplicableForMultipleVehicleOwner,
                vsf.feeForMultipleVehicle,
                vsf.isElectricVehicle,
                null,
                null
            )
            FROM VehicleServiceFees vsf
            LEFT JOIN ServiceEntity ce ON vsf.serviceId = ce.id
            WHERE vsf.serviceId IN :serviceIds
            AND (vsf.isActive = true OR vsf.effectiveEndDate >= CURRENT_TIMESTAMP)
            GROUP BY
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
                vsf.isApplicableForMultipleVehicleOwner,
                vsf.feeForMultipleVehicle,
                vsf.isElectricVehicle,
                ce.priority
            ORDER BY ce.priority ASC
            """)
    List<VehicleServiceFeesResponse> getServiceWithFeesByServiceIds(List<Long> serviceIds);

    @Query("SELECT s.serviceId FROM VehicleServiceFees s WHERE s.isActive = true")
    List<Long> getServicesIdsIsActiveTrue();


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
                vsf.isApplicableForMultipleVehicleOwner,
                vsf.feeForMultipleVehicle,
                vsf.isElectricVehicle,
                null,
                null
            )
            FROM VehicleServiceFees vsf
            LEFT JOIN ServiceEntity ce ON vsf.serviceId = ce.id
            WHERE
            vsf.serviceId IN :serviceIds
            AND (:cc IS NULL OR vsf.ccMin <= :cc AND vsf.ccMax >= :cc)
            AND (:seat IS NULL OR vsf.seatMin <= :seat AND vsf.seatMax >= :seat)
            AND (:weight IS NULL OR vsf.weightMin <= :weight AND vsf.weightMax >= :weight)
            AND (:kw IS NULL OR vsf.kwMin <= :kw AND vsf.kwMax >= :kw)
            AND (:isAirCondition IS NULL OR vsf.isAirCondition = :isAirCondition)
            AND (:isHire IS NULL OR vsf.isHire = :isHire)
            AND (
                vsf.isActive = true OR
                (vsf.effectiveStartDate <= CURRENT_TIMESTAMP AND vsf.effectiveEndDate >= CURRENT_TIMESTAMP)
            )
            GROUP BY
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
                vsf.isApplicableForMultipleVehicleOwner,
                vsf.feeForMultipleVehicle,
                vsf.isElectricVehicle,
                ce.priority
            ORDER BY ce.priority ASC
            """)
    List<VehicleServiceFeesResponse> getServiceWithFeesByServiceIdsAndVehicleInfoParams(List<Long> serviceIds, Integer cc, Integer seat, Integer weight, Integer kw, Boolean isAirCondition, Boolean isHire);

//    @Query("""
//        SELECT vsf FROM VehicleServiceFees vsf
//        WHERE serviceId = :serviceId
//        AND (:cc IS NULL OR cc = :cc)
//        AND (:seat IS NULL OR seat = :seat)
//        AND (:weight IS NULL OR weight = :weight)
//        AND (:isAirCondition IS NULL OR isAirCondition = :isAirCondition)
//        AND (:isHire IS NULL OR isHire = :isHire)
//        AND (isActive = true OR (effectiveStartDate <= CURRENT_TIMESTAMP AND effectiveEndDate >= CURRENT_TIMESTAMP))
//    """)
//    VehicleServiceFees findByServiceIdAndVehicleInfoParams(Long serviceId, Boolean isElectricVehicle, Integer cc, Integer seat, Integer weight, Boolean isAirCondition, Boolean isHire);
//    VehicleServiceFees findByServiceIdAndVehicleInfoParams(Long serviceId, Integer cc, Integer seat, Integer weight, Integer kw, Boolean isAirCondition, Boolean isHire);

//    @Query("""
//        SELECT vsf FROM VehicleServiceFees vsf
//        WHERE serviceId = :serviceId
//        AND (:isElectricVehicle IS NULL OR isElectricVehicle = :isElectricVehicle)
//        AND (:kw IS NULL OR ccMin <= :kw AND ccMax <= :kw)
//        AND (:seat IS NULL OR seatMin <= :seat AND seatMax <= :seat)
//        AND (:weight IS NULL OR weightMin <= :weight AND weightMax <= :weight)
//        AND (:isAirCondition IS NULL OR isAirCondition = :isAirCondition)
//        AND (:isHire IS NULL OR isHire = :isHire)
//        AND (isActive = true OR (effectiveStartDate <= CURRENT_TIMESTAMP AND effectiveEndDate >= CURRENT_TIMESTAMP))
//    """)
//    VehicleServiceFees findFeesForElectricVehicle(Long serviceId, Boolean isElectricVehicle, Integer kw, Integer seat, Integer weight, Boolean isAirCondition, Boolean isHire);
//
    @Query("""
        SELECT vsf FROM VehicleServiceFees vsf
        WHERE serviceId = :serviceId
        AND (:kw IS NULL OR (kwMin <= :kw AND (kwMax IS NULL OR kwMax >= :kw)))
        AND (:isAirCondition IS NULL OR isAirCondition = :isAirCondition)
        AND (:isHire IS NULL OR isHire = :isHire)
        AND (isActive = true OR (effectiveStartDate <= CURRENT_TIMESTAMP AND effectiveEndDate >= CURRENT_TIMESTAMP))
    """)
    VehicleServiceFees findFeesForElectricVehicle(Long serviceId, Integer kw, Boolean isAirCondition, Boolean isHire);

    @Query("""
        SELECT vsf FROM VehicleServiceFees vsf
        WHERE serviceId = :serviceId
        AND (:cc IS NULL OR (ccMin <= :cc AND (ccMax IS NULL OR ccMax >= :cc)))
        AND (:seat IS NULL OR (seatMin <= :seat AND (seatMax IS NULL OR seatMax >= :seat)))
        AND (:weight IS NULL OR (weightMin <= :weight AND (weightMax IS NULL OR weightMax >= :weight)))
        AND (:isAirCondition IS NULL OR isAirCondition = :isAirCondition)
        AND (:isHire IS NULL OR isHire = :isHire)
        AND (isActive = true OR (effectiveStartDate <= CURRENT_TIMESTAMP AND effectiveEndDate >= CURRENT_TIMESTAMP))
    """)
    VehicleServiceFees findFeesForNotElectricVehicle(Long serviceId, Integer cc, Integer seat, Integer weight, Boolean isAirCondition, Boolean isHire);


    @Query(value = """
        SELECT vsf.*
        FROM f_vehicle_service_fees vsf
        JOIN f_vehicle_service_fees_vehicle_type_maps vsfvtm
        ON vsf.v_service_fees_id = vsfvtm.v_service_fees_id
        WHERE vsf.service_id = :serviceId
        AND vsfvtm.vehicle_type_id = :vehicleTypeId
        AND (:isElectricVehicle IS NULL OR vsf.is_electric_vehicle = :isElectricVehicle)
        AND (:kw IS NULL OR vsf.cc_min <= :kw AND vsf.cc_max >= :kw)
        AND (:seat IS NULL OR vsf.seat_min <= :seat AND vsf.seat_max >= :seat)
        AND (:weight IS NULL OR vsf.weight_min <= :weight AND vsf.weight_max >= :weight)
        AND (:isAirCondition IS NULL OR vsf.is_air_condition = :isAirCondition)
        AND (:isHire IS NULL OR vsf.is_hire = :isHire)
        AND (vsf.is_active = true OR (vsf.effective_start_date <= CURRENT_TIMESTAMP AND vsf.effective_end_date >= CURRENT_TIMESTAMP))
    """, nativeQuery = true)
    VehicleServiceFees findFeesForVehicleRegistration(
            @Param("serviceId") Long serviceId,
            @Param("vehicleTypeId") Long vehicleTypeId,
            @Param("isElectricVehicle") Boolean isElectricVehicle,
            @Param("kw") Integer ccOrKw,
            @Param("seat") Integer seat,
            @Param("weight") Integer weight,
            @Param("isAirCondition") Boolean isAirCondition,
            @Param("isHire") Boolean isHire);

}
