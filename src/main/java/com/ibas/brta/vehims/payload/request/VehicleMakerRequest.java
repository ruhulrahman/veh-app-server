package com.ibas.brta.vehims.payload.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleMakerRequest {
    private Long id;

    @NotNull(message = "Country ID cannot be null")
    @Column(name = "country_id", nullable = false)
    private Long countryId;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false)
    private String nameBn;

    @NotBlank(message = "Address cannot be blank")
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
