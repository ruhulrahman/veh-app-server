package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.StatusGroup;
import com.ibas.brta.vehims.model.User;
import com.ibas.brta.vehims.model.UserOfficeRole;
import com.ibas.brta.vehims.payload.response.UserOfficeRoleResponse;
import com.ibas.brta.vehims.projection.CommonProjection;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.projection.VehicleClassProjection;

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

        @Query(value = "SELECT org_id as id, name_en as nameEn, name_bn as nameBn FROM c_organizations WHERE is_active = true ORDER BY name_en ASC", nativeQuery = true)
        List<CommonProjection> getActiveOrganizations();

        @Query(value = "SELECT org_id as id, name_en as nameEn, name_bn as nameBn FROM c_organizations WHERE office_type_id = :officeTypeId AND is_active = true ORDER BY name_en ASC", nativeQuery = true)
        List<CommonProjection> getActiveOrganizationsByOfficeTypeId(Long officeTypeId);

        @Query(value = "SELECT status_id as id, name_en as nameEn, name_bn as nameBn FROM c_statuses WHERE status_code = :statusCodeOrId OR CAST(status_id AS CHAR) = :statusCodeOrId", nativeQuery = true)
        StatusProjection getStatusByStatusCodeOrId(@Param("statusCodeOrId") String statusCodeOrId);

        @Query(value = "SELECT location_id as id, name_en as nameEn, name_bn as nameBn FROM c_locations WHERE location_type_id = :locationTypeId AND is_active = true ORDER BY name_en ASC", nativeQuery = true)
        List<CommonProjection> getActiveLocationsByLocationTypeId(@Param("locationTypeId") Long locationTypeId);

        @Query(value = "SELECT role_id FROM s_user_organization_roles WHERE user_id = :userId", nativeQuery = true)
        List<Integer> getRoleIdsByUserId(@Param("userId") Long userId);

        @Query(value = "SELECT permission_id FROM u_role_permissions WHERE role_id IN (:roleIds)", nativeQuery = true)
        List<Integer> getPermissionIdsByRoleIds(@Param("roleIds") List<Integer> roleIds);

        @Query(value = "SELECT permission_id FROM u_role_permissions WHERE role_id = :roleIds", nativeQuery = true)
        List<Integer> getPermissionIdsByRoleId(@Param("roleIds") Integer roleIds);

        @Query(value = "SELECT permission_code FROM u_permissions WHERE permission_id IN (:permissionIds)", nativeQuery = true)
        List<String> getPermissionCodeByPermissionIds(@Param("permissionIds") List<Integer> permissionIds);

        @Query(value = "SELECT suor.user_id AS userId, " +
                        "suor.org_id AS orgId, " +
                        "suor.role_id AS roleId, " +
                        "org.name_en AS orgNameEn, " +
                        "org.name_bn AS orgNameBn, " +
                        "role.name_en AS roleNameEn, " +
                        "role.name_bn AS roleNameBn " +
                        "FROM s_user_organization_roles suor " +
                        "JOIN c_organizations org ON suor.org_id = org.org_id " +
                        "JOIN u_roles role ON suor.role_id = role.role_id " +
                        "WHERE suor.user_id = :userId", nativeQuery = true)
        List<UserOfficeRoleResponse> getUserOfficeRolesByUserId(@Param("userId") Long userId);

        @Query(value = "SELECT exporter_id as id, name_en as nameEn, name_bn as nameBn FROM v_exporters WHERE is_active = true ORDER BY name_en ASC", nativeQuery = true)
        List<CommonProjection> getActiveExporters();

        @Query(value = "SELECT importer_id as id, name_en as nameEn, name_bn as nameBn FROM v_importers WHERE is_active = true ORDER BY name_en ASC", nativeQuery = true)
        List<CommonProjection> getActiveImporters();

        @Query(value = "SELECT maker_id as id, name_en as nameEn, name_bn as nameBn, country_id as countryId FROM c_vehicle_makers WHERE is_active = true ORDER BY name_en ASC", nativeQuery = true)
        List<CommonProjection> getActiveVehicleMakers();

        @Query(value = "SELECT vehicle_color_id as id, name_en as nameEn, name_bn as nameBn FROM c_vehicle_colors WHERE is_active = true ORDER BY name_en ASC", nativeQuery = true)
        List<CommonProjection> getActiveVehicleColors();

        @Query(value = "SELECT vehicle_class_id as id, name_en as nameEn, name_bn as nameBn, vehicle_type_id as vehicleTypeId, symbol_en as symbolEn, symbol_bn as symbolBn FROM c_vehicle_classes WHERE is_active = true ORDER BY name_en ASC", nativeQuery = true)
        List<VehicleClassProjection> getActiveVehicleClasses();

        @Query(value = "SELECT fuel_type_id as id, name_en as nameEn, name_bn as nameBn FROM c_fuel_types WHERE is_active = true ORDER BY name_en ASC", nativeQuery = true)
        List<CommonProjection> getActiveFuelTypes();

        @Query(value = "SELECT brand_id as id, name_en as nameEn, name_bn as nameBn FROM c_vehicle_brands WHERE is_active = true ORDER BY name_en ASC", nativeQuery = true)
        List<CommonProjection> getActiveBrands();
}
