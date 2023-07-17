package com.healthAppointment.healthAppointment.repository;

import com.healthAppointment.healthAppointment.model.RegulatoryAgency;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegulatoryAgencyRepository extends MongoRepository<RegulatoryAgency, String>{

    @Query("{'name': {$regex: ?0, $options: 'i'} }")
    Page<RegulatoryAgency> findByName(String name, Pageable page);

    @Query("{'qualification.code': {$regex: ?0, $options: 'i'}, 'state': ?1 }")
    Page<RegulatoryAgency> findByQualificationAndState(String qualificationCode, StateAcronym state, Pageable page);
}
