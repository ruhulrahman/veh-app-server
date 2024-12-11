package com.ibas.brta.vehims.serviceFees.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.common.payload.response.ApiResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.common.payload.response.ServiceEconomicCodeResponse;
import com.ibas.brta.vehims.serviceFees.payload.request.VehicleServiceFeesRequest;
import com.ibas.brta.vehims.serviceFees.payload.request.VehicleServiceFeesSearchFilter;
import com.ibas.brta.vehims.serviceFees.payload.response.VehicleServiceFeesResponse;
import com.ibas.brta.vehims.serviceFees.service.VehicleServiceFeesService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class VehicleServiceFeesController {

        @Autowired
        private VehicleServiceFeesService vehicleServiceFeesService;

        @PostMapping("/v1/admin/configurations/vehicle-related-service-fees/create")
        public ResponseEntity<?> createData(@Valid @RequestBody VehicleServiceFeesRequest request) {
                VehicleServiceFeesResponse saveData = vehicleServiceFeesService.createData(request);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/appointment-timeslot/")
                                .buildAndExpand().toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success("Service fees saved.", saveData));
        }

        // Update an existing item
        @PutMapping("/v1/admin/configurations/vehicle-related-service-fees/update/{id}")
        public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
                        @RequestBody VehicleServiceFeesRequest request) {

                VehicleServiceFeesResponse updatedData = vehicleServiceFeesService.updateData(id,
                                request);

                URI location = ServletUriComponentsBuilder
                                .fromCurrentContextPath().path("/vehicle-related-service-fees/updated")
                                .buildAndExpand().toUri();

                return ResponseEntity.created(location)
                                .body(ApiResponse.success("Service fees updated.", updatedData));
        }

        // Delete a item
        @DeleteMapping("/v1/admin/configurations/vehicle-related-service-fees/delete/{id}")
        public ResponseEntity<?> deleteData(@PathVariable Long id) {
                vehicleServiceFeesService.deleteData(id);
                return ResponseEntity.noContent().build();
        }

        // @GetMapping("/v1/admin/configurations/vehicle-related-service-fees/list")
        // public ResponseEntity<?> findListWithPaginationBySearch(
        // @RequestParam(required = false) Long serviceId,
        // @RequestParam(required = false) Long vehicleTypeId,
        // @RequestParam(required = false) Boolean isActive,
        // @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
        // @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        @GetMapping("/v1/admin/configurations/vehicle-related-service-fees/list")
        public ResponseEntity<?> findListWithPaginationBySearch(@ModelAttribute VehicleServiceFeesSearchFilter filter) {

                PagedResponse<VehicleServiceFeesResponse> responseData = vehicleServiceFeesService
                                .findAllBySearch(filter);

                // return responseData;

                List<Long> serviceIds = vehicleServiceFeesService.getServicesIds();

                Map<String, Object> object = new HashMap<>();
                object.put("list", responseData);
                object.put("serviceIds", serviceIds);

                return ResponseEntity.ok(ApiResponse.success("Fetched list", object));
        }

        // Get a single item by ID
        @GetMapping("/v1/admin/configurations/vehicle-related-service-fees/{id}")
        public ResponseEntity<?> getDataById(@PathVariable Long id) {
                VehicleServiceFeesResponse response = vehicleServiceFeesService.getDataById(id);
                return ResponseEntity.ok(response);
        }

        // Get a single item by ID
        @GetMapping("/v1/admin/configurations/vehicle-related-service-fees-with-parent-service-code/{serviceCode}")
        public ResponseEntity<?> getServiceWithFeesByParentServiceCode(@PathVariable String serviceCode) {
                List<VehicleServiceFeesResponse> response = vehicleServiceFeesService
                                .getServiceWithFeesByParentServiceCode(serviceCode);

                ServiceEconomicCodeResponse serviceEconomicCode = vehicleServiceFeesService
                                .getServiceEconomicCodeByServiceCode(serviceCode);

                Map<String, Object> object = new HashMap<>();
                object.put("list", response);
                object.put("serviceEconomicCode", serviceEconomicCode);
                return ResponseEntity.ok(object);
        }

}
