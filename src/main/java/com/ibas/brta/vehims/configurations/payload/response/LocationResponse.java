package com.ibas.brta.vehims.configurations.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LocationResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private Long locationTypeId;
    private String locationTypeNameEn;
    private String locationTypeNameBn;
    private Long parentId;
    private LocationResponse parentLocation;
    private Boolean isActive;
}
