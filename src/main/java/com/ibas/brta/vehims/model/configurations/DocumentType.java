package com.ibas.brta.vehims.model.configurations;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_document_types")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "document_type_id"))

public class DocumentType extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false, unique = true)
    private String nameBn;

    @NotBlank(message = "Document Format cannot be null")
    @Size(max = 100)
    @Column(name = "document_format", nullable = false)
    private String documentFormat;

    @NotNull(message = "Document Size cannot be null")
    @Column(name = "document_size", nullable = false)
    private Integer documentSize;
}
