package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.model.rbac.Role;
import com.ibas.brta.vehims.model.rbac.Menu;
import com.ibas.brta.vehims.payload.response.MenuResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.projection.MenuProjection;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IMenu {
    List<Menu> getAll();
    Optional<Menu> findById(Long id);
    Menu save(Menu menu);
    List<MenuProjection> findMenuByRoleId(Long roleId);
    public PagedResponse<MenuResponse> getAll(int page, int size);
    List<MenuResponse> getAllRecord();
    public boolean menusAssignToRole(Collection<Menu> entity, Collection<Menu> unAssignMenus, Role role);
}
