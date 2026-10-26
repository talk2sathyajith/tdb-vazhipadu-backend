package com.temple.tdb_vazhipadu_backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.temple.tdb_vazhipadu_backend.dto.CreateOfferingRequest;
import com.temple.tdb_vazhipadu_backend.dto.OfferingResponse;
import com.temple.tdb_vazhipadu_backend.service.OfferingService;

@RestController
@RequestMapping("/api/offerings")
public class OfferingController {

    private final OfferingService service;

    public OfferingController(OfferingService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfferingResponse create(
            @Valid @RequestBody CreateOfferingRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<OfferingResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OfferingResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }
}