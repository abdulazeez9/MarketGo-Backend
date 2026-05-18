package com.marketgo.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }


    public static AppException notFound(String message) {
        return new AppException(message, HttpStatus.NOT_FOUND);
    }

    public static AppException internalServerError(String message) {
        return new AppException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static AppException badRequest(String message) {
        return new AppException(message, HttpStatus.BAD_REQUEST);
    }

    public static AppException unauthorized(String message) {
        return new AppException(message, HttpStatus.UNAUTHORIZED);
    }

    public static AppException forbidden(String message) {
        return new AppException(message, HttpStatus.FORBIDDEN);
    }

}