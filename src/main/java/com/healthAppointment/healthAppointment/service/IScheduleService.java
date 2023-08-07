package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;

import java.util.List;

public interface IScheduleService {

    ScheduleDTO save(ScheduleDTO request) throws BusException;

    List<ScheduleDTO> findAllByDate(String date);

    ScheduleDTO findById(String id);

    ScheduleDTO addPatient(String id, String patientId) throws Exception, BusException, ResourceNotFoundException;
}
