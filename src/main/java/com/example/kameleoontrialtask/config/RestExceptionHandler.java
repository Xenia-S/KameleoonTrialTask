package com.example.kameleoontrialtask.config;

import com.example.kameleoontrialtask.dto.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        RestErrorResponse errorResponse = new RestErrorResponse(buildErrorMessage(fieldErrors));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestErrorResponse> handleException(Exception ex) {
        RestErrorResponse errorResponse = new RestErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private String buildErrorMessage(List<FieldError> fieldErrors) {
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        for (FieldError error : fieldErrors) {
            errorMessage.append(error.getDefaultMessage()).append(". ");
        }
        return errorMessage.toString().trim();
    }
}
