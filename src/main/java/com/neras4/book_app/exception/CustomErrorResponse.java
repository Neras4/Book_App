package com.neras4.book_app.exception;


import java.time.LocalDateTime;

public class CustomErrorResponse {
    private int errorCode;
    private String errorMessage;
    private String errorDetails;
    private final LocalDateTime timestamp;

    public CustomErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public CustomErrorResponse(int errorCode, String errorMessage, String errorDetails) {
        this();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }



    @Override
    public String toString() {
        return "CustomErrorResponse{" +
                "\n    errorCode=" + errorCode +
                ",\n    errorMessage='" + errorMessage + '\'' +
                ",\n    errorDetails='" + errorDetails + '\'' +
                ",\n    timestamp=" + timestamp +
                "\n}";
    }
}
