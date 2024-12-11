package com.ibas.brta.vehims.common.service.rbac;

import com.ibas.brta.vehims.iservice.IPrivilege;
import com.ibas.brta.vehims.common.model.rbac.Privilege;
import com.ibas.brta.vehims.common.repository.rbac.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeService implements IPrivilege {
    @Autowired
    PrivilegeRepository repository;

    public List<Privilege> getAll() {
        return repository.findAll();
    }

    public Optional<Privilege> findById(Long id) {
        return repository.findById(id);

    }

    public Optional<Privilege> findByName(String name) {
        return repository.findByName(name);
    }

    public Privilege save(Privilege privilege) {
        return repository.save(privilege);
    }


}