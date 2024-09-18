package com.ibas.brta.vehims.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StatusGroupResponse {
    private Long id;
    private String statusGroupCode;
    private String nameEn;
    private String nameBn;
    private Boolean isActive;
}
