package com.gsi.vacation.service.dto.responses;

import com.gsi.vacation.service.operationresult.OperationResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto<U> {
    private OperationResult operationResult;
    private String message;
    private U result;
}
