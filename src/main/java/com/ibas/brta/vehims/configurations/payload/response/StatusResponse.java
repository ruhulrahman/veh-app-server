package com.ibas.brta.vehims.configurations.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StatusResponse {
    private Long id;
    private Long statusGroupId;
    private String statusCode;
    private String nameEn;
    private String nameBn;
    private String colorName;
    private Integer priority;
    private Boolean isActive;
    private StatusGroupResponse statusGroup;
}
