package com.ibas.brta.vehims.drivingLicense.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DLServiceMediaRequest {

    private Long id;

    private Long mediaId;

    private Long documentTypeId;

    private String serviceRequestNo;

    private Long serviceRequestId;

    private Long dlInfoId;

    private MultipartFile attachment;

}