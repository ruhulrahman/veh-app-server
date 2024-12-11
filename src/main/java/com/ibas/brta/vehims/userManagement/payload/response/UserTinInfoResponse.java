package com.ibas.brta.vehims.userManagement.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserTinInfoResponse {
    private Long id;
    private Long userId;
    private String tinNumber;
    private String tinType;
    private String assessName;
    private String nid;
    private String passportNumber;
    private String smartId;
    private String fatherName;
    private String motherName;
    private String mobile;
    private String email;
    private String dob;
    private String nationality;
    private Integer zoneNo;
    private String zoneName;
    private Integer circleNo;
    private String circleName;
    private String status;
}
