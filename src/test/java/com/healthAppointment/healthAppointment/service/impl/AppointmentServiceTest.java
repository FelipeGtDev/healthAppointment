package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.Pratictioner;
import com.healthAppointment.healthAppointment.model.Qualification;
import com.healthAppointment.healthAppointment.model.Schedule;
import com.healthAppointment.healthAppointment.model.dto.PatientReducedDTO;
import com.healthAppointment.healthAppointment.model.dto.PratictionerReducedDTO;
import com.healthAppointment.healthAppointment.model.dto.QualificationReducedDTO;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    private Schedule scheduleMultiplePatients;
    private ScheduleDTO scheduleDtoMultiplePatients;

    @Mock
    private PatientService patientService;
    @Mock
    private PratictionerService pratictionerService;
    @Mock
    private QualificationService qualificationService;
    @Mock
    private AppointmentRepository repository;
    @Mock
    private ModelMapper modelMapper;
    private final Utils utils;

    @InjectMocks
    private AppointmentService service;


    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);

        scheduleMultiplePatients = utils.createScheduleWhithMultiplePatients();
        scheduleDtoMultiplePatients = utils.createScheduleDtoWhithMultiplePatients();

    }

    AppointmentServiceTest() {
        this.utils = new Utils();
    }


    @Test
    void createAppointmentsFromSchedule_ShouldSaveListOfAppointments() throws ParseException, ResourceNotFoundException {
        // Arrange
        when(modelMapper.map(any(PratictionerReducedDTO.class), eq(Pratictioner.class))).thenReturn(utils.createPratictioner1());
        when(modelMapper.map(any(QualificationReducedDTO.class), eq(Qualification.class))).thenReturn(utils.createQualificationPhisioterapy());
        when(modelMapper.map(any(PatientReducedDTO.class), eq(Patient.class))).thenReturn(utils.createPatient1(), utils.createPatient2());

        when(pratictionerService.getById(anyString())).thenReturn(utils.createPratictionerDTO1());
        when(qualificationService.findByCode(anyString())).thenReturn(utils.createQualificationPhisioterapy());
        when(patientService.findById(anyString())).thenReturn(Optional.ofNullable(utils.createPatient1()), Optional.ofNullable(utils.createPatient2()));
        when(repository.save(any())).thenReturn(utils.createAppointment(utils.createAppointmentDto1DONE()), utils.createAppointment(utils.createAppointmentDto2DONE()));

        // Act
        service.createAppointmentsFromSchedule(scheduleDtoMultiplePatients);

        // Assert
        verify(repository, times(2)).save(any());

    }
}