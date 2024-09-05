package com.ibas.brta.vehims.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibas.brta.vehims.model.Country;
import com.ibas.brta.vehims.payload.response.ApiResponse;
import com.ibas.brta.vehims.service.CountryService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/")
public class CountryController {

    @Autowired
    CountryService countryService;

    @PostMapping("/v1/admin/configurations/country/create")
    public ResponseEntity<?> createCountryV1(@Valid @RequestBody Country country) {
        Country _country = countryService.saveCountry(country);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/country/")
                .buildAndExpand(_country.getNameEn()).toUri();

        return ResponseEntity.created(location).body(ApiResponse.success("Country has been created", _country));
    }

    @PostMapping("/v1/admin/configurations/country/update")
    public ResponseEntity<?> updateCountryV1(@Valid @RequestBody Country country) {
        Country _country = countryService.saveCountry(country);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/country/")
                .buildAndExpand(_country.getNameEn()).toUri();

        return ResponseEntity.created(location).body(ApiResponse.success("Country has been updated", _country));
    }

    @GetMapping("/v1/admin/configurations/country/list")
    public ResponseEntity<?> listCountryV1() {
        List<Country> _countries = countryService.findAllCountries();

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/country/")
                .buildAndExpand(_countries).toUri();

        return ResponseEntity.created(location).body(ApiResponse.success("Fetch Country list", _countries));
    }

    @PostMapping("/v1/admin/configurations/country/delete")
    public ResponseEntity<?> deleteCountryV1(@Valid @RequestBody Long country_id) {
        countryService.deleteCountryById(country_id);

        return ResponseEntity.ok().body(ApiResponse.success("Country has been deleted", country_id));
    }

}
