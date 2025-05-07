package com.gsi.vacation.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentDto {

    private String id;
    private String name;
    private CompanyDto companyDto;
}
