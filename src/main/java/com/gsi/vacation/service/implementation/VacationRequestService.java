package com.gsi.vacation.service.implementation;

import com.gsi.vacation.entity.Employee;
import com.gsi.vacation.entity.VacationRequest;
import com.gsi.vacation.repository.VacationRequestRepository;
import com.gsi.vacation.service.IVacationRequestService;
import com.gsi.vacation.service.dto.*;
import com.gsi.vacation.service.exception.OperationNotSuccessful;
import com.gsi.vacation.service.operationresult.OperationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacationRequestService implements IVacationRequestService {
    private final VacationRequestRepository vacationRequestRepository;

    @Override
    public Optional<List<VacationRequestCreateDto>> select(VacationRequestFilterDto vacationRequestDto) {
        log.debug("Select vacation request");
        return Optional.ofNullable(vacationRequestRepository.findAll().stream()
                .map(e -> VacationRequestCreateDto.builder().build()).collect(Collectors.toList()));
    }

    @Override
    public Boolean delete(UUID id) {
        log.debug("Delete vacation request");
        vacationRequestRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public VacationRequestDto create(VacationRequestCreateDto vacationRequestCreateDto) {
        log.debug("create vacation request " + vacationRequestCreateDto.toString());
        vacationRequestRepository.save(VacationRequest.builder()
                .status(vacationRequestCreateDto.getStatus())
                .employee(Employee.builder().id(vacationRequestCreateDto.getEmployee()).build())
                .endDate(vacationRequestCreateDto.getEndDate())
                .startDate(vacationRequestCreateDto.getStartDate())
                .reason(vacationRequestCreateDto.getReason())
                .build());
        return VacationRequestDto.builder()
                .status(vacationRequestCreateDto.getStatus())
                .employee(EmployeeDto.builder().id(vacationRequestCreateDto.getEmployee()).build())
                .endDate(vacationRequestCreateDto.getEndDate())
                .startDate(vacationRequestCreateDto.getStartDate())
                .reason(vacationRequestCreateDto.getReason())
                .build();
    }

    @Override
    public VacationRequestDto update(VacationRequestUpdateDto vacationRequestCreateDto) {
        log.debug("update vacation request " + vacationRequestCreateDto.toString());
        Optional<VacationRequest> vacationRequest = vacationRequestRepository.findById(vacationRequestCreateDto.getId());
        if (vacationRequest.isPresent()) {
            VacationRequest vacationRequest1 = vacationRequest.get();
            vacationRequest1.setStatus(vacationRequestCreateDto.getStatus());
            vacationRequest1.setEndDate(vacationRequestCreateDto.getEndDate());
            vacationRequest1.setStartDate(vacationRequestCreateDto.getStartDate());
            vacationRequest1.setReason(vacationRequestCreateDto.getReason());
            vacationRequestRepository.save(vacationRequest1);
            return VacationRequestDto.builder()
                    .status(vacationRequestCreateDto.getStatus())
                    .employee(EmployeeDto.builder().id(vacationRequestCreateDto.getEmployee()).build())
                    .endDate(vacationRequestCreateDto.getEndDate())
                    .startDate(vacationRequestCreateDto.getStartDate())
                    .reason(vacationRequestCreateDto.getReason())
                    .build();
        }

        throw OperationNotSuccessful.builder()
                .message("Operation failed.")
                .errorCode("ERR_001")
                .build();

    }
}
