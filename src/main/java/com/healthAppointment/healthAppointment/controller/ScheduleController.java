package com.healthAppointment.healthAppointment.controller;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.CREATE_ERROR;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final IScheduleService service;

    @Autowired
    ScheduleController(IScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ScheduleDTO request) {
        try {
            ScheduleDTO response = service.save(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (BusException e) {
            //TODO loggar erro
            return ResponseEntity.badRequest().body(CREATE_ERROR + e.getMessage());

        } catch (Exception e) {
            //TODO loggar erro
            return ResponseEntity.internalServerError().body(CREATE_ERROR);
        }
    }

}
