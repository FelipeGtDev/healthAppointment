package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.Pratictioner;
import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import com.healthAppointment.healthAppointment.repository.PratictionerRepository;
import com.healthAppointment.healthAppointment.repository.QualificationRepository;
import com.healthAppointment.healthAppointment.service.IQualificationService;
import com.healthAppointment.healthAppointment.service.IRegulatoryAgencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PratictionerServiceTest {
    private PratictionerDTO pratictionerDTO;
    private Pratictioner pratictioner;

    @Mock
    private PratictionerRepository repository;

//    @Mock
    private IQualificationService qualificationService;
    private QualificationRepository qualificationRepository;

    @Mock
    private IRegulatoryAgencyService regulatoryAgencyService;

    @Mock
    private ModelMapper modelMapper;
    private final Utils utils;

    @InjectMocks
    private PratictionerService pratictionerService;


    PratictionerServiceTest() {
        this.utils = new Utils();
    }

    @BeforeEach
    public void setup() throws ParseException {
        MockitoAnnotations.initMocks(this);

        pratictionerDTO = utils.createPratictionerDTO1();
        pratictioner = utils.createPratictioner1();

        qualificationService = new QualificationService(qualificationRepository, modelMapper);
    }

    @Test
    public void testSavePratictioner() {
        // Configuração do mock repository
        when(repository.save(any(Pratictioner.class))).thenReturn(pratictioner);

        var qualiCodes = new ArrayList<String>();
        qualiCodes.add("codeMed");

        // Configuração do mock regulatoryAgencyService
        Mockito.when(regulatoryAgencyService.findById(anyString())).thenReturn(Optional.of(pratictioner.getRegulatoryAgency()));

        // Configuração do mock qualificationService
        when(qualificationService.getCodeListFromTypes(anyList())).thenReturn(qualiCodes);

        // Configuração do mock modelMapper
        when(modelMapper.map(any(PratictionerDTO.class), eq(Pratictioner.class))).thenReturn(pratictioner);
        when(modelMapper.map(any(Pratictioner.class), eq(PratictionerDTO.class))).thenReturn(pratictionerDTO);

        // Chamar o método de teste
        PratictionerDTO result = pratictionerService.save(pratictionerDTO);

        // Verificar se o método repository.save foi chamado
        verify(repository, times(1)).save(pratictioner);

        // Verificar se o método modelMapper.map foi chamado corretamente
        verify(modelMapper, times(1)).map(pratictionerDTO, Pratictioner.class);
        verify(modelMapper, times(1)).map(pratictioner, PratictionerDTO.class);

        // Verificar o resultado
        assertEquals(pratictionerDTO.getId(), result.getId());
        assertEquals(pratictionerDTO.getName(), result.getName());
    }

    @Test
    public void testFindAllActive() {
        // Configuração do mock repository
        Page<Pratictioner> page = mock(Page.class);
        when(repository.findAllActive(any(Pageable.class))).thenReturn(page);

        // Configuração do mock modelMapper
        when(modelMapper.map(any(Pratictioner.class), eq(PratictionerDTO.class))).thenReturn(pratictionerDTO);

        // Chamar o método de teste
        Page<PratictionerDTO> result = pratictionerService.findAllActive(Pageable.unpaged());

        // Verificar o resultado
        assertEquals(page, result);
    }

    // Adicione aqui outros métodos de teste para os outros métodos da classe PratictionerService


}