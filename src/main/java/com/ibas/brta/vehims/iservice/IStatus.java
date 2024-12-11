package com.ibas.brta.vehims.iservice;

import java.util.*;

import com.ibas.brta.vehims.configurations.model.Status;
import com.ibas.brta.vehims.configurations.payload.request.StatusRequest;

public interface IStatus {

    List<Status> findAllStatusGroups();

    Status findStatusByStatusCode(String statusCode);

    Status createStatus(Status status);

    Status updateStatus(StatusRequest status);

    void deleteStatusById(Long id);

    void deleteStatusByStatusCode(String statusCode);
}
