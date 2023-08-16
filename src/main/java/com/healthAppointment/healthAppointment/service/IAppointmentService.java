package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;

public interface IAppointmentService {
    void createAppointmentsFromSchedule(ScheduleDTO request) throws ResourceNotFoundException;
}
