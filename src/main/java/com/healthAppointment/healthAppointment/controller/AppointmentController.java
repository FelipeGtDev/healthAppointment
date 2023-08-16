package com.healthAppointment.healthAppointment.controller;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.service.IAppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                    .body("Erro ao salvar Profissional: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
