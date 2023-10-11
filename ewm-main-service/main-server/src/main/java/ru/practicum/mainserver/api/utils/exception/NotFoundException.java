package ru.practicum.mainserver.api.utils.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Class object, Long id) {
        super(String.format("%s with id=%s was not found", object.getSimpleName(), id));
    }
}
