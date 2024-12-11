package com.ibas.brta.vehims.configurations.payload.response;

import java.util.List;

import com.ibas.brta.vehims.common.payload.response.ServiceEconomicCodeResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ServiceEntityResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private String serviceCode;
    private Boolean isActive;
    private Long parentServiceId;
    private ServiceEntityResponse parentService;
    private List<Long> childServiceIds;
    private List<ServiceEntityResponse> childServices;
    private Integer priority;
    private Long serviceEconomicCodeId;
    private ServiceEconomicCodeResponse serviceEconomicCode;
}
