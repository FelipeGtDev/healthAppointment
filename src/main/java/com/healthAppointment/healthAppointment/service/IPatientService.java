package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPatientService {

    public PatientDTO save(PatientDTO request);

    public Page<PatientDTO> findAll(Pageable page);
}
