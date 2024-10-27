package com.ibas.brta.vehims.model.configurations;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_notificaiton_templates")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "notificaiton_template_id"))

public class NotificationTemplate extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
