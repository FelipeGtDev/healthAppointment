package com.healthAppointment.healthAppointment.controller;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.dto.*;
import com.healthAppointment.healthAppointment.service.IAppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final IAppointmentService service;

    AppointmentController(IAppointmentService service) {
        this.service = service;
    }

    @PostMapping("/schedule")
    public ResponseEntity<?> createAppointmentFromSchedule(@RequestBody ScheduleDTO request) {
        try {
            service.createAppointmentsFromSchedule(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao salvar consulta: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/find_all")
    public Page<AppointmentDTO> findAll(
            @RequestParam(required = false) String pratictionerId,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String healthProcedureCode,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(size = 25, page = 0, direction = Sort.Direction.ASC, sort = {"dateTime"}) Pageable page){

        AppointmentDTO appointmentDTO = new AppointmentDTO();

        if (pratictionerId != null) {
            appointmentDTO.setPratictioner(new PratictionerReducedDTO(pratictionerId));
        }
        if (patientId != null) {
            appointmentDTO.setPatient(new PatientReducedDTO(patientId));
        }
        if (healthProcedureCode != null) {
            appointmentDTO.setHealthProcedure(new QualificationReducedDTO(healthProcedureCode));
        }

        return service.findAll(appointmentDTO, startDate, endDate, page);

    }
}
