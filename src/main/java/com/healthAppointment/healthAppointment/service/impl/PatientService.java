package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.Address;
import com.healthAppointment.healthAppointment.model.Contacts;
import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.dto.AddressDTO;
import com.healthAppointment.healthAppointment.model.dto.ContactsDTO;
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

    public Page<PatientDTO> findAll(Pageable page) {

        Page<Patient> response = repository.findAllActive(page);
        return buildPatientDTOList(response);

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

    private Address buildAddress(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }

    private Contacts buildContacts(ContactsDTO contactsDTO) {
        return modelMapper.map(contactsDTO, Contacts.class);
    }
}
