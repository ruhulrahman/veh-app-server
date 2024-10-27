package com.ibas.brta.vehims.util;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibas.brta.vehims.model.configurations.Designation;
import com.ibas.brta.vehims.model.configurations.Status;
import com.ibas.brta.vehims.model.configurations.StatusGroup;
import com.ibas.brta.vehims.model.configurations.VehicleType;
import com.ibas.brta.vehims.payload.response.DesignationResponse;
import com.ibas.brta.vehims.payload.response.StatusGroupResponse;
import com.ibas.brta.vehims.payload.response.StatusResponse;
import com.ibas.brta.vehims.payload.response.VehicleTypeResponse;

/**
 * Converting Domain Objects to DTOs (Data Transfer Object) or vice-versa
 *
 * @author ashshakur.rahaman
 * @version 1.0 08/19/24
 */

public class ModelMapper {

    private static final Logger logger = LoggerFactory.getLogger(ModelMapper.class);

    public static DesignationResponse DesignationToResponse(Designation data) {
        DesignationResponse response = new DesignationResponse();
        Optional.ofNullable(data.getId()).ifPresent(response::setId);
        Optional.ofNullable(data.getNameBn()).ifPresent(response::setNameBn);
        Optional.ofNullable(data.getNameEn()).ifPresent(response::setNameEn);
        Optional.ofNullable(data.getLevelNumber()).ifPresent(response::setLevelNumber);
        Optional.ofNullable(data.getParentDesignationId()).ifPresent(response::setParentDesignationId);
        // Optional.ofNullable(data.getSubDesignations());
        Optional.ofNullable(data.getParentDesignation()).ifPresent(response::setParentDesignation);
        Optional.ofNullable(data.getIsActive()).ifPresent(response::setIsActive);

        return response;
    }

    public static StatusGroupResponse StatusGroupToResponse(StatusGroup data) {
        StatusGroupResponse response = new StatusGroupResponse();
        Optional.ofNullable(data.getId()).ifPresent(response::setId);
        Optional.ofNullable(data.getStatusGroupCode()).ifPresent(response::setStatusGroupCode);
        Optional.ofNullable(data.getNameBn()).ifPresent(response::setNameBn);
        Optional.ofNullable(data.getNameEn()).ifPresent(response::setNameEn);
        Optional.ofNullable(data.getIsActive()).ifPresent(response::setIsActive);

        // if (data.getStatuses() != null) {
        // List<StatusResponse> statusList = new ArrayList<>();
        // for (Status status : data.getStatuses()) {
        // StatusResponse statusResponse = StatusToResponse(status);
        // statusList.add(statusResponse);
        // }
        // response.setStatuses(statusList);
        // }

        return response;
    }

    public static StatusResponse StatusToResponse(Status data) {
        StatusResponse response = new StatusResponse();
        Optional.ofNullable(data.getId()).ifPresent(response::setId);
        // Optional.ofNullable(data.getStatusGroupId()).ifPresent(response::setStatusGroupId);
        Optional.ofNullable(data.getStatusCode()).ifPresent(response::setStatusCode);
        Optional.ofNullable(data.getNameBn()).ifPresent(response::setNameBn);
        Optional.ofNullable(data.getNameEn()).ifPresent(response::setNameEn);
        Optional.ofNullable(data.getColorName()).ifPresent(response::setColorName);
        Optional.ofNullable(data.getPriority()).ifPresent(response::setPriority);
        Optional.ofNullable(data.getIsActive()).ifPresent(response::setIsActive);
        // Optional.ofNullable(data.getStatusGroup()).ifPresent(response::setStatusGroup);

        // if (data.getStatusGroup() != null) {
        // StatusGroupResponse statusGroup =
        // ModelMapper.StatusGroupToResponse(data.getStatusGroup());
        // response.setStatusGroup(statusGroup);
        // }

        logger.info("status list =" + response.toString());

        return response;
    }

    public static VehicleTypeResponse VehicleTypeToResponse(VehicleType data) {
        VehicleTypeResponse response = new VehicleTypeResponse();
        Optional.ofNullable(data.getId()).ifPresent(response::setId);
        Optional.ofNullable(data.getNameBn()).ifPresent(response::setNameBn);
        Optional.ofNullable(data.getNameEn()).ifPresent(response::setNameEn);
        Optional.ofNullable(data.getIsActive()).ifPresent(response::setIsActive);

        // BeanUtils.copyProperties(data, response);

        return response;
    }

}
