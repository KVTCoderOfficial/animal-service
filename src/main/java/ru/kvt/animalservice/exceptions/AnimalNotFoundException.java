package ru.kvt.animalservice.exceptions;

public class AnimalNotFoundException extends RuntimeException {

    public AnimalNotFoundException(String message) {
        super(message);
    }

}
