package com.ibas.brta.vehims.util;

import com.ibas.brta.vehims.common.model.rbac.Menu;
import com.ibas.brta.vehims.common.payload.request.MenuRequest;
import com.ibas.brta.vehims.common.payload.response.MenuResponse;

/**
 * Mapper to convert DTO objects to Entity class object and vice-versa.
 *
 */

public class MenuMapper {

    public static MenuResponse EntityToResponse(Menu menu) {
        MenuResponse response = new MenuResponse();


        response.setMenuId(menu.getId());
        response.setMenuNameEn(menu.getMenuNameEn());
        response.setMenuNameBn(menu.getMenuNameBn());
        response.setMenuCode(menu.getMenuCode());
        response.setParentMenuId(menu.getParentMenuId());
        response.setMenuUrl(menu.getMenuUrl());
        return response;
    }

    public static Menu RequestToEntity(MenuRequest request) {
        Menu menu = new Menu();

        menu.setMenuNameEn(request.getMenuNameEn());
        menu.setMenuNameBn(request.getMenuNameBn());
        menu.setMenuCode(request.getMenuCode());
        menu.setParentMenuId(request.getParentMenuId());
        menu.setMenuUrl(request.getMenuUrl());
        return menu;
    }
}
