package com.ibas.brta.vehims.configurations.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrganizationResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private Long officeTypeId;
    private String officeTypeNameEn;
    private String officeTypeNameBn;
    private Long parentId;
    private OrganizationResponse parentOrganization;
    private Long locationId;
    private String locationEn;
    private String locationBn;
    private String postCode;
    private String addressEn;
    private String addressBn;
    private Boolean isActive;
}
