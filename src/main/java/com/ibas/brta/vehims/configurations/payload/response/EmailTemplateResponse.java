package com.ibas.brta.vehims.configurations.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmailTemplateResponse {
    private Long id;
    private Long serviceId;
    private String templateName;
    private String subjectEn;
    private String subjectBn;
    private String messageEn;
    private String messageBn;
    private Boolean isActive;
    private ServiceEntityResponse service;
}
