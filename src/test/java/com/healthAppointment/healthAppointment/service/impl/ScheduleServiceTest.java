package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.model.Schedule;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    private ScheduleDTO scheduleDtoWhitOnePatient;
    private ScheduleDTO scheduleDTOWhitMultiplePatients;
    private Schedule scheduleWhitOnePatient;
    private Schedule scheduleWhitMultiplePatients;
    @Mock
    private ScheduleRepository repository;
    @InjectMocks
    private ScheduleService service;
    @Mock
    private ModelMapper modelMapper;

    private final Utils utils;

    ScheduleServiceTest() {
        this.utils = new Utils();
    }


    @BeforeEach
    void setUp() throws ParseException {
        scheduleDtoWhitOnePatient = utils.createScheduleDtoWhithOnePatient();
        scheduleDTOWhitMultiplePatients = utils.createScheduleDtoWhithMultiplePatients();
        scheduleWhitOnePatient = utils.createScheduleWhithOnePatient();


        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldReturnScheduleWhitOnePatient() throws BusException {

        //Arrange
        when(repository.save(any())).thenReturn(scheduleWhitOnePatient);
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWhitOnePatient);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDtoWhitOnePatient);


        // Act
        var result = service.save(scheduleDtoWhitOnePatient);

        // Assert
        assertNotNull(result);

        assertEquals(scheduleDtoWhitOnePatient.getPratictioner(), result.getPratictioner());
        assertEquals(scheduleDtoWhitOnePatient.getDateTime(), result.getDateTime());
        assertEquals(scheduleDtoWhitOnePatient.getPatients().size(), result.getPatients().size());
        assertEquals(scheduleDtoWhitOnePatient.getPatients().get(0), result.getPatients().get(0));
        assertEquals(scheduleDtoWhitOnePatient.getHealthProcedure(), result.getHealthProcedure());

        verify(repository, times(1)).save(any());
    }

    @Test
    void save_shouldReturnScheduleWhitMultiplePatients() {

    }

    // deve retornar erro ao tentar criar um agendamento com dataHora e Profissional já existente.

    // deve retornar erro ao tentar criar um agendamento com mais pacientes do que o permitido.


}