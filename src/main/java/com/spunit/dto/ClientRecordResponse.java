package com.spunit.dto;

import com.spunit.domain.ClientRecord;

import java.time.Instant;

public class ClientRecordResponse {

    private Long id;
    private String tenantId;
    private String clientReference;
    private String name;
    private Instant createdAt;

    public static ClientRecordResponse fromEntity(ClientRecord entity) {
        ClientRecordResponse response = new ClientRecordResponse();
        response.setId(entity.getId());
        response.setTenantId(entity.getTenantId());
        response.setClientReference(entity.getClientReference());
        response.setName(entity.getName());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

