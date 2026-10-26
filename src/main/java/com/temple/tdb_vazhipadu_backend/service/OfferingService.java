package com.temple.tdb_vazhipadu_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.temple.tdb_vazhipadu_backend.dto.CreateOfferingRequest;
import com.temple.tdb_vazhipadu_backend.dto.OfferingResponse;
import com.temple.tdb_vazhipadu_backend.entity.Offering;
import com.temple.tdb_vazhipadu_backend.exception.ResourceNotFoundException;
import com.temple.tdb_vazhipadu_backend.repository.OfferingRepository;

@Service
public class OfferingService {

    private final OfferingRepository repository;

    public OfferingService(OfferingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public OfferingResponse create(CreateOfferingRequest request) {
        boolean active = request.active() == null || request.active();

        Offering offering = new Offering(
            request.name().trim(),
            request.price(),
            active
        );

        return toResponse(repository.save(offering));
    }

    @Transactional(readOnly = true)
    public List<OfferingResponse> findAll() {
        return repository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public OfferingResponse findById(Long id) {
        Offering offering = repository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Offering not found: " + id
                ));

        return toResponse(offering);
    }

    private OfferingResponse toResponse(Offering offering) {
        return new OfferingResponse(
            offering.getId(),
            offering.getName(),
            offering.getPrice(),
            offering.isActive()
        );
    }
}