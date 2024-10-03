package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.SUser;

@Repository
public interface SUserRepository extends JpaRepository<SUser, Long> {

        SUser findByNameEn(String nameEn);

        List<SUser> findByIsActiveTrueOrderByNameEnAsc();

        // Complex query with JPQL and named parameters
        @Query("SELECT s FROM SUser s WHERE "
                        + "(:nameEn IS NULL OR LOWER(s.nameEn) LIKE LOWER(CONCAT('%', :nameEn, '%'))"
                        + " OR LOWER(s.nameBn) LIKE LOWER(CONCAT('%', :nameEn, '%')))"
                        + " AND (:email IS NULL OR s.email = :email)"
                        + " AND (:mobile IS NULL OR s.mobile = :mobile)"
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