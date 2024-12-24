package com.ibas.brta.vehims.vehicle.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.ibas.brta.vehims.common.payload.response.MediaResponse;

/**
 * @author ashshakur.rahaman
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VServiceMediaResponse {
    private Long id;
    private Long serviceRequestId;
    private String serviceRequestNo;
    private Long mediaId;
    private Long documentTypeId;
    private String documentTypeNameEn;
    private String documentTypeNameBn;
    private Long vehicleInfoId;
    private MultipartFile attachment;
    private String mediaUrl;
    private String fileName;
    private MediaResponse media;
}
