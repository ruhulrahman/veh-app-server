package com.ibas.brta.vehims.payload.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ibas.brta.vehims.model.Country;
import com.ibas.brta.vehims.model.Status;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleClassResponse {
    private Long id;
    private Long vehicleTypeId;
    private VehicleTypeResponse vehicleType;
    private String nameEn;
    private String nameBn;
    private String vehicleClassCode;
    private String symbolEn;
    private String symbolBn;
    private Integer startNumber;
    private Integer end_number;
    private String remarksEn;
    private String remarksBn;
    private Boolean isActive;
}
