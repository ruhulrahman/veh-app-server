package com.ibas.brta.vehims.drivingLicense.repository;

import com.ibas.brta.vehims.drivingLicense.model.DLServiceMedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DLServiceMediaRepository extends JpaRepository<DLServiceMedia, Long> {

        List<DLServiceMedia> findByServiceRequestId(Long serviceRequestId);

        DLServiceMedia findByMediaId(Long mediaId);

        @Modifying
    void deleteByMediaId(Long mediaId);
}
