package com.ibas.brta.vehims.payload.request;

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
