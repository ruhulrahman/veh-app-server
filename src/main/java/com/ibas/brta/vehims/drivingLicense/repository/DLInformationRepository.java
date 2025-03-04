package com.ibas.brta.vehims.drivingLicense.repository;

import com.ibas.brta.vehims.drivingLicense.model.DLInformation;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceRequest;
import com.ibas.brta.vehims.drivingLicense.payload.projections.DrivingLicenseApplicationProjection;
import com.ibas.brta.vehims.drivingLicense.payload.response.DrivingLicenseApplicationDto;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface DLInformationRepository extends JpaRepository<DLInformation, Long> {
        List<DLInformation> findByNidNumber(String nidNumber);

        @Query(value = "SELECT" +
                        " new com.ibas.brta.vehims.drivingLicense.payload.response.DrivingLicenseApplicationDto(" +
                        " sr.id, sr.serviceRequestNo, u.nameEn, cs1.nameEn, cs2.nameEn, org.nameEn, ni.nidNumber," +
                        " sr.applicationDate, cs.id, cs.nameEn, cs.statusCode, cs.colorName," +
                        " sr.dlExamStatusId, sr.dlExamRemarks, sr.dlExamDate, sr.approvalRemarks, sr.approvalDate, sr.rejectionDate, sr.isLearnerFeePaid, sr.isLicenseFeePaid)"
                        +
                        " FROM DLInformation info" +
                        " LEFT JOIN DLServiceRequest sr ON sr.dlInfoId = info.id" +
                        " LEFT JOIN User u ON sr.applicantId = u.id" +
                        " LEFT JOIN UserNidInfo ni ON ni.userId = u.id" +
                        " LEFT JOIN LearnerLicense dll on sr.id = dll.dlServiceRequestId" +
                        " LEFT JOIN Status cs on sr.applicationStatusId = cs.id" +
                        " LEFT join Status cs1 on cs1.id=info.applicationTypeId" +
                        " LEFT join Status cs2 on cs2.id=info.licenseTypeId" +
                        " LEFT join Organization org on org.id=sr.orgId" +
                        " WHERE " +
                        "    (case when :orgId is null then true else sr.orgId = :orgId end) AND" +
                        "    (case when :userId is null then true else sr.applicantId = :userId end) AND" +
                        "    (case when :serviceRequestNo is null or :serviceRequestNo = '' then true else sr.serviceRequestNo = LOWER(:serviceRequestNo) end) AND"
                        +
                        "    (case when :learnerNo is null or :learnerNo = '' then true else dll.learnerNumber = LOWER(:learnerNo) end) AND"
                        +
                        "    (case when :mobile is null or :mobile = '' then true else u.mobile = LOWER(:mobile) end) AND"
                        +
                        "    (case when :nid is null or :nid = '' then true else ni.nidNumber = LOWER(:nid) end) AND " +
//                        "    (case when :applicationDate is null then true else cast(sr.applicationDate as date) = cast(:applicationDate as date) end)")
//                        "    (:applicationDate is null or DATE(sr.applicationDate) = :applicationDate)")
//                        "    (:applicationDate IS NULL OR DATE(sr.applicationDate) = CAST(:applicationDate AS DATE))")
                        "    (case when :applicationDate is null then true else cast(sr.applicationDate as date) = :applicationDate end)")
                Page<DrivingLicenseApplicationDto> searchDrivingLicenseApplications(String serviceRequestNo, String nid,
                                                                            String learnerNo, String mobile,
                                                                            Date applicationDate, Long orgId, Long userId, Pageable pageable);

        @Transactional
        @Modifying
        @Query(value = "INSERT INTO s_dl_info_classes(dl_info_id, dl_class_id, created_date) VALUES(:dlInfoId, :dlClassId, CURRENT_TIMESTAMP)", nativeQuery = true)
        void storeDLClasses(@Param("dlInfoId") Long dlInfoId, @Param("dlClassId") Long dlClassId);

        @Transactional
        @Modifying
        @Query(value = "DELETE FROM s_dl_info_classes WHERE dl_info_id = :dlInfoId", nativeQuery = true)
        void deleteDLClassesByDlInfoId(@Param("dlInfoId") Long dlInfoId);

        // get service request by applicant id and application status
        @Query(value = "SELECT dl_class_id FROM s_dl_info_classes WHERE dl_info_id = :dlInfoId", nativeQuery = true)
        List<Long> getApplicationDLClasseIdsByDlInfoId(@Param("dlInfoId") Long dlInfoId);

        @Query(value = "SELECT dl_class_id FROM s_dl_info_classes WHERE dll_id = :dllId", nativeQuery = true)
        List<Long> getApplicationDLClasseIdsByDllId(@Param("dllId") Long dllId);


        @Query(value = "SELECT " +
                "    ROW_NUMBER() OVER (ORDER BY info.dl_info_id) AS sl," +
                "    sr.dl_service_request_id AS serviceRequestId, " +
                "    sr.service_request_no AS serviceRequestNo, " +
                "    u.name_en AS applicantName, " +
                "    cs1.name_en AS applicationType, " +
                "    cs2.name_en AS licenseType, " +
                "    org.name_en AS orgName, " +
                "    ni.nid_number AS nidNumber, " +
                "    sr.application_date AS applicationDate, " +
                "    cs.status_id AS applicationStatusId, " +
                "    cs.name_en AS applicationStatusName, " +
                "    cs.status_code AS applicationStatusCode, " +
                "    cs.color_name AS applicationStatusColor, " +
                "    sr.dl_exam_status_id AS dlExamStatusId, " +
                "    sr.dl_exam_remarks AS dlExamRemarks, " +
                "    sr.dl_exam_date AS dlExamDate, " +
                "    sr.approval_remarks AS approvalRemarks, " +
                "    sr.approval_date AS approvalDate, " +
                "    sr.rejection_date AS rejectionDate, " +
                "    sr.is_learner_fee_paid AS isLearnerFeePaid, " +
                "    sr.is_license_fee_paid AS isLicenseFeePaid " +
                "FROM " +
                "    dl_informations info " +
                "LEFT JOIN " +
                "    dl_service_requests sr ON sr.dl_info_id = info.dl_info_id " +
                "LEFT JOIN " +
                "    s_users u ON sr.applicant_id = u.user_id " +
                "LEFT JOIN " +
                "    s_user_nid_infos ni ON ni.user_id = u.user_id " +
                "LEFT JOIN " +
                "    s_driving_learner_licenses dll ON sr.dl_service_request_id = dll.dl_service_request_id " +
                "LEFT JOIN " +
                "    c_statuses cs ON sr.application_status_id = cs.status_id " +
                "LEFT JOIN " +
                "    c_statuses cs1 ON cs1.status_id = info.application_type_id " +
                "LEFT JOIN " +
                "    c_statuses cs2 ON cs2.status_id = info.license_type_id " +
                "LEFT JOIN " +
                "    c_organizations org ON org.org_id = sr.org_id " +
                "WHERE " +
                "    (case when :orgId is null then true else sr.org_id = :orgId end) AND " +
                "    (case when :userId is null then true else sr.applicant_id = :userId end) AND " +
                "    (case when :serviceRequestNo is null or :serviceRequestNo = '' then true else sr.service_request_no = LOWER(:serviceRequestNo) end) AND "
                +
                "    (case when :learnerNo is null or :learnerNo = '' then true else dll.learner_number = LOWER(:learnerNo) end) AND "
                +
                "    (case when :mobile is null or :mobile = '' then true else u.mobile = LOWER(:mobile) end) AND "
                +
                "    (case when :nid is null or :nid = '' then true else ni.nid_number = LOWER(:nid) end) AND " +
                "    (case when cast(:applicationDate as date) is null then true else date(sr.application_date) = :applicationDate end)",
                nativeQuery = true)
        Page<DrivingLicenseApplicationProjection> searchDrivingLicenseApplications22(
                @Param("serviceRequestNo") String serviceRequestNo,
                @Param("nid") String nid,
                @Param("learnerNo") String learnerNo,
                @Param("mobile") String mobile,
                @Param("applicationDate") Date applicationDate, // Use String for flexibility
                @Param("orgId") Long orgId,
                @Param("userId") Long userId,
                Pageable pageable);

        @Query(value = "SELECT COUNT(sr.dl_service_request_id) " +
                "FROM dl_informations dl " +
                "JOIN dl_service_requests sr ON dl.dl_info_id = sr.dl_info_id " +
                "WHERE (:serviceId IS NULL OR sr.service_id = :serviceId) " +
                "AND (:applicationStatusId IS NULL OR sr.application_status_id = :applicationStatusId) " +
                "AND (:orgId IS NULL OR sr.org_id = :orgId) " +
                "AND (:licenseTypeId IS NULL OR dl.license_type_id = :licenseTypeId) " +
                "AND (:applicationDate IS NULL OR DATE(sr.application_date) = CAST(:applicationDate AS DATE))",
                nativeQuery = true)
        Long getDrivingLicenseReport(Long serviceId, Long applicationStatusId, Long orgId, Long licenseTypeId, String applicationDate);
}
