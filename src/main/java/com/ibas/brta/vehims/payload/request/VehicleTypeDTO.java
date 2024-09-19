package com.ibas.brta.vehims.payload.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleTypeDTO implements Serializable {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nameEn;

    @NotBlank
    @Size(max = 100)
    private String nameBn;
}
