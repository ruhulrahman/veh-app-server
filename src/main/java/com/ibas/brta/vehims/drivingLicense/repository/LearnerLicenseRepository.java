package com.ibas.brta.vehims.drivingLicense.repository;

import com.ibas.brta.vehims.drivingLicense.model.LearnerLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LearnerLicenseRepository extends JpaRepository<LearnerLicense, Long> {
        List<LearnerLicense> findByApplicantId(Long applicantId);

        List<LearnerLicense> getListByApplicantId(Long applicantId);

        LearnerLicense getItemByApplicantId(Long applicantId);

        LearnerLicense findByDlServiceRequestId(Long serviceRequestId);

        LearnerLicense findItemByExamDateAndExamVenueIdOrderByIdDesc(LocalDateTime examDate, Long examVenueId);

        Optional<LearnerLicense> findTopByExamDateAndExamVenueIdOrderByIdDesc(LocalDateTime examDate, Long examVenueId);

        Integer countByExamDateAndExamVenueId(LocalDateTime examDate, Long examVenueId);

}
