package com.ibas.brta.vehims.payload.response;

import java.io.Serializable;
import java.util.Set;

import com.ibas.brta.vehims.model.Status;
import com.ibas.brta.vehims.model.StatusGroup;

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
