package com.ibas.brta.vehims.iservice;

import java.util.*;

import com.ibas.brta.vehims.configurations.model.StatusGroup;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.configurations.payload.response.StatusGroupResponse;

public interface IStatusGroup {

    List<StatusGroup> findAllStatusGroups();

    PagedResponse<StatusGroupResponse> findAllBySearch(String nameEn, Boolean isActive, int page, int size);

    StatusGroup findStatusGroupByStatusGroupCode(String statusGroupCode);

    StatusGroup createStatusGroup(StatusGroup statusGroup);

    StatusGroup updateStatusGroup(StatusGroup statusGroup);

    void deleteStatusGroupById(Long id);

    void deleteStatusGroupByStatusGroupCode(String statusGroupCode);
}
