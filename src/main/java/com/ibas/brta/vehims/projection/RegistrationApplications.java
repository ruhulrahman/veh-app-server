package com.ibas.brta.vehims.projection;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * To retrieve the views of the tables: v_vehicle_infos,
 * v_service_requests sr, s_users u
 * 
 * @author ashshakur.rahaman
 */
public interface RegistrationApplications {
    Long getSl();

    Long getServiceRequestId();

    String getServiceRequestNo();

    Long getVehicleInfoId();

    Long getVehicleClassId();

    String getVehicleClassName();

    String getChassisNumber();

    String getEngineNumber();

    Long getCcOrKw();

    Long getManufacturingYear();

    Long getApplicantId();

    Instant getAplicationDate();

    Long getApplicationStatusId();

    String getApplicationStatusName();

    String getApplicationStatusCode();

    Long getUserId();

    String getNid();

    String getMobile();
    
    LocalDateTime getForwardDateForInspection();
    Long getInspectorId();
    Long getInspectionStatusId();
    String getInspectionRemarks();
    LocalDateTime getInspectionDate();
    LocalDateTime getForwardDateForRevenue();
    Long getRevenueCheckerId();
    Long getRevenueStatusId();
    String getRevenueRemarks();
    LocalDateTime getRevenueCheckDate();
    Long getApprovalId();
    String getApprovalRemarks();
    LocalDateTime getApprovalDate();
    LocalDateTime getRejectionDate();
}
