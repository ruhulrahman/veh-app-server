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

public class ServiceEntityDTO {

    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false, unique = true)
    private String nameBn;

    @NotBlank
    @Size(max = 100)
    @Column(name = "service_code", nullable = false, unique = true)
    private String serviceCode;

    @Column(name = "parent_service_id", nullable = true)
    private Long parentServiceId;

    private ServiceEntityDTO parentService;

    private List<Long> childServiceIds;

    private Integer priority;

    private Long serviceEconomicCodeId;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
