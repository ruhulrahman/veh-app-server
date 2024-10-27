package com.ibas.brta.vehims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.userManagement.UserNidInfo;

@Repository
public interface UserNidInfoRepository extends JpaRepository<UserNidInfo, Long> {

    @Query(value = "SELECT * FROM s_user_nid_infos WHERE user_id = :userId", nativeQuery = true)
    UserNidInfo findByUserId(@Param("userId") Long userId);
}
