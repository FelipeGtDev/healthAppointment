package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.enums.Gender;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;
import com.healthAppointment.healthAppointment.model.*;
import com.healthAppointment.healthAppointment.model.dto.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Utils {

    // ############################### PATIENT ##########################################

    public Patient createPatient1() throws ParseException {
        Patient patient = new Patient();
        patient.setId("1");
        patient.setName(new HumanName(null, "John Doe", null));
        patient.setGender(Gender.MALE);
        patient.setAddress(new Address("Rua 1", "123", "apt 201", "Bairro 1", "Cidade 1", StateAcronym.RJ, "12345678"));
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
        patient.setGender(Gender.OTHER);
        patient.setAddress(new Address("Rua 2", "123", "apt 201", "Bairro 1", "Cidade 1", StateAcronym.MG, "12345678"));
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
        patientDTO.setGender(Gender.MALE);
        patientDTO.setAddress(new AddressDTO("Rua 1", "123", "apt 201", "Bairro 1", "Cidade 1", StateAcronym.RJ, "12345678"));
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
        patientDTO.setGender(Gender.OTHER);
        patientDTO.setAddress(new AddressDTO("Rua 2", "123", "apt 201", "Bairro 1", "Cidade 1", StateAcronym.MG, "12345678"));
        patientDTO.setContacts(new ContactsDTO("qwe@email.com", new ArrayList<>(Arrays.asList(
                new PhoneDTO("32", "12345678901", true),
                new PhoneDTO("21", "12345678901", false)))));
        patientDTO.setBirthDate("02/02/2000");
        patientDTO.setCpf("12345678902");

        return patientDTO;
    }


    // ############################### PRATICTIOINER ##########################################

    public Pratictioner createPratictioner1() throws ParseException {
        Pratictioner pratictioner = new Pratictioner();

        pratictioner.setId("1");
        pratictioner.setName(new HumanName("Dr.", "John Armless", null));
        pratictioner.setGender(Gender.MALE);
        pratictioner.setAddress(new Address("Rua 1", "123", "apt 201", "Bairro 1", "Cidade 1", StateAcronym.RJ, "12345678"));
        pratictioner.setContacts(new Contacts("qwe@email.com", new ArrayList<>(Arrays.asList(
                new Phone("21", "55555678901", true),
                new Phone("11", "55555678901", false)))));
        pratictioner.setBirthDate("02/02/2000");
        pratictioner.setActive(true);
        pratictioner.setCreatedAt(LocalDateTime.now().minusYears(1));
        var qualification = new Qualification("1", "Medicina", "codeMed", null,null);
        var agency =  new RegulatoryAgency(
                "1","CRM", StateAcronym.SP,qualification);
        pratictioner.setRegulatoryAgency(agency);
        pratictioner.setRegisterNumber("12345678901");
        pratictioner.setQualifications(new ArrayList<>(Arrays.asList(qualification)));

        return pratictioner;
    }

    public PratictionerDTO createPratictionerDTO1(){
        PratictionerDTO pratictionerDTO = new PratictionerDTO();

        pratictionerDTO.setId("1");
        pratictionerDTO.setName(new HumanNameDTO("Dr.", "John Armless", null));
        pratictionerDTO.setGender(Gender.MALE);
        pratictionerDTO.setAddress(new AddressDTO("Rua 1", "123", "apt 201", "Bairro 1", "Cidade 1", StateAcronym.RJ, "12345678"));
        pratictionerDTO.setContacts(new ContactsDTO("qwe@email.com", new ArrayList<>(Arrays.asList(
                new PhoneDTO("21", "55555678901", true),
                new PhoneDTO("11", "55555678901", false)))));
        pratictionerDTO.setBirthDate("02/02/2000");

        var qualification = new QualificationDTO("Medicina", "codeMed", null,null);
        var qualificationReduced = new QualificationReducedDTO("Medicina", "codeMed");
        var agency =  new RegulatoryAgencyDTO(
                "1","CRM", StateAcronym.SP,qualificationReduced);
        pratictionerDTO.setRegulatoryAgency(agency);
        pratictionerDTO.setRegisterNumber("12345678901");
        pratictionerDTO.setQualifications(new ArrayList<>(Arrays.asList(qualification)));

        return pratictionerDTO;
    }
}