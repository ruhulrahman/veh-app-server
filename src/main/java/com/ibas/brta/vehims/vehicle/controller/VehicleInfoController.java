package com.ibas.brta.vehims.vehicle.controller;

import com.ibas.brta.vehims.vehicle.payload.response.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.vehicle.payload.request.VServiceMediaRequest;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleRegPage1Request;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleRegPage2Request;
import com.ibas.brta.vehims.vehicle.payload.request.VehicleRegPage3Request;
import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.payload.response.ServiceDocumentMapResponse;
import com.ibas.brta.vehims.drivingLicense.payload.request.DLServiceMediaRequest;
import com.ibas.brta.vehims.drivingLicense.payload.response.DLServiceMediaResponse;
import com.ibas.brta.vehims.vehicle.service.VServiceRequestService;
import com.ibas.brta.vehims.vehicle.service.VehicleInfoService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/")
public class VehicleInfoController {

        @Autowired
        VehicleInfoService vehicleInfoService;

        @Autowired
        VServiceRequestService serviceRequestService;

        @PostMapping("/v1/applicant/vehicle/registration-application-page1")
        public ResponseEntity<?> storeVehicleRegPage1(@Valid @RequestBody VehicleRegPage1Request request) {
                VServiceRequestResponse saveData = vehicleInfoService.storeVehicleRegPage1(request);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/")
                                .buildAndExpand("").toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success("Page 1 data saved.", saveData));
        }

        @PostMapping("/v1/applicant/vehicle/registration-application-page2")
        public ResponseEntity<?> storeVehicleRegPage2(@Valid @RequestBody VehicleRegPage2Request request) {
                VehicleInfoResponse saveData = vehicleInfoService.storeVehicleRegPage2(request);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/")
                                .buildAndExpand("").toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success("Page 2 data saved.", saveData));
        }

        @PostMapping("/v1/applicant/vehicle/registration-application-page3")
        public ResponseEntity<?> storeVehicleRegPage3(@Valid @RequestBody VehicleRegPage3Request request) {
                VehicleInfoResponse saveData = vehicleInfoService.storeVehicleRegPage3(request);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/")
                                .buildAndExpand("").toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success("Page 3 data saved.", saveData));
        }

        @GetMapping("/v1/applicant/vehicle/registration-application/list")
        public PagedResponse<?> findListWithPaginationBySearch(
                        @RequestParam(required = false) String chassisNumber,
                        @RequestParam(required = false) String engineNumber,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                        @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

                PagedResponse<VehicleInfoResponse> responseData = vehicleInfoService.findAllBySearch(
                                chassisNumber,
                                engineNumber,
                                page,
                                size);

                return responseData;
        }

        @PostMapping("/v1/applicant/vehicle/document-upload")
        public ResponseEntity<?> uploadDocument(
                        @RequestParam("serviceRequestId") String serviceRequestId,
                        @RequestParam(required = false) String mediaId,
                        @RequestParam("documentTypeId") Long documentTypeId,
                        @RequestParam("attachment") MultipartFile attachment) {

                if (attachment.isEmpty()) {
                        throw new RuntimeException("File is empty.");
                }

                String fileExtension = StringUtils.getFilenameExtension(attachment.getOriginalFilename());
                if (!"jpg".equalsIgnoreCase(fileExtension) && !"png".equalsIgnoreCase(fileExtension)
                                && !"pdf".equalsIgnoreCase(fileExtension)) {
                        // throw new RuntimeException("Invalid file type. Only JPG, PNG, and PDF are
                        // allowed.");
                        return ResponseEntity
                                        .badRequest().body("Invalid file type. Only JPG, PNG, and PDF are allowed.");
                }

                VServiceMediaRequest request = new VServiceMediaRequest();
                // request.setServiceRequestId(serviceRequestId);
                request.setServiceRequestId(Long.parseLong(serviceRequestId));
                request.setDocumentTypeId(documentTypeId);
                if ("null".equalsIgnoreCase(mediaId) || "undefined".equalsIgnoreCase(mediaId)) {
                        mediaId = null;
                } else {
                        request.setMediaId(Long.parseLong(mediaId));
                }
                request.setAttachment(attachment);

                VServiceMediaResponse response = vehicleInfoService.uploadDocument(request);
                return ResponseEntity.ok(ApiResponse.success("Document uploaded.", response));
        }

        @GetMapping("/v1/applicant/vehicle/get-service-medias-by-service-request-id/{serviceRequestId}")
        public ResponseEntity<?> getServiceMediasByServiceRequestId(@PathVariable Long serviceRequestId) {

                List<VServiceMediaResponse> vServiceMedias = vehicleInfoService
                                .getServiceMediasByServiceRequestId(serviceRequestId);
                return ResponseEntity.ok(vServiceMedias);
        }

        // Get a single item by ID
        @GetMapping("/v1/applicant/vehicle/{id}")
        public ResponseEntity<?> getDataById(@PathVariable Long serviceRequestId) {
                VehicleInfoResponse response = vehicleInfoService.getDataById(serviceRequestId);
                return ResponseEntity.ok(response);
        }

        // Get a single item by ID
        @GetMapping("/v1/applicant/vehicle/service/{serviceRequestId}")
        public ResponseEntity<?> getVerhicleServiceReqeustById(@PathVariable Long serviceRequestId) {
                VServiceRequestResponse response = serviceRequestService
                                .getServiceRequestWithDetailsById(serviceRequestId);
                return ResponseEntity.ok(response);
        }
        // Get a single item by ID
        @GetMapping("/v1/applicant/vehicle/get-digital-registration-details/{serviceRequestId}")
        public ResponseEntity<?> getDigitalRegistrationCertificateDetailsReqeustById(@PathVariable Long serviceRequestId) {
                DigitalRegistrationCertficateResponse response = serviceRequestService
                                .getDigitalRegistrationCertificateDetailsReqeustById(serviceRequestId);
                return ResponseEntity.ok(response);
        }

        // Get a single item by ID
        @GetMapping("/v1/applicant/vehicle/get-fitness-certificate-details/{serviceRequestId}")
        public ResponseEntity<?> getFitnessCertificateDetailsReqeustById(@PathVariable Long serviceRequestId) {
                FitnessCertficateResponse response = serviceRequestService
                        .getFitnessCertificateDetailsReqeustById(serviceRequestId);
                return ResponseEntity.ok(response);
        }

        // Get a single item by ID
        @GetMapping("/v1/applicant/vehicle/get-service-documents/{serviceRequestId}")
        public ResponseEntity<?> getVerhicleServiceDocumentsReqeustById(@PathVariable Long serviceRequestId) {

                List<ServiceDocumentMapResponse> response = serviceRequestService
                                .getServiceDocumentsByServiceRequestId(serviceRequestId);
                return ResponseEntity.ok(response);
        }

}
