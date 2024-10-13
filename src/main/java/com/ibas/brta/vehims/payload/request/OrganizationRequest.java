package com.ibas.brta.vehims.payload.request;

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

public class OrganizationRequest {
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    private String nameBn;

    @NotNull(message = "Office Type Id cannot be null")
    private Long officeTypeId;

    @Column(name = "parent_org_id")
    private Long parentId;

    @NotNull(message = "Location Id cannot be null")
    private Long locationId;

    @NotNull(message = "Post Code cannot be null")
    @Size(max = 20)
    private String postCode;

    @NotNull(message = "Address in English cannot be null")
    @Size(max = 100)
    private String addressEn;

    @NotNull(message = "Address in Bangla cannot be null")
    @Size(max = 100)
    private String addressBn;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
