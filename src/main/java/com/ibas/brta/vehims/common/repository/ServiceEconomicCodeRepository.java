package com.ibas.brta.vehims.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.common.model.ServiceEconomicCode;

import java.util.Optional;

@Repository
public interface ServiceEconomicCodeRepository extends JpaRepository<ServiceEconomicCode, Long> {

    Optional<ServiceEconomicCode> findByEconomicCode(String economicCode);

    Optional<ServiceEconomicCode> findByEconomicDescriptionEn(String economicDescriptionEn);
}