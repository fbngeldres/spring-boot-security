package com.gsi.vacation.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class VacationRequestFilterDto {

    private UUID id;
    private UUID employeeId;
}
