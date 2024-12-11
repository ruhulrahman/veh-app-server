package com.ibas.brta.vehims.configurations.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrganizationWithThanas {
    private Boolean isEdit;
    private Long orgId;
    private String orgNameEn;
    private String orgNameBn;
    private Long divisionId;
    private Long districtId;
    private List<Long> thanaIds;
    private List<LocationResponse> thanas;
    private Boolean isActive;
}
