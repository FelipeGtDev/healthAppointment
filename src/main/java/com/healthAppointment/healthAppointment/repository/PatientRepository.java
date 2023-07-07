package com.healthAppointment.healthAppointment.repository;

import com.healthAppointment.healthAppointment.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {


    @Query("{ 'active' : true }")
    Page<Patient> findAllActive(Pageable page);
}
