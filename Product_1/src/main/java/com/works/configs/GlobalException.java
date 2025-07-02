package com.works.configs;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return parseError(ex.getFieldErrors());
    }

    private Object parseError(List<FieldError> fieldErrors) {
        List<Object> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("field", fieldError.getField());
            errorMap.put("message", fieldError.getDefaultMessage());
            errorMap.put("rejectedValue", fieldError.getRejectedValue().toString());
            errors.add(errorMap);
        }
        return errors;
    }

}
