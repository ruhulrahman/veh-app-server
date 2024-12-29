package com.ibas.brta.vehims.configurations.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDocumentMapRequest {

    private Long id;

    @NotNull(message = "serviceId cannot be null")
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @NotNull(message = "documentTypeId cannot be null")
    @Column(name = "document_type_id", nullable = false)
    private Long documentTypeId;

    @Column(name = "is_document_required", nullable = false)
    private Boolean isDocumentRequired = false;

    @Column(name = "priority")
    private Integer priority;

}
