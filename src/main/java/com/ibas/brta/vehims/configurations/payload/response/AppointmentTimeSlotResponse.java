package com.ibas.brta.vehims.configurations.payload.response;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AppointmentTimeSlotResponse {
    private Long id;
    private String slotNameEn;
    private String slotNameBn;
    private LocalTime slotStartTime;
    private LocalTime slotEndTime;
    private Boolean isActive;
}
