package com.ibas.brta.vehims.payload.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FiscalYearRequest {
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "name_bn", nullable = false)
    private String nameBn;

    @NotNull(message = "Start Date cannot be blank")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End Date cannot be blank")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull(message = "Active status cannot be null")
    private Boolean isActive;
}
