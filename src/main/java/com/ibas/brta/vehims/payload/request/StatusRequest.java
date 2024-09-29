package com.ibas.brta.vehims.payload.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StatusRequest {
    private Long id;
    private Long statusGroupId;
    private String statusCode;
    private String nameEn;
    private String nameBn;
    private String colorName;
    private Integer priority;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
