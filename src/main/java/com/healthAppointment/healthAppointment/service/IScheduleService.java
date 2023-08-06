package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;

import java.util.List;

public interface IScheduleService {

    ScheduleDTO save(ScheduleDTO request) throws BusException;

    List<ScheduleDTO> findAllByDate(String date);
}
