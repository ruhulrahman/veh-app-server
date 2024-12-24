package com.ibas.brta.vehims.common.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import com.ibas.brta.vehims.util.Utils;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaRequest {
    private Long id;

    private MultipartFile attachmentFile;

    // private String model;

    // private Long modelId;

    private String originalName;

    private String folderPath;

    private String file;

    @Size(max = 20)
    private String extension;

    @Size(max = 20)
    private String type;

    private Long size;

}