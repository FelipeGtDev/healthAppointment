package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.Qualification;
import com.healthAppointment.healthAppointment.model.dto.QualificationDTO;
import com.healthAppointment.healthAppointment.model.dto.QualificationReducedDTO;
import com.healthAppointment.healthAppointment.repository.QualificationRepository;
import com.healthAppointment.healthAppointment.service.IQualificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QualificationService implements IQualificationService {

    private final QualificationRepository repository;
    private final ModelMapper modelMapper;

    public QualificationService(QualificationRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public QualificationDTO save(QualificationDTO requestDTO) {
        List<Qualification> types;

        var request = modelMapper.map(requestDTO, Qualification.class);

        if (requestDTO.getTypes() != null) {
            List<String> typeCodes = getCodeListFromTypes(requestDTO.getTypes());
            types = repository.findByCodeList(typeCodes);
            request.setTypes(types);
        }
        var response = repository.save(request);
        return modelMapper.map(response, QualificationDTO.class);
    }

    @Override
    public Qualification addType(String qualificationId, String typeId) {
        Optional<Qualification> speciality = Optional.of(repository.findById(qualificationId).get());
        Optional<Qualification> type = Optional.of(repository.findById(typeId).get());

        speciality.get().addType(type.get());

        return repository.save(speciality.get());
    }

    @Override
    public List<Qualification> findByCodeList(List<String> codes) {
        return repository.findByCodeList(codes);
    }

    @Override
    public Qualification findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public List<QualificationReducedDTO> listByType(String code) {
        List<Qualification> qualifications = repository.listByType(code);

        return buildQualificationReducedDTOList(qualifications);
    }

    @Override
    public List<String> getCodeListFromTypes(List<QualificationDTO> qualificationsDTO) {

        List<String> codeList = new ArrayList<>();

        qualificationsDTO.stream()
                .map(QualificationDTO::getCode)
                .forEach(codeList::add);

        return codeList;
    }


    private List<QualificationReducedDTO> buildQualificationReducedDTOList(List<Qualification> response) {
        List<QualificationReducedDTO> list = new ArrayList<>();
        for (Qualification qualification : response) {
            list.add(buildQualificationReducedDTO(qualification));
        }

        return list;
    }

    private QualificationReducedDTO buildQualificationReducedDTO(Qualification response) {
        return modelMapper.map(response, QualificationReducedDTO.class);
    }
}
