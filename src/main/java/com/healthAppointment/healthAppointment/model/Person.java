package com.healthAppointment.healthAppointment.model;

import com.healthAppointment.healthAppointment.model.enums.Gender;
import com.healthAppointment.healthAppointment.utils.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor

public abstract class Person {


    private HumanName name;
    private Gender gender;
    private Address address;
    private Contacts contacts;
    private Date birthDate;
    private Boolean active = true;
    private LocalDateTime createdAt = LocalDateTime.now();


    public void setBirthDate(String birthDate) throws ParseException {
        this.birthDate = DateUtils.dateFormat.parse(birthDate);
    }

    public Person(HumanName name, Gender gender, Address address, Contacts contacts, String birthDate, Boolean active, LocalDateTime createdAt) throws ParseException {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.contacts = contacts;
        this.birthDate = DateUtils.dateFormat.parse(birthDate);
        this.active = active;
        this.createdAt = createdAt;
    }
}
