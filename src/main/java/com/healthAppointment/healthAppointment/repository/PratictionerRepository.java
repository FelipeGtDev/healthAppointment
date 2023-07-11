package com.healthAppointment.healthAppointment.repository;

import com.healthAppointment.healthAppointment.model.Pratictioner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PratictionerRepository extends MongoRepository<Pratictioner, String> {

    @Query("{ 'active' : true }")
    Page<Pratictioner> findAllActive(Pageable page);

    @Query("{ 'active' : false }")
    Page<Pratictioner> findAllInactive(Pageable page);

    @Query("{ $or: [ " +
            "{'name.name': {$regex: ?0, $options: 'i'} }, " +
            "{'name.socialName.name': {$regex: ?0, $options: 'i'} } " +
            "] }")
    Page<Pratictioner> findByName(String name, Pageable page);
}
