package com.ibas.brta.vehims.drivingLicense.repository;

import com.ibas.brta.vehims.drivingLicense.model.DLInformation;
import com.ibas.brta.vehims.drivingLicense.model.DLServiceRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DrivingLicenseApplicationDto;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
                        "    (case when :userId is null then true else sr.applicantId = :userId end) AND" +
                        "    (case when :serviceRequestNo is null or :serviceRequestNo = '' then true else sr.serviceRequestNo = LOWER(:serviceRequestNo) end) AND"
                        +
                        "    (case when :learnerNo is null or :learnerNo = '' then true else dll.learnerNumber = LOWER(:learnerNo) end) AND"
                        +
                        "    (case when :mobile is null or :mobile = '' then true else u.mobile = LOWER(:mobile) end) AND"
                        +
                        "    (case when :nid is null or :nid = '' then true else ni.nidNumber = LOWER(:nid) end) AND " +
                        "    (case when :applicationDate is null then true else cast(sr.applicationDate as date) = :applicationDate end)")
        Page<DrivingLicenseApplicationDto> searchDrivingLicenseApplications(String serviceRequestNo, String nid,
                        String learnerNo, String mobile,
                        Date applicationDate, Long userId, Pageable pageable);

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
}
