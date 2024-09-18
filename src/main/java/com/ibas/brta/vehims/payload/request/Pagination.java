package com.ibas.brta.vehims.payload.request;

import com.ibas.brta.vehims.util.AppConstants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pagination implements AppConstants {
    private String page = DEFAULT_PAGE_NUMBER;
    private String size = DEFAULT_PAGE_SIZE;
    // private int page = 0;
    // private int size = 10;
}
