package com.gsi.vacation.controller;

import com.gsi.vacation.service.dto.VacationRequestCreateDto;
import com.gsi.vacation.service.dto.VacationRequestDto;
import com.gsi.vacation.service.dto.VacationRequestFilterDto;
import com.gsi.vacation.service.dto.VacationRequestUpdateDto;
import com.gsi.vacation.service.dto.responses.ResponseDto;
import com.gsi.vacation.service.implementation.VacationRequestService;
import com.gsi.vacation.service.operationresult.OperationResult;
import com.gsi.vacation.utils.Rol;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/companies/{companyId}/employees/{employeeId}/vacation-requests")
@RequiredArgsConstructor
@Slf4j
public class VacationRequestController {

    private final VacationRequestService vacationRequestService;

    @GetMapping
    @RolesAllowed({Rol.USER_EMPLOYEE,Rol.USER_BOSS})
    public ResponseEntity<List<VacationRequestCreateDto>> findAllByEmployee(@PathVariable("companyId") UUID companyId
            , @PathVariable("employeeId") UUID employeeId) {
        Optional<List<VacationRequestCreateDto>> vacationsRequest = vacationRequestService.select(VacationRequestFilterDto.builder().employeeId(employeeId)
                .build());
        if(vacationsRequest.isPresent()){
            return ResponseEntity.ok(vacationsRequest.get());
        }

        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping
    @RolesAllowed({Rol.USER_EMPLOYEE})
    public ResponseEntity<ResponseDto> createVacationRequest(
            @PathVariable("companyId") UUID companyId
            , @PathVariable("employeeId") UUID employeeId
            , @RequestBody @Valid VacationRequestCreateDto vacationRequestCreateDto) {

        vacationRequestCreateDto.setEmployee(employeeId);
        return ResponseEntity.ok(ResponseDto.<VacationRequestDto>builder()
                .operationResult(OperationResult.SUCCESS)
                .result(vacationRequestService.create(vacationRequestCreateDto))
                .build());
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Rol.USER_EMPLOYEE})
    public ResponseEntity<ResponseDto> deleteVacationRequest(
            @PathVariable("companyId") UUID companyId
            , @PathVariable("employeeId") UUID employeeId
            , @PathVariable("id") UUID id) {
        return  ResponseEntity.ok( ResponseDto.<Boolean>builder()
                .operationResult(OperationResult.SUCCESS)
                .result(vacationRequestService.delete(id))
                .build());
    }

    @PutMapping("/{id}")
    @RolesAllowed({Rol.USER_EMPLOYEE})
    public ResponseEntity<ResponseDto> updateVacationRequest(
            @PathVariable("companyId") UUID companyId
            , @PathVariable("employeeId") UUID employeeId
            , @PathVariable("id") UUID id
            , @RequestBody @Valid VacationRequestUpdateDto requestUpdateDto) {
        requestUpdateDto.setEmployee(employeeId);
        requestUpdateDto.setId(id);

         return ResponseEntity.ok(ResponseDto.<VacationRequestDto>builder()
                         .operationResult(OperationResult.SUCCESS)
                         .result(vacationRequestService.update(requestUpdateDto))
                 .build());
    }
}
