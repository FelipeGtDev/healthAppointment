package com.healthAppointment.healthAppointment.controller;

import com.healthAppointment.healthAppointment.exceptions.BusException;
import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.dto.ScheduleDTO;
import com.healthAppointment.healthAppointment.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.CREATE_ERROR;
import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.UPDATE_ERROR;

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

    @GetMapping
    public ResponseEntity<?> findAllByDate(@RequestParam(value = "date", defaultValue = "") String date) {
        List<ScheduleDTO> response = service.findAllByDate(date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        ScheduleDTO response = service.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Add paciente a Agendamento
    @PutMapping("/{id}/patient/{patientId}")
    public ResponseEntity<?> addPatient(@PathVariable String id, @PathVariable String patientId) {
        try {
            ScheduleDTO response = service.addPatient(id, patientId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BusException e) {
            //TODO loggar erro
            return ResponseEntity.badRequest().body(UPDATE_ERROR + e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(UPDATE_ERROR + e.getMessage());
        } catch (Exception e) {
            //TODO loggar erro
            return ResponseEntity.internalServerError().body(UPDATE_ERROR);
        }
    }

// Editar Agendamento

// remover Agendamento


// Listar por healthProcedure - apenas agendamentos que não se tornaram consultas (Paginado)

}
