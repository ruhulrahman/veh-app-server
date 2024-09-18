package com.ibas.brta.vehims.util;

import java.util.Optional;

import com.ibas.brta.vehims.model.Designation;
import com.ibas.brta.vehims.model.Status;
import com.ibas.brta.vehims.model.StatusGroup;
import com.ibas.brta.vehims.payload.response.DesignationResponse;
import com.ibas.brta.vehims.payload.response.StatusGroupResponse;
import com.ibas.brta.vehims.payload.response.StatusResponse;

/**
 * Converting Domain Objects to DTOs (Data Transfer Object) or vice-versa
 *
 * @author ashshakur.rahaman
 * @version 1.0 08/19/24
 */

public class ModelMapper {
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

        return response;
    }

    public static StatusResponse StatusToResponse(Status data) {
        StatusResponse response = new StatusResponse();
        Optional.ofNullable(data.getId()).ifPresent(response::setId);
        Optional.ofNullable(data.getStatusGroupId()).ifPresent(response::setStatusGroupId);
        Optional.ofNullable(data.getStatusCode()).ifPresent(response::setStatusCode);
        Optional.ofNullable(data.getNameBn()).ifPresent(response::setNameBn);
        Optional.ofNullable(data.getNameEn()).ifPresent(response::setNameEn);
        Optional.ofNullable(data.getColorName()).ifPresent(response::setColorName);
        Optional.ofNullable(data.getPriority()).ifPresent(response::setPriority);
        Optional.ofNullable(data.getIsActive()).ifPresent(response::setIsActive);
        Optional.ofNullable(data.getStatusGroup()).ifPresent(response::setStatusGroup);

        return response;
    }

}
