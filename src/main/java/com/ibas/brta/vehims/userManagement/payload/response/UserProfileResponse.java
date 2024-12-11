package com.ibas.brta.vehims.userManagement.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserProfileResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private String mobile;
    private String email;
    private Boolean isProfileCompleted;

    private UserDetailResponse userDetails;
}