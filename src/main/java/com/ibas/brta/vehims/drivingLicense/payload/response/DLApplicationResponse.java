package com.ibas.brta.vehims.drivingLicense.payload.response;

import lombok.Data;
import java.time.LocalDateTime;

import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.CardDeliveryAddressResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;

@Data
public class DLApplicationResponse {
    private Long id;
    private Long serviceRequestId;
    private String serviceRequestNo;
    private Long dlInfoId;
    private Long serviceId;
    private Long orgId;
    private Long applicantId;
    private LocalDateTime applicationDate;
    private Integer volumeNo;
    private Integer pageNo;
    private LocalDateTime forwardDateForInspection;
    private Long inspectorId;
    private Long inspectionStatusId;
    private String inspectionRemarks;
    private LocalDateTime inspectionDate;
    private Long dlExamStatusId;
    private String dlExamRemarks;
    private LocalDateTime dlExamDate;
    private Long approvalId;
    private String approvalRemarks;
    private LocalDateTime approvalDate;
    private LocalDateTime rejectionDate;
    private Long applicationStatusId;

    private Long examVenueId;
    private String officePhoneNumber;
    private UserNidInfoResponse applicantNidInfo;
    private DLInformationResponse dlInformation;
    private AddressResponse permanentAddress;
    private AddressResponse presentAddress;
    private CardDeliveryAddressResponse cardDeliveryAddress;

}
