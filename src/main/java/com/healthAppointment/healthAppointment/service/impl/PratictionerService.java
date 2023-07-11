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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PratictionerService implements IPratictionerService {

    private final PratictionerRepository repository;
    private final ModelMapper modelMapper;

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

    @Override
    public Page<PratictionerDTO> findAllActive(Pageable page) {
        Page<Pratictioner> response = repository.findAllActive(page);
        return buildPratictionerDTOList(response);
    }

    @Override
    public Page<PratictionerDTO> findAllInactive(Pageable page) {
        Page<Pratictioner> response = repository.findAllInactive(page);
        return buildPratictionerDTOList(response);
    }

    @Override
    public Page<PratictionerDTO> findAll(Pageable page) {
        Page<Pratictioner> response = repository.findAll(page);
        return buildPratictionerDTOList(response);
    }

    @Override
    public PratictionerDTO findById(String id) throws Exception {
        Optional<Pratictioner> responseOp = repository.findById(id);
        if(responseOp.isEmpty()) {
            throw new Exception("Profissional não encontrado");
        }
        return buildPratictionerDTO(responseOp.get());
    }

    @Override
    public Page<PratictionerDTO> findByName(String name, Pageable page) {
        Page<Pratictioner> response = repository.findByName(name, page);
        return buildPratictionerDTOList(response);
    }

    @Override
    public PratictionerDTO update(String id, PratictionerDTO request) throws Exception {
        Optional<Pratictioner> responseOp = repository.findById(id);
        if(responseOp.isEmpty()) {
            throw new Exception("Profissional não encontrado");
        }
        Pratictioner pratictioner = buildPratictioner(request);
        repository.save(pratictioner);
        return buildPratictionerDTO(pratictioner);
    }

    @Override
    public void delete(String id) throws Exception {
        Optional<Pratictioner> responseOp = repository.findById(id);
        if(responseOp.isEmpty()) {
            throw new Exception("Profissional não encontrado");
        }
        repository.delete(responseOp.get());
    }


    private Page<PratictionerDTO> buildPratictionerDTOList(Page<Pratictioner> response) {
        return response.map(this::buildPratictionerDTO);
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
