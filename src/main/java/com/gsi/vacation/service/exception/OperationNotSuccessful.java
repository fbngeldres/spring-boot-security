package com.gsi.vacation.service.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
public class OperationNotSuccessful extends  RuntimeException {
    private String message;
    private String errorCode;
    public OperationNotSuccessful(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String message;
        private String errorCode;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public OperationNotSuccessful build() {
            return new OperationNotSuccessful(message, errorCode);
        }
    }
}
