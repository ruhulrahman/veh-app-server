package com.ibas.brta.vehims.projection;

public interface MenuProjection {
    Long getMenuId();

    String getMenuNameEn();

    String getMenuNameBn();

    String getMenuUrl();

    Long getParentMenuId();
}
