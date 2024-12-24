package com.ibas.brta.vehims.configurations.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleRegistrationMarkResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private List<Long> orgIds;
    private Boolean isActive;
}
