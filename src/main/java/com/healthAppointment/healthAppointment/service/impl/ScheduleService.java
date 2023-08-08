package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.Patient;
import com.healthAppointment.healthAppointment.model.Schedule;
import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import com.healthAppointment.healthAppointment.model.dto.PatientReducedDTO;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.repository.ScheduleRepository;
import com.healthAppointment.healthAppointment.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.*;
import static com.healthAppointment.healthAppointment.utils.DateUtils.PATERN_DATE;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {

    private final ScheduleRepository repository;
    private final ModelMapper modelMapper;
    private final PatientService patientService;

    @Override
    public ScheduleDTO save(ScheduleDTO requestDTO) throws BusException {
        Optional<Schedule> existingSchedule = repository.findScheduleByDateTimeAndPratictionerId(
                requestDTO.getDateTime(),
                requestDTO.getPratictioner().getId());
        if (existingSchedule.isPresent()) {
            throw new BusException(SCHEDULE_ALREADY_EXISTS);
        }
        Schedule request = buildSchedule(requestDTO);

        validatePatientsPerSchedule(request);
        duplicatePatientValidation(request);

        Schedule response = repository.save(request);
        return buildScheduleDTO(response);
    }

    @Override
    public List<ScheduleDTO> findAllByDate(String date) {

        var startDate = date.isEmpty() ? LocalDate.now() : LocalDate.parse(date, DateTimeFormatter.ofPattern(PATERN_DATE));
        var endDate = startDate.plusDays(1);

        List<Schedule> response = repository.findScheduleAllByDateTime_Date(startDate, endDate);
        response.sort(Comparator.comparing(Schedule::getDateTime));
        try {
            validateUnicDate(response);
            return buildScheduleDTOList(response);
        } catch (Exception e) {
            //TODO loggar erro de listagem retornando registros de mais de uma data
            return null;
        }
    }

    @Override
    public ScheduleDTO findById(String id) {
        Optional<Schedule> response = repository.findById(id);
        return buildScheduleDTO(response.orElse(null));
    }

    @Override
    public ScheduleDTO addPatient(String id, String patientId) throws BusException, ResourceNotFoundException {
        Optional<Schedule> schedule = repository.findById(id);
        Optional<PatientDTO> patientDTO = Optional.ofNullable(patientService.findById(patientId));

        areScheduleAndPatientPresent(schedule, patientDTO);

        PatientReducedDTO patientReducedDTO = modelMapper.map(patientDTO.get(), PatientReducedDTO.class);
        Patient patient = modelMapper.map(patientReducedDTO, Patient.class);

        var request = schedule.get();
        request.getPatients().add(patient);

        validatePatientsPerSchedule(request);
        duplicatePatientValidation(request);

        Schedule response = repository.save(request);
        return buildScheduleDTO(response);

    }

    @Override
    public ScheduleDTO update(String id, ScheduleDTO requestDTO) throws ResourceNotFoundException, BusException {
        Optional<Schedule> schedule = repository.findById(id);
        if (schedule.isEmpty()) {
            //TODO loggar erro
            throw new ResourceNotFoundException(SCHEDULE_NOT_FOUND);
        }
        var request = buildSchedule(requestDTO);

        validatePatientsPerSchedule(request);
        duplicatePatientValidation(request);

        request.setId(id);
        Schedule response = repository.save(request);

        return buildScheduleDTO(response);
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException {
        Optional<Schedule> schedule = repository.findById(id);
        if (schedule.isEmpty()) {
            //TODO loggar erro
            throw new ResourceNotFoundException(SCHEDULE_NOT_FOUND);
        }
        if (schedule.get().getDateTime().isBefore(LocalDateTime.now())) {
            //TODO loggar erro
            throw new ResourceNotFoundException(SCHEDULE_NOT_FOUND);
        }

        repository.deleteById(id);
    }

    @Override
    public ScheduleDTO removePatient(String id, String patientId) throws ResourceNotFoundException, BusException {
        Optional<Schedule> schedule = repository.findById(id);
        Optional<PatientDTO> patientDTO = Optional.ofNullable(patientService.findById(patientId));
        areScheduleAndPatientPresent(schedule, patientDTO);

        var request = schedule.get();
        request.getPatients().removeIf((p) -> p.getId().equals(patientId));

        validatePatientsPerSchedule(request);
        duplicatePatientValidation(request);

        Schedule response = repository.save(request);
        return buildScheduleDTO(response);
    }

    private void validatePatientsPerSchedule(Schedule request) throws BusException {
        getMaxPatientsAppointment(request);
        if (request.getPatients() == null) {
            return;
        }
            if (request.getPatients().size() > request.getMaxPatients()) {
                //TODO loggar erro
                throw new BusException(SCHEDULE_PATIENTS_FULL);
            }
    }

    private void areScheduleAndPatientPresent(Optional<Schedule> schedule, Optional<PatientDTO> patientDTO) throws ResourceNotFoundException {
        if (schedule.isEmpty()) {
            //TODO loggar erro
            throw new ResourceNotFoundException(SCHEDULE_NOT_FOUND);
        }
        if (patientDTO.isEmpty()) {
            //TODO loggar erro
            throw new ResourceNotFoundException(PATIENT_NOT_FOUND);
        }
    }

    private void validateUnicDate(List<Schedule> response) {
        if (response.size() > 0) {
            LocalDate date = response.get(0).getDateTime().toLocalDate();
            response.forEach(schedule -> {
                assert (!schedule.getDateTime().toLocalDate().equals(date));
            });
        }
    }

    private List<ScheduleDTO> buildScheduleDTOList(List<Schedule> response) {
        return response.stream()
                .map(this::buildScheduleDTO)
                .toList();
    }

    private void getMaxPatientsAppointment(Schedule request) { //TODO melhorar futuramente- não deixar hard-coded
        request.setMaxPatients(request.getHealthProcedure().getCode().equals("50000861") ? 6 : 1);
    }

    private Schedule buildSchedule(ScheduleDTO requestDTO) {
        return modelMapper.map(requestDTO, Schedule.class);
    }

    private ScheduleDTO buildScheduleDTO(Schedule response) {
        return modelMapper.map(response, ScheduleDTO.class);
    }

    private void duplicatePatientValidation(Schedule request) throws BusException {
        if (request.getPatients() == null) {
            return;
        }
        List<String> patientsIds = request.getPatients().stream()
                .map(Patient::getId)
                .toList();
        if (patientsIds.size() != patientsIds.stream().distinct().count()) {
            throw new BusException(DUPLICATE_PATIENT);
        }
    }
}
