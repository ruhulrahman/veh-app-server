package com.ibas.brta.vehims.configurations.repository;

import com.ibas.brta.vehims.configurations.model.ElementData;
import com.ibas.brta.vehims.configurations.model.GovernmentOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GovernmentOfficeRepository extends JpaRepository<GovernmentOffice, Long> {

    GovernmentOffice findByNameEn(String nameEn);

    GovernmentOffice findByNameBn(String nameBn);

    GovernmentOffice findByFullCode(String fullCode);

    List<GovernmentOffice> findByStatus(String status);

    List<GovernmentOffice> findByParentCode(String parentCode);

}