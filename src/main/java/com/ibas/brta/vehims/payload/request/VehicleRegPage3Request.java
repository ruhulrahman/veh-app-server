package com.ibas.brta.vehims.payload.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
