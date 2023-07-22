package com.healthAppointment.healthAppointment.service.impl;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.healthAppointment.healthAppointment.model.Qualification;
import com.healthAppointment.healthAppointment.model.dto.QualificationDTO;
import com.healthAppointment.healthAppointment.model.dto.QualificationReducedDTO;
import com.healthAppointment.healthAppointment.repository.QualificationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
class QualificationServiceTest {

    private QualificationService qualificationService;
    private QualificationRepository qualificationRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        // Configurar objetos mock para o repositório e o modelMapper
        qualificationRepository = mock(QualificationRepository.class);
        modelMapper = mock(ModelMapper.class);

        qualificationService = new QualificationService(qualificationRepository, modelMapper);
    }

    @Test
    public void testSaveQualificationDTO() {
        // Configurar dados de entrada para o teste
        QualificationDTO requestDTO = new QualificationDTO();
        requestDTO.setCode("Q001");
        requestDTO.setName("Qualificação 1");

        Qualification requestEntity = new Qualification();
        requestEntity.setCode("Q001");
        requestEntity.setName("Qualificação 1");

        Qualification savedEntity = new Qualification();
        savedEntity.setCode("Q001");
        savedEntity.setName("Qualificação 1");

        // Configurar comportamento do mock
        when(modelMapper.map(requestDTO, Qualification.class)).thenReturn(requestEntity);
        when(qualificationRepository.save(any(Qualification.class))).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, QualificationDTO.class)).thenReturn(requestDTO);

        // Executar o método e verificar o resultado
        QualificationDTO resultDTO = qualificationService.save(requestDTO);
        // Adicione aqui asserções para verificar se o resultado está correto
    }

    @Test
    public void testAddType() {
        // Configurar dados de entrada para o teste
        String qualificationId = "Q001";
        String typeId = "T001";

        Qualification qualification = new Qualification();
        qualification.setId(qualificationId);

        Qualification type = new Qualification();
        type.setId(typeId);

        // Configurar comportamento do mock
        when(qualificationRepository.findById(qualificationId)).thenReturn(Optional.of(qualification));
        when(qualificationRepository.findById(typeId)).thenReturn(Optional.of(type));
        when(qualificationRepository.save(any(Qualification.class))).thenReturn(qualification);

        // Executar o método e verificar o resultado
        Qualification result = qualificationService.addType(qualificationId, typeId);
        // Adicione aqui asserções para verificar se o resultado está correto
    }

    @Test
    public void testFindByCodeList() {
        // Configurar dados de entrada para o teste
        List<String> codes = new ArrayList<>();
        codes.add("Q001");
        codes.add("Q002");

        // Configurar comportamento do mock
        when(qualificationRepository.findByCodeList(codes)).thenReturn(new ArrayList<>());

        // Executar o método e verificar o resultado
        List<Qualification> result = qualificationService.findByCodeList(codes);
        // Adicione aqui asserções para verificar se o resultado está correto
    }

    // Adicione mais testes para outros métodos, se necessário...
}