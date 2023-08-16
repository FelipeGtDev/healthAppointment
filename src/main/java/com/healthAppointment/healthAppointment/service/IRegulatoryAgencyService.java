package com.healthAppointment.healthAppointment.service;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.RegulatoryAgency;
import com.healthAppointment.healthAppointment.model.dto.RegulatoryAgencyDTO;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IRegulatoryAgencyService {
    RegulatoryAgencyDTO save(RegulatoryAgencyDTO request) throws ResourceNotFoundException;

    Optional<RegulatoryAgency> findById(String id);

    Page<RegulatoryAgencyDTO> findAll(Pageable page);

    Page<RegulatoryAgencyDTO> findByName(String name, Pageable page);

    Page<RegulatoryAgencyDTO> findByQualificationAndState(String qualification, StateAcronym state, Pageable page) throws ResourceNotFoundException;
}
