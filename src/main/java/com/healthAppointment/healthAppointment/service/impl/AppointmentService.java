package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.*;
import com.healthAppointment.healthAppointment.model.dto.PatientReducedDTO;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.repository.AppointmentRepository;
import com.healthAppointment.healthAppointment.service.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {

    private final ModelMapper modalMapper;
    private final PatientService patientService;
    private final PratictionerService pratictionerService;
    private final AppointmentRepository repository;
    private final QualificationService qualificationService;

    @Override

    public void createAppointmentsFromSchedule(ScheduleDTO request) throws ResourceNotFoundException {

        validateSchedule(request);

        var dateTime = request.getDateTime();
        var pratictioner = modalMapper.map(request.getPratictioner(), Pratictioner.class);
        var healthProcedure = modalMapper.map(request.getHealthProcedure(), Qualification.class);

        for (PatientReducedDTO patientDTO : request.getPatients()) {
            var patient = modalMapper.map(patientDTO, Patient.class);

            var appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setDateTime(dateTime);
            appointment.setPratictioner(pratictioner);
            appointment.setHealthProcedure(healthProcedure);
            appointment.setProcedureStatus(ProcedureStatus.DONE);
//          appontioment.setPayment = TODO decidir como vai ser o pagamento (salvar apenas ID e data

            repository.save(appointment);
        }
    }

    private void validateSchedule(ScheduleDTO request) throws ResourceNotFoundException {
        var pratictioner = pratictionerService.getById(request.getPratictioner().getId());
        var patient = new ArrayList<Patient>();
        for (PatientReducedDTO patientDTO : request.getPatients()) {
            patient.add(patientService.findById(patientDTO.getId()).get());
        }
        var healthProcedure = qualificationService.findByCode(request.getHealthProcedure().getCode());

        assert pratictioner != null;
        assert patient.size() == request.getPatients().size();
        assert healthProcedure != null;
    }
}
