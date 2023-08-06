package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.Schedule;
import com.healthAppointment.healthAppointment.model.dto.PatientReducedDTO;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.sql.ClientInfoStatus;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.*;
import static org.junit.jupiter.api.Assertions.*;
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
        // Objects
        scheduleWhitOnePatient = utils.createScheduleWhithOnePatient();
        scheduleWhitMultiplePatients = utils.createScheduleWhithMultiplePatients();
        // DTOs
        scheduleDtoWhitOnePatient = utils.createScheduleDtoWhithOnePatient();
        scheduleDTOWhitMultiplePatients = utils.createScheduleDtoWhithMultiplePatients();

        MockitoAnnotations.openMocks(this);
    }


    // ##################### CREATE #####################
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
    void save_shouldThrowExceptionWhenTryCreateMultiplePatientsScheduleInNotPilatesSchedule() throws BusException {
        //Arrange
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWhitMultiplePatients);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDTOWhitMultiplePatients);
        scheduleWhitMultiplePatients.setHealthProcedure(utils.createQualificationPhisioterapy());

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDTOWhitMultiplePatients));
        assertEquals(SCHEDULE_PATIENTS_FULL, exc.getMessage());
    }

    @Test
    void save_shouldThrowExceptionWhenTryCreatePilatesScheduleWithMoreThanSixPatients() throws BusException {
        //Arrange
        for (Integer i = 0; i < 5; i++) {
            var p = new Patient();
            p.setId(String.valueOf((i + 3)));
            scheduleWhitMultiplePatients.getPatients().add(p);
        }
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWhitMultiplePatients);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDTOWhitMultiplePatients);

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDTOWhitMultiplePatients));
        assertEquals(SCHEDULE_PATIENTS_FULL, exc.getMessage());
    }

    @Test
    void save_shouldThrowExceptionWhenTryCreateScheduleWithAnExistingDateTimeAndPratictioner() {
        //Arrange
        when(repository.findScheduleByDateTimeAndPratictionerId(any(), any())).thenReturn(Optional.ofNullable(scheduleWhitOnePatient));

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDTOWhitMultiplePatients));
        assertEquals(SCHEDULE_ALREADY_EXISTS, exc.getMessage());
    }

    @Test
    void save_shouldThrowExceptionWhenTryCreateScheduleWithDuplicatePatient() {
        //Arrange
        scheduleWhitMultiplePatients.getPatients().add(scheduleWhitMultiplePatients.getPatients().get(0));
        scheduleDTOWhitMultiplePatients.getPatients().add(scheduleDTOWhitMultiplePatients.getPatients().get(0));
        when(repository.save(any())).thenReturn(scheduleWhitMultiplePatients);
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWhitMultiplePatients);

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDTOWhitMultiplePatients));
        assertEquals(DUPLICATE_PATIENT, exc.getMessage());
    }

    // ##################### FIND BY DATE #####################

    @Test
    void findAllByDate_ShouldThrowExceptionIfReturnsMoreThanOneDate() {
        //Arrange
        List<Schedule> lista = new ArrayList<>();
        scheduleWhitMultiplePatients.setDateTime(LocalDateTime.now());
        lista.add(scheduleWhitMultiplePatients);
        lista.add(scheduleWhitOnePatient);
        when(repository.findScheduleAllByDateTime_Date(LocalDate.now(), LocalDate.now().plusDays(1))).thenReturn(lista);

        // Act & Assert
        assertThrows(AssertionError.class, () -> service.findAllByDate(""));
    }
}