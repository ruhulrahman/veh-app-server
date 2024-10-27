package com.ibas.brta.vehims.payload.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Integer genderId;
    private String mobile;
}
