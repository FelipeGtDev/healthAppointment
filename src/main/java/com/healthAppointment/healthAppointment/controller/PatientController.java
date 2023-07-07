package com.healthAppointment.healthAppointment.controller;

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
        PatientDTO response = service.save(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<?>> findAll(@PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {
        Page<PatientDTO> response = service.findAll(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
