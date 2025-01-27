package com.ibas.brta.vehims.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.configurations.payload.response.CommonDropdownResponse;
import com.ibas.brta.vehims.projection.CommonProjection;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.projection.VehicleClassProjection;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
// import com.ibas.brta.vehims.configurations.service.DesignationService;
// import com.ibas.brta.vehims.configurations.service.StatusGroupService;
// import com.ibas.brta.vehims.configurations.service.StatusService;
import com.ibas.brta.vehims.configurations.service.AppointmentTimeSlotService;
import com.ibas.brta.vehims.configurations.service.BloodGroupService;
import com.ibas.brta.vehims.configurations.service.CommonService;
import com.ibas.brta.vehims.configurations.service.CountryService;
import com.ibas.brta.vehims.configurations.service.DesignationService;
import com.ibas.brta.vehims.configurations.service.DocumentTypeService;
import com.ibas.brta.vehims.configurations.service.FuelTypeService;
import com.ibas.brta.vehims.configurations.service.GenderService;
import com.ibas.brta.vehims.configurations.service.ServiceEntityService;
import com.ibas.brta.vehims.configurations.service.StatusGroupService;
import com.ibas.brta.vehims.configurations.service.StatusService;
import com.ibas.brta.vehims.configurations.service.VehicleColorService;
import com.ibas.brta.vehims.configurations.service.VehicleTypeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/")
public class CommonController {

        @Autowired
        DesignationService designationService;

        @Autowired
        StatusGroupService statusGroupService;

        @Autowired
        StatusService statusService;

        @Autowired
        BloodGroupService bloodGroupService;

        @Autowired
        VehicleColorService vehicleColorService;

        @Autowired
        VehicleTypeService vehicleTypeService;

        @Autowired
        CountryService countryService;

        @Autowired
        ServiceEntityService serviceEntityService;

        @Autowired
        FuelTypeService fuelTypeService;

        @Autowired
        GenderService genderService;

        @Autowired
        DocumentTypeService documentTypeService;

        @Autowired
        AppointmentTimeSlotService appointmentTimeSlotService;

        @Autowired
        CommonService commonService;

        @Autowired
        CommonRepository commonRepository;

