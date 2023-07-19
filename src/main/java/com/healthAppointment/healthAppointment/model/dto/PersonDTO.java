package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.healthAppointment.healthAppointment.model.enums.Gender;
import com.healthAppointment.healthAppointment.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class PersonDTO {

    @NotNull
    private HumanNameDTO name;
    @NotNull
    private Gender gender;
    private AddressDTO address;
    @NotNull
    private ContactsDTO contacts;
    @JsonProperty("birth_date")
    private String birthDate;

    public void PatientDTO(HumanNameDTO name, Gender gender, AddressDTO address, ContactsDTO contacts, String birthDate) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.contacts = contacts;
        this.birthDate = birthDate;
    }


    public void setBirthDate(Date birthDate) {
        if (birthDate == null) {
            this.birthDate = null;
            return;
        }
        this.birthDate = DateUtils.dateFormat.format(birthDate
        );
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
