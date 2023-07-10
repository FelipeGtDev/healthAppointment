package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.healthAppointment.healthAppointment.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO extends PersonDTO {

    private String id;
    private String cpf;

    public PatientDTO(String id, HumanNameDTO name, Gender gender, AddressDTO address, ContactsDTO contacts, String birthDate, String cpf) {
        super(name, gender, address, contacts, birthDate);
        this.id = id;
        this.cpf = cpf;
    }

    public PatientDTO(HumanNameDTO name, Gender gender, AddressDTO address, ContactsDTO contacts, String birthDate, String cpf) {
        super(name, gender, address, contacts, birthDate);
        this.cpf = cpf;
    }
}
