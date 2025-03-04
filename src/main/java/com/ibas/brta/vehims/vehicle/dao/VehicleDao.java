package com.ibas.brta.vehims.vehicle.dao;

import com.ibas.brta.vehims.drivingLicense.payload.request.GetDrivingLicenseApplicationRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DrivingLicenseApplicationDto;
import com.ibas.brta.vehims.projection.RegistrationApplications;
import com.ibas.brta.vehims.util.Utils;
import com.ibas.brta.vehims.vehicle.payload.request.ApplicationFilterRequest;
import com.ibas.brta.vehims.vehicle.payload.response.VehRegistrationApplicationDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Slf4j
@Repository
public class VehicleDao {
    EntityManager entityManager;

    public VehicleDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

//    String qStr = "SELECT" +
//            "    ROW_NUMBER() OVER (ORDER BY vi.vehicle_info_id) AS sl," +
//            "    sr.service_request_id AS serviceRequestId, " +
//            "    sr.service_request_no AS serviceRequestNo, " +
//            "    vi.vehicle_info_id AS vehicleInfoId, " +
//            "    vi.vehicle_class_id AS vehicleClassId, " +
//            "    cvc.name_en AS vehicleClassName, " +
//            "    vi.chassis_number AS chassisNumber, " +
//            "    vi.engine_number AS engineNumber, " +
//            "    vi.cc_or_kw AS ccOrKw, " +
//            "    vi.manufacturing_year AS manufacturingYear, " +
//            "    sr.applicant_id AS applicantId, " +
//            "    sr.application_date AS applicationDate, " +
//            "    sr.application_status_id AS applicationStatusId," +
//            "    cs.name_en AS applicationStatusName, " +
//            "    cs.color_name AS applicationStatusColor, " +
//            "    cs.status_code AS applicationStatusCode, " +
//            "    u.user_id AS userId, " +
//            "    ni.nid_number AS nid, " +
//            "    u.mobile AS mobile, " +
//            "    sr.forward_date_for_inspection AS forwardDateForInspection, " +
//            "    sr.inspector_id AS inspectorId, " +
//            "    sr.inspection_status_id AS inspectionStatusId, " +
//            "    sr.inspection_remarks AS inspectionRemarks, " +
//            "    sr.inspection_date AS inspectionDate, " +
//            "    sr.forward_date_for_revenue AS forwardDateForRevenue, " +
//            "    sr.revenue_checker_id AS revenueCheckerId, " +
//            "    sr.revenue_status_id AS revenueStatusId, " +
//            "    sr.revenue_remarks AS revenueRemarks, " +
//            "    sr.revenue_check_date AS revenueCheckDate, " +
//            "    sr.approval_id AS approvalId, " +
//            "    sr.approval_remarks AS approvalRemarks, " +
//            "    sr.approval_date AS approvalDate, " +
//            "    sr.rejection_date AS rejectionDate" +
//            "            FROM " +
//            "    v_vehicle_infos vi " +
//            "    JOIN" +
//            "    v_service_requests sr ON vi.vehicle_info_id = sr.vehicle_info_id " +
//            "            JOIN" +
//            "    s_users u ON sr.applicant_id = u.user_id " +
//            "            LEFT JOIN" +
//            "    s_user_nid_infos ni ON ni.user_id = u.user_id" +
//            "    LEFT JOIN c_statuses cs on sr.application_status_id = cs.status_id" +
//            "    LEFT JOIN c_vehicle_classes cvc on vi.vehicle_class_id=cvc.vehicle_class_id" +
//            "            WHERE 1=1";


//    String qStr = "SELECT new com.ibas.brta.vehims.vehicle.payload.response.VehRegistrationApplicationDTO(" +
//            "    ROW_NUMBER() OVER (ORDER BY vi.vehicle_info_id), " +
//            "    sr.service_request_id, " +
//            "    sr.service_request_no, " +
//            "    vi.vehicle_info_id, " +
//            "    vi.vehicle_class_id, " +
//            "    cvc.name_en, " +
//            "    vi.chassis_number, " +
//            "    vi.engine_number, " +
//            "    vi.cc_or_kw, " +
//            "    vi.manufacturing_year, " +
//            "    sr.applicant_id, " +
//            "    sr.application_date, " +
//            "    sr.application_status_id, " +
//            "    cs.name_en, " +
//            "    cs.color_name, " +
//            "    cs.status_code, " +
//            "    u.user_id, " +
//            "    ni.nid_number, " +
//            "    u.mobile, " +
//            "    sr.forward_date_for_inspection, " +
//            "    sr.inspector_id, " +
//            "    sr.inspection_status_id, " +
//            "    sr.inspection_remarks, " +
//            "    sr.inspection_date, " +
//            "    sr.forward_date_for_revenue, " +
//            "    sr.revenue_checker_id, " +
//            "    sr.revenue_status_id, " +
//            "    sr.revenue_remarks, " +
//            "    sr.revenue_check_date, " +
//            "    sr.approval_id, " +
//            "    sr.approval_remarks, " +
//            "    sr.approval_date, " +
//            "    sr.rejection_date" +
//            ") " +
//            " FROM " +
//            "    v_vehicle_infos vi " +
//            "    JOIN v_service_requests sr ON vi.vehicle_info_id = sr.vehicle_info_id " +
//            "    JOIN s_users u ON sr.applicant_id = u.user_id " +
//            "    LEFT JOIN s_user_nid_infos ni ON ni.user_id = u.user_id " +
//            "    LEFT JOIN c_statuses cs ON sr.application_status_id = cs.status_id " +
//            "    LEFT JOIN c_vehicle_classes cvc ON vi.vehicle_class_id = cvc.vehicle_class_id " +
//            " WHERE 1=1";

