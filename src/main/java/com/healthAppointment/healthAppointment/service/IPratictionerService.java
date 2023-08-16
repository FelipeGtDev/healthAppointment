package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.Pratictioner;
import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPratictionerService {
    PratictionerDTO save(PratictionerDTO request);

    Page<PratictionerDTO> findAllActive(Pageable page);

    Page<PratictionerDTO> findAllInactive(Pageable page);

    Page<PratictionerDTO> findAll(Pageable page);

    PratictionerDTO getById(String id) throws Exception, ResourceNotFoundException;

    Optional<Pratictioner> findById(String id) throws Exception, ResourceNotFoundException;

    Page<PratictionerDTO> findByName(String name, Pageable page);

    PratictionerDTO update(String id, PratictionerDTO request) throws Exception;

    void delete(String id) throws Exception;
}
