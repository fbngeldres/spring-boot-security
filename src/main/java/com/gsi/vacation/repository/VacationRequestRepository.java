package com.gsi.vacation.repository;

import com.gsi.vacation.entity.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, UUID> {
    List<VacationRequest> findByEmployeeId(UUID employeeId);
}