        @GetMapping("/v1/admin/common/dropdown-list")
        public ResponseEntity<?> getCommonDropdownList() {
                List<?> designationList = designationService.getActiveList();
                List<?> statusGroupList = statusGroupService.getActiveList();
                // List<?> statuses = statusService.getActiveList();
                List<?> bloodList = bloodGroupService.getActiveList();
                List<?> vehicleColorList = vehicleColorService.getActiveList();
                List<?> vehicleTypeList = vehicleTypeService.getActiveList();
                List<?> countryList = countryService.getActiveList();
                List<?> fuelTypeList = fuelTypeService.getActiveList();
                List<?> serviceList = serviceEntityService.getActiveList();
                List<?> genderList = genderService.getActiveList();
                List<?> documentTypeList = documentTypeService.getActiveList();
                List<?> appointmentTimeSlotList = appointmentTimeSlotService.getActiveList();

                List<?> routePermitTypes = commonService.findByStatusesByGroupCode("route_permit_types");
                List<?> locationTypeList = commonService.findByStatusesByGroupCode("locations");
                List<?> userTypeList = commonService.findByStatusesByGroupCode("user_types");
                List<?> officeTypeList = commonService.findByStatusesByGroupCode("office_types");
                List<?> ownerTypeList = commonService.findByStatusesByGroupCode("owner_types");
                List<?> organizationList = commonService.getActiveOrganizations();
                List<?> revenueCheckStatusList = commonService.findByStatusesByGroupCode("revenue_check_statuses");
                List<?> inspectionStatusList = commonService.findByStatusesByGroupCode("inspection_statuses");
                List<?> vehicleApplicationCheckStatusList = commonService
                                .findByStatusesByGroupCode("vehicle_application_statuses");
                List<?> drivingTestNameList = commonService
                                .findByStatusesByGroupCode("driving_tests");
                List<?> userList = commonService.getUsers();

                Map<String, Object> customArray = new HashMap<>();

                customArray.put("designationList", designationList);
                customArray.put("countryList", countryList);
                customArray.put("bloodList", bloodList);
                customArray.put("genderList", genderList);
                customArray.put("documentTypeList", documentTypeList);
                customArray.put("fuelTypeList", fuelTypeList);
                customArray.put("userTypeList", userTypeList);
                customArray.put("serviceList", serviceList);
                customArray.put("vehicleTypeList", vehicleTypeList);
                customArray.put("vehicleColorList", vehicleColorList);
                customArray.put("statusGroupList", statusGroupList);
                // customArray.put("fiscalYearList", fiscalYearList);
                customArray.put("appointmentTimeSlotList", appointmentTimeSlotList);
                customArray.put("locationTypeList", locationTypeList);
                customArray.put("routePermitTypes", routePermitTypes);
                // customArray.put("paymentStatusList", paymentStatusList);
                customArray.put("officeTypeList", officeTypeList);
                // customArray.put("locationList", locationList);
                customArray.put("userTypeList", userTypeList);
                customArray.put("orgList", organizationList);
                customArray.put("ownerTypeList", ownerTypeList);
                customArray.put("userList", userList);
                customArray.put("revenueCheckStatusList", revenueCheckStatusList);
                customArray.put("inspectionStatusList", inspectionStatusList);
                customArray.put("vehicleApplicationCheckStatusList", vehicleApplicationCheckStatusList);
                customArray.put("drivingTestNameList", drivingTestNameList);

                // Driving License Related Start =====================================
                List<?> occupationList = commonService.findByStatusesByGroupCode("occupation");
                List<?> educationalQualificationList = commonService
                                .findByStatusesByGroupCode("educational_qualifications");
                List<?> maritalStatusList = commonService.findByStatusesByGroupCode("marital_statuses");
                List<?> languageList = commonService.findByStatusesByGroupCode("language");
                List<?> drivingLicenseClassList = commonService.findByStatusesByGroupCode("driving_license_classes");
                List<?> drivingLicenseTypeList = commonService.findByStatusesByGroupCode("driving_license_types");
                List<?> addressTypeList = commonService.findByStatusesByGroupCode("address_types");
                List<?> drivingLicenseApplicationTypeList = commonService
                                .findByStatusesByGroupCode("driving_license_application_types");
                List<?> applicantTypeList = commonService
                                .findByStatusesByGroupCode("applicant_types");
                List<?> drivingLicenseApplicationStatusList = commonService
                                .findByStatusesByGroupCode("driving_license_application_statuses");
                List<?> drivingLicenseRecommendationStatusList = commonService
                                .findByStatusesByGroupCode("driving_license_recommendation_statuses");
                List<?> relationshipList = commonService
                                .findByStatusesByGroupCode("relationships");

                customArray.put("educationalQualificationList", educationalQualificationList);
                customArray.put("occupationList", occupationList);
                customArray.put("maritalStatusList", maritalStatusList);
                customArray.put("languageList", languageList);
                customArray.put("drivingLicenseClassList", drivingLicenseClassList);
                customArray.put("drivingLicenseTypeList", drivingLicenseTypeList);
                customArray.put("addressTypeList", addressTypeList);
                customArray.put("drivingLicenseApplicationTypeList", drivingLicenseApplicationTypeList);
                customArray.put("applicantTypeList", applicantTypeList);
                customArray.put("drivingLicenseApplicationStatusList", drivingLicenseApplicationStatusList);
                customArray.put("drivingLicenseRecommendationStatusList", drivingLicenseRecommendationStatusList);
                customArray.put("relationshipList", relationshipList);
                // Driving License Related End =====================================

                StatusProjection locationTypeDivision = commonService.getStatusByStatusCodeOrId("division");

                if (locationTypeDivision != null) {
                        List<?> divisionList = commonService
                                        .getActiveLocationsByLocationTypeId(locationTypeDivision.getId());
                        customArray.put("divisionList", divisionList);
                }

                return ResponseEntity.ok(ApiResponse.success("Fetched list", customArray));
        }

        @GetMapping("/v1/admin/common/get-active-orgation-list")
        public ResponseEntity<?> getActiveOrganizations() {
                List<?> orgList = commonService.getActiveOrganizations();
                return ResponseEntity.ok(orgList);
        }

        @GetMapping("/v1/admin/common/get-vehicle-active-list")
        public ResponseEntity<?> getVehicleActiveClassList() {
                List<VehicleClassProjection> vehicleClassList = commonRepository.getActiveVehicleClasses();

                Map<String, Object> customArray = new HashMap<>();
                customArray.put("vehicleClassList", vehicleClassList);

                return ResponseEntity.ok(customArray);
        }

