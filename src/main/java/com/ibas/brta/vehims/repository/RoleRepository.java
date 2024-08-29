package com.ibas.brta.vehims.repository;

import com.ibas.brta.vehims.model.rbac.Role;
import com.ibas.brta.vehims.model.rbac.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
