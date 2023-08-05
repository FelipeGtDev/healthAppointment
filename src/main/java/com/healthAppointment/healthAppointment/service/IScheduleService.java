package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;

public interface IScheduleService {

    ScheduleDTO save(ScheduleDTO request) throws BusException;
}
