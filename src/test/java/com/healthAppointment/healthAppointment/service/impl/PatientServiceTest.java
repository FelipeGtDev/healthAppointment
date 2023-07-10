package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import com.healthAppointment.healthAppointment.repository.PatientRepository;
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
    @Mock
    private PatientRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private final PatientUtils patientUtils;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    public PatientServiceTest() {
        this.patientUtils = new PatientUtils();
    }

    @Test
    void save_shouldReturnPatientDTO() throws ParseException {


        // Arrange
        PatientDTO patientDTO = patientUtils.createPatientDTO1();
        Patient patient = patientUtils.createPatient1();

        when(modelMapper.map(patientDTO, Patient.class)).thenReturn(patient);
        when(repository.save(patient)).thenReturn(patient);
        when(modelMapper.map(patient, PatientDTO.class)).thenReturn(patientDTO);

        // Act
        PatientDTO response = patientService.save(patientDTO);

        // Assert
        assertNotNull(response);
        assertEquals(patient, response);
        assertEquals(patient.getId(), response.getId());
        assertEquals(patient.getBirthDate(), response.getBirthDate());
        assertEquals(patient.getCpf(), response.getCpf());
        assertEquals(patient.getName(), response.getName());
        assertEquals(patient.getGender(), response.getGender());


        verify(repository, times(1)).save(patient);


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

        Patient patient1 = patientUtils.createPatient1();
        Patient patient2 = patientUtils.createPatient2();

        PatientDTO patientDTO1 = patientUtils.createPatientDTO1();
        PatientDTO patientDTO2 = patientUtils.createPatientDTO2();

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
    void update_shouldReturnUpdatedPatientDTO() {
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
        assertThrows(RuntimeException.class, () -> patientService.update(id, request));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void delete_shouldDeletePatient() {
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
        assertThrows(RuntimeException.class, () -> patientService.delete(id));
        verify(repository, times(1)).findById(id);
    }


}