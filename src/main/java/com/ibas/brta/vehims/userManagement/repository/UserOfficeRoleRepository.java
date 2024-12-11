package com.ibas.brta.vehims.userManagement.repository;

import com.ibas.brta.vehims.userManagement.model.UserOfficeRole;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOfficeRoleRepository extends JpaRepository<UserOfficeRole, Long> {

    @Query(value = "SELECT * FROM s_user_organization_roles st WHERE st.user_id = :userId", nativeQuery = true)
    List<UserOfficeRole> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "DELETE FROM s_user_organization_roles st WHERE st.user_id = :userId", nativeQuery = true)
    void deleteAllByUserId(@Param("userId") Long userId);
}
