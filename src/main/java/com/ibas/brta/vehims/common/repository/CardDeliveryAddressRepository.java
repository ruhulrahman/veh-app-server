package com.ibas.brta.vehims.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.common.model.Address;
import com.ibas.brta.vehims.common.model.CardDeliveryAddress;

@Repository
public interface CardDeliveryAddressRepository extends JpaRepository<CardDeliveryAddress, Long> {

}