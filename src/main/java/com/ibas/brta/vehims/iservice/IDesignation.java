package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.model.Designation;
import com.ibas.brta.vehims.payload.request.DesignationRequest;

import javax.swing.text.html.Option;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IDesignation {

    List<Designation> findAllDesignations();

    Page<Designation> findAllDesignationsWithPagination(DesignationRequest request);

    Optional<Designation> findDesignationById(Long id);

    Designation saveDesignation(Designation designation);

}
