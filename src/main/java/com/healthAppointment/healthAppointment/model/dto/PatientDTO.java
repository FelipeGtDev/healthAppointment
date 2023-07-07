package com.healthAppointment.healthAppointment.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO extends PersonDTO {


    private String id;
    private String cpf;

    public PatientDTO(String id, HumanNameDTO name, AddressDTO address, ContactsDTO contacts, String birthDate, String cpf) {
        super(name, address, contacts, birthDate);
        this.id = id;
        this.cpf = cpf;
    }

    public PatientDTO(HumanNameDTO name, AddressDTO address, ContactsDTO contacts, String birthDate, String cpf) {
        super(name, address, contacts, birthDate);
        this.cpf = cpf;
    }
}
