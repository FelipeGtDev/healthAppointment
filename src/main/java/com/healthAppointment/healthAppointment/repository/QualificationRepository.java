package com.healthAppointment.healthAppointment.repository;

import com.healthAppointment.healthAppointment.model.Qualification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationRepository extends MongoRepository<Qualification, String> {

    @Query("{ 'code' : { $in: ?0} }")
    List<Qualification> findByCodeList(List<String> codes);

    @Query("{ 'code' : ?0 }")
    Qualification findByCode(String code);

    @Query("{ 'types.code' : ?0 }")
    List<Qualification> listByType(String code);
}
