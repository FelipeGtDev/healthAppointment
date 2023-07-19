package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.HumanName;
import com.healthAppointment.healthAppointment.model.Pratictioner;
import com.healthAppointment.healthAppointment.model.RegulatoryAgency;
import com.healthAppointment.healthAppointment.model.dto.HumanNameDTO;
import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import com.healthAppointment.healthAppointment.repository.PratictionerRepository;
import com.healthAppointment.healthAppointment.service.IQualificationService;
import com.healthAppointment.healthAppointment.service.IRegulatoryAgencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PratictionerServiceTest {

    @Mock
    private PratictionerRepository repository;

    @Mock
    private IQualificationService qualificationService;

    @Mock
    private IRegulatoryAgencyService regulatoryAgencyService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PratictionerService pratictionerService;

    private PratictionerDTO pratictionerDTO;
    private Pratictioner pratictioner;

    @BeforeEach
    public void setup() {
        pratictionerDTO = new PratictionerDTO();
        pratictionerDTO.setId("1");
        pratictionerDTO.setName(new HumanNameDTO("Dr.","John Doe", new HumanNameDTO("Dra.","Jane Doe", null)));

        pratictioner = new Pratictioner();
        pratictioner.setId("1");
        pratictioner.setName(new HumanName("Dr.","John Doe", new HumanName("Dr.","Jane Doe", null)));
    }

    @Test
    public void testSavePratictioner() {
        // Configuração do mock repository
        when(repository.save(any(Pratictioner.class))).thenReturn(pratictioner);

        // Configuração do mock qualificationService
        when(qualificationService.getCodeListFromTypes(anyList())).thenReturn(new ArrayList<>());

        // Configuração do mock regulatoryAgencyService
        when(regulatoryAgencyService.findById(anyString())).thenReturn(Optional.of(new RegulatoryAgency()));

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