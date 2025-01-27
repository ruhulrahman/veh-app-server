package com.ibas.brta.vehims.userManagement.payload.response;

import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDetailResponse {
    private Long id;

    private Long userId;

    private String nidNumber;

    private LocalDate dob;

    private String fatherOrHusbandNameEn;

    private String fatherOrHusbandNameBn;

    private String motherNameEn;

    private String motherNameBn;

    private Long genderId;

    private Long presentAddressId;

    private AddressResponse presentAddress;

    private Long permanentAddressId;

    private AddressResponse permanentAddress;

    private String passportNumber;

    private String birthRegNumber;

    private String photo;

    private Long photoMediaId;

    private Long bloodGroupId;
}
