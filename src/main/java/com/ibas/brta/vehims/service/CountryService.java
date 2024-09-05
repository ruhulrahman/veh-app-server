package com.ibas.brta.vehims.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.iservice.ICountry;
import com.ibas.brta.vehims.model.Country;
import com.ibas.brta.vehims.repository.CountryRepository;

import jakarta.validation.Valid;

@Service
public class CountryService implements ICountry {
    @Autowired
    CountryRepository countryRepository;

    @Override
    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> findCountryById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public void deleteCountryById(Long country_id) {

        if (countryRepository.existsById(country_id)) {
            countryRepository.deleteById(country_id);
        } else {
            throw new RuntimeException("Data not found with id " + country_id);
        }
    }

}
