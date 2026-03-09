package com.spunit.controller;

import com.spunit.dto.ClientRecordRequest;
import com.spunit.dto.ClientRecordResponse;
import com.spunit.service.ClientRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/records")
public class ClientRecordController {

    private final ClientRecordService clientRecordService;

    public ClientRecordController(ClientRecordService clientRecordService) {
        this.clientRecordService = clientRecordService;
    }

    @GetMapping
    public List<ClientRecordResponse> list() {
        return clientRecordService.listCurrentTenantRecords()
                .stream()
                .map(ClientRecordResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ClientRecordResponse get(@PathVariable Long id) {
        return ClientRecordResponse.fromEntity(clientRecordService.getCurrentTenantRecord(id));
    }

    @PostMapping
    public ResponseEntity<ClientRecordResponse> create(@Valid @RequestBody ClientRecordRequest request) {
        ClientRecordResponse response = ClientRecordResponse.fromEntity(
                clientRecordService.createCurrentTenantRecord(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ClientRecordResponse update(@PathVariable Long id, @Valid @RequestBody ClientRecordRequest request) {
        return ClientRecordResponse.fromEntity(clientRecordService.updateCurrentTenantRecord(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientRecordService.deleteCurrentTenantRecord(id);
        return ResponseEntity.noContent().build();
    }
}

