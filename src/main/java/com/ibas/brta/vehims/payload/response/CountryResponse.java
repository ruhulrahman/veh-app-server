package com.ibas.brta.vehims.payload.response;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CountryResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private Boolean isActive;
}