    String qStr = "SELECT new com.ibas.brta.vehims.vehicle.payload.response.VehRegistrationApplicationDTO(" +
            "sr.id, sr.serviceRequestNo, sr.vehicleInfoId, vi.vehicleClassId, cvc.nameEn, " +
            "vi.chassisNumber, vi.engineNumber, vi.ccOrKw, vi.manufacturingYear, sr.applicantId, " +
            "sr.applicationDate, sr.applicationStatusId, cs.nameEn, cs.colorName, cs.statusCode, " +
            "ni.userId, ni.nidNumber, u.mobile, sr.forwardDateForInspection, sr.inspectorId, " +
            "sr.inspectionStatusId, sr.inspectionRemarks, sr.inspectionDate, sr.forwardDateForRevenue, " +
            "sr.revenueCheckerId, sr.revenueStatusId, sr.revenueRemarks, sr.revenueCheckDate, " +
            "sr.approvalId, sr.approvalRemarks, sr.approvalDate, sr.rejectionDate) " +
            "FROM VehicleInfo vi " +
            " LEFT JOIN VServiceRequest sr ON vi.id = sr.vehicleInfoId " +
            " LEFT JOIN User u ON sr.applicantId = u.id" +
            " LEFT JOIN UserNidInfo ni ON ni.userId = u.id" +
            " LEFT JOIN Status cs on sr.applicationStatusId = cs.id" +
            " LEFT join VehicleClass cvc on vi.vehicleClassId=cvc.id" +
            " LEFT join Organization org on org.id=sr.orgId" +
            " WHERE 1=1";


    public Page<VehRegistrationApplicationDTO> searchVehRegApplicationsForMvi(Pageable pageable, ApplicationFilterRequest filters) {

        String serviceRequestNo = filters.getServiceRequestNo();
        String chassisNumber = filters.getChassisNumber();
        String engineNumber = filters.getEngineNumber();
        String nid = filters.getNid();
        String mobile = filters.getMobile();
        Date applicationDate = filters.getApplicationDate();
        Long applicantId = filters.getApplicantId();
        Long inspectorId = filters.getInspectorId();
        Long applicationStatusId = filters.getApplicationStatusId();
        Long orgId = filters.getOrgId();

        try {
            StringBuilder querySt = new StringBuilder();
            querySt.append(qStr);

            if (Utils.notNullOrEmpty(orgId)) {
                querySt.append(" AND sr.orgId = :orgId");
            }

            if (Utils.notNullOrEmpty(serviceRequestNo)) {
                querySt.append(" AND sr.serviceRequestNo LIKE :serviceRequestNo");
            }

            if (Utils.notNullOrEmpty(chassisNumber)) {
                querySt.append(" AND vi.chassisNumber = LOWER(:chassisNumber)");
            }
            if (Utils.notNullOrEmpty(engineNumber)) {
                querySt.append(" AND vi.engineNumber = LOWER(:engineNumber)");
            }


//            if (Utils.notNullOrEmpty(applicantId)) {
//                querySt.append(" AND sr.applicant_id = :applicantId");
//            }

            if (Utils.notNullOrEmpty(inspectorId)) {
                querySt.append(" AND sr.inspectorId = :inspectorId");
            }

            if (Utils.notNullOrEmpty(mobile)) {
                querySt.append(" AND u.mobile LIKE :mobile");
            }

            if (Utils.notNullOrEmpty(nid)) {
                querySt.append(" AND ni.nidNumber LIKE :nid");
            }

            if (applicationDate != null) {
                querySt.append(" AND DATE(sr.applicationDate) = :applicationDate");
            }

            if (Utils.notNullOrEmpty(applicationStatusId)) {
                querySt.append(" AND sr.applicationStatusId IS NOT NULL");
                querySt.append(" AND sr.applicationStatusId != :applicationStatusId");
            }

            // Execute the query
            Query query = entityManager.createQuery(querySt.toString(), VehRegistrationApplicationDTO.class);


            if (Utils.notNullOrEmpty(orgId)) {
                query.setParameter("orgId", orgId);
            }

            if (Utils.notNullOrEmpty(serviceRequestNo)) {
                query.setParameter("serviceRequestNo", serviceRequestNo);
            }
            if (Utils.notNullOrEmpty(chassisNumber)) {
                query.setParameter("chassisNumber", chassisNumber.toLowerCase());
            }
            if (Utils.notNullOrEmpty(engineNumber)) {
                query.setParameter("engineNumber", engineNumber.toLowerCase());
            }
            // Get the raw result list
//            if (applicantId != null) {
//                query.setParameter("applicantId", applicantId);
//            }

            if (inspectorId != null) {
                query.setParameter("inspectorId", inspectorId);
            }

            if (applicationDate != null) {
                query.setParameter("applicationDate", applicationDate);
            }

            if (Utils.notNullOrEmpty(applicationStatusId)) {
                query.setParameter("applicationStatusId", applicationStatusId);
            }
            if (Utils.notNullOrEmpty(mobile)) {
                query.setParameter("mobile", mobile);
            }
            if (Utils.notNullOrEmpty(nid)) {
                query.setParameter("nid", nid);
            }

            List<VehRegistrationApplicationDTO> resultList = query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();

            long totalCount = ((org.hibernate.query.Query<?>) query).unwrap(org.hibernate.query.Query.class)
                    .getResultList().size();

            return new PageImpl<>(resultList, pageable, totalCount);
        } catch (Exception e) {
            log.error("error -----> {}", e.getMessage());
        }

        return null;
    }

}
