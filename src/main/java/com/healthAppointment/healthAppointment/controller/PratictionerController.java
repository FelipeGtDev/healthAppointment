package com.healthAppointment.healthAppointment.controller;

import com.healthAppointment.healthAppointment.exceptions.ResourceNotFoundException;
import com.healthAppointment.healthAppointment.model.dto.PratictionerDTO;
import com.healthAppointment.healthAppointment.service.IPratictionerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pratictioner")
public class PratictionerController {

    private final IPratictionerService service;

    PratictionerController(IPratictionerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PratictionerDTO request) {
        try {
            PratictionerDTO response = service.save(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao salvar Profissional: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        }
    }

    @GetMapping("/active")
    public ResponseEntity<Page<?>> findAllActive(@PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {
        Page<PratictionerDTO> response = service.findAllActive(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<?>> findAllInactive(@PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {
        Page<PratictionerDTO> response = service.findAllInactive(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<?>> findAll(@PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {
        Page<PratictionerDTO> response = service.findAll(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        try {
            PratictionerDTO response = service.getById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception | ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao buscar Profissional: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        }
    }

    @GetMapping("/list_by_name")
    public ResponseEntity<Page<?>> findByname(
            @RequestParam("name") String name,
            @PageableDefault(size = 15, page = 0, direction = Sort.Direction.DESC, sort = {"createdAt"}) Pageable page) {
        Page<PratictionerDTO> response = service.findByName(name, page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody PratictionerDTO request) {
        try {
            PratictionerDTO response = service.update(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar Profissional: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao deletar Profissional: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        }
    }

}
