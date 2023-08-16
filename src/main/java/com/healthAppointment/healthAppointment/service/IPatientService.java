package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPatientService {

    PatientDTO save(PatientDTO request);

    Page<PatientDTO> findAllActive(Pageable page);

    Page<PatientDTO> findAllInactive(Pageable page);

    Page<PatientDTO> findAll(Pageable page);

    PatientDTO getById(String id);

    Optional<Patient> findById(String id);

    Page<PatientDTO> findByName(String name, Pageable page);

    PatientDTO update(String id, PatientDTO request) throws Exception;

    void delete(String id) throws Exception;
}
