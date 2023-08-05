package com.healthAppointment.healthAppointment.repository;

import com.healthAppointment.healthAppointment.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    Optional<Schedule> findScheduleByDateTimeAndPratictionerId(LocalDateTime dateTime, String praticioterId);

    @Query("{ 'dateTime' : {$gte: ?0, $lte: ?1 }}")
    List<Schedule> findScheduleAllByDateTime_Date(LocalDate startDate, LocalDate endDate);
}
