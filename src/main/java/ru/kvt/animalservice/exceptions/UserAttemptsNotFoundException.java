package ru.kvt.animalservice.exceptions;

public class UserAttemptsNotFoundException extends RuntimeException {

    public UserAttemptsNotFoundException(String message) {
        super(message);
    }
}
