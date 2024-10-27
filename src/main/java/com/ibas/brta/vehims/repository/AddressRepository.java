package com.ibas.brta.vehims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(value = "SELECT * FROM s_addresses WHERE user_id = :userId ORDER BY version_no DESC", nativeQuery = true)
    Address findByUserId(@Param("userId") Long userId);
}