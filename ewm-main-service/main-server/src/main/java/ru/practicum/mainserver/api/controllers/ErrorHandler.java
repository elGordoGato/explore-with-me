package ru.practicum.mainserver.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainserver.api.utils.exception.BadRequestException;
import ru.practicum.mainserver.api.utils.exception.ErrorApi;
import ru.practicum.mainserver.api.utils.exception.ForbiddenException;
import ru.practicum.mainserver.api.utils.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(value
            = {ConstraintViolationException.class, BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorApi handleConstraintViolationException(final RuntimeException e) {
        return ErrorApi.builder()
                .error(e.toString())
                .reason("For the requested operation the conditions are not met.")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorApi handleNotFoundException(final NotFoundException e) {
        return ErrorApi.builder()
                .error(e.toString())
                .reason("For the requested operation the entity was not found.")
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorApi handleForbiddenException(final ForbiddenException e) {
        return ErrorApi.builder()
                .error(e.toString())
                .reason("For the requested operation the conditions are not met.")
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorApi handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        return ErrorApi.builder()
                .error(e.toString())
                .reason("For the requested operation the conditions are not met.")
                .message(e.getCause().getCause().getLocalizedMessage())
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
    }
}