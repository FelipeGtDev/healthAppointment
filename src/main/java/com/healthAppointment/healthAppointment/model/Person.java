package com.healthAppointment.healthAppointment.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public abstract class Person {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private String name;
    private String socialName;
    private Address address;
    private Contacts contacts;
    private Date birthDate;
    private Boolean active = true;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Person(String name, Address address, Contacts contacts, String birthDate, Boolean active, LocalDateTime createdAt) throws ParseException {
        this.name = name;
        this.address = address;
        this.contacts = contacts;
        this.birthDate = dateFormat.parse(birthDate);
        this.active = active;
        this.createdAt = createdAt;
    }
}
