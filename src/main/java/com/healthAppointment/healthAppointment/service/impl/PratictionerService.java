package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.Pratictioner;
import com.healthAppointment.healthAppointment.model.Qualification;
import com.healthAppointment.healthAppointment.model.RegulatoryAgency;
import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import com.healthAppointment.healthAppointment.model.dto.QualificationDTO;
import com.healthAppointment.healthAppointment.repository.PratictionerRepository;
import com.healthAppointment.healthAppointment.service.IPratictionerService;
import com.healthAppointment.healthAppointment.service.IQualificationService;
import com.healthAppointment.healthAppointment.service.IRegulatoryAgencyService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PratictionerService implements IPratictionerService {

    private final PratictionerRepository repository;
    private final IQualificationService qualificationService;
    private final IRegulatoryAgencyService regulatoryAgencyService;
    private final ModelMapper modelMapper;

    private PratictionerService(PratictionerRepository repository, QualificationService qualificationService, IRegulatoryAgencyService regulatoryAgencyService, ModelMapper modelMapper) {
        this.repository = repository;
        this.qualificationService = qualificationService;
        this.regulatoryAgencyService = regulatoryAgencyService;
        this.modelMapper = modelMapper;
    }


    public PratictionerDTO save(PratictionerDTO request) {
        Pratictioner pratictioner = buildPratictioner(request);

        if (request.getRegulatoryAgency() != null) {
            pratictioner.setRegulatoryAgency(findRegulatoryAgency(request.getRegulatoryAgency().getId()));
        }

        if (request.getQualifications() != null) {
            pratictioner.setQualifications(findQualifications(request.getQualifications()));
        }
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
        if (responseOp.isEmpty()) {
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
        if (responseOp.isEmpty()) {
            throw new Exception("Profissional não encontrado");
        }
        Pratictioner pratictioner = buildPratictioner(request);
        repository.save(pratictioner);
        return buildPratictionerDTO(pratictioner);
    }

    @Override
    public void delete(String id) throws Exception {
        Optional<Pratictioner> responseOp = repository.findById(id);
        if (responseOp.isEmpty()) {
            throw new Exception("Profissional não encontrado");
        }
        repository.delete(responseOp.get());
    }


    private Page<PratictionerDTO> buildPratictionerDTOList(Page<Pratictioner> response) {
        return response.map(this::buildPratictionerDTO);
    }

    private List<Qualification> findQualifications(List<QualificationDTO> qualifications) {
        List<String> codeList = qualificationService.getCodeListFromTypes(qualifications);

        return qualificationService.findByCodeList(codeList);
    }

    private RegulatoryAgency findRegulatoryAgency(String id) {
        Optional<RegulatoryAgency> agency = regulatoryAgencyService.findById(id);
        if (agency.isEmpty()) {
            throw new RuntimeException(); // TODO Melhorar
        }
        return agency.get();
    }

    private PratictionerDTO buildPratictionerDTO(Pratictioner pratictioner) {
        return modelMapper.map(pratictioner, PratictionerDTO.class);
    }

    private Pratictioner buildPratictioner(PratictionerDTO request) {
        return modelMapper.map(request, Pratictioner.class);
    }
}
