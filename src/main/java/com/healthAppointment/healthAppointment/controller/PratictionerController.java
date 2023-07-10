package com.healthAppointment.healthAppointment.controller;

import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import com.healthAppointment.healthAppointment.service.IPratictionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pratictioner")
public class PratictionerController {

    private final IPratictionerService service;

    @Autowired
    PratictionerController(IPratictionerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PratictionerDTO request) {
        PratictionerDTO response = service.save(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
