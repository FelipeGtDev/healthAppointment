package com.healthAppointment.healthAppointment.controller;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.dto.PatientDTO;
import com.healthAppointment.healthAppointment.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final IPatientService service;

    @Autowired
    PatientController(IPatientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PatientDTO request) {
        try {
            PatientDTO response = service.save(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao salvar Paciente" + e.getMessage()
                            .replace("java.lang.Exception: ", ""));

        }
    }

    @GetMapping("/active")
    public ResponseEntity<Page<?>> findAllActive(@PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {
        Page<PatientDTO> response = service.findAllActive(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<?>> findAllInactive(@PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {
        Page<PatientDTO> response = service.findAllInactive(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<?>> findAll(@PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {
        Page<PatientDTO> response = service.findAll(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) throws ResourceNotFoundException {
        PatientDTO response = service.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list_by_name")
    public ResponseEntity<Page<?>> findByName(
            @RequestParam("name") String name,
            @PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {
        Page<PatientDTO> response = service.findByName(name, page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody PatientDTO request) {
        try {
            PatientDTO response = service.update(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException | Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
