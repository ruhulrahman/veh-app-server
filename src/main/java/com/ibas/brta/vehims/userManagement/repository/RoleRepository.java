package com.ibas.brta.vehims.userManagement.repository;

import com.ibas.brta.vehims.common.model.rbac.Role;
import com.ibas.brta.vehims.common.model.rbac.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
