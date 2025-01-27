package com.ibas.brta.vehims.serviceFees.repository;

import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFeesRule;
import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFeesVehicleTypeMap;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleServiceFeesRuleRepository
                extends JpaRepository<VehicleServiceFeesRule, Long> {

         List<VehicleServiceFeesRule> findAllByServiceIdAndVehicleTypeId(Long serviceId, Long vehicleTypeId);

//         List<Long> getStatusIdsByServiceIdAndVehicleTypeId(Long serviceId, Long vehicleTypeId);

         List<VehicleServiceFeesRule> findAllByVehicleTypeIdIn(List<Long> vehicleTypeIds);

        @Modifying
        @Query("DELETE FROM VehicleServiceFeesVehicleTypeMap vc WHERE vc.vehicleTypeId = :vehicleTypeId")
        void deleteByVehicleTypeId(@Param("vehicleTypeId") Long vehicleTypeId);

        @Modifying
        @Query("DELETE FROM VehicleServiceFeesRule vc WHERE vc.serviceFeesId = :serviceFeesId")
        void deleteByServiceFeesId(@Param("serviceFeesId") Long serviceFeesId);

        @Modifying
        @Query("DELETE FROM VehicleServiceFeesRule vc WHERE vc.vehicleTypeId = :vehicleTypeId AND vc.serviceFeesId = :serviceFeesId")
        void deleteByVehicleTypeIdAndServiceFeesId(@Param("vehicleTypeId") Long vehicleTypeId,
                        @Param("serviceFeesId") Long serviceFeesId);

        // List<Long> findVehicleClassIdsByVehicleTypeId(Long vehicleTypeId);

//        UNNEST(s.childServiceIds)
        @Query("SELECT UNNEST(vc.statusIds) FROM VehicleServiceFeesRule vc WHERE vc.vehicleTypeId = :vehicleTypeId")
        List<Long> findServiceFeesIdsByVehicleTypeId(@Param("vehicleTypeId") Long vehicleTypeId);


        @Query("SELECT UNNEST(vc.statusIds) FROM VehicleServiceFeesRule vc WHERE  vc.serviceId = :serviceId AND vc.vehicleTypeId = :vehicleTypeId")
        List<Long> getStatusIdsByServiceIdAndVehicleTypeId(@Param("serviceId") Long serviceId, @Param("vehicleTypeId") Long vehicleTypeId);

        @Query("SELECT vc.vehicleTypeId FROM VehicleServiceFeesRule vc WHERE vc.serviceFeesId = :serviceFeesId AND vc.vehicleTypeId = :vehicleTypeId")
        List<Long> findVehicleTypeIdsByServiceFeesIdAndVehicleTypeId(@Param("serviceFeesId") Long serviceFeesId, Long vehicleTypeId);

        @Query("SELECT vc.vehicleTypeId FROM VehicleServiceFeesRule vc WHERE vc.serviceFeesId = :serviceFeesId")
        List<Long> findVehicleTypeIdsByServiceFeesId(@Param("serviceFeesId") Long serviceFeesId);
}
