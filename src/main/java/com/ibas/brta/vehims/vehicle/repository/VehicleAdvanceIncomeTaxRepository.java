package com.ibas.brta.vehims.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.vehicle.model.VehicleAdvanceIncomeTax;

import java.util.Optional;

@Repository
public interface VehicleAdvanceIncomeTaxRepository extends JpaRepository<VehicleAdvanceIncomeTax, Long> {
    Optional<VehicleAdvanceIncomeTax> findByServiceRequestId(Long serviceRequestId);

    Optional<VehicleAdvanceIncomeTax> findByVehicleInfoId(Long vehicleInfoId);
}
