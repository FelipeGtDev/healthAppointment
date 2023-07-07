package com.healthAppointment.healthAppointment.model;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "patient")
public class Patient extends Person{

    @Id
    private String id;
    private String cpf;

    public Patient(String id, String name, Address address, Contacts contacts, String birthDate, String cpf, LocalDateTime createdAt, Boolean active) throws ParseException {
        super(name, address, contacts, birthDate, active, createdAt);
        this.id = id;
        this.cpf = cpf;
    }
    public Patient(String name, Address address, Contacts contacts, String birthDate, String cpf, LocalDateTime createdAt, Boolean active) throws ParseException {
        super(name, address, contacts, birthDate, active, createdAt);
        this.cpf = cpf;
    }
}
