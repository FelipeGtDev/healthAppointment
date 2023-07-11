package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPratictionerService {
    PratictionerDTO save(PratictionerDTO request);

    Page<PratictionerDTO> findAllActive(Pageable page);

    Page<PratictionerDTO> findAllInactive(Pageable page);

    Page<PratictionerDTO> findAll(Pageable page);

    PratictionerDTO findById(String id) throws Exception;

    Page<PratictionerDTO> findByName(String name, Pageable page);

    PratictionerDTO update(String id, PratictionerDTO request) throws Exception;

    void delete(String id) throws Exception;
}
