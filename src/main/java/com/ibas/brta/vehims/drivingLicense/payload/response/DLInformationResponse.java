package com.ibas.brta.vehims.drivingLicense.payload.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.CardDeliveryAddressResponse;
import com.ibas.brta.vehims.configurations.payload.response.OrganizationResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;

import jakarta.persistence.Column;

@Data
public class DLInformationResponse {

    private String dlNumber;

    private String dlReferenceNumber;

    private Long dlLanguageId;

    private Long applicationTypeId;

    private Long applicantTypeId;

    private Long licenseTypeId;

    private Long bloodGroupId;

    private Long issuedOfficeId;

    private LocalDateTime issueDate;

    private LocalDateTime expireDate;

    private Long eduQualificationId;

    private Long occupationId;

    private Long nationalityId;

    private Long maritalStatusId;

    private String spouseName;

    private String spouseContactNo;

    private Boolean isOtherCitizenship;

    private String officePhoneNumber;

    private String residencePhoneNumber;

    private String emergencyContactName;

    private String emergencyContactNumber;

    private Long emergencyContactRelationshipId;

    private String emergencyContactEmail;

    private Long presentAddressId;

    private Long permanentAddressId;

    private Long cdOptionId;
    private Long cardDeliveryAddressId;

    private Long statusId;

    private List<Long> dlVehicleClassIds;

    private UserNidInfoResponse applicantNidInfo;
    private AddressResponse permanentAddress;
    private AddressResponse presentAddress;
    private CardDeliveryAddressResponse cardDeliveryAddress;
    private OrganizationResponse issuedOffice;
}
