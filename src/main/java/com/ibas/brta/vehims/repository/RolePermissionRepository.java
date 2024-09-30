package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.RolePermission;
import com.ibas.brta.vehims.model.RoleU;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    @Query(value = "SELECT * FROM u_role_permissions rp WHERE rp.role_id = :roleId", nativeQuery = true)
    List<RoleU> findByRoleId(@Param("roleId") Long roleId);

    @Query(value = "SELECT * FROM u_role_permissions rp WHERE rp.permission_id = :permissionId", nativeQuery = true)
    List<RoleU> findByPermissionId(@Param("permissionId") Long permissionId);

    @Query(value = "INSERT INTO u_role_permissions (role_id, permission_id) VALUES (:roleId, :permissionId)", nativeQuery = true)
    List<RolePermission> storeData(
            @Param("roleId") Long roleId,
            @Param("permissionId") Long permissionId);

    @Query(value = "SELECT role_id FROM u_role_permissions rp WHERE rp.role_id = :roleId", nativeQuery = true)
    List<RoleU> getPermissionIdsByRoleId(@Param("roleId") Long roleId);

}