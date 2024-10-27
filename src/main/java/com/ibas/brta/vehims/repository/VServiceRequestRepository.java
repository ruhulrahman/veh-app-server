package com.ibas.brta.vehims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.vehicle.VServiceRequest;

import java.util.Optional;

@Repository
public interface VServiceRequestRepository extends JpaRepository<VServiceRequest, Long> {
    Optional<VServiceRequest> findByServiceRequestNo(String serviceRequestNo);
}
