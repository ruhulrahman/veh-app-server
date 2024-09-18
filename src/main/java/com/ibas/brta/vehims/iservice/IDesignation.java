package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.model.Designation;
import com.ibas.brta.vehims.payload.request.DesignationRequest;
import com.ibas.brta.vehims.payload.response.DesignationResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;

import javax.swing.text.html.Option;

import org.springframework.data.domain.Page;

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
