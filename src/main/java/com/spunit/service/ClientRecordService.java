package com.spunit.service;

import com.spunit.domain.ClientRecord;
import com.spunit.dto.ClientRecordRequest;
import com.spunit.exception.NotFoundException;
import com.spunit.repository.ClientRecordRepository;
import com.spunit.tenant.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientRecordService {

    private final ClientRecordRepository repository;

    public ClientRecordService(ClientRecordRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ClientRecord> listCurrentTenantRecords() {
        return repository.findAllByTenantIdOrderByIdAsc(requiredTenant());
    }

    @Transactional(readOnly = true)
    public ClientRecord getCurrentTenantRecord(Long id) {
        return repository.findByIdAndTenantId(id, requiredTenant())
                .orElseThrow(() -> new NotFoundException("Record not found for current tenant"));
    }

    @Transactional
    public ClientRecord createCurrentTenantRecord(ClientRecordRequest request) {
        ClientRecord record = new ClientRecord();
        record.setTenantId(requiredTenant());
        record.setClientReference(request.getClientReference());
        record.setName(request.getName());
        return repository.save(record);
    }

    @Transactional
    public ClientRecord updateCurrentTenantRecord(Long id, ClientRecordRequest request) {
        ClientRecord existing = getCurrentTenantRecord(id);
        existing.setClientReference(request.getClientReference());
        existing.setName(request.getName());
        return repository.save(existing);
    }

    @Transactional
    public void deleteCurrentTenantRecord(Long id) {
        ClientRecord existing = getCurrentTenantRecord(id);
        repository.delete(existing);
    }

    private String requiredTenant() {
        String tenantId = TenantContext.getTenantId();
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalStateException("Tenant context is missing");
        }
        return tenantId;
    }
}

