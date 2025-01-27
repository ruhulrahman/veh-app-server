package com.ibas.brta.vehims.userManagement.payload.response;

import lombok.Data;
import java.time.LocalDate;

import jakarta.persistence.Column;

@Data
public class UserNidInfoResponse {

    private Long id;
    private Long userId;
    private String nidNumber;
    private LocalDate dob;
    private String nameBn;
    private String nameEn;
    private String fatherOrHusbandNameEn;
    private String fatherOrHusbandNameBn;
    private String motherNameEn;
    private String motherNameBn;
    private String addressEn;
    private String addressBn;
    private Long genderId;
    private String mobile;
    private String photo;
    private String presentAddress;
    private String permanentAddress;
}
