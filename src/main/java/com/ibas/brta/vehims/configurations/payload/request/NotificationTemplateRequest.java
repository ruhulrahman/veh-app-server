package com.ibas.brta.vehims.configurations.payload.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NotificationTemplateRequest {
    private Long id;

    @NotNull(message = "service Id cannot be null")
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @NotBlank(message = "Title in English cannot be blank")
    @Size(max = 255)
    @Column(name = "title_en", nullable = false, unique = true)
    private String titleEn;

    @NotBlank(message = "Title in Bangla cannot be blank")
    @Size(max = 255)
    @Column(name = "title_bn", nullable = false, unique = true)
    private String titleBn;

    @NotBlank(message = "Message in English cannot be blank")
    @Size(max = 255)
    @Column(name = "message_en", nullable = false, unique = true)
    private String messageEn;

    @NotBlank(message = "Message in Bangla cannot be blank")
    @Size(max = 255)
    @Column(name = "message_bn", nullable = false, unique = true)
    private String messageBn;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
