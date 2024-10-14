package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.RoleU;

import jakarta.transaction.Transactional;

@Repository
public interface RoleURepository extends JpaRepository<RoleU, Long> {
    RoleU findByNameEn(String nameEn);

    List<RoleU> findByIsActiveTrueOrderByNameEnAsc();

    // Complex query with JPQL and named parameters
    @Query("SELECT s FROM RoleU s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
    Page<RoleU> findListWithPaginationBySearch(
            @Param("nameEn") String nameEn,
            @Param("isActive") Boolean isActive,
            Pageable pageable);

    @Query(value = "SELECT * FROM u_permissions p ORDER BY p.name_en ASC", nativeQuery = true)
    List<RoleU> findAllOrderByNameEnAsc();

    @Query(value = "SELECT * FROM u_permissions p WHERE p.parent_permission_id IS NULL ORDER BY p.name_en ASC", nativeQuery = true)
    List<RoleU> findParentOrderByNameEnAsc();

    @Modifying
    @Transactional
    @Query("DELETE FROM RolePermission ur WHERE ur.roleId = :roleId")
    int deleteAllPermissionsByRoleId(@Param("roleId") Long roleId);
}