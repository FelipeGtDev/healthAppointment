package com.healthAppointment.healthAppointment.repository;

import com.healthAppointment.healthAppointment.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    Optional<Schedule> findScheduleByDateTimeAndPratictionerId(LocalDateTime dateTime, String praticioterId);
}
