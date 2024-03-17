package com.example.sec.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(),  error.getDefaultMessage());
        }

        ApiException apiException =
                new ApiException("INVALID ARGUMENT PASSED", HttpStatus.BAD_REQUEST,
                        errors, LocalDateTime.now());

        return handleExceptionInternal(ex, apiException, headers, apiException.httpStatus(),
                request);
    }

    /*
    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<Object> handleExpiredJwtException(
            ExpiredJwtException e,
            WebRequest webRequest
    ) {
        ApiException apiException = new ApiException(e.getLocalizedMessage(),
                HttpStatus.FORBIDDEN, List.of(webRequest.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.httpStatus());
    }
    *
     */

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> notFoundException(
            NotFoundException exception,
            WebRequest webRequest
    ) {
        ApiException apiException = new ApiException(
                exception.getLocalizedMessage(),
                HttpStatus.NOT_FOUND,
                Map.of("url", webRequest.getDescription(false)),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.httpStatus());
    }
}
