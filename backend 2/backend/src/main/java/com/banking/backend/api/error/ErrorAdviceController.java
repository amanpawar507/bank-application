package com.banking.backend.api.error;

import com.banking.backend.exception.NotFoundException;
import com.banking.backend.exception.UnprocessableContentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.healthcare.backend.api")
@Slf4j
public class ErrorAdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorBaseResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodException) {

        log.info("MethodArgumentNotValidException >> ", methodException);

        final var errors = methodException.getBindingResult().getAllErrors()
                .stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return new ErrorBaseResponse("Bean validation exception", errors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorBaseResponse handleNotFoundException(
            NotFoundException notFoundException) {

        log.info("NotFoundException >> ", notFoundException);

        return new ErrorBaseResponse(notFoundException.getMessage(), Collections.emptyMap());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableContentException.class)
    public ErrorBaseResponse handleUnprocessableContentException(
            UnprocessableContentException unprocessableContentException) {

        log.info("UnprocessableContentException >> ", unprocessableContentException);

        return new ErrorBaseResponse(unprocessableContentException.getMessage(),
                Collections.emptyMap());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorBaseResponse handleRuntimeException(
            RuntimeException runtimeException) {

        log.info("RuntimeException >> ", runtimeException);

        return new ErrorBaseResponse(runtimeException.getMessage(), Collections.emptyMap());
    }
}
