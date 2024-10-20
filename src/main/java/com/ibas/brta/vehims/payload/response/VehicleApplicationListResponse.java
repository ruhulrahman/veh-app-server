package com.ibas.brta.vehims.payload.response;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.NaturalId;

import com.ibas.brta.vehims.model.Status;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
