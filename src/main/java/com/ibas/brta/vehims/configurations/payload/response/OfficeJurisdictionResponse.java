package com.ibas.brta.vehims.configurations.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OfficeJurisdictionResponse {
    private Long id;
    private Long orgId;
    private String orgNameEn;
    private String orgNameBn;
    private Long thanaId;
    private String thanaNameEn;
    private String thanaNameBn;
    private List<Long> thanaIds;
    private List<LocationResponse> thanas;
    private Boolean isActive;
}
