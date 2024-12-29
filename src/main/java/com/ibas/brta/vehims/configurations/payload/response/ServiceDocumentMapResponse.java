package com.ibas.brta.vehims.configurations.payload.response;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ServiceDocumentMapResponse {
    private Long id;
    private Long serviceId;
    private Long documentTypeId;
    private Boolean isDocumentRequired;
    private Integer priority;
    private DocumentTypeResponse documentType;
    private ServiceEntityResponse service;
    private Boolean fileExist = false;
    private List<FileResponse> files;
}
