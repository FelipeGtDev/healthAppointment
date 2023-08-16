package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.Pratictioner;
import com.healthAppointment.healthAppointment.model.Qualification;
import com.healthAppointment.healthAppointment.model.RegulatoryAgency;
import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;
import com.healthAppointment.healthAppointment.repository.PratictionerRepository;
import com.healthAppointment.healthAppointment.repository.QualificationRepository;
import com.healthAppointment.healthAppointment.service.IRegulatoryAgencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.PRATICTIONER_NOT_FOUND;

class PratictionerServiceTest {
    private PratictionerDTO pratictionerDTO;
    private Pratictioner pratictioner;

    @Mock
    private PratictionerRepository repository;
    @Mock
    private QualificationService qualificationService;
    @Mock
    private IRegulatoryAgencyService regulatoryAgencyService;
    @Mock
    private ModelMapper modelMapper;

    private final Utils utils;

    @InjectMocks
    private PratictionerService service;


    PratictionerServiceTest() {
        this.utils = new Utils();
    }

    @BeforeEach
    public void setup() throws ParseException {
        MockitoAnnotations.openMocks(this);

        pratictionerDTO = utils.createPratictionerDTO1();
        pratictioner = utils.createPratictioner1();

    }

    @Test
    public void testSavePratictioner() {
        // Configuração do mock repository
        when(repository.save(any(Pratictioner.class))).thenReturn(pratictioner);

        var qualiCodes = new ArrayList<String>();
        qualiCodes.add("codeMed");
        var qualification = new Qualification("1", "qualification1", "qualiCode", "desc", new ArrayList<>());
        var listQualification = new ArrayList<Qualification>();
        listQualification.add(qualification);
        var agency = new RegulatoryAgency("1", "agency1", StateAcronym.SP, qualification);

        // Configuração do mock regulatoryAgencyService
        when(regulatoryAgencyService.findById(anyString())).thenReturn(Optional.of(agency));

        // Configuração do mock qualificationService
        when(qualificationService.getCodeListFromTypes(anyList())).thenReturn(qualiCodes);
        when(qualificationService.findByCodeList(anyList())).thenReturn(listQualification);

        // Configuração do mock modelMapper
        when(modelMapper.map(any(PratictionerDTO.class), eq(Pratictioner.class))).thenReturn(pratictioner);
        when(modelMapper.map(any(Pratictioner.class), eq(PratictionerDTO.class))).thenReturn(pratictionerDTO);

        // Chamar o método de teste
        PratictionerDTO result = service.save(pratictionerDTO);

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
        List<Pratictioner> pratictionerList = new ArrayList<>();
        pratictionerList.add(pratictioner);
        Page<Pratictioner> page = new PageImpl<>(new ArrayList<>(pratictionerList));

        when(repository.findAllActive(any(Pageable.class))).thenReturn(page);

        // Configuração do mock modelMapper
        when(modelMapper.map(any(Pratictioner.class), eq(PratictionerDTO.class))).thenReturn(pratictionerDTO);

        // Chamar o método de teste
        Page<PratictionerDTO> result = service.findAllActive(Pageable.unpaged());

        // Verificar o resultado
        assertEquals(page.getContent().get(0).getId(), result.getContent().get(0).getId());
        assertTrue(result.getContent().get(0).getActive());
    }

    // Adicione aqui outros métodos de teste para os outros métodos da classe PratictionerService

    @Test
    void findById_shouldThrowExceptionWhenPratictionerNotFound() {
        // Arrange
        String id = "1";

        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        var exc = assertThrows(ResourceNotFoundException.class, () -> service.findById(id));
        assertEquals(PRATICTIONER_NOT_FOUND + id, exc.getMessage());
        verify(repository, times(1)).findById(id);
    }
}