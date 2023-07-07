package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPatientService {

    PatientDTO save(PatientDTO request);

    Page<PatientDTO> findAll(Pageable page);
}
