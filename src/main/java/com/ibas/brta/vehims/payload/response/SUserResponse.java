package com.ibas.brta.vehims.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SUserResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private String username;
    private String mobile;
    private String email;
    private String password;
    private Long userTypeId;
    private Long designationId;
    private Boolean isProfileCompleted;
    private Boolean isActive;
    private String userTypeNameEn;
    private String userTypeNameBn;
    private String designationNameEn;
    private String designationNameBn;
}