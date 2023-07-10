package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.Pratictioner;
import com.healthAppointment.healthAppointment.model.RegulatoryAgency;
import com.healthAppointment.healthAppointment.model.Speciality;
import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import com.healthAppointment.healthAppointment.model.dto.SpecialityDTO;
import com.healthAppointment.healthAppointment.model.mockObjects.MockObjects;
import com.healthAppointment.healthAppointment.repository.PratictionerRepository;
import com.healthAppointment.healthAppointment.service.IPratictionerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PratictionerService implements IPratictionerService {

    private final PratictionerRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    private PratictionerService(PratictionerRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }


    public PratictionerDTO save(PratictionerDTO request) {
        Pratictioner pratictioner = buildPratictioner(request);
        pratictioner.setRegulatoryAgency(findRegulatoryAgency(request.getRegulatoryAgency().getId()));
        pratictioner.setSpecialties(findSpecialties(request.getSpecialties()));
        Optional<Pratictioner> responseOp = Optional.of(repository.save(pratictioner));

        return buildPratictionerDTO(responseOp.get());
    }

    // TODO Método mocado. Remover quando implementar o RegulatoryAgencyService
    private List<Speciality> findSpecialties(List<SpecialityDTO> specialties) {
        List<Speciality> list = new ArrayList<>();
        MockObjects mockObjects = new MockObjects();
        for (SpecialityDTO speciality : specialties) {
            list.add(mockObjects.getSpeciality(speciality.getCode()));
        }
        return list;
    }

    // TODO Método mocado. Remover quando implementar o RegulatoryAgencyService
    private RegulatoryAgency findRegulatoryAgency(String id) {
        MockObjects mockObjects = new MockObjects();
        return mockObjects.getAgency(id);
    }

    private PratictionerDTO buildPratictionerDTO(Pratictioner pratictioner) {
        return modelMapper.map(pratictioner, PratictionerDTO.class);
    }

    private Pratictioner buildPratictioner(PratictionerDTO request) {
        return modelMapper.map(request, Pratictioner.class);
    }
}
