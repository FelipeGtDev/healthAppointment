package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class PersonDTO {

    private static SimpleDateFormat dateFormatBR = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat dateFormatUS = new SimpleDateFormat("yyyy-MM-dd");

    private HumanNameDTO name;
    private AddressDTO address;
    private ContactsDTO contacts;
    private String birthDate;

    public void PatientDTO(HumanNameDTO name, AddressDTO address, ContactsDTO contacts, String birthDate) {
        this.name = name;
        this.address = address;
        this.contacts = contacts;
        this.birthDate = birthDate;
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
