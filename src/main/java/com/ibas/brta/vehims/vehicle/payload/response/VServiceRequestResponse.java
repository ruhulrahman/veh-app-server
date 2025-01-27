package com.ibas.brta.vehims.vehicle.payload.response;

import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VServiceRequestResponse {
    private Long id;
    private String serviceRequestNo;
    private Long vehicleInfoId;
    private VehicleInfoResponse vehicleInfo;
    private Long serviceId;
    private Long orgId;
    private Long applicantId;
    private UserNidInfoResponse applicantNidInfo;
    private LocalDateTime applicationDate;
    private Integer volumeNo;
    private Integer pageNo;
    private LocalDateTime forwardDateForInspection;
    private Long inspectorId;
    private Long inspectionStatusId;
    private String inspectionRemarks;
    private LocalDateTime inspectionDate;
    private LocalDateTime forwardDateForRevenue;
    private Long revenueCheckerId;
    private Long revenueStatusId;
    private String revenueRemarks;
    private LocalDateTime revenueCheckDate;
    private Long approvalId;
    private String approvalRemarks;
    private LocalDateTime approvalDate;
    private LocalDateTime rejectionDate;
    private Long applicationStatusId;
    private VehicleOwnerResponse vehicleOwner;
    private AddressResponse addressInfo;
    List<VehicleJointOwnerResponse> vehicleJointOwners;
}
