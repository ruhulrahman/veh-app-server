package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.model.audit.DateAudit;
import com.ibas.brta.vehims.model.audit.RecordAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "medias")
public class Media extends DateAudit {
    @Id
    @Column(name = "media_id", nullable = false)
    private Long id;

    @Column(name = "model", length = Integer.MAX_VALUE)
    private String model;

    @Column(name = "model_id")
    private Integer modelId;

    @Column(name = "original_name", length = Integer.MAX_VALUE)
    private String originalName;

    @Column(name = "folder_path", length = Integer.MAX_VALUE)
    private String folderPath;

    @Column(name = "file", length = Integer.MAX_VALUE)
    private String file;

    @Size(max = 20)
    @Column(name = "extension", length = 20)
    private String extension;

    @Size(max = 20)
    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "size")
    private Integer size;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

}