package com.ibas.brta.vehims.vehicle.repository;

import com.ibas.brta.vehims.vehicle.model.VServiceMedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VServiceMediaRepository extends JpaRepository<VServiceMedia, Long> {

    List<VServiceMedia> findByServiceRequestId(Long serviceRequestId);

    VServiceMedia findByMediaId(Long mediaId);

    @Modifying
    void deleteByMediaId(Long mediaId);
}
