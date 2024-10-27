package com.ibas.brta.vehims.payload.response;

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
}
