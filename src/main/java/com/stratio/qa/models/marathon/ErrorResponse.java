package com.stratio.qa.models.marathon;

public class ErrorResponse {

    private String message;

    private ErrorDetail[] details;

    public ErrorDetail[] getDetails() {
        return details;
    }

    public String getMessage() {
        return message;
    }
}
