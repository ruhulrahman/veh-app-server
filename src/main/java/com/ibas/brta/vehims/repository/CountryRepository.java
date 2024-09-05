package com.ibas.brta.vehims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibas.brta.vehims.model.Country;

import jakarta.validation.Valid;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    void deleteById(int id);
}