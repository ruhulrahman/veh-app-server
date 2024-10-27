package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.userManagement.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
        Permission findByNameEn(String nameEn);

        List<Permission> findByIsActiveTrueOrderByNameEnAsc();

        // Complex query with JPQL and named parameters
        @Query("SELECT s FROM Permission s WHERE (:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%')) OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%'))) AND (:type IS NULL OR s.type = :type) AND (:parentId IS NULL OR s.parentId = :parentId) AND (:isActive IS NULL OR s.isActive = :isActive) ORDER BY s.createdAt DESC")
        Page<Permission> findListWithPaginationBySearch(
                        @Param("nameEn") String nameEn,
                        @Param("type") Long type,
                        @Param("parentId") Long parentId,
                        @Param("isActive") Boolean isActive,
                        Pageable pageable);

        @Query(value = "SELECT * FROM u_permissions p ORDER BY p.name_en ASC", nativeQuery = true)
        List<Permission> findAllOrderByNameEnAsc();

        @Query(value = "SELECT * FROM u_permissions p WHERE p.parent_permission_id IS NULL ORDER BY p.name_en ASC", nativeQuery = true)
        List<Permission> findParentOrderByNameEnAsc();

        @Query(value = "SELECT * FROM u_permissions p WHERE p.type = :type ORDER BY p.name_en ASC", nativeQuery = true)
        List<Permission> findByType(@Param("type") Long type);

        @Query(value = "SELECT * FROM u_permissions p WHERE p.parent_permission_id = :parentPermissionId ORDER BY p.name_en ASC", nativeQuery = true)
        List<Permission> findByParentPermissionId(@Param("parentPermissionId") Long parentPermissionId);

        @Query(value = "SELECT * FROM u_permissions p WHERE p.parent_permission_id = :parentPermissionId AND p.type = :type AND is_active = true ORDER BY p.name_en ASC", nativeQuery = true)
        List<Permission> findByParentPermissionIdAndType(
                        @Param("parentPermissionId") Long parentPermissionId,
                        @Param("type") Long type);
}