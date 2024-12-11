package com.ibas.brta.vehims.configurations.payload.request;

import com.ibas.brta.vehims.common.payload.request.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DesignationRequest extends Pagination {
    private String nameEn;
    private Boolean isActive;
}
