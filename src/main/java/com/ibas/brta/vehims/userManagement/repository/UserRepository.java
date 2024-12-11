package com.ibas.brta.vehims.userManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.userManagement.model.SUser;
import com.ibas.brta.vehims.userManagement.model.User;
import com.ibas.brta.vehims.userManagement.model.UserDetail;

/**
 * An interface that handles data access for user entities.
 *
 * @author ashshakur.rahaman
 * @version 1.0 08/20/24
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);

        User findByMobile(String mobile);

        Optional<User> findByUsernameOrMobile(String username, String mobile);

        Optional<User> findByUsernameOrMobileOrEmail(String username, String mobile, String email);

        List<User> findByIdIn(List<Long> userIds);
//        List<User> findByUserIdIn(List<Long> userIds);

        Optional<User> findByUsername(String username);

        Boolean existsByUsername(String username);

        Boolean existsByEmail(String email);

        SUser findByNameEn(String nameEn);

        List<SUser> findByIsActiveTrueOrderByNameEnAsc();

        // Complex query with JPQL and named parameters
        @Query("SELECT s FROM SUser s WHERE "
                        + "(:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%'))"
                        + " OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%')))"
                        + " AND (:email IS NULL OR :email = '' OR s.email = :email)"
                        + " AND (:mobile IS NULL OR :mobile = '' OR s.mobile = :mobile)"
                        + " AND (:userTypeId IS NULL OR s.userTypeId = :userTypeId)"
                        + " AND (:designationId IS NULL OR s.designationId = :designationId)"
                        + " AND (:isActive IS NULL OR s.isActive = :isActive)"
                        + " ORDER BY s.createdAt DESC")
        Page<SUser> findListWithPaginationBySearch(
                        @Param("nameEn") String nameEn,
                        @Param("email") String email,
                        @Param("mobile") String mobile,
                        @Param("userTypeId") Long userTypeId,
                        @Param("designationId") Long designationId,
                        @Param("isActive") Boolean isActive,
                        Pageable pageable);
}
