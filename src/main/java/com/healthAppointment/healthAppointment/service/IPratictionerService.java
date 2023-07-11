package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPratictionerService {
    PratictionerDTO save(PratictionerDTO request);

    Page<PratictionerDTO> findAllActive(Pageable page);
}
