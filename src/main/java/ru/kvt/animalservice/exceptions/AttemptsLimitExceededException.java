package ru.kvt.animalservice.exceptions;

public class AttemptsLimitExceededException extends RuntimeException {

    public AttemptsLimitExceededException(String message) {
        super(message);
    }

}
