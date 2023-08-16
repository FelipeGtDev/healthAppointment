package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import com.healthAppointment.healthAppointment.repository.PatientRepository;
import com.healthAppointment.healthAppointment.service.IPatientService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.PATIENT_NOT_FOUND;

@Service
public class PatientService implements IPatientService {


    private final PatientRepository repository;
    private final ModelMapper modelMapper;

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
    public PatientDTO getById(String id) throws ResourceNotFoundException {
        Optional<Patient> responseOp = findById(id);
        return responseOp.map(this::buildPatientDTO).orElse(null);
    }

    @Override
    public Optional<Patient> findById(String id) throws ResourceNotFoundException {

        Optional<Patient> responseOp = repository.findById(id);
        if (responseOp.isEmpty()) {
            throw new ResourceNotFoundException(PATIENT_NOT_FOUND + id);
        }
        return responseOp;
    }

    @Override
    public Page<PatientDTO> findByName(String name, Pageable page) {
        Page<Patient> response = repository.findByName(name, page);
        return buildPatientDTOList(response);
    }

    @Override
    public PatientDTO update(String id, PatientDTO request) throws ResourceNotFoundException {
        Optional<Patient> responseOp = findById(id);

        Patient patient = buildPatient(request);
        patient = repository.save(patient);
        return buildPatientDTO(patient);
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException {
        Optional<Patient> responseOp = findById(id);

        repository.delete(responseOp.get());
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
