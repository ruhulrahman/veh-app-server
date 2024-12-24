package com.ibas.brta.vehims.drivingLicense.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ibas.brta.vehims.common.model.Media;
import com.ibas.brta.vehims.common.payload.response.MediaResponse;
import com.ibas.brta.vehims.configurations.payload.response.DocumentTypeResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DLServiceMediaResponse {

    private Long id;
    private Long serviceRequestId;
    private String serviceRequestNo;
    private Long mediaId;
    private Long documentTypeId;
    private String documentTypeNameEn;
    private String documentTypeNameBn;
    private Long dlInfoId;
    private MultipartFile attachment;
    private String mediaUrl;
    private String fileName;
    private MediaResponse media;

}