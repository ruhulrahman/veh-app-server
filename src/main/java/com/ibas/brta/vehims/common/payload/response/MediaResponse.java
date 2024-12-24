package com.ibas.brta.vehims.common.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaResponse {
    private Long id;

    // private String model;

    // private Long modelId;

    private String originalName;

    private String folderPath;

    private String file;

    @Size(max = 20)
    private String extension;

    @Size(max = 20)
    private String type;

    private Resource resource;

    private Long size;

    private LocalDateTime createdDate;

}