package com.ibas.brta.vehims.drivingLicense.payload.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

import com.ibas.brta.vehims.common.payload.response.AddressResponse;
import com.ibas.brta.vehims.common.payload.response.CardDeliveryAddressResponse;
import com.ibas.brta.vehims.configurations.model.Status;
import com.ibas.brta.vehims.userManagement.payload.response.UserNidInfoResponse;

import jakarta.persistence.Column;

@Data
public class DrivingLicenseClassResponse {
    private Long id;
    private Long dllId;
    private Long dlInfoId;
    private Long dlClassId;
    private Status dlClass;
}
