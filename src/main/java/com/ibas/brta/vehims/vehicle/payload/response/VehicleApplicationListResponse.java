package com.ibas.brta.vehims.vehicle.payload.response;

import java.time.LocalDateTime;

import com.ibas.brta.vehims.configurations.model.Status;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleApplicationListResponse {
    private Long id;
    private Long serviceRequestNo;
    private String chassisNumber;
    private String engineNumber;
    private Long vehicleClassId;
    private String vehicleClassNameEn;
    private String vehicleClassNameBn;
    private Integer ccOrKw;
    private Integer manufacturingYear;
    private LocalDateTime applicationDate;
    private Long applicationStatusId;
    private Status applicationStatus;
}