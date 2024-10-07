package com.ibas.brta.vehims.repository;

import com.ibas.brta.vehims.model.UserOfficeRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOfficeRoleRepository extends JpaRepository<UserOfficeRole, Long> {

    @Query(value = "DELETE FROM s_user_organization_roles p WHERE p.user_id = :userId", nativeQuery = true)
    void deleteAllByUserId(Long userId);
}
