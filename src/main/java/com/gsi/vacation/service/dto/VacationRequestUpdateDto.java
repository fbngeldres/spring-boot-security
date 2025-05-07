package com.gsi.vacation.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class VacationRequestUpdateDto {

    private UUID id;

    private UUID employee;
    @NotNull
    private LocalDate startDate;
@NotNull
    private LocalDate endDate;
    private String reason;
    private String status;
}
