package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.Qualification;
import com.healthAppointment.healthAppointment.model.RegulatoryAgency;
import com.healthAppointment.healthAppointment.model.dto.RegulatoryAgencyDTO;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;
import com.healthAppointment.healthAppointment.repository.RegulatoryAgencyRepository;
import com.healthAppointment.healthAppointment.service.IRegulatoryAgencyService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegulatoryAgencyService implements IRegulatoryAgencyService {

    private final RegulatoryAgencyRepository repository;
    private final QualificationService qualificationService;
    private final ModelMapper modelMapper;

    public RegulatoryAgencyService(RegulatoryAgencyRepository repository, QualificationService qualificationService, ModelMapper modelMapper) {
        this.repository = repository;
        this.qualificationService = qualificationService;
        this.modelMapper = modelMapper;
    }
    @Override
    public RegulatoryAgencyDTO save(RegulatoryAgencyDTO request) throws ResourceNotFoundException {

        RegulatoryAgency agency = buildRegulatoryAgency(request);
        Optional<RegulatoryAgency> response = Optional.of(repository.save(agency));

        return buildRegulatoryAgencyDTO(response.get());
    }

    @Override
    public Optional<RegulatoryAgency> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Page<RegulatoryAgencyDTO> findAll(Pageable page) {
        Page<RegulatoryAgency> response = repository.findAll(page);
        return buildRegulatoryAgencyDTOList(response);
    }

    @Override
    public Page<RegulatoryAgencyDTO> findByName(String name, Pageable page) {
        Page<RegulatoryAgency> response = repository.findByName(name, page);
        return buildRegulatoryAgencyDTOList(response);
    }

    @Override
    public Page<RegulatoryAgencyDTO> findByQualificationAndState(String qualificationCode
            , StateAcronym state, Pageable page) throws ResourceNotFoundException {
        Optional<Qualification> qualification = Optional.ofNullable(qualificationService.findByCode(qualificationCode));
        if (qualification.isPresent()) {
            if (qualification.get().getTypes().isEmpty()){

                Page<RegulatoryAgency> response = repository.findByQualificationAndState(qualificationCode, state, page);
                return buildRegulatoryAgencyDTOList(response);
            }
        }
        throw new IllegalArgumentException("Qualification has types!");
    }

    private Page<RegulatoryAgencyDTO> buildRegulatoryAgencyDTOList(Page<RegulatoryAgency> response) {
        return response.map(this::buildRegulatoryAgencyDTO);
    }

    private RegulatoryAgencyDTO buildRegulatoryAgencyDTO(RegulatoryAgency regulatoryAgency) {
        return modelMapper.map(regulatoryAgency, RegulatoryAgencyDTO.class);
    }

    private RegulatoryAgency buildRegulatoryAgency(RegulatoryAgencyDTO request) throws ResourceNotFoundException {
        RegulatoryAgency agency = modelMapper.map(request, RegulatoryAgency.class);

        String relatedQualificationCode = request.getQualification().getCode();
        Optional<Qualification> qualification = Optional.ofNullable(qualificationService.findByCode(relatedQualificationCode));
        if (qualification.isPresent()) {
            agency.getQualification().setName(qualification.get().getName());
            agency.getQualification().setCode(qualification.get().getCode());
        }

        return agency;
    }
}
