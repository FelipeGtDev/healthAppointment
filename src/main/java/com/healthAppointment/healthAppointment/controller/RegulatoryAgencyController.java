package com.healthAppointment.healthAppointment.controller;

import com.healthAppointment.healthAppointment.model.dto.RegulatoryAgencyDTO;
import com.healthAppointment.healthAppointment.model.enums.StateAcronym;
import com.healthAppointment.healthAppointment.service.IRegulatoryAgencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/regulatory_agency")
public class RegulatoryAgencyController {

    private final IRegulatoryAgencyService service;

    public RegulatoryAgencyController(IRegulatoryAgencyService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody RegulatoryAgencyDTO request) {
        try {
            RegulatoryAgencyDTO response = service.save(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao salvar Agência Reguladora: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        }
    }

    @GetMapping
    public ResponseEntity<Page<?>> findAll(@PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {
        Page<RegulatoryAgencyDTO> response = service.findAll(page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listByName")
    public ResponseEntity<Page<?>> findByName(
            @RequestParam("name") String name,
            @PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {

        Page<RegulatoryAgencyDTO> response = service.findByName(name, page);
        return ResponseEntity.ok(response);
    }

        @GetMapping("/list_by_state_and_qualification")
    public ResponseEntity<Page<?>> findByQualificationAndState(
            @RequestParam("qualificationCode") String qualificationCode,
            @RequestParam("state") StateAcronym state,
            @PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {

        Page<RegulatoryAgencyDTO> response = service.findByQualificationAndState(qualificationCode,state, page);
        return ResponseEntity.ok(response);
    }
}
