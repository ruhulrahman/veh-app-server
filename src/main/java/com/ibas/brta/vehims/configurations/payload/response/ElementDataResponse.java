package com.ibas.brta.vehims.configurations.payload.response;

import java.time.LocalTime;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ElementDataResponse {
    private Long id;

    private Integer subSegmentId;

    private String code;

    private String blockCode;

    private String nameEn;

    private String nameBn;

    private String description;

    private Integer referenceStatusId;
}
