package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.model.Country;
import com.ibas.brta.vehims.payload.request.CountryDTO;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.payload.response.CountryResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.service.CountryService;
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
public class CountryController {

    @Autowired
    CountryService countryService;

    @PostMapping("/v1/admin/configurations/country/create")
    public ResponseEntity<?> createCountryV1(@Valid @RequestBody CountryDTO countryDTO) {
        Country saveData = countryService.createData(countryDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/country/")
                .buildAndExpand(saveData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(saveData.getNameEn() + " saved.", saveData));
    }

    // Update an existing item
    @PutMapping("/v1/admin/configurations/country/update/{id}")
    public ResponseEntity<?> updateStatusGroup(
            @PathVariable Long id,
            @RequestBody CountryDTO countryDTO) {

        Country updatedData = countryService.updateData(id, countryDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/country/updated")
                .buildAndExpand(updatedData.getNameEn()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success(updatedData.getNameEn() + " updated.", updatedData));
    }

    // Delete a item
    @DeleteMapping("/v1/admin/configurations/country/delete/{id}")
    public ResponseEntity<?> deleteStatusGroup(@PathVariable Long id) {
        countryService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/admin/configurations/country/list")
    public PagedResponse<?> findListWithPaginationBySearch(
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        PagedResponse<CountryResponse> responseData = countryService.findAllBySearch(nameEn,
                isActive,
                page,
                size);

        return responseData;
    }

    // Get a single item by ID
    @GetMapping("/v1/admin/configurations/country/{id}")
    public ResponseEntity<Country> getStatusGroupById(@PathVariable Long id) {
        Country vehicleType = countryService.getDataById(id);
        return ResponseEntity.ok(vehicleType);
    }

}
