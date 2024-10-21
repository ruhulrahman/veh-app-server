package com.ibas.brta.vehims.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.CommonDropdownResponse;
import com.ibas.brta.vehims.projection.CommonProjection;
import com.ibas.brta.vehims.projection.StatusProjection;
import com.ibas.brta.vehims.projection.VehicleClassProjection;
import com.ibas.brta.vehims.repository.CommonRepository;
// import com.ibas.brta.vehims.service.DesignationService;
// import com.ibas.brta.vehims.service.StatusGroupService;
// import com.ibas.brta.vehims.service.StatusService;
import com.ibas.brta.vehims.service.AppointmentTimeSlotService;
import com.ibas.brta.vehims.service.BloodGroupService;
import com.ibas.brta.vehims.service.CommonService;
import com.ibas.brta.vehims.service.CountryService;
import com.ibas.brta.vehims.service.DesignationService;
import com.ibas.brta.vehims.service.DocumentTypeService;
import com.ibas.brta.vehims.service.FuelTypeService;
import com.ibas.brta.vehims.service.GenderService;
import com.ibas.brta.vehims.service.ServiceEntityService;
import com.ibas.brta.vehims.service.StatusGroupService;
import com.ibas.brta.vehims.service.StatusService;
import com.ibas.brta.vehims.service.VehicleColorService;
import com.ibas.brta.vehims.service.VehicleTypeService;

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
        List<?> designations = designationService.getActiveList();
        List<?> statusGroups = statusGroupService.getActiveList();
        // List<?> statuses = statusService.getActiveList();
        List<?> bloodGroups = bloodGroupService.getActiveList();
        List<?> vehicleColors = vehicleColorService.getActiveList();
        List<?> vehicleTypes = vehicleTypeService.getActiveList();
        List<?> countries = countryService.getActiveList();
        List<?> fuelTypes = fuelTypeService.getActiveList();
        List<?> services = serviceEntityService.getActiveList();
        List<?> genders = genderService.getActiveList();
        List<?> documentTypes = documentTypeService.getActiveList();
        List<?> appointmentTimeSlotList = appointmentTimeSlotService.getActiveList();

        List<?> routePermitTypes = commonService.findByStatusesByGroupCode("route_permit_types");
        List<?> locationList = commonService.findByStatusesByGroupCode("locations");
        List<?> userTypes = commonService.findByStatusesByGroupCode("user_types");
        List<?> officeTypeList = commonService.findByStatusesByGroupCode("office_types");
        List<?> orgList = commonService.getActiveOrganizations();

        CommonDropdownResponse dropdowns = new CommonDropdownResponse();
        dropdowns.setDesignationList(designations);
        dropdowns.setStatusGroupList(statusGroups);
        dropdowns.setStatusList(new ArrayList<>());
        dropdowns.setBloodList(bloodGroups);
        dropdowns.setCountryList(countries);
        dropdowns.setVehicleColorList(vehicleColors);
        dropdowns.setVehicleTypeList(vehicleTypes);
        dropdowns.setFuelTypeList(fuelTypes);
        dropdowns.setServiceList(services);
        dropdowns.setGenderList(genders);
        dropdowns.setDocumentTypeList(documentTypes);
        dropdowns.setAppointmentTimeSlotList(appointmentTimeSlotList);
        dropdowns.setRoutePermitTypes(routePermitTypes);
        dropdowns.setLocationTypeList(locationList);
        dropdowns.setUserTypeList(userTypes);
        dropdowns.setOrganizationList(orgList);
        dropdowns.setOfficeTypeList(officeTypeList);
        dropdowns.setPaymentStatusList(new ArrayList<>());

        StatusProjection locationTypeDivision = commonService.getStatusByStatusCodeOrId("division");

        if (locationTypeDivision != null) {
            List<?> divisionList = commonService.getActiveLocationsByLocationTypeId(locationTypeDivision.getId());
            dropdowns.setDivisionList(divisionList);
        }

        return ResponseEntity.ok(ApiResponse.success("Fetched list", dropdowns));
    }

    @GetMapping("/v1/admin/common/get-active-orgation-list")
    public ResponseEntity<?> getActiveOrganizations() {
        List<?> orgList = commonService.getActiveOrganizations();
        return ResponseEntity.ok(orgList);
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

}
