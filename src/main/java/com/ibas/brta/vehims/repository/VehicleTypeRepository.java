package com.ibas.brta.vehims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.VehicleType;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
}