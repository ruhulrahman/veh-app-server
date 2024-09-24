package com.ibas.brta.vehims.payload.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmailTemplateRequest {
    private Long id;

    @NotNull(message = "service Id cannot be null")
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @NotBlank(message = "Template Name cannot be blank")
    @Column(name = "template_name", nullable = false, unique = true)
    private String templateName;

    @NotBlank(message = "Subject in English cannot be blank")
    @Column(name = "subject_en", nullable = false, unique = true)
    private String subjectEn;

    @NotBlank(message = "Subject in Bangla cannot be blank")
    @Column(name = "subject_bn", nullable = false, unique = true)
    private String subjectBn;

    @NotBlank(message = "Email Body in English cannot be blank")
    @Column(name = "emai_body_en", nullable = false, unique = true)
    private String messageEn;

    @NotBlank(message = "Email Body in Bangla cannot be blank")
    @Column(name = "emai_body_bn", nullable = false, unique = true)
    private String messageBn;

    private Boolean isActive;
}
