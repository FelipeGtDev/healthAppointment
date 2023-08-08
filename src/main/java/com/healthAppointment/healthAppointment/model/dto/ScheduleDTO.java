package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO {
    private String id;
    private LocalDateTime dateTime;
    private PratictionerReducedDTO pratictioner;
    private List<PatientReducedDTO> patients;
    private QualificationReducedDTO healthProcedure;
    private List<AppointmentDTO> appointments; // TODO acho q nao precisa disso aqui

    public ScheduleDTO(ScheduleDTO scheduleDTO) {
        this.id = scheduleDTO.getId();
        this.dateTime = scheduleDTO.getDateTime();
        this.pratictioner = scheduleDTO.getPratictioner();
        this.patients = scheduleDTO.getPatients();
        this.healthProcedure = scheduleDTO.getHealthProcedure();
        this.appointments = scheduleDTO.getAppointments();
    }
}
