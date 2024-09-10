package com.ibas.brta.vehims.payload.request;

import com.ibas.brta.vehims.util.AppConstants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignationRequest extends Pagination {
    private String nameEn;
    private Boolean isActive;
    private int page;
    private int size;
}
