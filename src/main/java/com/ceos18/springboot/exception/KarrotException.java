package com.ceos18.springboot.exception;

import lombok.Getter;

@Getter
public class KarrotException extends RuntimeException {

    private int status;
    private String message;
    private String solution;

    public KarrotException(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getHttpStatus().value();
        this.solution = errorCode.getSolution();
    }

    public KarrotException(ErrorCode errorCode, String message) {
        this.message = message;
        this.status = errorCode.getHttpStatus().value();
        this.solution = errorCode.getSolution();
    }

    public KarrotException(ErrorCode errorCode, String message, String solution) {
        this.message = message;
        this.status = errorCode.getHttpStatus().value();
        this.solution = solution;
    }

}
