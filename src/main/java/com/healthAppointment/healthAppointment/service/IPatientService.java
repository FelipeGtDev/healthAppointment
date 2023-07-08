package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPatientService {

    PatientDTO save(PatientDTO request);

    Page<PatientDTO> findAllActive(Pageable page);

    Page<PatientDTO> findAllInactive(Pageable page);

    Page<PatientDTO> findAll(Pageable page);

    PatientDTO findById(String id) throws Exception;

    Page<PatientDTO> findByName(String name, Pageable page);

    PatientDTO update(String id, PatientDTO request);

    void delete(String id);
}
