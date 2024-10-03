package com.ibas.brta.vehims.payload.response;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.ibas.brta.vehims.model.Status;
import com.ibas.brta.vehims.model.StatusGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoleUResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private String roleCode;
    private Boolean isActive;
    private List<Long> permissionIds;
}
