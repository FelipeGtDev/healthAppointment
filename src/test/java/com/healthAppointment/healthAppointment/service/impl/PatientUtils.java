package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.*;
import com.healthAppointment.healthAppointment.model.dto.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class PatientUtils {

    public Patient createPatient1() throws ParseException {
        Patient patient = new Patient();
        patient.setId("1");
        patient.setName(new HumanName(null, "John Doe", null));
        patient.setAddress(new Address("Rua 1", "123", "apt 201", "Bairro 1", "Cidade 1", "Estado 1", "12345678"));
        patient.setContacts(new Contacts("qwe@email.com", new ArrayList<>(Arrays.asList(
                new Phone("21", "12345678901", true),
                new Phone("11", "12345678901", false)))));
        patient.setBirthDate("01/01/2000");
        patient.setActive(true);
        patient.setCreatedAt(LocalDateTime.now());
        patient.setCpf("12345678901");

        return patient;
    }

    public Patient createPatient2() throws ParseException {
        Patient patient = new Patient();

        patient.setId("2");
        patient.setName(new HumanName(null, "Jane Armless", new HumanName("Sr.", "Simão Armless",null)));
        patient.setAddress(new Address("Rua 2", "123", "apt 201", "Bairro 1", "Cidade 1", "Estado 1", "12345678"));
        patient.setContacts(new Contacts("qwe@email.com", new ArrayList<>(Arrays.asList(
                new Phone("32", "12345678901", true),
                new Phone("21", "12345678901", false)))));
        patient.setBirthDate("02/02/2000");
        patient.setActive(true);
        patient.setCreatedAt(LocalDateTime.now());
        patient.setCpf("12345678902");

        return patient;
    }

    public PatientDTO createPatientDTO1(){
        PatientDTO patientDTO = new PatientDTO();

        patientDTO.setId("1");
        patientDTO.setName(new HumanNameDTO(null, "John Doe", null));
        patientDTO.setAddress(new AddressDTO("Rua 1", "123", "apt 201", "Bairro 1", "Cidade 1", "Estado 1", "12345678"));
        patientDTO.setContacts(new ContactsDTO("qwe@email.com", new ArrayList<>(Arrays.asList(
                new PhoneDTO("21", "12345678901", true),
                new PhoneDTO("11", "12345678901", false)))));
        patientDTO.setBirthDate("01/01/2000");
        patientDTO.setCpf("12345678901");

        return patientDTO;
    }

    public PatientDTO createPatientDTO2(){
        PatientDTO patientDTO = new PatientDTO();

        patientDTO.setId("2");
        patientDTO.setName(new HumanNameDTO(null, "Jane Armless", new HumanNameDTO("Sr.", "Simão Armless",null)));
        patientDTO.setAddress(new AddressDTO("Rua 2", "123", "apt 201", "Bairro 1", "Cidade 1", "Estado 1", "12345678"));
        patientDTO.setContacts(new ContactsDTO("qwe@email.com", new ArrayList<>(Arrays.asList(
                new PhoneDTO("32", "12345678901", true),
                new PhoneDTO("21", "12345678901", false)))));
        patientDTO.setBirthDate("02/02/2000");
        patientDTO.setCpf("12345678902");

        return patientDTO;
    }
}
