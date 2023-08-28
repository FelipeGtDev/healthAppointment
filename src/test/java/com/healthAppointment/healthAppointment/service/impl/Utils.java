package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.model.*;
import com.healthAppointment.healthAppointment.model.dto.*;
import com.healthAppointment.healthAppointment.model.enums.Gender;
import com.healthAppointment.healthAppointment.model.enums.PaymentMethod;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Utils {

    private final ModelMapper modelMapper;

    public Utils() {
        this.modelMapper = new ModelMapper();
    }

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
        patient.setName(new HumanName(null, "Jane Armless", new HumanName("Sr.", "Simão Armless", null)));
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

    public PatientDTO createPatientDTO1() {
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

    public PatientDTO createPatientDTO2() {
        PatientDTO patientDTO = new PatientDTO();

        patientDTO.setId("2");
        patientDTO.setName(new HumanNameDTO(null, "Jane Armless", new HumanNameDTO("Sr.", "Simão Armless", null)));
        patientDTO.setGender(Gender.OTHER);
        patientDTO.setAddress(new AddressDTO("Rua 2", "123", "apt 201", "Bairro 1", "Cidade 1", StateAcronym.MG, "12345678"));
        patientDTO.setContacts(new ContactsDTO("qwe@email.com", new ArrayList<>(Arrays.asList(
                new PhoneDTO("32", "12345678901", true),
                new PhoneDTO("21", "12345678901", false)))));
        patientDTO.setBirthDate("02/02/2000");
        patientDTO.setCpf("12345678902");

        return patientDTO;
    }

    public PatientReducedDTO createPatientReducedDTO(Patient patient) {

        return modelMapper.map(patient, PatientReducedDTO.class);
    }


    // ############################### PRATICTIONER ##########################################

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
        var qualification = new Qualification("1", "Medicina", "codeMed", null, null);
        var agency = new RegulatoryAgency(
                "1", "CRM", StateAcronym.SP, qualification);
        pratictioner.setRegulatoryAgency(agency);
        pratictioner.setRegisterNumber("12345678901");
        pratictioner.setQualifications(new ArrayList<>(List.of(qualification)));

        return pratictioner;
    }

    public PratictionerDTO createPratictionerDTO1() {
        PratictionerDTO pratictionerDTO = new PratictionerDTO();

        pratictionerDTO.setId("1");
        pratictionerDTO.setName(new HumanNameDTO("Dr.", "John Armless", null));
        pratictionerDTO.setGender(Gender.MALE);
        pratictionerDTO.setAddress(new AddressDTO("Rua 1", "123", "apt 201", "Bairro 1", "Cidade 1", StateAcronym.RJ, "12345678"));
        pratictionerDTO.setContacts(new ContactsDTO("qwe@email.com", new ArrayList<>(Arrays.asList(
                new PhoneDTO("21", "55555678901", true),
                new PhoneDTO("11", "55555678901", false)))));
        pratictionerDTO.setBirthDate("02/02/2000");

        var qualification = new QualificationDTO("Medicina", "codeMed", null, null);
        var qualificationReduced = new QualificationReducedDTO("Medicina", "codeMed");
        var agency = new RegulatoryAgencyDTO(
                "1", "CRM", StateAcronym.SP, qualificationReduced);
        pratictionerDTO.setRegulatoryAgency(agency);
        pratictionerDTO.setRegisterNumber("12345678901");
        pratictionerDTO.setQualifications(new ArrayList<>(List.of(qualification)));

        return pratictionerDTO;
    }

    public PratictionerReducedDTO createPratictionerReducedDTO1() throws ParseException {
        var pratictioner = createPratictioner1();
        return modelMapper.map(pratictioner, PratictionerReducedDTO.class);
    }

    // ########################### HEALTH PROCEDURE ######################################
    public Qualification createQualificationPhisioterapy() {
        var Qualification = new Qualification();

        Qualification.setId("1");
        Qualification.setName("Fisioterapia");
        Qualification.setCode("codeFisioterapia");
        Qualification.setDescription("descriptionFisioterapia");

        return Qualification;
    }

    public Qualification createQualificationPilates() {
        var qualification = new Qualification();

        qualification.setId("1");
        qualification.setName("Pilates");
        qualification.setCode("50000861");
        qualification.setDescription("descriptionPilates");
        qualification.setTypes(new ArrayList<>(List.of(createQualificationPhisioterapy())));

        return qualification;
    }

    public QualificationReducedDTO createQualificationReducedDTO(Qualification qualification) {
        return modelMapper.map(qualification, QualificationReducedDTO.class);
    }

    // ############################### SCHEDULE ##########################################

    public Schedule createScheduleWhithOnePatient() throws ParseException {
        Schedule schedule = new Schedule();

        schedule.setId("1");
        schedule.setDateTime(LocalDateTime.parse("2023-12-01T08:00:00"));
        schedule.setPratictioner(createPratictioner1());
        schedule.setPatients(new ArrayList<>(List.of(createPatient1())));
        schedule.setHealthProcedure(createQualificationPhisioterapy());
//        scheduleDTO.setAppointments(new ArrayList<>(Arrays.asList()));

        return schedule;
    }

    public Schedule createScheduleWhithMultiplePatients() throws ParseException {
        var schedule = createScheduleWhithOnePatient();

        schedule.setHealthProcedure(createQualificationPilates());
        schedule.getPatients().add(createPatient2());

        return schedule;
    }

    public ScheduleDTO createScheduleDtoWhithOnePatient() throws ParseException {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId("1");
        scheduleDTO.setDateTime(LocalDateTime.parse("2023-12-01T08:00:00"));
        scheduleDTO.setPratictioner(createPratictionerReducedDTO1());
        scheduleDTO.setPatients(new ArrayList<>(List.of(createPatientReducedDTO(createPatient1()))));
        scheduleDTO.setHealthProcedure(createQualificationReducedDTO(createQualificationPhisioterapy()));
//        scheduleDTO.setAppointments(new ArrayList<>(Arrays.asList()));

        return scheduleDTO;
    }

    public ScheduleDTO createScheduleDtoWhithMultiplePatients() throws ParseException {
        var scheduleDTO = createScheduleDtoWhithOnePatient();

        scheduleDTO.setHealthProcedure(createQualificationReducedDTO(createQualificationPilates()));
        scheduleDTO.getPatients().add(createPatientReducedDTO(createPatient2()));

        return scheduleDTO;
    }

    // ############################### APPOINTMENT ##########################################

    public AppointmentDTO createAppointmentDto1DONE() throws ParseException {
        var appointment = new AppointmentDTO();

        appointment.setId("1");
        appointment.setDateTime(LocalDateTime.parse("2023-12-01T08:00:00"));
        appointment.setPratictioner(createPratictionerReducedDTO1());
        appointment.setPatient(createPatientReducedDTO(createPatient1()));
        appointment.setHealthProcedure(createQualificationReducedDTO(createQualificationPhisioterapy()));
        appointment.setProcedureStatus(ProcedureStatus.DONE);

        return appointment;

    }
    public AppointmentDTO createAppointmentDto2DONE() throws ParseException {
        var appointment = new AppointmentDTO();

        appointment.setId("2");
        appointment.setDateTime(LocalDateTime.parse("2023-12-01T08:00:00"));
        appointment.setPratictioner(createPratictionerReducedDTO1());
        appointment.setPatient(createPatientReducedDTO(createPatient2()));
        appointment.setHealthProcedure(createQualificationReducedDTO(createQualificationPhisioterapy()));
        appointment.setProcedureStatus(ProcedureStatus.DONE);

        return appointment;

    }

    public Appointment createAppointment(AppointmentDTO appointmentDTO) throws ParseException {
        return modelMapper.map(appointmentDTO, Appointment.class);
    }

    public AppointmentReducedDTO createAppointmentReducedDTO(Appointment appointment) throws ParseException {
        return modelMapper.map(appointment, AppointmentReducedDTO.class);
    }

    // ############################### PAYMENT ##########################################


    public PaymentDTO createPaymentPayedDto() throws ParseException {
        var payment = new PaymentDTO();

        payment.setId("1");
        payment.setPaymentMethod(PaymentMethod.PIX);
        payment.setAmount(140.0);
        payment.setPaymentSucceeded(true);
        payment.setPaymentDate(Date.from(Instant.now()));
        payment.setAppointments(new ArrayList<>(List.of(
                createAppointmentReducedDTO(createAppointment(createAppointmentDto1DONE())))));
        payment.setBalance(3);
        payment.setAppointmentsPayed(8);

        return payment;
    }

    public Payment createPaymentPayed(PaymentDTO paymentDTO) throws ParseException {
        return modelMapper.map(paymentDTO, Payment.class);
    }

    public PaymentReducedDTO createPaymentReducedDTO(Payment payment) throws ParseException {
        return modelMapper.map(payment, PaymentReducedDTO.class);
    }


}