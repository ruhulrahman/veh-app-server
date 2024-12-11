package com.ibas.brta.vehims.drivingLicense.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import com.ibas.brta.vehims.common.model.Address;
import com.ibas.brta.vehims.common.model.CardDeliveryAddress;
import com.ibas.brta.vehims.common.payload.request.CardDeliveryAddressRequest;
import com.ibas.brta.vehims.configurations.payload.request.AddressRequest;
import com.ibas.brta.vehims.userManagement.model.UserNidInfo;
import com.ibas.brta.vehims.userManagement.payload.request.UserNidInfoRequest;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DLApplicationPage2Request {

    // Service Request columns Start-------------------
    private Long id;
    private String serviceRequestNo;

    private Long orgId;

    private Long dlInfoId;

    private Long serviceId;

    private Long applicantId;

    @Min(value = 1, message = "Volume number must be at least 1")
    private Integer volumeNo;

    @Min(value = 1, message = "Page number must be at least 1")
    private Integer pageNo;

    private Long applicationStatusId;

    @PastOrPresent(message = "Application date cannot be in the future")
    private LocalDateTime applicationDate;
    // Service Request columns End ----------------------

    private Long examVenueId;

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

    private Integer stageCompleted;

    private AddressRequest permanentAddress;
    private AddressRequest presentAddress;
    private CardDeliveryAddressRequest cardDeliveryAddress;

}