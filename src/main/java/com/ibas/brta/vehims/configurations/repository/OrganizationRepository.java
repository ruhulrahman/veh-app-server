package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
        Organization findByNameEn(String nameEn);

        List<Organization> findByIsActiveTrueOrderByNameEnAsc();

        // Complex query with JPQL and named parameters
        // @Query("SELECT s FROM Organization s WHERE (:nameEn IS NULL OR
        // LOWER(s.nameEn) " +
        // "LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE
        // LOWER(CONCAT('%', :nameEn, '%'))) "
        // +
        // "AND (:officeTypeId IS NULL OR s.officeTypeId = :officeTypeId) " +
        // " AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt
        // DESC")

        // Page<Organization> findListWithPaginationBySearch(
        // @Param("nameEn") String nameEn,
        // @Param("officeTypeId") Long officeTypeId,
        // @Param("divisionId") Long divisionId,
        // @Param("districtId") Long districtId,
        // @Param("isActive") Boolean isActive,
        // Pageable pageable);

        // Complex query with JPQL and named parameters
        @Query(value = "SELECT org.org_id, org.name_en, org.name_bn, " +
                        "org.post_code, org.address_en, org.address_bn, org.is_active, " +
                        "loc.name_en AS location_name_en, loc.name_bn AS location_name_bn, " +
                        "status.name_en AS office_type_name_en, status.name_bn AS office_type_name_bn" +
                        "FROM c_organizations org " +
                        "JOIN c_locations loc ON org.location_id = loc.location_id " +
                        "JOIN c_statuses status ON org.office_type_id = status.status_id " +
                        "WHERE (:nameEn IS NULL OR LOWER(org.name_en) LIKE LOWER(CONCAT('%', :nameEn,'%'))) " +
                        "AND (:officeTypeId IS NULL OR org.office_type_id = :officeTypeId) " +
                        "AND (:divisionId IS NULL OR loc.parent_location_id = :divisionId) " +
                        "AND (:districtId IS NULL OR loc.location_id = :districtId) " +
                        "AND (:isActive IS NULL OR org.is_active = :isActive)", nativeQuery = true)
        Page<Organization> findListWithPaginationBySearchWithNativeQuery(
                        @Param("nameEn") String nameEn,
                        @Param("officeTypeId") Long officeTypeId,
                        @Param("divisionId") Long divisionId,
                        @Param("districtId") Long districtId,
                        @Param("isActive") Boolean isActive,
                        Pageable pageable);
}