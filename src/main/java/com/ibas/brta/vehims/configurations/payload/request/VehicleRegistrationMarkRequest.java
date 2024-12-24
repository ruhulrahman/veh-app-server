package com.ibas.brta.vehims.configurations.payload.request;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleRegistrationMarkRequest {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nameEn;

    @NotBlank
    @Size(max = 100)
    private String nameBn;

    @NotNull
    private List<Long> orgIds;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
