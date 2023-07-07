package com.healthAppointment.healthAppointment.model.dto;

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
public class PatientDTO extends PersonDTO{



    private String id;
    private String cpf;

     public PatientDTO(String id, String name, AddressDTO address, ContactsDTO contacts, String birthDate, String cpf) {
        super(name, address, contacts, birthDate);
        this.id = id;
        this.cpf = cpf;
    }
    public PatientDTO(String name, AddressDTO address, ContactsDTO contacts, String birthDate, String cpf) {
        super(name, address, contacts, birthDate);
        this.cpf = cpf;
    }
}
