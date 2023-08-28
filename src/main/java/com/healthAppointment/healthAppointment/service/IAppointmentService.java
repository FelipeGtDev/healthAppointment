package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.dto.AppointmentDTO;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAppointmentService {
    void createAppointmentsFromSchedule(ScheduleDTO request) throws ResourceNotFoundException;

    Page<AppointmentDTO> findAll(AppointmentDTO appointmentDTO, String startDate, String endDate, Pageable page);
}
