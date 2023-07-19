package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import com.healthAppointment.healthAppointment.repository.PatientRepository;
import com.healthAppointment.healthAppointment.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    Patient patient1;
    Patient patient2;
    PatientDTO patientDTO1;
    PatientDTO patientDTO2;

    @Mock
    private PatientRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private final Utils patientUtils;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() throws ParseException {

        MockitoAnnotations.initMocks(this);
        patient1 = patientUtils.createPatient1();
        patient2 = patientUtils.createPatient2();

        patientDTO1 = patientUtils.createPatientDTO1();
        patientDTO2 = patientUtils.createPatientDTO2();
    }


    public PatientServiceTest() {
        this.patientUtils = new Utils();
    }

    @Test
    void save_shouldReturnPatientDTO() throws ParseException {

        // Arrange
        when(modelMapper.map(patientDTO1, Patient.class)).thenReturn(patient1);
        when(repository.save(any())).thenReturn(patient1);
        when(modelMapper.map(patient1, PatientDTO.class)).thenReturn(patientDTO1);

        // Act
        PatientDTO response = patientService.save(patientDTO1);

        // Assert
        assertNotNull(response);

        assertEquals(patient1.getId(), response.getId());
        assertEquals(patient1.getBirthDate(), DateUtils.dateFormat.parse(response.getBirthDate()));
        assertEquals(patient1.getCpf(), response.getCpf());
        assertEquals(patient1.getName().getName(), response.getName().getName());
        assertEquals(patient1.getGender(), response.getGender());


        verify(repository, times(1)).save(patient1);
    }

    @Test
    void findAllActive_shouldReturnPatientDTOPage() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<Patient> patients = new PageImpl<>(new ArrayList<>());
        Page<PatientDTO> expectedResponse = new PageImpl<>(new ArrayList<>());

        when(repository.findAllActive(pageable)).thenReturn(patients);
        when(modelMapper.map(any(Patient.class), eq(PatientDTO.class))).thenReturn(new PatientDTO());

        // Act
        Page<PatientDTO> response = patientService.findAllActive(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(repository, times(1)).findAllActive(pageable);
    }

    @Test
    void findAllInactive_shouldReturnPatientDTOPage() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<Patient> patients = new PageImpl<>(new ArrayList<>());
        Page<PatientDTO> expectedResponse = new PageImpl<>(new ArrayList<>());

        when(repository.findAllInactive(pageable)).thenReturn(patients);
        when(modelMapper.map(any(Patient.class), eq(PatientDTO.class))).thenReturn(new PatientDTO());

        // Act
        Page<PatientDTO> response = patientService.findAllInactive(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(repository, times(1)).findAllInactive(pageable);
    }

    @Test
    void findAll_shouldReturnPatientDTOPage() throws ParseException {


        // Arrange
        Pageable pageable = mock(Pageable.class);
        List patientsList = new ArrayList();
        patientsList.add(patient1);
        patientsList.add(patient2);

        List patientsDTOList = new ArrayList();
        patientsDTOList.add(patientDTO1);
        patientsDTOList.add(patientDTO2);

        Page<Patient> patients = new PageImpl<>(patientsList);
        Page<PatientDTO> expectedResponse = new PageImpl<>(patientsDTOList);

        when(repository.findAll(pageable)).thenReturn(patients);
        when(modelMapper.map(patient1, PatientDTO.class)).thenReturn(patientDTO1);
        when(modelMapper.map(patient2, PatientDTO.class)).thenReturn(patientDTO2);

        // Act
        Page<PatientDTO> response = patientService.findAll(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void findById_shouldReturnPatientDTO() throws Exception {
        // Arrange
        String id = "1";
        Patient patient = new Patient();
        PatientDTO expectedResponse = new PatientDTO();

        when(repository.findById(id)).thenReturn(Optional.of(patient));
        when(modelMapper.map(patient, PatientDTO.class)).thenReturn(expectedResponse);

        // Act
        PatientDTO response = patientService.findById(id);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findById_shouldThrowExceptionWhenPatientNotFound() {
        // Arrange
        String id = "1";

        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> patientService.findById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findByName_shouldReturnPatientDTOPage() {
        // Arrange
        String name = "John Doe";
        Pageable pageable = mock(Pageable.class);
        Page<Patient> patients = new PageImpl<>(new ArrayList<>());
        Page<PatientDTO> expectedResponse = new PageImpl<>(new ArrayList<>());

        when(repository.findByName(name, pageable)).thenReturn(patients);
        when(modelMapper.map(any(Patient.class), eq(PatientDTO.class))).thenReturn(new PatientDTO());

        // Act
        Page<PatientDTO> response = patientService.findByName(name, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(repository, times(1)).findByName(name, pageable);
    }

    @Test
    void update_shouldReturnUpdatedPatientDTO() throws Exception {
        // Arrange
        String id = "1";
        PatientDTO request = new PatientDTO();
        Patient existingPatient = new Patient();
        Patient updatedPatient = new Patient();
        PatientDTO expectedResponse = new PatientDTO();

        when(repository.findById(id)).thenReturn(Optional.of(existingPatient));
        when(modelMapper.map(request, Patient.class)).thenReturn(updatedPatient);
        when(repository.save(updatedPatient)).thenReturn(updatedPatient);
        when(modelMapper.map(updatedPatient, PatientDTO.class)).thenReturn(expectedResponse);

        // Act
        PatientDTO response = patientService.update(id, request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(updatedPatient);
    }

    @Test
    void update_shouldThrowExceptionWhenPatientNotFound() {
        // Arrange
        String id = "1";
        PatientDTO request = new PatientDTO();

        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> patientService.update(id, request));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void delete_shouldDeletePatient() throws Exception {
        // Arrange
        String id = "1";
        Patient patient = new Patient();

        when(repository.findById(id)).thenReturn(Optional.of(patient));

        // Act
        patientService.delete(id);

        // Assert
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).delete(patient);
    }

    @Test
    void delete_shouldThrowExceptionWhenPatientNotFound() {
        // Arrange
        String id = "1";

        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> patientService.delete(id));
        verify(repository, times(1)).findById(id);
    }


}