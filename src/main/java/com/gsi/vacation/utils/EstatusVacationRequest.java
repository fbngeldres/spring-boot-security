package com.gsi.vacation.utils;

public enum EstatusVacationRequest {

    PENDING ("PENDING"),
    APPROVED ("APPROVED"),
    ELIMINATED ("ELIMINATED"),
    NOT_APPROVED ("NOT_APPROVED");

    private final String value;

    EstatusVacationRequest(String value) {
        this.value =value;
    }
    public String getValue() {
        return value;
    }
}
