package com.ibas.brta.vehims.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.vehicle.model.VehicleTaxToken;

import java.util.Optional;

@Repository
public interface VehicleTaxTokenRepository extends JpaRepository<VehicleTaxToken, Long> {
    Optional<VehicleTaxToken> findByServiceRequestId(Long serviceRequestId);

    Optional<VehicleTaxToken> findByVehicleInfoId(Long vehicleInfoId);
}
