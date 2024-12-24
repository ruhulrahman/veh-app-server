package com.ibas.brta.vehims.vehicle.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * Request payload to search registration applications
 * 
 * @author ashshakur.rahaman
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VServiceMediaRequest {

    private Long id;

    private Long mediaId;

    private Long documentTypeId;

    private String serviceRequestNo;

    private Long serviceRequestId;

    private Long vehicleInfoId;

    private MultipartFile attachment;
}
