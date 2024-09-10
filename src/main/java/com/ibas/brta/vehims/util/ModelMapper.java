package com.ibas.brta.vehims.util;

import java.util.Optional;

import com.ibas.brta.vehims.model.Designation;
import com.ibas.brta.vehims.payload.response.DesignationResponse;

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
        Optional.ofNullable(data.getParentDesignation());
        Optional.ofNullable(data.getIsActive()).ifPresent(response::setIsActive);

        return response;
    }

}
