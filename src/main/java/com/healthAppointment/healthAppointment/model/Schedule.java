package com.healthAppointment.healthAppointment.model;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.SCHEDULE_PATIENTS_FULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "schedule")
public class Schedule {
    private String id;
    private LocalDateTime dateTime;
    private Pratictioner pratictioner;
    private Integer maxPatients;
    private List<Patient> patients;
    private Qualification healthProcedure;
    private List<Appointment> appointments;

    public Schedule(Schedule scheduleWhitOnePatient) {
        this.id = scheduleWhitOnePatient.getId();
        this.dateTime = scheduleWhitOnePatient.getDateTime();
        this.pratictioner = scheduleWhitOnePatient.getPratictioner();
        this.maxPatients = scheduleWhitOnePatient.getMaxPatients();
        this.patients = scheduleWhitOnePatient.getPatients();
        this.healthProcedure = scheduleWhitOnePatient.getHealthProcedure();
        this.appointments = scheduleWhitOnePatient.getAppointments();
    }


    public void addPatient(Patient patient) throws BusException {
        if (this.patients.size() < this.maxPatients) {
            this.patients.add(patient);
        } else {
            throw new BusException(SCHEDULE_PATIENTS_FULL);
        }

    }
}
