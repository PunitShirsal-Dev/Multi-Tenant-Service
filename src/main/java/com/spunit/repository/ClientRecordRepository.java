package com.spunit.repository;

import com.spunit.domain.ClientRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRecordRepository extends JpaRepository<ClientRecord, Long> {

    List<ClientRecord> findAllByTenantIdOrderByIdAsc(String tenantId);

    Optional<ClientRecord> findByIdAndTenantId(Long id, String tenantId);
}

