package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.StatusGroup;
import com.ibas.brta.vehims.model.User;
import com.ibas.brta.vehims.projection.StatusProjection;

@Repository
public interface CommonRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM c_status_groups WHERE status_group_code = :statusGroupCode", nativeQuery = true)
    StatusGroup findByStatusGroupByCode(String statusGroupCode);

    // @Query(value = "SELECT * FROM c_statuses WHERE status_group_code =
    // :statusGroupCode", nativeQuery = true)
    // StatusGroup findByStatusesByGroupCode(String statusGroupCode);

    // @Query(value = "SELECT sg.status_group_id, sg.status_group_code, s.* " +
    // "FROM c_status_groups sg " +
    // "JOIN c_statuses s ON sg.status_group_id = s.status_group_id " +
    // "WHERE sg.status_group_code = :statusGroupCode", nativeQuery = true)
    // List<Object[]> findByStatusesByGroupCode(String statusGroupCode);

    @Query(value = "SELECT s.status_id as id, s.name_en as nameEn, s.name_bn as nameBn, s.status_code as statusCode, s.color_name as colorName, s.priority "
            +
            "FROM c_status_groups AS sg " +
            "JOIN c_statuses AS s ON sg.status_group_id = s.status_group_id " +
            "WHERE sg.status_group_code = :statusGroupCode AND s.is_active = true " +
            "ORDER BY s.priority ASC", nativeQuery = true)
    List<StatusProjection> findByStatusesByGroupCode(String statusGroupCode);
}
