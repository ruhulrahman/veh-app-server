package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ibas.brta.vehims.configurations.model.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    ServiceEntity findByNameEn(String nameEn);

    ServiceEntity findByNameBn(String nameBn);

    ServiceEntity findByServiceCode(String serviceCode);

    boolean existsByNameEn(String nameEn);

    boolean existsByNameBn(String nameBn);

    boolean existsByServiceCode(String serviceCode);

    List<ServiceEntity> findAllByOrderByNameEnAsc();

    List<ServiceEntity> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM ServiceEntity s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.serviceCode) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:parentServiceId IS NULL OR s.parentServiceId = :parentServiceId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC, s.priority ASC")
    Page<ServiceEntity> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("parentServiceId") Long parentServiceId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    @Query(value = "SELECT * FROM c_services WHERE :childServiceId = ANY(childServiceIds);", nativeQuery = true)
    List<ServiceEntity> getServicesByChildServiceId(@Param("childServiceId") Long childServiceId);

    // Query to get childServiceIds based on serviceCode
    @Query("SELECT UNNEST(s.childServiceIds) FROM ServiceEntity s WHERE s.serviceCode = :serviceCode")
    List<Long> findChildServiceIdsByServiceCode(@Param("serviceCode") String serviceCode);

    // @Query(value = "SELECT UNNEST(s.child_service_ids) FROM c_services s WHERE
    // s.service_code = :serviceCode", nativeQuery = true)
    // List<Long> findChildServiceIdsByServiceCode(@Param("serviceCode") String
    // serviceCode);

    // Query to get services by a list of IDs
    List<ServiceEntity> findByIdInOrderByPriorityAsc(List<Long> ids);

    List<ServiceEntity> findByIdInAndIsActiveTrueOrderByPriorityAsc(List<Long> ids);

    @Query("""
             SELECT s
             FROM ServiceEntity s
             WHERE s.parentServiceId = (
                 SELECT parent.id
                 FROM ServiceEntity parent
                 WHERE parent.serviceCode = :serviceCode
             )
             ORDER BY s.priority ASC
            """)
    List<ServiceEntity> getChildServicesByParentServiceCode(@Param("serviceCode") String serviceCode);

    @Query("""
             SELECT s.id
             FROM ServiceEntity s
             WHERE s.parentServiceId = (
                 SELECT parent.id
                 FROM ServiceEntity parent
                 WHERE parent.serviceCode = :serviceCode
             )
             ORDER BY s.priority ASC
            """)
    List<Long> getChildServiceIdsByParentServiceCode(@Param("serviceCode") String serviceCode);

    List<Long> findServiceIdsByIsActiveTrue();

    @Query("SELECT s.id FROM ServiceEntity s WHERE s.isActive = true")
    List<Long> getServicesIdsIsActiveTrue();

    @Query("SELECT s FROM ServiceEntity s WHERE s.serviceCode IN (:serviceCodes) AND s.isActive = true")
    List<ServiceEntity> getServicesByServiceCodes(@Param("serviceCodes") List<String> serviceCodes);

}
