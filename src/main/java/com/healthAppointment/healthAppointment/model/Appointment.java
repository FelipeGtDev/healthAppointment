package com.healthAppointment.healthAppointment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointment")
public class Appointment {
    private String id;
    private LocalDateTime dateTime;
    private Pratictioner pratictioner;
    private Patient patient;
    private Qualification healthProcedure;
    private ProcedureStatus procedureStatus;
    private Payment payment;

}
