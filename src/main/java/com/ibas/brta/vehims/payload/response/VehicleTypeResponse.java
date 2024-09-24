package com.ibas.brta.vehims.payload.response;

import java.io.Serializable;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleTypeResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private Boolean isActive;
}
