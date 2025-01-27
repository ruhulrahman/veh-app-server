package com.ibas.brta.vehims.serviceFees.dao;

import com.ibas.brta.vehims.serviceFees.model.VehicleServiceFees;
import com.ibas.brta.vehims.util.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleServiceFeesDao {
    EntityManager entityManager;

    public VehicleServiceFeesDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Object[]> findVehicleServiceFees(List<String> statusCodes,
                                                 Long serviceId,
                                                 Long vehicleTypeId,
                                                 Boolean isElectricVehicle,
                                                 Integer ccOrKw,
                                                 Integer seat,
                                                 Integer weight,
                                                 Boolean isAirCondition,
                                                 Boolean isHire) {

        StringBuilder querySt = new StringBuilder();
        querySt.append("SELECT vsf.* FROM f_vehicle_service_fees vsf ");

        if (Utils.notNullOrEmpty(vehicleTypeId)) {
            querySt.append("JOIN f_vehicle_service_fees_vehicle_type_maps vsfvtm ON vsf.v_service_fees_id = vsfvtm.v_service_fees_id ");
            querySt.append("WHERE vsf.service_id = :serviceId AND vsfvtm.vehicle_type_id = :vehicleTypeId ");
        } else {
            querySt.append("WHERE vsf.service_id = :serviceId ");
        }

        if (statusCodes.contains("electric_vehicle") && isElectricVehicle != null) {
            querySt.append("AND (:isElectricVehicle IS NULL OR vsf.is_electric_vehicle = :isElectricVehicle) ");
        }

        if (statusCodes.contains("cc") && Utils.notNullOrEmpty(ccOrKw)) {
            querySt.append("AND (:ccOrKw IS NULL OR vsf.cc_min <= :ccOrKw AND vsf.cc_max >= :ccOrKw) ");
        }

        if (statusCodes.contains("kw") && Utils.notNullOrEmpty(ccOrKw)) {
            querySt.append("AND (:ccOrKw IS NULL OR vsf.kw_min <= :ccOrKw AND vsf.kw_max >= :ccOrKw) ");
        }

        if (statusCodes.contains("seat") && Utils.notNullOrEmpty(seat)) {
            querySt.append("AND (:seat IS NULL OR vsf.seat_min <= :seat AND vsf.seat_max >= :seat) ");
        }

        if (statusCodes.contains("weight") && Utils.notNullOrEmpty(weight)) {
            querySt.append("AND (:weight IS NULL OR vsf.weight_min <= :weight AND vsf.weight_max >= :weight) ");
        }

        if (statusCodes.contains("air_conditioned") && isAirCondition != null) {
            querySt.append("AND (:isAirCondition IS NULL OR vsf.is_air_condition = :isAirCondition) ");
        }

        if (statusCodes.contains("hire") && isHire != null) {
            querySt.append("AND (:isHire IS NULL OR vsf.is_hire = :isHire) ");
        }

        querySt.append("AND (vsf.is_active = true OR (vsf.effective_start_date <= CURRENT_TIMESTAMP AND vsf.effective_end_date >= CURRENT_TIMESTAMP)) ");
        querySt.append("ORDER BY vsf.updated_date DESC, vsf.created_date DESC");

        Query query = entityManager.createNativeQuery(querySt.toString());

        query.setParameter("serviceId", serviceId);

        if (Utils.notNullOrEmpty(vehicleTypeId)) {
            query.setParameter("vehicleTypeId", vehicleTypeId);
        }

        if (statusCodes.contains("electric_vehicle")) {
            query.setParameter("isElectricVehicle", isElectricVehicle);
        }

        if (statusCodes.contains("cc")) {
            query.setParameter("ccOrKw", ccOrKw);
        }

        if (statusCodes.contains("kw")) {
            query.setParameter("ccOrKw", ccOrKw);
        }

        if (statusCodes.contains("seat")) {
            query.setParameter("seat", seat);
        }

        if (statusCodes.contains("weight")) {
            query.setParameter("weight", weight);
        }

        if (statusCodes.contains("air_conditioned")) {
            query.setParameter("isAirCondition", isAirCondition);
        }

        if (statusCodes.contains("hire")) {
            query.setParameter("isHire", isHire);
        }

        List<Object[]> result = query.getResultList();

        return result;
    }

    public VehicleServiceFees findFirstVehicleServiceFees(List<String> statusCodes,
                                                          Long serviceId,
                                                          Long vehicleTypeId,
                                                          Boolean isElectricVehicle,
                                                          Integer ccOrKw,
                                                          Integer seat,
                                                          Integer weight,
                                                          Boolean isAirCondition,
                                                          Boolean isHire) {
        // Construct the query
        StringBuilder querySt = new StringBuilder();
        querySt.append("SELECT vsf.* FROM f_vehicle_service_fees vsf ");

        if (Utils.notNullOrEmpty(vehicleTypeId) && !statusCodes.isEmpty()) {
            querySt.append("JOIN f_vehicle_service_fees_vehicle_type_maps vsfvtm ON vsf.v_service_fees_id = vsfvtm.v_service_fees_id ");
            querySt.append("WHERE vsf.service_id = :serviceId AND vsfvtm.vehicle_type_id = :vehicleTypeId ");
        } else {
            querySt.append("WHERE vsf.service_id = :serviceId ");
        }

//        if (statusCodes.contains("electric_vehicle") && isElectricVehicle != null) {
//            querySt.append("AND (:isElectricVehicle IS NULL OR vsf.is_electric_vehicle = :isElectricVehicle) ");
//        }

        if (isElectricVehicle) {
            if (statusCodes.contains("kw") && Utils.notNullOrEmpty(ccOrKw)) {
//            querySt.append("AND (:ccOrKw IS NULL OR vsf.kw_min <= :ccOrKw AND vsf.kw_max >= :ccOrKw) ");
                querySt.append("AND (:ccOrKw IS NULL OR (vsf.kw_min <= :ccOrKw AND (vsf.kw_max IS NULL OR vsf.kw_max >= :ccOrKw))) ");
            }
        } else {

            if (statusCodes.contains("cc") && Utils.notNullOrEmpty(ccOrKw)) {
//            querySt.append("AND (:ccOrKw IS NULL OR vsf.cc_min <= :ccOrKw AND vsf.cc_max >= :ccOrKw) ");
                querySt.append("AND (:ccOrKw IS NULL OR (vsf.cc_min <= :ccOrKw AND (vsf.cc_max IS NULL OR vsf.cc_max >= :ccOrKw))) ");
            }

            if (statusCodes.contains("seat") && Utils.notNullOrEmpty(seat)) {
//            querySt.append("AND (:seat IS NULL OR vsf.seat_min <= :seat AND vsf.seat_max >= :seat) ");
                querySt.append("AND (:seat IS NULL OR (vsf.seat_min <= :seat AND (vsf.seat_max IS NULL OR vsf.seat_max >= :seat))) ");
            }

            if (statusCodes.contains("weight") && Utils.notNullOrEmpty(weight)) {
//            querySt.append("AND (:weight IS NULL OR vsf.weight_min <= :weight AND vsf.weight_max >= :weight) ");
                querySt.append("AND (:weight IS NULL OR (vsf.weight_min <= :weight AND (vsf.weight_max IS NULL OR vsf.weight_max >= :weight))) ");
            }
        }

        if (statusCodes.contains("air_conditioned") && isAirCondition != null) {
            querySt.append("AND (:isAirCondition IS NULL OR vsf.is_air_condition = :isAirCondition) ");
        }

        if (statusCodes.contains("hire") && isHire != null) {
            querySt.append("AND (:isHire IS NULL OR vsf.is_hire = :isHire) ");
        }

        querySt.append("AND (vsf.is_active = true OR (vsf.effective_start_date <= CURRENT_TIMESTAMP AND vsf.effective_end_date >= CURRENT_TIMESTAMP)) ");
        querySt.append("ORDER BY vsf.updated_date DESC, vsf.created_date DESC ");

        Query query = entityManager.createNativeQuery(querySt.toString(), VehicleServiceFees.class);

        // Set query parameters
        query.setParameter("serviceId", serviceId);

        if (Utils.notNullOrEmpty(vehicleTypeId)) {
            query.setParameter("vehicleTypeId", vehicleTypeId);
        }

//        if (statusCodes.contains("electric_vehicle")) {
//            query.setParameter("isElectricVehicle", isElectricVehicle);
//        }

        if (statusCodes.contains("cc") || statusCodes.contains("kw")) {
            query.setParameter("ccOrKw", ccOrKw);
        }

        if (statusCodes.contains("seat")) {
            query.setParameter("seat", seat);
        }

        if (statusCodes.contains("weight")) {
            query.setParameter("weight", weight);
        }

        if (statusCodes.contains("air_conditioned")) {
            query.setParameter("isAirCondition", isAirCondition);
        }

        if (statusCodes.contains("hire")) {
            query.setParameter("isHire", isHire);
        }

        // Fetch the first result
        query.setMaxResults(1);

        try {
            return (VehicleServiceFees) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // No result found
        }
    }


}
