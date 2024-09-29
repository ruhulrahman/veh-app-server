package com.ibas.brta.vehims.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.model.StatusGroup;
import com.ibas.brta.vehims.payload.response.StatusGroupResponse;
import com.ibas.brta.vehims.repository.CommonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonService {

    @Autowired
    CommonRepository repository;

    public StatusGroupResponse findByStatusGroupByCode(String statusGroupCode) {

        StatusGroup statusGroup = repository.findByStatusGroupByCode(statusGroupCode);

        StatusGroupResponse response = new StatusGroupResponse();
        BeanUtils.copyProperties(statusGroup, response);
        return response;
    }

    public List<?> findByStatusesByGroupCode(String statusGroupCode) {
        return repository.findByStatusesByGroupCode(statusGroupCode);
    }
}
