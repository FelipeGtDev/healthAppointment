package com.healthAppointment.healthAppointment.model;

public class AppConstants {
    public static class Messages {
        public static final String CREATE_ERROR = "Erro ao criar!";
        public static final String UPDATE_ERROR = "Erro ao atualizar!";

        // #################### PATIENT ####################
        public static final String PATIENT_NOT_FOUND = "Paciente não encontrado.";


        // #################### SCHEDULE ####################
        public static final String SCHEDULE_NOT_FOUND = "Agendamento não encontrado.";
        public static final String SCHEDULE_ALREADY_EXISTS = "Agendamento já existe.";
        public static final String SCHEDULE_PATIENTS_FULL = "Agendamento de pacientes está cheio.";
        public static final String DUPLICATE_PATIENT  = "Paciente duplicado";
        public static final String SCHEDULES_WITH_DIFFERENT_DATES  = "Lista de agendamento diário com mais de uma data";

    }
}
