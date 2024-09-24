package com.ibas.brta.vehims.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.CommonDropdownResponse;
import com.ibas.brta.vehims.service.*;
// import com.ibas.brta.vehims.service.DesignationService;
// import com.ibas.brta.vehims.service.StatusGroupService;
// import com.ibas.brta.vehims.service.StatusService;

@RestController
@RequestMapping("/api/")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);

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
    VehicleTypService vehicleTypService;

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

    @GetMapping("/v1/admin/configurations/common-dropdown-list")
    public ResponseEntity<?> getParentDesignationList() {
        List<?> designations = designationService.getActiveList();
        List<?> statusGroups = statusGroupService.getActiveList();
        List<?> statuses = statusService.getActiveList();
        List<?> bloodGroups = bloodGroupService.getActiveList();
        List<?> vehicleColors = vehicleColorService.getActiveList();
        List<?> vehicleTypes = vehicleTypService.getActiveList();
        List<?> countries = countryService.getActiveList();
        List<?> fuelTypes = fuelTypeService.getActiveList();
        List<?> services = serviceEntityService.getActiveList();
        List<?> genders = genderService.getActiveList();
        List<?> documentTypes = documentTypeService.getActiveList();

        CommonDropdownResponse dropdowns = new CommonDropdownResponse();
        dropdowns.setDesignationList(designations);
        dropdowns.setStatusGroupList(statusGroups);
        dropdowns.setStatusList(statuses);
        dropdowns.setBloodList(bloodGroups);
        dropdowns.setCountryList(countries);
        dropdowns.setVehicleColorList(vehicleColors);
        dropdowns.setVehicleTypeList(vehicleTypes);
        dropdowns.setFuelTypeList(fuelTypes);
        dropdowns.setServiceList(services);
        dropdowns.setGenderList(genders);
        dropdowns.setDocumentTypeList(documentTypes);
        dropdowns.setUserTypeList(new ArrayList<>());

        return ResponseEntity.ok(ApiResponse.success("Fetched list", dropdowns));
    }

}
