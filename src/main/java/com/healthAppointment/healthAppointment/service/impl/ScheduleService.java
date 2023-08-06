package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.model.Schedule;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.repository.ScheduleRepository;
import com.healthAppointment.healthAppointment.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.*;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {

    private final ScheduleRepository repository;
    private final ModelMapper modelMapper;


    @Override
    public ScheduleDTO save(ScheduleDTO requestDTO) throws BusException {
        Optional<Schedule> existingSchedule = repository.findScheduleByDateTimeAndPratictionerId(
                requestDTO.getDateTime(),
                requestDTO.getPratictioner().getId());
        if (existingSchedule.isPresent()) {
            throw new BusException(SCHEDULE_ALREADY_EXISTS);
        }
        Schedule request = buildSchedule(requestDTO);
        int maxPatientsPerSchedule = getMaxPatientsAppointment(request);
        if (request.getPatients().size() > maxPatientsPerSchedule) {
            //TODO loggar erro
            throw new BusException(SCHEDULE_PATIENTS_FULL);
        } else {
            duplicatePatientValidation(request);
            Schedule response = repository.save(request);
            return buildScheduleDTO(response);
        }
    }

    @Override
    public List<ScheduleDTO> findAllByDate(String date) {

        LocalDate startDate = date.isEmpty() ? LocalDate.now() : LocalDate.parse(date);
        LocalDate endDate = startDate.plusDays(1);

        List<Schedule> response = repository.findScheduleAllByDateTime_Date(startDate, endDate);
        response.sort(Comparator.comparing(Schedule::getDateTime));
        validateUnicDate(response);
        return buildScheduleDTOList(response);
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

    private int getMaxPatientsAppointment(Schedule request) { //TODO melhorar futuramente- não deixar hard-coded
        return request.getHealthProcedure().getCode().equals("50000861") ? 6 : 1;
    }

    private Schedule buildSchedule(ScheduleDTO requestDTO) {
        return modelMapper.map(requestDTO, Schedule.class);
    }

    private ScheduleDTO buildScheduleDTO(Schedule response) {
        return modelMapper.map(response, ScheduleDTO.class);
    }

    private void duplicatePatientValidation(Schedule request) throws BusException {
        List<String> patientsIds = request.getPatients().stream()
                .map(patient -> patient.getId())
                .toList();
        if (patientsIds.size() != patientsIds.stream().distinct().count()) {
            throw new BusException(DUPLICATE_PATIENT);
        }
    }
}
