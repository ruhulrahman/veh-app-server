package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.VehicleInfo;
import com.ibas.brta.vehims.payload.response.VehicleInfoResponse;

@Repository
public interface VehicleInfoRepository extends JpaRepository<VehicleInfo, Long> {

        // Complex query with Native SQL and named parameters
        @Query(value = "SELECT vinfo.*, "
                        + "importer.name_en AS importer_name_en, "
                        + "importer.name_bn AS importer_name_bn "
                        + "FROM v_vehicle_infos vinfo "
                        + "JOIN v_importers importer ON vinfo.importer_id = importer.importer_id "
                        + "WHERE (:chassisNumber IS NULL OR vinfo.chassis_number = :chassisNumber) "
                        + "AND (:engineNumber IS NULL OR vinfo.engine_number = :engineNumber)", nativeQuery = true)
        Page<VehicleInfoResponse> findListWithPaginationBySearchWithNativeQuery(
                        @Param("chassisNumber") String chassisNumber,
                        @Param("engineNumber") String engineNumber,
                        Pageable pageable);
}