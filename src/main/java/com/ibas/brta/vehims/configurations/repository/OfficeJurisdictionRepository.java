package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.OfficeJurisdiction;
import com.ibas.brta.vehims.configurations.payload.response.OrganizationWithThanas;

import jakarta.transaction.Transactional;

@Repository
public interface OfficeJurisdictionRepository extends JpaRepository<OfficeJurisdiction, Long> {

    List<OfficeJurisdiction> findByIsActiveTrueOrderByCreatedAtAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM OfficeJurisdiction s WHERE (:orgId IS NULL OR s.orgId = :orgId) AND (:thanaId IS NULL OR s.thanaId = :thanaId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<OfficeJurisdiction> findListWithPaginationBySearch(
            @Param("orgId") Long orgId,
            @Param("thanaId") Long thanaId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    @Query("SELECT s.thanaId FROM OfficeJurisdiction s WHERE s.orgId = :orgId")
    List<Long> findThanaIdsByOrgId(@Param("orgId") Long orgId);

    @Query(value = """
                SELECT
                    o.org_id,
                    o.org_name_en,
                    o.org_name_bn,
                    array_agg(j.thana_id) as thana_ids
                FROM
                    c_office_jurisdictions j
                JOIN
                    c_organizations o
                ON
                    j.org_id = o.org_id
                GROUP BY
                    o.org_id, o.org_name_en, o.org_name_bn
            """, nativeQuery = true)
    List<Object[]> getOrganizationWithThanasAndNamesNative();

    @Query(value = """
                SELECT
                    o.org_id AS orgId,
                    o.name_en AS orgNameEn,
                    o.name_bn AS orgNameBn,
                    ARRAY_AGG(j.thana_id) AS thanaIds,
                    o.is_active AS isActive
                FROM c_office_jurisdictions j
                JOIN c_organizations o ON j.org_id = o.org_id
                WHERE (:orgId IS NULL OR o.org_id = :orgId)
                  AND (:thanaId IS NULL OR j.thana_id = :thanaId)
                  AND (:isActive IS NULL OR o.is_active = :isActive)
                GROUP BY o.org_id, o.name_en, o.name_bn, o.is_active
            """, nativeQuery = true)
    Page<Object[]> findListWithPaginationBySearchNative(
            @Param("orgId") Long orgId,
            @Param("thanaId") Long thanaId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM OfficeJurisdiction o WHERE o.orgId = :orgId")
    void deleteByOrgId(@Param("orgId") Long orgId);
}