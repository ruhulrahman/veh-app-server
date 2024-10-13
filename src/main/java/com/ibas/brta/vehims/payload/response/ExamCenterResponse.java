package com.ibas.brta.vehims.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExamCenterResponse {
    private Long id;
    private Long orgId;
    private String orgNameEn;
    private String orgNameBn;
    private Long thanaId;
    private String thanaNameEn;
    private String thanaNameBn;
    private String addressEn;
    private String addressBn;
    private Boolean isActive;
}
