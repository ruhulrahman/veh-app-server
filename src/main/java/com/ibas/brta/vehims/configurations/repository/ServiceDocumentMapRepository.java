package com.ibas.brta.vehims.configurations.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.configurations.model.ServiceDocumentMap;

@Repository
public interface ServiceDocumentMapRepository extends JpaRepository<ServiceDocumentMap, Long> {
    ServiceDocumentMap findByDocumentTypeId(Long documentTypeId);

    List<ServiceDocumentMap> findByServiceIdOrderByPriorityAsc(Long serviceId);

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM ServiceDocumentMap s WHERE (:documentTypeId IS NULL OR s.documentTypeId = :documentTypeId) AND (:serviceId IS NULL OR s.serviceId = :serviceId) ORDER BY s.priority, s.createdAt DESC")
    Page<ServiceDocumentMap> findListWithPaginationBySearch(
            @Param("documentTypeId") Long documentTypeId,
            @Param("serviceId") Long serviceId,
            Pageable pageable);
}