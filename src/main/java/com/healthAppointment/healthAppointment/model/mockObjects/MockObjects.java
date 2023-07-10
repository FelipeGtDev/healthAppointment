package com.healthAppointment.healthAppointment.model.mockObjects;

import com.healthAppointment.healthAppointment.model.RegulatoryAgency;
import com.healthAppointment.healthAppointment.model.Speciality;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;

import java.util.Arrays;

public class MockObjects {


    Speciality speciality1 = new Speciality("1", "Fisioterapia", "1543", "Fisioterapia", "tipo");
    Speciality speciality2 = new Speciality("2", "Pilates", "2543", "Exercícios aplicados ...", "tipo");
    Speciality speciality3 = new Speciality("3", "Ventosaterapia", "15456", "prática alternativa que consiste no uso de ventosas em regiões do corpo para melhorar a circulação sanguínea. Isso acontece porque elas sugam a pele, criando um efeito de vácuo e um aumento do tamanho dos vasos sanguíneos no local aplicado. ", "tipo");
    Speciality speciality4 = new Speciality("4", "Auticuloterapia", "1983", " técnica derivada da acupuntura, que faz pressão em pontos específicos da orelha para tratar e diagnosticar diversos problemas físicos, mentais e até emocionais.", "tipo");
    Speciality speciality5 = new Speciality("5", "RPG (Reeducação Postural Global)", "1543", "um dos métodos da fisioterapia que trata das desarmonias do corpo humano levando em consideração as necessidades individuais do paciente.", "tipo");

    Speciality[] specialities = {speciality1, speciality2, speciality3, speciality4, speciality5};


    public RegulatoryAgency getAgency(String id) {
        return new RegulatoryAgency(id, "CREFITO", StateAcronym.RJ, "123456");
    }

    public Speciality getSpeciality(String code) {
        return Arrays.stream(specialities).filter(speciality -> speciality.getCode().equals(code)).findFirst().get();
    }







}
