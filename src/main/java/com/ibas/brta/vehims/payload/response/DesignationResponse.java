package com.ibas.brta.vehims.payload.response;

import com.ibas.brta.vehims.model.configurations.Designation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesignationResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private int levelNumber;
    private Long parentDesignationId;
    private Designation parentDesignation;
    private Boolean isActive;
    // private String createdBy;
    // private String updatedBy;
    // private String createdAt;
    // private String updatedAt;
}
