package com.ibas.brta.vehims.common.service.rbac;

import com.ibas.brta.vehims.exception.BadRequestException;
import com.ibas.brta.vehims.iservice.IMenu;
import com.ibas.brta.vehims.common.model.rbac.Role;
import com.ibas.brta.vehims.common.model.rbac.Menu;
import com.ibas.brta.vehims.common.payload.response.MenuResponse;
import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.projection.MenuProjection;
import com.ibas.brta.vehims.common.repository.rbac.MenuRepository;
import com.ibas.brta.vehims.util.AppConstants;
import com.ibas.brta.vehims.util.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MenuService implements IMenu {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> getAll(){
        return menuRepository.findAll();
    }

    @Override
    public Optional<Menu> findById(Long id){
        return menuRepository.findById(id);
    }

    @Override
    public Menu save(Menu menu){
        return menuRepository.save(menu);
    }


    @Override
    public List<MenuProjection> findMenuByRoleId(Long roleId){

        return menuRepository.findMenuByRoleId(roleId);

    }
    @Override
    public PagedResponse<MenuResponse> getAll(int page, int size){
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");
        Page<Menu> menus = menuRepository.findAll(pageable);

        if(menus.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), menus.getNumber(),
                    menus.getSize(), menus.getTotalElements(), menus.getTotalPages(), menus.isLast());
        }

        List<MenuResponse> menuResponses = menus.map(menu -> {
            return MenuMapper.EntityToResponse(menu);
        }).getContent();

        return new PagedResponse<>(menuResponses, menus.getNumber(),
                menus.getSize(), menus.getTotalElements(), menus.getTotalPages(), menus.isLast());

    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    @Override
    public List<MenuResponse> getAllRecord() {
        List<Menu> menus =  menuRepository.findAll();
        List<MenuResponse> menuResponses = new ArrayList<MenuResponse>();
        for (Menu menu : menus) {
            menuResponses.add(MenuMapper.EntityToResponse(menu));
        }
        return menuResponses;
    }

    @Transactional
    @Override
    public boolean menusAssignToRole(Collection<Menu> entity, Collection<Menu> unAssignMenus, Role role) {
        try {

            for (Menu menu : entity) {
                menuRepository.save(menu);
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

}
