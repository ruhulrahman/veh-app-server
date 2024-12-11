package com.ibas.brta.vehims.vehicle.payload.request;

import com.ibas.brta.vehims.configurations.payload.request.AddressRequest;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleRegPage3Request {
    private Long id;

    private VehicleOwnerRequest vehicleOwner;

    private AddressRequest addressInfo;

    private Integer pageCompleted;

    private Long orgId;
    private Long serviceRequestId;
    private Long vehicleInfoId;

}
