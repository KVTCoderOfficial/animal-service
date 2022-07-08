package ru.kvt.animalservice.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kvt.animalservice.model.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findUserByUsername(String username);

    void createUser(String username, String password);

    Boolean checkName(String username);

}
