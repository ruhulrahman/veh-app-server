package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.common.model.rbac.Privilege;

import java.util.List;
import java.util.Optional;

public interface IPrivilege{

    List<Privilege> getAll();

    Optional<Privilege> findById(Long id);

    Optional<Privilege> findByName(String name);

    Privilege save(Privilege privilege);

}