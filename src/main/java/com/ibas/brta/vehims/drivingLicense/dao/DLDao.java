package com.ibas.brta.vehims.drivingLicense.dao;

import com.ibas.brta.vehims.drivingLicense.payload.request.GetDrivingLicenseApplicationRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DrivingLicenseApplicationDto;
import com.ibas.brta.vehims.userManagement.payload.response.UserOfficeRoleResponse;
import com.ibas.brta.vehims.util.Utils;
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
public class DLDao {

    EntityManager entityManager;

    public DLDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    String qStr = "SELECT" +
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
            " WHERE 1=1";

    public Page<DrivingLicenseApplicationDto> searchDrivingLicenseApplicationsForMvi(Pageable pageable, GetDrivingLicenseApplicationRequest filters) {

        String serviceRequestNo = filters.getServiceRequestNo();
        String nid = filters.getNid();
        String learnerNo = filters.getLearnerNo();
        String mobile = filters.getMobile();
        Date applicationDate = filters.getApplicationDate();
        Long applicantId = filters.getApplicantId();
        Long applicationStatusId = filters.getApplicationStatusId();
        Long orgId = filters.getOrgId();
        Long inspectorId = filters.getInspectorId();

        try {
            StringBuilder querySt = new StringBuilder();
            querySt.append(qStr);

//            if (Utils.notNullOrEmpty(applicantId)) {
//                querySt.append(" AND sr.applicantId = :applicantId");
//            }

            if (Utils.notNullOrEmpty(inspectorId)) {
                querySt.append(" AND sr.inspectorId = :inspectorId");
            }

            if (Utils.notNullOrEmpty(orgId)) {
                querySt.append(" AND sr.orgId = :orgId");
            }

            if (Utils.notNullOrEmpty(serviceRequestNo)) {
                querySt.append(" AND LOWER(sr.serviceRequestNo) LIKE :serviceRequestNo");
            }

            if (Utils.notNullOrEmpty(learnerNo)) {
                querySt.append(" AND LOWER(dll.learnerNumber) LIKE :learnerNo");
            }

            if (Utils.notNullOrEmpty(mobile)) {
                querySt.append(" AND LOWER(u.mobile) LIKE :mobile");
            }

            if (Utils.notNullOrEmpty(nid)) {
                querySt.append(" AND LOWER(ni.nidNumber) LIKE :nid");
            }

            if (applicationDate != null) {
                querySt.append(" AND DATE(sr.applicationDate) = :applicationDate");
            }

            if (Utils.notNullOrEmpty(applicationStatusId)) {
                querySt.append(" AND sr.applicationStatusId IS NOT NULL");
                querySt.append(" AND sr.applicationStatusId != :applicationStatusId");
            }

            // Execute the query
            Query query = entityManager.createQuery(querySt.toString(), DrivingLicenseApplicationDto.class);

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

            if (Utils.notNullOrEmpty(orgId)) {
                query.setParameter("orgId", orgId);
            }

            if (Utils.notNullOrEmpty(serviceRequestNo)) {
                query.setParameter("serviceRequestNo", serviceRequestNo);
            }

            if (Utils.notNullOrEmpty(learnerNo)) {
                query.setParameter("learnerNo", learnerNo);
            }
            if (Utils.notNullOrEmpty(mobile)) {
                query.setParameter("mobile", mobile);
            }
            if (Utils.notNullOrEmpty(nid)) {
                query.setParameter("nid", nid);
            }

            List<DrivingLicenseApplicationDto> resultList = query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
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
    public Page<DrivingLicenseApplicationDto> searchDrivingLicenseApplications(String serviceRequestNo, String nid,
                                                                        String learnerNo, String mobile,
                                                                        Date applicationDate, Long orgId, Long userId, Long applicationStatusId, Pageable pageable) {

        try {
            StringBuilder querySt = new StringBuilder();
            querySt.append(qStr);

            if (Utils.notNullOrEmpty(userId)) {
                querySt.append(" AND sr.applicantId = :userId");
            }

            if (Utils.notNullOrEmpty(orgId)) {
                querySt.append(" AND sr.orgId = :orgId");
            }

            if (Utils.notNullOrEmpty(serviceRequestNo)) {
                querySt.append(" AND LOWER(sr.serviceRequestNo) LIKE :serviceRequestNo");
            }

            if (Utils.notNullOrEmpty(learnerNo)) {
                querySt.append(" AND LOWER(dll.learnerNumber) LIKE :learnerNo");
            }

            if (Utils.notNullOrEmpty(mobile)) {
                querySt.append(" AND LOWER(u.mobile) LIKE :mobile");
            }

            if (Utils.notNullOrEmpty(nid)) {
                querySt.append(" AND LOWER(ni.nidNumber) LIKE :nid");
            }

            if (applicationDate != null) {
                querySt.append(" AND DATE(sr.applicationDate) = :applicationDate");
            }

            if (Utils.notNullOrEmpty(applicationStatusId)) {
                querySt.append(" AND sr.applicationStatusId IS NOT NULL");
                querySt.append(" AND sr.applicationStatusId != :applicationStatusId");
            }

            // Execute the query
            Query query = entityManager.createQuery(querySt.toString(), DrivingLicenseApplicationDto.class);

            // Get the raw result list
            if (userId != null) {
                query.setParameter("userId", userId);
            }

            if (applicationDate != null) {
                query.setParameter("applicationDate", applicationDate);
            }

            if (Utils.notNullOrEmpty(applicationStatusId)) {
                query.setParameter("applicationStatusId", applicationStatusId);
            }

            if (Utils.notNullOrEmpty(orgId)) {
                query.setParameter("orgId", orgId);
            }

            if (Utils.notNullOrEmpty(serviceRequestNo)) {
                query.setParameter("serviceRequestNo", serviceRequestNo);
            }

            if (Utils.notNullOrEmpty(learnerNo)) {
                query.setParameter("learnerNo", learnerNo);
            }
            if (Utils.notNullOrEmpty(mobile)) {
                query.setParameter("mobile", mobile);
            }
            if (Utils.notNullOrEmpty(nid)) {
                query.setParameter("nid", nid);
            }

            List<DrivingLicenseApplicationDto> resultList = query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();

            long totalCount = ((org.hibernate.query.Query<?>) query).unwrap(org.hibernate.query.Query.class)
                    .getResultList().size();

            return new PageImpl<>(resultList, pageable, totalCount);
        } catch (Exception e) {
            log.error("error {}", e.getMessage());
        }

        return null;
    }
}
