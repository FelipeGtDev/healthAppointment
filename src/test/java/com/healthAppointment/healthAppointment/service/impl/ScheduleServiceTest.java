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

    private ScheduleDTO scheduleDtoWithOnePatient;
    private ScheduleDTO scheduleDtoWithMultiplePatients;
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
        scheduleDtoWithOnePatient = utils.createScheduleDtoWhithOnePatient();
        scheduleDtoWithMultiplePatients = utils.createScheduleDtoWhithMultiplePatients();

        MockitoAnnotations.openMocks(this);
    }


    // ##################### CREATE #####################
    @Test
    void save_shouldReturnScheduleWhitOnePatient() throws BusException {

        //Arrange
        when(repository.save(any())).thenReturn(scheduleWithOnePatient);
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWithOnePatient);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDtoWithOnePatient);

        // Act
        var result = service.save(scheduleDtoWithOnePatient);

        // Assert
        assertNotNull(result);

        assertEquals(scheduleDtoWithOnePatient.getPratictioner(), result.getPratictioner());
        assertEquals(scheduleDtoWithOnePatient.getDateTime(), result.getDateTime());
        assertEquals(scheduleDtoWithOnePatient.getPatients().size(), result.getPatients().size());
        assertEquals(scheduleDtoWithOnePatient.getPatients().get(0), result.getPatients().get(0));
        assertEquals(scheduleDtoWithOnePatient.getHealthProcedure(), result.getHealthProcedure());

        verify(repository, times(1)).save(any());
    }

    @Test
    void save_shouldReturnScheduleWithoutPatient() throws BusException {

        //Arrange
        var scheduleDtoWithoutPatient = new ScheduleDTO(scheduleDtoWithOnePatient);
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
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDtoWithMultiplePatients);
        scheduleWithMultiplePatients.setHealthProcedure(utils.createQualificationPhisioterapy());

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDtoWithMultiplePatients));
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
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDtoWithMultiplePatients);

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDtoWithMultiplePatients));
        assertEquals(SCHEDULE_PATIENTS_FULL, exc.getMessage());
    }

    @Test
    void save_shouldThrowExceptionWhenTryCreateScheduleWithAnExistingDateTimeAndPratictioner() {
        //Arrange
        when(repository.findScheduleByDateTimeAndPratictionerId(any(), any())).thenReturn(Optional.ofNullable(scheduleWithOnePatient));

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDtoWithMultiplePatients));
        assertEquals(SCHEDULE_ALREADY_EXISTS, exc.getMessage());
    }

    @Test
    void save_shouldThrowExceptionWhenTryCreateScheduleWithDuplicatePatient() {
        //Arrange
        scheduleWithMultiplePatients.getPatients().add(scheduleWithMultiplePatients.getPatients().get(0));
        scheduleDtoWithMultiplePatients.getPatients().add(scheduleDtoWithMultiplePatients.getPatients().get(0));
        when(repository.save(any())).thenReturn(scheduleWithMultiplePatients);
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWithMultiplePatients);

        // Act & Assert
        var exc = assertThrows(BusException.class, () -> service.save(scheduleDtoWithMultiplePatients));
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
        when(repository.findByDateTimeBetween(LocalDate.now(), LocalDate.now().plusDays(1))).thenReturn(lista);

        // Act & Assert
        assertThrows(AssertionError.class, () -> service.findAllByDate(""));
    }

    // ##################### ADD PATIENT IN SCHEDULE #####################

    @Test
    void addPatient_shouldReturnScheduleWhitPatientAdded() throws BusException, ResourceNotFoundException, ParseException {
        //Arrange
        var scheduleDtoWithoutPatient = new ScheduleDTO(scheduleDtoWithOnePatient);
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
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDtoWithOnePatient);

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
    @Test
    void removePatient_shouldReturnScheduleWhitPatientRemoved() throws BusException, ResourceNotFoundException, ParseException {
        //Arrange
        var scheduleDtoWithoutPatient = new ScheduleDTO(scheduleDtoWithOnePatient);
        var scheduleWithoutPatient = new Schedule(scheduleWithOnePatient);
        scheduleWithoutPatient.setPatients(null);
        scheduleDtoWithoutPatient.setPatients(null);
        when(repository.findById(anyString())).thenReturn(Optional.of(scheduleWithOnePatient));
        when(patientService.findById(anyString())).thenReturn(utils.createPatientDTO1());
        when(modelMapper.map(any(PatientDTO.class), eq(PatientReducedDTO.class))).thenReturn(
                utils.createPatientReducedDTO(utils.createPatient1()));
        when(modelMapper.map(any(PatientReducedDTO.class), eq(Patient.class))).thenReturn(utils.createPatient1());
        when(repository.save(any())).thenReturn(scheduleWithoutPatient);
        when(modelMapper.map(any(ScheduleDTO.class), eq(Schedule.class))).thenReturn(scheduleWithOnePatient);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDtoWithoutPatient);

        // Act
        var result = service.removePatient("id", "1");

        // Assert
        assertNotNull(result);

        assertEquals(scheduleDtoWithoutPatient.getPratictioner(), result.getPratictioner());
        assertEquals(scheduleDtoWithoutPatient.getDateTime(), result.getDateTime());
        assertTrue(result.getPatients() == null || result.getPatients().size() == 0);
        assertEquals(scheduleDtoWithoutPatient.getHealthProcedure(), result.getHealthProcedure());

        verify(repository, times(1)).save(any());
    }

    // deve retornar exceção se não encontrar agendamento
    @Test
    void removePatient_shouldThrowExceptionWhenTryRemovePatientInScheduleThatNotExists() {
        //Arrange
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        var exc = assertThrows(ResourceNotFoundException.class, () -> service.removePatient("id", "patientId"));
        assertEquals(SCHEDULE_NOT_FOUND, exc.getMessage());
    }

    // deve retornar exceção se não encontrar paciente
    @Test
    void removePatient_shouldThrowExceptionWhenTryRemovePatientThatNotExists() {
        //Arrange
        when(repository.findById(anyString())).thenReturn(Optional.ofNullable(scheduleWithOnePatient));
        when(patientService.findById(anyString())).thenReturn(null);

        // Act & Assert
        var exc = assertThrows(ResourceNotFoundException.class, () -> service.removePatient("id", "patientId"));
        assertEquals(PATIENT_NOT_FOUND, exc.getMessage());
    }


    // ##################### FIND BY DATE #####################
    @Test
    void findByDate_shouldReturnScheduleList() throws ParseException {
        //Arrange
        // differentiating schedule times
        scheduleDtoWithOnePatient.setDateTime(LocalDateTime.parse("2023-12-01T08:00:00"));
        scheduleDtoWithMultiplePatients.setDateTime(LocalDateTime.parse("2023-12-01T09:00:00"));

        List lista = new ArrayList();
        lista.add(scheduleWithOnePatient);
        lista.add(scheduleWithMultiplePatients);

        when(repository.findByDateTimeBetween(any(), any())).thenReturn(lista);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDtoWithOnePatient, scheduleDtoWithMultiplePatients);

        // Act
        var result = service.findAllByDate("");

        // Assert
        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertTrue(result.get(0).getDateTime().isBefore(result.get(1).getDateTime()));
        verify(repository, times(1)).findByDateTimeBetween(any(), any());
    }

    // deve retornar exceção se resposta trouxer agendamentos de mais de uma data
    @Test
    void findByDate_shouldThrowExceptionWhenTryFindScheduleWithMoreThanOneDate() throws ParseException {
        //Arrange
        // differentiating schedule times
        scheduleWithOnePatient.setDateTime(LocalDateTime.parse("2023-12-01T08:00:00"));
        scheduleWithMultiplePatients.setDateTime(LocalDateTime.parse("2023-12-02T09:00:00"));

        List lista = new ArrayList();
        lista.add(scheduleWithOnePatient);
        lista.add(scheduleWithMultiplePatients);

        when(repository.findByDateTimeBetween(any(), any())).thenReturn(lista);
        when(modelMapper.map(any(Schedule.class), eq(ScheduleDTO.class))).thenReturn(scheduleDtoWithOnePatient, scheduleDtoWithMultiplePatients);

        // Act & Assert
        var exc = assertThrows(AssertionError.class, () -> service.findAllByDate(""));
    }

    // ##################### UPDATE #####################

    // sucesso

    // deve retornar exceção se não encontrar agendamento

    // deve retornar exceção se não encontrar paciente

    // deve retornar exceção se não encontrar procedimento de saúde

    // deve retornar exceção se tentar se adicionar mais pacientes do que o permitido

    // deve retornar exceção se tentar adicionar um paciente que já está no agendamento

    // ##################### DELETE #####################

    // sucesso

    // deve retornar exceção se não encontrar agendamento

    // ##################### FIND BY ID #####################

    // sucesso

    // deve retornar exceção se não encontrar agendamento


}