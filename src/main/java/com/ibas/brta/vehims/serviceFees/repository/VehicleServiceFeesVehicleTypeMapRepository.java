package com.ibas.brta.vehims.serviceFees.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFeesVehicleTypeMap;

import jakarta.transaction.Transactional;

@Repository
public interface VehicleServiceFeesVehicleTypeMapRepository
                extends JpaRepository<VehicleServiceFeesVehicleTypeMap, Long> {

        // List<VehicleServiceFeesVehicleTypeMap>
        // findAllByVehicleServiceFeeIdAndVehicleTypeIdIn(Long vehicleServiceFeeId,
        // List<Long> vehicleTypeIds);

        // List<VehicleServiceFeesVehicleTypeMap> findAllByVehicleTypeIdIn(List<Long>
        // vehicleTypeIds);

        @Transactional
        @Modifying
        @Query("DELETE FROM VehicleServiceFeesVehicleTypeMap vc WHERE vc.vehicleTypeId = :vehicleTypeId")
        void deleteByVehicleTypeId(@Param("vehicleTypeId") Long vehicleTypeId);

        @Transactional
        @Modifying
        @Query("DELETE FROM VehicleServiceFeesVehicleTypeMap vc WHERE vc.serviceFeesId = :serviceFeesId")
        void deleteByServiceFeesId(@Param("serviceFeesId") Long serviceFeesId);

        @Modifying
        @Query("DELETE FROM VehicleServiceFeesVehicleTypeMap vc WHERE vc.vehicleTypeId = :vehicleTypeId AND vc.serviceFeesId = :serviceFeesId")
        void deleteByVehicleTypeIdAndServiceFeesId(@Param("vehicleTypeId") Long vehicleTypeId,
                        @Param("serviceFeesId") Long serviceFeesId);

        // List<Long> findVehicleClassIdsByVehicleTypeId(Long vehicleTypeId);

        @Query("SELECT vc.serviceFeesId FROM VehicleServiceFeesVehicleTypeMap vc WHERE vc.vehicleTypeId = :vehicleTypeId")
        List<Long> findServiceFeesIdsByVehicleTypeId(@Param("vehicleTypeId") Long vehicleTypeId);

        @Query("SELECT vc.vehicleTypeId FROM VehicleServiceFeesVehicleTypeMap vc WHERE vc.serviceFeesId = :serviceFeesId")
        List<Long> findVehicleTypeIdsByServiceFeesId(@Param("serviceFeesId") Long serviceFeesId);
}
