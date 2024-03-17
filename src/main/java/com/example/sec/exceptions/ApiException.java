package com.example.sec.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ApiException(String message, HttpStatus httpStatus, Map<String, String> errors, LocalDateTime localDateTime) {
}
