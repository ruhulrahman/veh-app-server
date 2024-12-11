package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.configurations.model.Designation;

import java.util.List;
import java.util.Optional;

public interface IDesignation {

    List<Designation> findAllDesignations();

    List<Designation> getParentDesignationList();

    Optional<Designation> findDesignationById(Long id);

    void deleteDesignationById(Long id);

    Designation saveDesignation(Designation designation);

    Designation updateDesignation(Designation designation);

}
