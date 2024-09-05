package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.model.Designation;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IDesignation {

    List<Designation> findAllDesignations();

    Optional<Designation> findDesignationById(Long id);

    Designation saveDesignation(Designation designation);

}
