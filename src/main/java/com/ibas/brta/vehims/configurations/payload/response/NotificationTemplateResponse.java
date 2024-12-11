package com.ibas.brta.vehims.configurations.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NotificationTemplateResponse {
    private Long id;
    private Long serviceId;
    private String titleEn;
    private String titleBn;
    private String messageEn;
    private String messageBn;
    private Boolean isActive;
    private ServiceEntityResponse service;
}
