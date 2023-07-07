package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import com.healthAppointment.healthAppointment.repository.PatientRepository;
import com.healthAppointment.healthAppointment.service.IPatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService implements IPatientService {


    private final PatientRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    private PatientService(PatientRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public PatientDTO save(PatientDTO request) {

        Patient patient = buildPatient(request);
        Optional<Patient> responseOp = Optional.of(repository.save(patient));

        return buildPatientDTO(responseOp.get());
    }

    public Page<PatientDTO> findAllActive(Pageable page) {

        Page<Patient> response = repository.findAllActive(page);
        return buildPatientDTOList(response);

    }

    @Override
    public Page<PatientDTO> findAllInactive(Pageable page) {
        Page<Patient> response = repository.findAllInactive(page);
        return buildPatientDTOList(response);
    }

    @Override
    public Page<PatientDTO> findAll(Pageable page) {
        Page<Patient> response = repository.findAll(page);
        return buildPatientDTOList(response);
    }

    @Override
    public PatientDTO findById(String id) throws Exception {
        Optional<Patient> responseOp = repository.findById(id);
        if(responseOp.isEmpty()) {
            throw new Exception("Paciente não encontrado");
        }
        return buildPatientDTO(responseOp.get());
    }

    private Page<PatientDTO> buildPatientDTOList(Page<Patient> response) {
        return response.map(this::buildPatientDTO);
    }

    private PatientDTO buildPatientDTO(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    private Patient buildPatient(PatientDTO request) {
        return modelMapper.map(request, Patient.class);
    }
}
