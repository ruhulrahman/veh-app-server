package com.ibas.brta.vehims.payload.request;

import jakarta.validation.constraints.NotNull;

public class StatusGroupRequest {
    private String statusGroupCode;
    private String nameEn;
    private String nameBn;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
