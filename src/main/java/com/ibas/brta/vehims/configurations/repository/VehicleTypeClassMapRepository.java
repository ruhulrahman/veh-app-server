package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.VehicleTypeClassMap;

@Repository
public interface VehicleTypeClassMapRepository extends JpaRepository<VehicleTypeClassMap, Long> {
    VehicleTypeClassMap findByVehicleTypeIdAndVehicleClassId(Long vehicleTypeId, Long vehicleClassId);

    boolean existsByVehicleTypeIdAndVehicleClassId(Long vehicleTypeId, Long vehicleClassId);

    @Modifying
    @Query("DELETE FROM VehicleTypeClassMap vc WHERE vc.vehicleTypeId = :vehicleTypeId")
    void deleteByVehicleTypeId(@Param("vehicleTypeId") Long vehicleTypeId);

    @Modifying
    @Query("DELETE FROM VehicleTypeClassMap vc WHERE vc.vehicleClassId = :vehicleClassId")
    void deleteByVehicleClassId(@Param("vehicleClassId") Long vehicleClassId);

    @Modifying
    @Query("DELETE FROM VehicleTypeClassMap vc WHERE vc.vehicleTypeId = :vehicleTypeId AND vc.vehicleClassId = :vehicleClassId")
    void deleteByVehicleTypeIdAndVehicleClassId(@Param("vehicleTypeId") Long vehicleTypeId,
            @Param("vehicleClassId") Long vehicleClassId);

    // List<Long> findVehicleClassIdsByVehicleTypeId(Long vehicleTypeId);

    @Query("SELECT vc.vehicleClassId FROM VehicleTypeClassMap vc WHERE vc.vehicleTypeId = :vehicleTypeId")
    List<Long> findVehicleClassIdsByVehicleTypeId(@Param("vehicleTypeId") Long vehicleTypeId);

}
