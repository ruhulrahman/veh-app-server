package com.ibas.brta.vehims.userManagement.payload.request;

import java.time.LocalDate;

import com.ibas.brta.vehims.configurations.payload.request.AddressRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDetailRequest {
    private Long id;
    private String nidNumber;
    private LocalDate dob;
    private String fatherOrHusbandNameEn;
    private String fatherOrHusbandNameBn;
    private String motherNameEn;
    private String motherNameBn;
    private Long genderId;
    private Long presentAddressId;
    private Long permanentAddressId;
    private String passportNumber;
    private String birthRegNumber;
    // private String photo;
    private String photo;
    private Long bloodGroupId;

    private AddressRequest presentAddress;

    private AddressRequest permanentAddress;
}
