package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.healthAppointment.healthAppointment.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class PersonDTO {

    private static SimpleDateFormat dateFormatBR = new SimpleDateFormat("dd/MM/yyyy");
//    private static SimpleDateFormat dateFormatUS = new SimpleDateFormat("yyyy-MM-dd");

    private HumanNameDTO name;
    private Gender gender;
    private AddressDTO address;
    private ContactsDTO contacts;
    private String birthDate;

    public void PatientDTO(HumanNameDTO name, Gender gender,AddressDTO address, ContactsDTO contacts, String birthDate) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.contacts = contacts;
        this.birthDate = birthDate;
    }


    public void setBirthDate(Date birthDate) {
        this.birthDate = dateFormatBR.format(birthDate);
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "name='" + name + '\'' +
                "gender='" + gender + '\'' +
                ", address=" + address +
                ", contacts=" + contacts +
                ", birthDate=" + birthDate +
                '}';
    }
}
