package com.healthAppointment.healthAppointment.repository;

import com.healthAppointment.healthAppointment.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {


    @Query("{ 'active' : true }")
    Page<Patient> findAllActive(Pageable page);

    @Query("{ 'active' : false }")
    Page<Patient> findAllInactive(Pageable page);

    @Query("{'name.name': {$regex: ?0, $options: 'i'}}")
    Page<Patient> findByName(String name, Pageable page);
}
