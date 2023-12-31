package com.healthAppointment.healthAppointment.model.dto;

import com.healthAppointment.healthAppointment.model.ProcedureStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private String id;
    private LocalDateTime dateTime;
    private PratictionerReducedDTO pratictioner;
    private QualificationReducedDTO healthProcedure;
    private ProcedureStatus procedureStatus;
    private PatientReducedDTO patient;
    private PaymentReducedDTO payment;
}
