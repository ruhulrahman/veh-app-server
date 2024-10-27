package com.ibas.brta.vehims.model.configurations;

import java.time.LocalTime;

import com.ibas.brta.vehims.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "appointment_timeslots")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "appointment_timeslot_id"))

public class AppointmentTimeSlot extends RecordAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Slot Name in English cannot be blank")
    @Size(max = 100)
    @Column(name = "slot_name_en", nullable = false)
    private String slotNameEn;

    @NotBlank(message = "Slot Name in Bangla cannot be blank")
    @Size(max = 100)
    @Column(name = "slot_name_bn", nullable = false)
    private String slotNameBn;

    @NotNull(message = "Slot Start Time cannot be blank")
    @Column(name = "slot_start_time", nullable = false)
    private LocalTime slotStartTime;

    @NotNull(message = "Slot End Time cannot be blank")
    @Column(name = "slot_end_time", nullable = false)
    private LocalTime slotEndTime;
}
