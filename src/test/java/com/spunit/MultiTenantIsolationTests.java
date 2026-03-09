package com.spunit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MultiTenantIsolationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnOnlyTenantSpecificRecords() throws Exception {
        mockMvc.perform(get("/api/v1/records").header("X-Tenant-ID", "tenant-a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tenantId").value("tenant-a"));

        mockMvc.perform(get("/api/v1/records").header("X-Tenant-ID", "tenant-b"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tenantId").value("tenant-b"));
    }

    @Test
    void shouldNotAllowCrossTenantRead() throws Exception {
        String payload = "{\"clientReference\":\"A-NEW-1\",\"name\":\"Tenant A Private\"}";

        mockMvc.perform(post("/api/v1/records")
                        .header("X-Tenant-ID", "tenant-a")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(get("/api/v1/records/3").header("X-Tenant-ID", "tenant-b"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFailWhenTenantHeaderMissing() throws Exception {
        mockMvc.perform(get("/api/v1/records"))
                .andExpect(status().isBadRequest());
    }
}

