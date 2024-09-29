package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_email_templates")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "email_template_id"))

public class EmailTemplate extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "emai_body_en", columnDefinition = "TEXT", nullable = false, unique = true)
    private String messageEn;

    @NotBlank(message = "Email Body in Bangla cannot be blank")
    @Column(name = "emai_body_bn", columnDefinition = "TEXT", nullable = false, unique = true)
    private String messageBn;
}
