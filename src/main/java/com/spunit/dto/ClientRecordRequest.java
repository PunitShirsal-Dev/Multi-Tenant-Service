package com.spunit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClientRecordRequest {

    @NotBlank
    @Size(max = 120)
    private String clientReference;

    @NotBlank
    @Size(max = 180)
    private String name;

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
}

