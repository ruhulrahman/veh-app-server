package com.ibas.brta.vehims.payload.request;

import com.ibas.brta.vehims.model.audit.RecordAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaRequest {
    private Long id;

    private String model;

    private Integer modelId;

    private String originalName;

    private String folderPath;

    private String file;

    private String extension;

    private String type;

    private Integer size;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime deletedDate;

    private Integer versionNo;

}