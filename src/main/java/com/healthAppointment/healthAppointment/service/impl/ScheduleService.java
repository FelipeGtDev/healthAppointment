package com.healthAppointment.healthAppointment.service.impl;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.model.Schedule;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.repository.ScheduleRepository;
import com.healthAppointment.healthAppointment.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.SCHEDULE_ALREADY_EXISTS;
import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.SCHEDULE_PATIENTS_FULL;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {

    private final ScheduleRepository repository;
    private final ModelMapper modelMapper;



    @Override
    public ScheduleDTO save(ScheduleDTO requestDTO) throws BusException { // TODO remover comentários quando estiver funcionando
        // realiza busca por agendamento (dateTime + profissional)
        Optional<Schedule> existingSchedule = repository.findScheduleByDateTimeAndPratictionerId(requestDTO.getDateTime(), requestDTO.getPratictioner().getId());
        // if (agendamento existe para essa data e hora? == true) {
        if (existingSchedule.isPresent()){
            throw new BusException(SCHEDULE_ALREADY_EXISTS);
        }
        // converter ScheduleDTO para Schedule
        Schedule request = buildSchedule(requestDTO);

        //if (quantidade de pacientes > quantidade maxima de pacientes no agendamento){
        int maxPatientsPerAppointment = getMaxPatientsAppointment(request);
        if (request.getPatients().size() > maxPatientsPerAppointment) {
            throw new BusException(SCHEDULE_PATIENTS_FULL);
        } else {
            //      salva agendamento
            Schedule response = repository.save(request);
            //      retorna agendamento salvo
            return buildScheduleDTO(response);
        }
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
}
