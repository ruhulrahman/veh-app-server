package com.ibas.brta.vehims.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.common.model.ServiceEconomicCode;

@Repository
public interface ServiceEconomicCodeRepository extends JpaRepository<ServiceEconomicCode, Long> {

    ServiceEconomicCode findByEconomicCode(String economicCode);

    ServiceEconomicCode findByEconomicDescriptionEn(String economicDescriptionEn);
}