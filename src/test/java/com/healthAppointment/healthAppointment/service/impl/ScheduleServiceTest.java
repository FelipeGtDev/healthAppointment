package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.Schedule;
import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import com.healthAppointment.healthAppointment.model.dto.PatientReducedDTO;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

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
    private Schedule scheduleWithOnePatient;
    private Schedule scheduleWithMultiplePatients;
    @Mock
    private ScheduleRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PatientService patientService;
    @InjectMocks
    private ScheduleService service;

    private final Utils utils;

    ScheduleServiceTest() {
        this.utils = new Utils();
    }


    @BeforeEach
    void setUp() throws ParseException {
        // Objects
        scheduleWithOnePatient = utils.createScheduleWhithOnePatient();
        scheduleWithMultiplePatients = utils.createScheduleWhithMultiplePatients();
        // DTOs
        scheduleDtoWhitOnePatient = utils.createScheduleDtoWhithOnePatient();
        scheduleDTOWhitMultiplePatients = utils.createScheduleDtoWhithMultiplePatients();

        MockitoAnnotations.openMocks(this);
    }


    // ##################### CREATE #####################
    @Test
    void save_shouldReturnScheduleWhitOnePatient() throws BusException {

        //Arrange
        when(repository.save(any())).thenReturn(scheduleWithOnePatient);
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWithOnePatient);
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
    void save_shouldReturnScheduleWithoutPatient() throws BusException {

        //Arrange
        var scheduleDtoWithoutPatient = new ScheduleDTO(scheduleDtoWhitOnePatient);
        var scheduleWithoutPatient = new Schedule(scheduleWithOnePatient);
        scheduleWithoutPatient.setPatients(null);
        scheduleDtoWithoutPatient.setPatients(null);
        when(repository.save(any())).thenReturn(scheduleWithoutPatient);
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWithoutPatient);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDtoWithoutPatient);

        // Act
        var result = service.save(scheduleDtoWithoutPatient);

        // Assert
        assertNotNull(result);

        assertEquals(scheduleDtoWithoutPatient.getPratictioner(), result.getPratictioner());
        assertEquals(scheduleDtoWithoutPatient.getDateTime(), result.getDateTime());
        assertNull(result.getPatients());
        assertEquals(scheduleDtoWithoutPatient.getHealthProcedure(), result.getHealthProcedure());

        verify(repository, times(1)).save(any());
    }

    @Test
    void save_shouldThrowExceptionWhenTryCreateMultiplePatientsScheduleInNotPilatesSchedule() {
        //Arrange
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWithMultiplePatients);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDTOWhitMultiplePatients);
        scheduleWithMultiplePatients.setHealthProcedure(utils.createQualificationPhisioterapy());

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDTOWhitMultiplePatients));
        assertEquals(SCHEDULE_PATIENTS_FULL, exc.getMessage());
    }

    @Test
    void save_shouldThrowExceptionWhenTryCreatePilatesScheduleWithMoreThanSixPatients() {
        //Arrange
        for (int i = 0; i < 5; i++) {
            var p = new Patient();
            p.setId(String.valueOf((i + 3)));
            scheduleWithMultiplePatients.getPatients().add(p);
        }
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWithMultiplePatients);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDTOWhitMultiplePatients);

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDTOWhitMultiplePatients));
        assertEquals(SCHEDULE_PATIENTS_FULL, exc.getMessage());
    }

    @Test
    void save_shouldThrowExceptionWhenTryCreateScheduleWithAnExistingDateTimeAndPratictioner() {
        //Arrange
        when(repository.findScheduleByDateTimeAndPratictionerId(any(), any())).thenReturn(Optional.ofNullable(scheduleWithOnePatient));

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDTOWhitMultiplePatients));
        assertEquals(SCHEDULE_ALREADY_EXISTS, exc.getMessage());
    }

    @Test
    void save_shouldThrowExceptionWhenTryCreateScheduleWithDuplicatePatient() {
        //Arrange
        scheduleWithMultiplePatients.getPatients().add(scheduleWithMultiplePatients.getPatients().get(0));
        scheduleDTOWhitMultiplePatients.getPatients().add(scheduleDTOWhitMultiplePatients.getPatients().get(0));
        when(repository.save(any())).thenReturn(scheduleWithMultiplePatients);
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWithMultiplePatients);

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDTOWhitMultiplePatients));
        assertEquals(DUPLICATE_PATIENT, exc.getMessage());
    }

    // ##################### FIND BY DATE #####################

    @Test
    void findAllByDate_ShouldThrowExceptionIfReturnsMoreThanOneDate() {
        //Arrange
        List<Schedule> lista = new ArrayList<>();
        scheduleWithMultiplePatients.setDateTime(LocalDateTime.now());
        lista.add(scheduleWithMultiplePatients);
        lista.add(scheduleWithOnePatient);
        when(repository.findScheduleAllByDateTime_Date(LocalDate.now(), LocalDate.now().plusDays(1))).thenReturn(lista);

        // Act & Assert
        assertThrows(AssertionError.class, () -> service.findAllByDate(""));
    }

    // ##################### ADD PATIENT IN SCHEDULE #####################

    @Test
    void addPatient_shouldReturnScheduleWhitPatientAdded() throws BusException, ResourceNotFoundException, ParseException {
        //Arrange
        var scheduleDtoWithoutPatient = new ScheduleDTO(scheduleDtoWhitOnePatient);
        var scheduleWithoutPatient = new Schedule(scheduleWithOnePatient);
        scheduleWithoutPatient.setPatients(null);
        scheduleDtoWithoutPatient.setPatients(null);
        when(repository.findById(anyString())).thenReturn(Optional.of(scheduleWithoutPatient));
        when(patientService.findById(anyString())).thenReturn(utils.createPatientDTO1());
        when(modelMapper.map(any(PatientDTO.class), eq(PatientReducedDTO.class))).thenReturn(
                utils.createPatientReducedDTO(utils.createPatient1()));
        when(modelMapper.map(any(PatientReducedDTO.class), eq(Patient.class))).thenReturn(utils.createPatient1());
        when(repository.save(any())).thenReturn(scheduleWithOnePatient);
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWithoutPatient);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDtoWhitOnePatient);

        // Act
        var result = service.addPatient("id", "patientId");

        // Assert
        assertNotNull(result);

        assertEquals(scheduleDtoWithoutPatient.getPratictioner(), result.getPratictioner());
        assertEquals(scheduleDtoWithoutPatient.getDateTime(), result.getDateTime());
        assertTrue(result.getPatients().size() > 0);
        assertEquals(scheduleDtoWithoutPatient.getHealthProcedure(), result.getHealthProcedure());

        verify(repository, times(1)).save(any());
    }

    // deve retornar exceção se não encontrar agendamento
    @Test
    void addPatient_shouldThrowExceptionWhenTryAddPatientInScheduleThatNotExists() {
        //Arrange
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        var exc = assertThrows(ResourceNotFoundException.class, () -> service.addPatient("id", "patientId"));
        assertEquals(SCHEDULE_NOT_FOUND, exc.getMessage());
    }

    // deve retornar exceção se não encontrar paciente
    @Test
    void addPatient_shouldThrowExceptionWhenTryAddPatientThatNotExists() {
        //Arrange
        when(repository.findById(anyString())).thenReturn(Optional.ofNullable(scheduleWithOnePatient));
        when(patientService.findById(anyString())).thenReturn(null);

        // Act & Assert
        var exc = assertThrows(ResourceNotFoundException.class, () -> service.addPatient("id", "patientId"));
        assertEquals(PATIENT_NOT_FOUND, exc.getMessage());
    }

    // deve retornar exceção se agendamento estiver cheio
    @Test
    void addPatient_shouldThrowExceptionWhenTryAddPatientInScheduleThatIsFull() {
        //Arrange
        when(repository.findById(anyString())).thenReturn(Optional.ofNullable(scheduleWithOnePatient));
        when(patientService.findById(anyString())).thenReturn(utils.createPatientDTO1());

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.addPatient("id", "patientId"));
        assertEquals(SCHEDULE_PATIENTS_FULL, exc.getMessage());
    }

    // deve retornar exceção se paciente já estiver no agendamento
    @Test
    void addPatient_shouldThrowExceptionWhenTryAddPatientThatAlreadyExistsInSchedule() throws ParseException {
        //Arrange
        when(repository.findById(anyString())).thenReturn(Optional.ofNullable(scheduleWithMultiplePatients));
        when(patientService.findById(anyString())).thenReturn(utils.createPatientDTO1());

        when(modelMapper.map(any(PatientDTO.class), eq(PatientReducedDTO.class))).thenReturn(
                utils.createPatientReducedDTO(utils.createPatient1()));
        when(modelMapper.map(any(PatientReducedDTO.class), eq(Patient.class))).thenReturn(utils.createPatient1());

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.addPatient("id", "1"));
        assertEquals(DUPLICATE_PATIENT, exc.getMessage());
    }

    // ##################### REMOVE PATIENT IN SCHEDULE #####################

    // sucesso


    // deve retornar exceção se não encontrar agendamento

    // deve retornar exceção se não encontrar paciente


    // ##################### UPDATE #####################

    // ##################### FIND BY DATE #####################

    // sucesso

    // deve retornar exceção se não encontrar nenhum agendamento

    // deve retornar exceção se data do agendamento for anterior a data atual
}