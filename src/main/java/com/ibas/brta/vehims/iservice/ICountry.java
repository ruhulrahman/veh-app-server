package com.ibas.brta.vehims.iservice;

import java.util.*;

import com.ibas.brta.vehims.model.Country;

public interface ICountry {

    List<Country> findAllCountries();

    Optional<Country> findCountryById(Long id);

    Country saveCountry(Country country);

    void deleteCountryById(Long country_id);
}
