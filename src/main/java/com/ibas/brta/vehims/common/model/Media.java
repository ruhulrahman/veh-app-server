package com.ibas.brta.vehims.common.model;

import com.ibas.brta.vehims.common.model.audit.DateAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Slf4j
@Entity
@Table(name = "medias")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id", nullable = false)
    private Long id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "folder_path")
    private String folderPath;

    @Column(name = "file")
    private String file;

    @Size(max = 20)
    @Column(name = "extension", length = 20)
    private String extension;

    @Size(max = 20)
    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "size")
    private Long size;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdAt;

}