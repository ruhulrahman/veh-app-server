package com.ibas.brta.vehims.common.repository.rbac;

import com.ibas.brta.vehims.common.model.rbac.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByName(String name);

}