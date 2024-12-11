package com.ibas.brta.vehims.drivingLicense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.drivingLicense.model.DrivingLicenseClass;

import jakarta.transaction.Transactional;

@Repository
public interface DrivingLicenseClassRepository extends JpaRepository<DrivingLicenseClass, Long> {
    List<DrivingLicenseClass> findByDlInfoIdAndDlClassId(Long dlInfoId, Long dlClassId);

    List<DrivingLicenseClass> findByDlInfoIdOrDlClassId(Long dlInfoId, Long dlClassId);

    List<DrivingLicenseClass> findByDlServiceRequestId(Long dlServiceRequestId);

    List<DrivingLicenseClass> findByDlInfoId(Long dlInfoId);

    List<DrivingLicenseClass> findByDllId(Long dllId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO s_dl_info_classes(dl_info_id, dl_class_id, created_date) VALUES(:dlInfoId, :dlClassId, CURRENT_TIMESTAMP)", nativeQuery = true)
    void storeDLClasses(@Param("dlInfoId") Long dlInfoId, @Param("dlClassId") Long dlClassId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM s_dl_info_classes WHERE dl_info_id = :dlInfoId", nativeQuery = true)
    void deleteDLClassesByDlInfoId(@Param("dlInfoId") Long dlInfoId);

    // get service request by applicant id and application status
    @Query(value = "SELECT dl_class_id FROM s_dl_info_classes WHERE dl_service_request_id = :dlServiceRequestId", nativeQuery = true)
    List<Long> getDLClasseIdsByDlServiceRequestId(@Param("dlServiceRequestId") Long dlServiceRequestId);

    @Query(value = "SELECT dl_class_id FROM s_dl_info_classes WHERE dl_info_id = :dlInfoId", nativeQuery = true)
    List<Long> getDLClasseIdsByDlInfoId(@Param("dlInfoId") Long dlInfoId);

    @Query(value = "SELECT dl_class_id FROM s_dl_info_classes WHERE dll_id = :dllId", nativeQuery = true)
    List<Long> getDLClasseIdsByDllId(@Param("dllId") Long dllId);

    // New delete methods
    // @Transactional
    // @Modifying
    void deleteByDlServiceRequestId(Long dlServiceRequestId);

    void deleteByDllId(Long dllId);

    void deleteByDlInfoId(Long dlInfoId);

}
