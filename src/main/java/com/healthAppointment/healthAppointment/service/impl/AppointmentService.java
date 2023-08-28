package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.*;
import com.healthAppointment.healthAppointment.model.dto.AppointmentDTO;
import com.healthAppointment.healthAppointment.model.dto.PatientReducedDTO;
import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.repository.AppointmentRepository;
import com.healthAppointment.healthAppointment.service.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.APPOINTMENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {

    private final ModelMapper modalMapper;
    private final PatientService patientService;
    private final PratictionerService pratictionerService;
    private final AppointmentRepository repository;
    private final QualificationService qualificationService;
    private final ModelMapper modelMapper;

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

    @Override
    public AppointmentDTO save(AppointmentDTO request) throws ResourceNotFoundException {
        validateAppontiomentsComponents(request);
        Appointment appointment = buildAppointment(request);
        Optional<Appointment> responseOp = Optional.of(repository.save(appointment));

        return buildAppointmentDTO(responseOp.get());
    }

    @Override
    public AppointmentDTO getById(String id) {
        var responseOp = repository.findById(id);
        return responseOp.map(this::buildAppointmentDTO).orElse(null);
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException {
        Optional<Appointment> appointment = repository.findById(id);
        if (appointment.isEmpty()) {
            throw new ResourceNotFoundException(APPOINTMENT_NOT_FOUND);
        }
        repository.deleteById(id);
    }

    @Override
    public AppointmentDTO update(String id, AppointmentDTO request) throws ResourceNotFoundException {
        Optional<Appointment> appointmentOp = repository.findById(id);
        if (appointmentOp.isEmpty()) {
            throw new ResourceNotFoundException(APPOINTMENT_NOT_FOUND);
        }
        validateAppontiomentsComponents(request);

        Appointment appointment = buildAppointment(request);
        appointment.setId(id);
        Optional<Appointment> responseOp = Optional.of(repository.save(appointment));

        return buildAppointmentDTO(responseOp.get());
    }

    @Override
    public Page<AppointmentDTO> findAll(AppointmentDTO appointmentDTO, String startDate, String endDate, Pageable page) {
        return null;

//        return repository.findAll(
//                // todo usar QUeryDSL
//        );
    }


    private AppointmentDTO buildAppointmentDTO(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentDTO.class);
    }

    private Appointment buildAppointment(AppointmentDTO request) {
        return modelMapper.map(request, Appointment.class);
    }

    private void validateSchedule(ScheduleDTO request) throws ResourceNotFoundException {
        var pratictioner = pratictionerService.getById(request.getPratictioner().getId());
        var patient = new ArrayList<Patient>();
        for (PatientReducedDTO patientDTO : request.getPatients()) {
            patient.add(patientService.findById(patientDTO.getId()).get());
        }
        var healthProcedure = qualificationService.findByCode(request.getHealthProcedure().getCode());

        assert patient.size() == request.getPatients().size();
        validateGenericItemsAssertions(pratictioner, healthProcedure);
    }

    private void validateAppontiomentsComponents(AppointmentDTO request) throws ResourceNotFoundException {
        var pratictioner = pratictionerService.getById(request.getPratictioner().getId());
        var patient = patientService.findById(request.getPatient().getId()).get();
        var healthProcedure = qualificationService.findByCode(request.getHealthProcedure().getCode());

        assert patient != null;
        validateGenericItemsAssertions(pratictioner, healthProcedure);
    }

    private void validateGenericItemsAssertions(PratictionerDTO pratictioner, Qualification healthProcedure) {
        assert pratictioner != null;
        assert healthProcedure != null;
    }
}
