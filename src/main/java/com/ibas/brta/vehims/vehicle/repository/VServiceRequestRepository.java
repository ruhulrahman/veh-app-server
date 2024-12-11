package com.ibas.brta.vehims.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.vehicle.model.VServiceRequest;

import java.util.Optional;

@Repository
public interface VServiceRequestRepository extends JpaRepository<VServiceRequest, Long> {
    Optional<VServiceRequest> findByServiceRequestNo(String serviceRequestNo);
}
