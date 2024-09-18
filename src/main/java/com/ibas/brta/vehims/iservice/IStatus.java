package com.ibas.brta.vehims.iservice;

import java.util.*;

import com.ibas.brta.vehims.model.Status;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.StatusResponse;

public interface IStatus {

    List<Status> findAllStatusGroups();

    PagedResponse<StatusResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size);

    Status findStatusById(Long id);

    Status findStatusByStatusCode(String statusCode);

    Status createStatus(Status status);

    Status updateStatus(Status status);

    void deleteStatusById(Long id);

    void deleteStatusByStatusCode(String statusCode);
}
