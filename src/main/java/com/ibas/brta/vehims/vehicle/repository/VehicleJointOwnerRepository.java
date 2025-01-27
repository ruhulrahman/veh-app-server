package com.ibas.brta.vehims.vehicle.repository;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.vehicle.model.VehicleJointOwner;
import com.ibas.brta.vehims.vehicle.model.VehicleOwner;

@Repository
public interface VehicleJointOwnerRepository extends JpaRepository<VehicleJointOwner, Long> {

    @Query(value = "SELECT * FROM v_vehicle_joint_owners WHERE vehicle_info_id = :vehicleInfoId AND v_service_request_id = :serviceRequestId", nativeQuery = true)
    List<VehicleJointOwner> findByVehicleInfoIdAndServiceRequestId(@Param("vehicleInfoId") Long vehicleInfoId,
            @Param("serviceRequestId") Long serviceRequestId);

    @Query(value = "SELECT * FROM v_vehicle_joint_owners WHERE vehicle_info_id = :vehicleInfoId", nativeQuery = true)
    List<VehicleJointOwner> findByVehicleInfoId(@Param("vehicleInfoId") Long vehicleInfoId);

    @Query(value = "SELECT * FROM v_vehicle_joint_owners WHERE vehicle_owner_id = :vehicleOwnerId", nativeQuery = true)
    List<VehicleJointOwner> findByVehicleOwnerId(@Param("vehicleOwnerId") Long vehicleOwnerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM v_vehicle_joint_owners WHERE vehicle_owner_id = :vehicleOwnerId", nativeQuery = true)
    void deleteByVehicleOwnerId(@Param("vehicleOwnerId") Long vehicleOwnerId);

}