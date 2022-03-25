package com.servico.advicer;

import com.servico.exception.TaskNotFoundException;
import com.servico.exception.model.ApiError;
import com.servico.exception.model.FormError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerRestExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {TaskNotFoundException.class})
    protected ResponseEntity<Object> handleTaskNotFound(RuntimeException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected List<FormError> handleFormError(MethodArgumentNotValidException exception) {
        List<FormError> errorList = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            errorList.add(new FormError(e.getField(), message));
        });

        return errorList;
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
