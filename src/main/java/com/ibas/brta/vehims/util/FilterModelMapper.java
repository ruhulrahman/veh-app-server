package com.ibas.brta.vehims.util;

import com.ibas.brta.vehims.model.rbac.Menu;
import com.ibas.brta.vehims.payload.response.MenuResponse;
import com.ibas.brta.vehims.payload.response.RegistrationApplicationResponse;
import com.ibas.brta.vehims.projection.RegistrationApplications;

import java.time.Instant;

public class FilterModelMapper {

    public static RegistrationApplicationResponse ProjectionToResponse(RegistrationApplications applications) {
        RegistrationApplicationResponse response = new RegistrationApplicationResponse();

        response.setSl(applications.getSl());
        response.setServiceRequestId(applications.getServiceRequestId());
        response.setServiceRequestNo(applications.getServiceRequestNo());
        response.setVehicleInfoId(applications.getVehicleInfoId());
        response.setVehicleClassId(applications.getVehicleClassId());
        response.setVehicleClassName(applications.getVehicleClassName());
        response.setChassisNumber(applications.getChassisNumber());
        response.setEngineNumber(applications.getEngineNumber());
        response.setCcOrKw(applications.getCcOrKw());
        response.setManufacturingYear(applications.getManufacturingYear());
        response.setApplicantId(applications.getApplicantId());
        response.setApplicationStatusId(applications.getApplicationStatusId());
        response.setApplicationStatusName(applications.getApplicationStatusName());
        response.setApplicationStatusCode(applications.getApplicationStatusCode());
        response.setAplicationDate(applications.getAplicationDate());

        response.setForwardDateForInspection(applications.getForwardDateForInspection());
        response.setInspectorId(applications.getInspectorId());
        response.setInspectionStatusId(applications.getInspectionStatusId());
        response.setInspectionRemarks(applications.getInspectionRemarks());
        response.setInspectionDate(applications.getInspectionDate());
        response.setForwardDateForRevenue(applications.getForwardDateForRevenue());
        response.setRevenueCheckerId(applications.getRevenueCheckerId());
        response.setRevenueStatusId(applications.getRevenueStatusId());
        response.setRevenueRemarks(applications.getRevenueRemarks());
        response.setRevenueCheckDate(applications.getRevenueCheckDate());
        response.setApprovalId(applications.getApprovalId());
        response.setApprovalRemarks(applications.getApprovalRemarks());
        response.setApprovalDate(applications.getApprovalDate());
        response.setRejectionDate(applications.getRejectionDate());
        return response;
    }

}