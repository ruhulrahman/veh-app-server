package com.ibas.brta.vehims.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.vehicle.model.VehicleFitness;

import java.util.Optional;

@Repository
public interface VehicleFitnessRepository extends JpaRepository<VehicleFitness, Long> {
    Optional<VehicleFitness> findByServiceRequestId(Long serviceRequestId);

    Optional<VehicleFitness> findByVehicleInfoId(Long vehicleInfoId);
}
