package com.neras4.book_app;

import java.time.LocalDateTime;

public class CustomErrorResponse {
    private int errorCode;
    private String errorMessage;
    private String errorDetails;
    private LocalDateTime timestamp;
    
    public CustomErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public CustomErrorResponse(int errorCode, String errorMessage, String errorDetails) {
        this();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CustomErrorResponse{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorDetails='" + errorDetails + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
