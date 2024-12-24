package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.VehicleRegistrationMarkOrganizationMap;

import jakarta.transaction.Transactional;

@Repository
public interface VehicleRegistrationMarkOrganizationMapRepository
                extends JpaRepository<VehicleRegistrationMarkOrganizationMap, Long> {
        VehicleRegistrationMarkOrganizationMap findByVehicleRegistrationMarkIdAndOrgId(
                        Long vehicleRegistrationMarkId, Long orgId);

        boolean existsByVehicleRegistrationMarkIdAndOrgId(Long vehicleRegistrationMarkId, Long orgId);

        @Transactional
        @Modifying
        @Query("DELETE FROM VehicleRegistrationMarkOrganizationMap vc WHERE vc.vehicleRegistrationMarkId = :vehicleRegistrationMarkId")
        void deleteByVehicleRegistrationMarkId(@Param("vehicleRegistrationMarkId") Long vehicleRegistrationMarkId);

        @Transactional
        @Modifying
        @Query("DELETE FROM VehicleRegistrationMarkOrganizationMap vc WHERE vc.orgId = :orgId")
        void deleteByOrgId(@Param("orgId") Long orgId);

        @Transactional
        @Modifying
        @Query("DELETE FROM VehicleRegistrationMarkOrganizationMap vc WHERE vc.vehicleRegistrationMarkId = :vehicleRegistrationMarkId AND vc.orgId = :orgId")
        void deleteByVehicleRegistrationMarkIdAndOrgId(
                        @Param("vehicleRegistrationMarkId") Long vehicleRegistrationMarkId,
                        @Param("orgId") Long orgId);

        // List<Long> findVehicleClassIdsByVehicleTypeId(Long vehicleTypeId);

        @Query("SELECT vc.orgId FROM VehicleRegistrationMarkOrganizationMap vc WHERE vc.vehicleRegistrationMarkId = :vehicleRegistrationMarkId")
        List<Long> findOrgIdsByVehicleRegistrationMarkId(
                        @Param("vehicleRegistrationMarkId") Long vehicleRegistrationMarkId);

}
