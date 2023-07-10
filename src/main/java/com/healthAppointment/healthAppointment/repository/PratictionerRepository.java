package com.healthAppointment.healthAppointment.repository;

import com.healthAppointment.healthAppointment.model.Pratictioner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PratictionerRepository extends MongoRepository<Pratictioner, String> {

}
