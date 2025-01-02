package com.ibas.brta.vehims.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.vehicle.model.VehicleRegistration;

import java.util.Optional;

@Repository
public interface VehicleRegistrationRepository extends JpaRepository<VehicleRegistration, Long> {
    Optional<VehicleRegistration> findByServiceRequestId(Long serviceRequestId);

    Optional<VehicleRegistration> findByVehicleInfoId(Long vehicleInfoId);

    Optional<VehicleRegistration> findByFullRegNumber(String fullRegNumber);

    @Query(value = "SELECT * FROM v_vehicle_registrations WHERE vehicle_class_id = :vehicleClassId order by created_at DESC", nativeQuery = true)
    Optional<VehicleRegistration> findByVehicleClassId(Long vehicleClassId);

    @Query(value = "SELECT * FROM v_vehicle_registrations WHERE reg_office_id = :officeId AND vehicle_class_id = :vehicleClassId order by created_at DESC", nativeQuery = true)
    Optional<VehicleRegistration> findByRegOfficeIdAndVehicleClassId(Long officeId, Long vehicleClassId);
}
