package org.lpzneider.veterinaria.exceptions;

public class ErrorJson {
    private int errorCode;
    private String errorMessage;

    public ErrorJson(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