        @GetMapping("/v1/admin/common/get-vehile-registration-related-dropdown-list")
        public ResponseEntity<?> getVehicleRegistrationRelatedDropdownList() {
                List<?> exporterList = commonService.getActiveExporters();
                List<?> importerList = commonService.getActiveImporters();
                List<StatusProjection> assembleOperationList = commonRepository
                                .findByStatusesByGroupCode("assemble_operations");

                // List<CommonProjection> vehicleColorList =
                // commonRepository.getActiveVehicleColors();
                // List<CommonProjection> fuelTypeList = commonRepository.getActiveFuelTypes();
                List<CommonProjection> vehicleMakerList = commonRepository.getActiveVehicleMakers();
                List<VehicleClassProjection> vehicleClassList = commonRepository.getActiveVehicleClasses();
                List<CommonProjection> vehicleBrandList = commonRepository.getActiveBrands();

                Map<String, Object> customArray = new HashMap<>();
                // customArray.put("vehicleColorList", vehicleColorList);
                // customArray.put("fuelTypeList", fuelTypeList);
                customArray.put("exporterList", exporterList);
                customArray.put("importerList", importerList);
                customArray.put("assembleOperationList", assembleOperationList);
                customArray.put("vehicleMakerList", vehicleMakerList);
                customArray.put("vehicleClassList", vehicleClassList);
                customArray.put("vehicleBrandList", vehicleBrandList);

                return ResponseEntity.ok(customArray);
        }

        @GetMapping("/v1/admin/common/get-locations-by-parent-location-id/{parentLocationId}")
        public ResponseEntity<?> getLocationsByParentId(@PathVariable Long parentLocationId) {
                List<CommonProjection> locationList = commonRepository
                                .getActiveLocationsByParentLocationId(parentLocationId);

                Map<String, Object> customArray = new HashMap<>();
                customArray.put("locationList", locationList);

                return ResponseEntity.ok(customArray);
        }

        @GetMapping("/v1/admin/common/get-division-list")
        public ResponseEntity<?> getDivisionList() {
                List<CommonProjection> locationList = commonRepository
                                .getActiveLocationsByLocationTypeId(5L);

                Map<String, Object> customArray = new HashMap<>();
                customArray.put("locationList", locationList);

                return ResponseEntity.ok(customArray);
        }

        @GetMapping("/v1/admin/common/get-district-list/{parentLocationId}")
        public ResponseEntity<?> getDistrictList(@PathVariable Long parentLocationId) {
                List<CommonProjection> locationList = commonRepository
                                .getActiveLocationsByParentLocationIdAndLocationTypeId(parentLocationId, 1L);

                Map<String, Object> customArray = new HashMap<>();
                customArray.put("locationList", locationList);

                return ResponseEntity.ok(customArray);
        }

        @GetMapping("/v1/admin/common/get-thana-list/{parentLocationId}")
        public ResponseEntity<?> getThanaList(@PathVariable Long parentLocationId) {
                List<CommonProjection> locationList = commonRepository
                                .getActiveLocationsByParentLocationIdAndLocationTypeId(parentLocationId, 12L);

                Map<String, Object> customArray = new HashMap<>();
                customArray.put("locationList", locationList);

                return ResponseEntity.ok(customArray);
        }

        @GetMapping("/v1/admin/common/get-locations-by-parent-location-type-code/{parentLocationTypeCodeOrId}")
        public ResponseEntity<?> getLocationsByParentLocationTypeCodeId(
                        @PathVariable String parentLocationTypeCodeOrId) {
                StatusProjection locationType = commonService.getStatusByStatusCodeOrId(parentLocationTypeCodeOrId);

                Map<String, Object> customArray = new HashMap<>();
                if (locationType != null) {
                        List<?> locationList = commonService
                                        .getActiveLocationsByLocationTypeId(locationType.getId());
                        customArray.put("locationList", locationList);
                }

                return ResponseEntity.ok(customArray);
        }

        @GetMapping("/v1/admin/common/get-organization-by-thana-id/{thanaId}")
        public CommonProjection getOrganizationByThanaId(@PathVariable Long thanaId) {
                CommonProjection locationList = commonRepository
                                .getOrganizationByThanaId(thanaId);

                return locationList;
        }

        @GetMapping("/v1/admin/common/get-organization-and-exam-vanue-by-thana-id/{thanaId}")
        public ResponseEntity<?> getOrganizationAndExamVenueByThanaId(@PathVariable Long thanaId) {
                CommonProjection organization = commonRepository
                                .getOrganizationByThanaId(thanaId);
                CommonProjection venue = commonRepository
                                .getExamVenueByThanaId(thanaId);

                Map<String, Object> customArray = new HashMap<>();
                customArray.put("organization", organization);
                customArray.put("venue", venue);

                return ResponseEntity.ok(customArray);
        }

}
