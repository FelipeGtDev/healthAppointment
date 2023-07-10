package com.healthAppointment.healthAppointment.model;


import com.healthAppointment.healthAppointment.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "patient")
public class Patient extends Person {

    @Id
    private String id;
    private String cpf;

    public Patient(String id, HumanName name, Gender gender, Address address, Contacts contacts, String birthDate, String cpf, LocalDateTime createdAt, Boolean active) throws ParseException {
        super(name, gender, address, contacts, birthDate, active, createdAt);
        this.id = id;
        this.cpf = cpf;
    }

    public Patient(HumanName name, Gender gender, Address address, Contacts contacts, String birthDate, String cpf, LocalDateTime createdAt, Boolean active) throws ParseException {
        super(name, gender, address, contacts, birthDate, active, createdAt);
        this.cpf = cpf;
    }
}
