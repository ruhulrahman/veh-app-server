package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.payload.request.AppointmentTimeSlotRequest;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.AppointmentTimeSlotResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.AppointmentTimeSlotService;
import com.ibas.brta.vehims.util.AppConstants;

import jakarta.validation.Valid;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/")
public class AppointmentTimeSlotController {

    @Autowired
    AppointmentTimeSlotService appointmentTimeSlotService;

    @PostMapping("/v1/admin/configurations/appointment-timeslot/create")
    public ResponseEntity<?> createData(@Valid @RequestBody AppointmentTimeSlotRequest request) {
        AppointmentTimeSlotResponse saveData = appointmentTimeSlotService.createData(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/appointment-timeslot/")
                .buildAndExpand(saveData.getSlotNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getSlotNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/appointment-timeslot/update/{id}")
    public ResponseEntity<?> updateData(@Valid @PathVariable Long id,
            @RequestBody AppointmentTimeSlotRequest request) {

        AppointmentTimeSlotResponse updatedData = appointmentTimeSlotService.updateData(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/appointment-timeslot/updated")
                .buildAndExpand(updatedData.getSlotNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getSlotNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/appointment-timeslot/delete/{id}")
    public ResponseEntity<?> deleteData(@PathVariable Long id) {
        appointmentTimeSlotService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/appointment-timeslot/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String slotNameEn,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<AppointmentTimeSlotResponse> responseData = appointmentTimeSlotService.findAllBySearch(slotNameEn,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/appointment-timeslot/{id}")
    public ResponseEntity<?> getDataById(@PathVariable Long id) {
        AppointmentTimeSlotResponse response = appointmentTimeSlotService.getDataById(id);
        return ResponseEntity.ok(response);
    }

}
