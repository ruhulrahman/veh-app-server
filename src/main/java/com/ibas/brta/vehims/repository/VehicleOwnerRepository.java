package com.ibas.brta.vehims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.vehicle.VehicleOwner;

@Repository
public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, Long> {

    @Query(value = "SELECT * FROM v_vehicle_owners WHERE vehicle_info_id = :vehicleInfoId AND service_request_id = :serviceRequestId", nativeQuery = true)
    VehicleOwner findByVehicleInfoIdAndServiceRequestId(@Param("vehicleInfoId") Long vehicleInfoId,
            @Param("serviceRequestId") Long serviceRequestId);
}