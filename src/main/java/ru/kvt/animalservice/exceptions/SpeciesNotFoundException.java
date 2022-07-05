package ru.kvt.animalservice.exceptions;

public class SpeciesNotFoundException extends RuntimeException {

    public SpeciesNotFoundException(String message) {
        super(message);
    }

}
