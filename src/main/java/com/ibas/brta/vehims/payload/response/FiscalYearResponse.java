package com.ibas.brta.vehims.payload.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FiscalYearResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private LocalDate startDate;
    private LocalDate endDate;
    private int startYear;
    private int endYear;
    private String fiscalYear;
    private Boolean isActive;
}
