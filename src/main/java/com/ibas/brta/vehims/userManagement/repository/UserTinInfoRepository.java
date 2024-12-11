package com.ibas.brta.vehims.userManagement.repository;

import com.ibas.brta.vehims.userManagement.model.UserTinInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTinInfoRepository extends JpaRepository<UserTinInfo, Long> {

    @Query(value = "SELECT * FROM s_user_tin_infos WHERE user_id = :userId", nativeQuery = true)
    UserTinInfo findByUserId(@Param("userId") Long userId);
}
