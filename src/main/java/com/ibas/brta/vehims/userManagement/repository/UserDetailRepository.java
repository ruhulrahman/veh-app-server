package com.ibas.brta.vehims.userManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.userManagement.model.UserDetail;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByUserId(Long userId);

    UserDetail findByUserIdAndIsActive(Long userId, boolean isActive);
}
