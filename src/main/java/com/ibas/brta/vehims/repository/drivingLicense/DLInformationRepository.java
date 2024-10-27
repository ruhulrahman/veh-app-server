package com.ibas.brta.vehims.repository.drivingLicense;

import com.ibas.brta.vehims.model.drivingLicense.DLInformation;
import com.ibas.brta.vehims.payload.drivingLicense.DrivingLicenseApplicationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public interface DLInformationRepository extends JpaRepository<DLInformation, Long> {
    List<DLInformation> findByApplicantId(Integer applicantId);

    @Query(value = "SELECT\n" +
            "new com.ibas.brta.vehims.payload.drivingLicense.DrivingLicenseApplicationDto(" +
            "ROW_NUMBER() OVER (ORDER BY sr.dl_info_id),\n" +
            "sr.service_request_no,\n" +
            "u.name_en, cs1.name_en, cs2.name_en,\n" +
            "org.name_en, ni.nid_number, sr.application_date, cs.name_en)\n" +
            "FROM     dl_service_requests sr\n" +
            "INNER JOIN    dl_informations info ON sr.dl_info_id = info.dl_info_id\n" +
            "INNER JOIN    s_users u ON sr.applicant_id = u.user_id\n" +
            "INNER JOIN    s_user_nid_infos ni ON ni.user_id = u.user_id\n" +
            "INNER JOIN s_driving_learner_licenses sdll on sr.dl_service_request_id = sdll.dl_service_request_id\n" +
            "LEFT JOIN c_statuses cs on sr.application_status_id = cs.status_id\n" +
            "left join c_statuses cs1 on cs1.status_id=info.application_type_id\n" +
            "left join c_statuses cs2 on cs2.status_id=info.license_type_id\n" +
            "left join c_organizations org on org.org_id=sr.org_id\n" +
            "            WHERE" +
            "    sr.service_request_no  LIKE LOWER(CONCAT('%', :serviceRequestNo, '%')) AND" +
            "    sdll.learner_number  LIKE LOWER(CONCAT('%', :learnerNo, '%')) AND" +
            "    u.mobile  LIKE LOWER(CONCAT('%', :mobile, '%')) AND" +
            "    ni.nid_number LIKE LOWER(CONCAT('%', :nid, '%')) AND " +
            "    (sr.application_date is null or sr.application_date = :applicationDate)" +
            "", nativeQuery = true)
    Page<DrivingLicenseApplicationDto> searchDrivingLicenseApplications(String serviceRequestNo, String nid,
                                                                        String learnerNo, String mobile,
                                                                        Date applicationDate, Pageable pageable);
}
