package com.ibas.brta.vehims.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.CommonDropdownResponse;
import com.ibas.brta.vehims.projection.CommonProjection;
import com.ibas.brta.vehims.service.*;
// import com.ibas.brta.vehims.service.DesignationService;
// import com.ibas.brta.vehims.service.StatusGroupService;
// import com.ibas.brta.vehims.service.StatusService;

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
        dropdowns.setPaymentStatusList(new ArrayList<>());

        return ResponseEntity.ok(ApiResponse.success("Fetched list", dropdowns));
    }

    @GetMapping("/v1/admin/common/get-active-orgation-list")
    public ResponseEntity<?> getActiveOrganizations() {
        List<?> orgList = commonService.getActiveOrganizations();
        return ResponseEntity.ok(orgList);
    }

}
