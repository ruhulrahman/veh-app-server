package com.ibas.brta.vehims.userManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.userManagement.model.UserNidInfo;

@Repository
public interface UserNidInfoRepository extends JpaRepository<UserNidInfo, Long> {

    @Query(value = "SELECT * FROM s_user_nid_infos WHERE user_id = :userId", nativeQuery = true)
    UserNidInfo findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM s_user_nid_infos WHERE nid_number = :nidNumber", nativeQuery = true)
    UserNidInfo findByNidNumber(@Param("nidNumber") String nidNumber);

    @Query(value = "SELECT * FROM s_user_nid_infos WHERE nid_number = :nidNumber AND mobile = :mobile", nativeQuery = true)
    UserNidInfo findByNidNumberAndMobile(@Param("nidNumber") Long nidNumber, @Param("mobile") String mobile);
}
