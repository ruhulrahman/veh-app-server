package com.ibas.brta.vehims.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LocationRequest {
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    private String nameBn;

    @NotNull(message = "Location Type Id cannot be null")
    private Long locationTypeId;

    private Long parentId;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
