package com.healthAppointment.healthAppointment.repository;

import com.healthAppointment.healthAppointment.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
}
