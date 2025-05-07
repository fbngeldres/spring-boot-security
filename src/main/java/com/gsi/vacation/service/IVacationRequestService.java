package com.gsi.vacation.service;

import com.gsi.vacation.service.dto.VacationRequestCreateDto;
import com.gsi.vacation.service.dto.VacationRequestDto;
import com.gsi.vacation.service.dto.VacationRequestFilterDto;
import com.gsi.vacation.service.dto.VacationRequestUpdateDto;
import com.gsi.vacation.service.operationresult.OperationResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IVacationRequestService {

    public Optional<List<VacationRequestCreateDto>> select(VacationRequestFilterDto vacationRequestDto);

    public Boolean delete(UUID id);

    public VacationRequestDto create(VacationRequestCreateDto vacationRequestCreateDto);

    public VacationRequestDto update(VacationRequestUpdateDto vacationRequestCreateDto);

}
