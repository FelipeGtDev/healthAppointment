package com.healthAppointment.healthAppointment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class PersonDTO {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private String name;
    private AddressDTO address;
    private ContactsDTO contacts;
    private String birthDate;

    public void PatientDTO(String name, AddressDTO address, ContactsDTO contacts, String birthDate) {
        this.name = name;
        this.address = address;
        this.contacts = contacts;
        this.birthDate  = birthDate;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", contacts=" + contacts +
                ", birthDate=" + birthDate +
                '}';
    }
}
