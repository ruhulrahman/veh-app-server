package com.ibas.brta.vehims.repository;

import com.ibas.brta.vehims.model.vehicle.VehicleInfo;
import com.ibas.brta.vehims.projection.RegistrationApplications;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * An interface that handles data access for Registration Application (Combining
 * 3 entities: Vehicle Info, Service Request, User NID infos)
 *
 * @author ashshakur.rahaman
 * @version 1.0 10/22/24 Initial version
 */
@Repository
public interface RegistrationApplicationRepository extends JpaRepository<VehicleInfo, Long> {

        @Query(value = "SELECT" +
                        "    ROW_NUMBER() OVER (ORDER BY vi.vehicle_info_id) AS sl," +
                        "    sr.service_request_id AS serviceRequestId, " +
                        "    sr.service_request_no AS serviceRequestNo, " +
                        "    vi.vehicle_info_id AS vehicleInfoId, " +
                        "    vi.vehicle_class_id AS vehicleClassId, " +
                        "    cvc.name_en AS vehicleClassName, " +
                        "    vi.chassis_number AS chassisNumber, " +
                        "    vi.engine_number AS engineNumber, " +
                        "    vi.cc_or_kw AS ccOrKw, " +
                        "    vi.manufacturing_year AS manufactueringYear, " +
                        "    sr.applicant_id AS applicantId, " +
                        "    sr.application_date AS applicationDate, " +
                        "    sr.application_status_id AS applicationStatusId," +
                        "    cs.name_en applicationStatusName, " +
                        "    cs.status_code as applicationStatusCode, " +
                        "    u.user_id AS userId, " +
                        "    ni.nid_number AS nid, " +
                        "    u.mobile AS mobile, " +

                        "    sr.forward_date_for_inspection as forwardDateForInspection, " +
                        "    sr.inspector_id as inspectorId, " +
                        "    sr.inspection_status_id as inspectionStatusId, " +
                        "    sr.inspection_remarks as inspectionRemarks, " +
                        "    sr.inspection_date as inspectionDate, " +
                        "    sr.forward_date_for_revenue as forwardDateForRevenue, " +
                        "    sr.revenue_checker_id as revenueCheckerId, " +
                        "    sr.revenue_status_id as revenueStatusId, " +
                        "    sr.revenue_remarks as revenueRemarks, " +
                        "    sr.revenue_check_date as revenueCheckDate, " +
                        "    sr.approval_id as approvalId, " +
                        "    sr.approval_remarks as approvalRemarks, " +
                        "    sr.approval_date as approvalDate, " +
                        "    sr.rejection_date as rejectionDate" +
                        "            FROM " +
                        "    v_vehicle_infos vi " +
                        "    JOIN" +
                        "    v_service_requests sr ON vi.vehicle_info_id = sr.vehicle_info_id " +
                        "            JOIN" +
                        "    s_users u ON sr.applicant_id = u.user_id " +
                        "            LEFT JOIN" +
                        "    s_user_nid_infos ni ON ni.user_id = u.user_id" +
                        "    LEFT JOIN c_statuses cs on sr.application_status_id = cs.status_id" +
                        "    LEFT JOIN c_vehicle_classes cvc on vi.vehicle_class_id=cvc.vehicle_class_id" +
                        "            WHERE" +
                        "    sr.applicant_id = :userId AND" +
                        "    sr.service_request_no  LIKE LOWER(CONCAT('%', :serviceRequestNo, '%')) AND" +
                        "    vi.chassis_number  LIKE LOWER(CONCAT('%', :chassisNumber, '%')) AND" +
                        "    vi.engine_number  LIKE LOWER(CONCAT('%', :engineNumber, '%')) AND" +
                        "    u.mobile  LIKE LOWER(CONCAT('%', :mobile, '%')) AND" +
                        "    ni.nid_number LIKE LOWER(CONCAT('%', :nid, '%')) AND " +
                        "    (sr.application_date is null or sr.application_date = :applicationDate)" +
                        "", nativeQuery = true)
        Page<RegistrationApplications> getAuthUserVehicleRegistrationApplicationsV1(String serviceRequestNo,
                        String chassisNumber,
                        String engineNumber, String nid, String mobile,
                        Date applicationDate, Long userId, Pageable pageable);

        @Query(value = "SELECT" +
                        "    ROW_NUMBER() OVER (ORDER BY vi.vehicle_info_id) AS sl," +
                        "    sr.service_request_no AS serviceRequestNo, " +
                        "    vi.vehicle_info_id AS vehicleInfoId, " +
                        "    vi.vehicle_class_id AS vehicleClassId, " +
                        "    cvc.name_en AS vehicleClassName, " +
                        "    vi.chassis_number AS chassisNumber, " +
                        "    vi.engine_number AS engineNumber, " +
                        "    vi.cc_or_kw AS ccOrKw, " +
                        "    vi.manufacturing_year AS manufactueringYear, " +
                        "    sr.applicant_id AS applicantId, " +
                        "    sr.application_date AS applicationDate, " +
                        "    sr.application_status_id AS applicationStatusId," +
                        "    cs.name_en applicationStatusName, " +
                        "    u.user_id AS userId, " +
                        "    ni.nid_number AS nid, " +
                        "    u.mobile AS mobile, " +

                        "    sr.forward_date_for_inspection as forwardDateForInspection, " +
                        "    sr.inspector_id as inspectorId, " +
                        "    sr.inspection_status_id as inspectionStatusId, " +
                        "    sr.inspection_remarks as inspectionRemarks, " +
                        "    sr.inspection_date as inspectionDate, " +
                        "    sr.forward_date_for_revenue as forwardDateForRevenue, " +
                        "    sr.revenue_checker_id as revenueCheckerId, " +
                        "    sr.revenue_status_id as revenueStatusId, " +
                        "    sr.revenue_remarks as revenueRemarks, " +
                        "    sr.revenue_check_date as revenueCheckDate, " +
                        "    sr.approval_id as approvalId, " +
                        "    sr.approval_remarks as approvalRemarks, " +
                        "    sr.approval_date as approvalDate, " +
                        "    sr.rejection_date as rejectionDate" +
                        "            FROM " +
                        "    v_vehicle_infos vi " +
                        "    JOIN" +
                        "    v_service_requests sr ON vi.vehicle_info_id = sr.vehicle_info_id " +
                        "            JOIN" +
                        "    s_users u ON sr.applicant_id = u.user_id " +
                        "            LEFT JOIN" +
                        "    s_user_nid_infos ni ON ni.user_id = u.user_id" +
                        "    LEFT JOIN c_statuses cs on sr.application_status_id = cs.status_id" +
                        "    LEFT JOIN c_vehicle_classes cvc on vi.vehicle_class_id=cvc.vehicle_class_id" +
                        "            WHERE" +
                        "    sr.service_request_no  LIKE LOWER(CONCAT('%', :serviceRequestNo, '%')) AND" +
                        "    vi.chassis_number  LIKE LOWER(CONCAT('%', :chassisNumber, '%')) AND" +
                        "    vi.engine_number  LIKE LOWER(CONCAT('%', :engineNumber, '%')) AND" +
                        "    u.mobile  LIKE LOWER(CONCAT('%', :mobile, '%')) AND" +
                        "    ni.nid_number LIKE LOWER(CONCAT('%', :nid, '%')) AND " +
                        "    (sr.application_date is null or sr.application_date = :applicationDate)" +
                        "", nativeQuery = true)
        Page<RegistrationApplications> searchVehRegApplications(String serviceRequestNo, String chassisNumber,
                        String engineNumber, String nid, String mobile,
                        Date applicationDate, Pageable pageable);

}
