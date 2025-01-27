package com.ibas.brta.vehims.vehicle.payload.request;

import com.ibas.brta.vehims.configurations.payload.request.AddressRequest;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleRegPage3Request {
    private Long id;

    private VehicleOwnerRequest vehicleOwner;
    private List<VehicleJointOwnerRequest> vehicleJointOwners;

    private AddressRequest addressInfo;

    private Integer pageCompleted;

    private Long orgId;
    private Long serviceRequestId;
    private Long vehicleInfoId;

}
