package com.ibas.brta.vehims.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.vehicle.model.VehicleRegistration;

import java.util.Optional;

@Repository
public interface VehicleRegistrationRepository extends JpaRepository<VehicleRegistration, Long> {

    @Query(value = "SELECT * FROM v_vehicle_registrations WHERE v_service_request_id = :serviceRequestId AND is_active = true order by created_date DESC", nativeQuery = true)
    Optional<VehicleRegistration> findByServiceRequestId(Long serviceRequestId);

    @Query(value = "SELECT * FROM v_vehicle_registrations WHERE vehicle_info_id = :vehicleInfoId AND is_active = true order by created_date DESC", nativeQuery = true)
    Optional<VehicleRegistration> findByVehicleInfoId(Long vehicleInfoId);

    @Query(value = "SELECT * FROM v_vehicle_registrations WHERE full_reg_number = :fullRegNumber AND is_active = true order by created_date DESC", nativeQuery = true)
    Optional<VehicleRegistration> findByFullRegNumber(String fullRegNumber);

    @Query(value = "SELECT * FROM v_vehicle_registrations WHERE vehicle_class_id = :vehicleClassId AND is_active = true order by created_date DESC", nativeQuery = true)
    Optional<VehicleRegistration> findByVehicleClassId(Long vehicleClassId);

    @Query(value = "SELECT * FROM v_vehicle_registrations WHERE reg_office_id = :officeId AND vehicle_class_id = :vehicleClassId AND is_active = true order by created_date DESC", nativeQuery = true)
    Optional<VehicleRegistration> findByRegOfficeIdAndVehicleClassId(Long officeId, Long vehicleClassId);
}
