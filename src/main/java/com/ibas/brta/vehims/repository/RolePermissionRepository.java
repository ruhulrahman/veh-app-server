package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.userManagement.RolePermission;

import jakarta.transaction.Transactional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    @Query(value = "SELECT * FROM u_role_permissions rp WHERE rp.role_id = :roleId", nativeQuery = true)
    List<RolePermission> findByRoleId(@Param("roleId") Long roleId);

    @Query(value = "SELECT * FROM u_role_permissions rp WHERE rp.permission_id = :permissionId", nativeQuery = true)
    List<RolePermission> findByPermissionId(@Param("permissionId") Long permissionId);

    @Query(value = "INSERT INTO u_role_permissions (role_id, permission_id) VALUES (:roleId, :permissionId)", nativeQuery = true)
    List<RolePermission> storeData(
            @Param("roleId") Long roleId,
            @Param("permissionId") Long permissionId);

    @Query(value = "DELETE FROM u_role_permissions p WHERE p.role_id = :roleId", nativeQuery = true)
    void deleteAllPermissionsByRoleId(Long roleId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RolePermission ur WHERE ur.roleId = :roleId")
    int deleteByRoleId(@Param("roleId") Long roleId);
}