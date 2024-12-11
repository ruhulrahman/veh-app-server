package com.ibas.brta.vehims.configurations.payload.request;

import java.time.LocalTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AppointmentTimeSlotRequest {
    private Long id;

    @NotBlank(message = "Slot Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "slot_name_en", nullable = false)
    private String slotNameEn;

    @NotBlank(message = "Slot Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "slot_name_bn", nullable = false)
    private String slotNameBn;

    @NotNull
    @Column(name = "slot_start_time")
    private LocalTime slotStartTime;

    @NotNull
    @Column(name = "slot_end_time")
    private LocalTime slotEndTime;

    private Boolean isActive;
}
