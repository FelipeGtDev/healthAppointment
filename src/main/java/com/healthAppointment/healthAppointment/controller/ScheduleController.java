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

import static com.healthAppointment.healthAppointment.model.AppConstants.Messages.*;

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

    @PutMapping("/{id}/add_patient/{patientId}")
    public ResponseEntity<?> addPatient(@PathVariable String id, @PathVariable String patientId) {
        try {
            ScheduleDTO response = service.addPatient(id, patientId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BusException | ResourceNotFoundException | Exception e) {
            //TODO loggar erro
            return ResponseEntity.badRequest().body(UPDATE_ERROR + e.getMessage());
        }
    }

    @PutMapping("/{id}/remove_patient/{patientId}")
    public ResponseEntity<?> removePatient(@PathVariable String id, @PathVariable String patientId) {
        try {
            ScheduleDTO response = service.removePatient(id, patientId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BusException | ResourceNotFoundException | Exception e) {
            //TODO loggar erro
            return ResponseEntity.badRequest().body(UPDATE_ERROR + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ScheduleDTO request) {
        try {
            ScheduleDTO response = service.update(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException | BusException | Exception e) {
            //TODO loggar erro
            return ResponseEntity.badRequest().body(UPDATE_ERROR + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException | Exception e) {
            //TODO loggar erro
            return ResponseEntity.badRequest().body(DELETE_ERROR + e.getMessage());
        }
    }

// Listar por healthProcedure - apenas agendamentos que não se tornaram consultas (Paginado)
//
// TODO criar metodo abaixo quando necessário. Deve realizar Specification
//    @GetMapping
//    public Page<ScheduleDTO> listAll(
//            @RequestParam(required = false) String pratictionerId,
//            @RequestParam(required = false) String patientId,
//            @RequestParam(required = false) String healthProcedureCode,
//            @RequestParam(required = false) String startDate,
//            @RequestParam(required = false) String endDate,
//            @PageableDefault(size = 25, page = 0, direction = Sort.Direction.DESC, sort = {"updatedAt"}) Pageable page) {
//        {
//            var request = new ScheduleDTO();
////            if (!pratictionerId.isEmpty()) request.getPratictioner().setId(pratictionerId);
//            if (!patientId.isEmpty()) {
//                request.getPatients().add(new PatientReducedDTO(patientId));
//            }
//            if (!healthProcedureCode.isEmpty()) request.getHealthProcedure().setCode(healthProcedureCode);
//
//
//            Page<ScheduleDTO> response = service.listAll(request, startDate, endDate, page);
//            return response;
//        }

}
