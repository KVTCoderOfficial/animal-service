package ru.kvt.animalservice.exceptions;

public class CurrentUsersAnimalNotFoundException extends RuntimeException {

    public CurrentUsersAnimalNotFoundException(String message) {
        super(message);
    }

}
