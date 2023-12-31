package com.healthAppointment.healthAppointment.service.impl;


import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.Qualification;
import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import com.healthAppointment.healthAppointment.model.dto.QualificationDTO;
import com.healthAppointment.healthAppointment.repository.QualificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.QUALIFICATION_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class QualificationServiceTest {

    @Mock
    private QualificationService service;
    @Mock
    private QualificationRepository repository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        // Configurar objetos mock para o repositório e o modelMapper
        repository = mock(QualificationRepository.class);
        modelMapper = mock(ModelMapper.class);

        service = new QualificationService(repository, modelMapper);
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
        when(repository.save(any(Qualification.class))).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, QualificationDTO.class)).thenReturn(requestDTO);

        // Executar o método e verificar o resultado
        QualificationDTO resultDTO = service.save(requestDTO); //TODO verificar - nunca usado
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
        when(repository.findById(qualificationId)).thenReturn(Optional.of(qualification));
        when(repository.findById(typeId)).thenReturn(Optional.of(type));
        when(repository.save(any(Qualification.class))).thenReturn(qualification);

        // Executar o método e verificar o resultado
        Qualification result = service.addType(qualificationId, typeId);
        // Adicione aqui asserções para verificar se o resultado está correto
    }

    @Test
    public void testFindByCodeList() {
        // Configurar dados de entrada para o teste
        List<String> codes = new ArrayList<>();
        codes.add("Q001");
        codes.add("Q002");

        // Configurar comportamento do mock
        when(repository.findByCodeList(codes)).thenReturn(new ArrayList<>());

        // Executar o método e verificar o resultado
        List<Qualification> result = service.findByCodeList(codes);
        // Adicione aqui asserções para verificar se o resultado está correto
    }

    // Adicione mais testes para outros métodos, se necessário...


    @Test
    void  findByCode_shouldThrowExceptionWhenQUalificationNotFound() {
        // Arrange
        String code = "1";

        when(repository.findQualificationByCode(code)).thenReturn(Optional.empty());

        // Act & Assert
        var exc = assertThrows(ResourceNotFoundException.class, () -> service.findByCode(code));
        assertEquals(QUALIFICATION_NOT_FOUND + code, exc.getMessage());
        verify(repository, times(1)).findQualificationByCode(code);
    }
}