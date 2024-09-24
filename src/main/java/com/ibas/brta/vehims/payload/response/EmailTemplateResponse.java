package com.ibas.brta.vehims.payload.response;

import jakarta.persistence.Column;
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
