package com.healthAppointment.healthAppointment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor

public abstract class Person {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private HumanName name;
    private Address address;
    private Contacts contacts;
    private Date birthDate;
    private Boolean active = true;
    private LocalDateTime createdAt = LocalDateTime.now();



    public void setBirthDate(String birthDate) throws ParseException {
        this.birthDate = dateFormat.parse(birthDate);
    }

    public Person(HumanName name, Address address, Contacts contacts, String birthDate, Boolean active, LocalDateTime createdAt) throws ParseException {
        this.name = name;
        this.address = address;
        this.contacts = contacts;
        this.birthDate = dateFormat.parse(birthDate);
        this.active = active;
        this.createdAt = createdAt;
    }
}
