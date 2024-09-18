package com.ibas.brta.vehims.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibas.brta.vehims.model.Designation;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.CommonDropdownResponse;
import com.ibas.brta.vehims.service.DesignationService;

@RestController
@RequestMapping("/api/")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);

    @Autowired
    DesignationService designationService;

    @GetMapping("/v1/admin/configurations/common-dropdown-list")
    public ResponseEntity<?> getParentDesignationList() {
        List<Designation> _designations = designationService.getDesignationListIsActiveTrue();

        CommonDropdownResponse dropdowns = new CommonDropdownResponse();
        dropdowns.setDesignationList(_designations);
        dropdowns.setCountryList(new ArrayList<>());
        dropdowns.setBloodList(new ArrayList<>());
        dropdowns.setGenderList(new ArrayList<>());
        dropdowns.setDocumentTypeList(new ArrayList<>());
        dropdowns.setFuelTypeList(new ArrayList<>());
        dropdowns.setUserTypeList(new ArrayList<>());
        dropdowns.setServiceList(new ArrayList<>());
        dropdowns.setVehicleTypeList(new ArrayList<>());
        dropdowns.setVehicleColorList(new ArrayList<>());

        return ResponseEntity.ok(ApiResponse.success("Fetched list", dropdowns));
    }

}
