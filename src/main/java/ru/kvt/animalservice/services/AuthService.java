package ru.kvt.animalservice.services;

public interface AuthService {

    Boolean authenticateUser(String username, String password);

}
