package ru.kvt.animalservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kvt.animalservice.exceptions.AttemptsLimitExceededException;
import ru.kvt.animalservice.exceptions.UserAttemptsNotFoundException;
import ru.kvt.animalservice.model.User;
import ru.kvt.animalservice.model.UserAttempts;
import ru.kvt.animalservice.repositories.UserAttemptsRepository;
import ru.kvt.animalservice.repositories.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${animal-service.auth.attempts-limit}")
    private Integer authAttemptsLimit;

    @Value("${animal-service.auth.attempts-limit-time-frame}")
    private Integer authAttemptsLimitTimeFrame;

    private final AuthenticationManager authenticationManager;

    private final UserAttemptsRepository userAttemptsRepository;

    private final UserRepository userRepository;

    @Transactional
    @Override
    public Boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("Incorrect username or password"));
        UserAttempts userAttempts = userAttemptsRepository.findByUser(user).orElseThrow(() -> new UserAttemptsNotFoundException("User attempts for current user not found"));
        while ((!userAttempts.getAllAttemptsTimestamps().equals("")) && (Duration.between(LocalDateTime.parse(userAttempts.getAllAttemptsTimestamps().split("\\s+")[0]), LocalDateTime.now()).toMillis() >= authAttemptsLimitTimeFrame)) {
            userAttempts.setAttempts(userAttempts.getAttempts() - 1);
            userAttempts.setAllAttemptsTimestamps(userAttempts.getAllAttemptsTimestamps().substring(userAttempts.getAllAttemptsTimestamps().split("\\s+")[0].length() + 1));
        }
        if (userAttempts.getAttempts() >= authAttemptsLimit) {
            throw new AttemptsLimitExceededException("Attempt limit exceeded");
        } else {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                userAttempts.setAttempts(0);
                userAttempts.setAllAttemptsTimestamps("");
                userAttemptsRepository.save(userAttempts);
                return true;
            } catch (BadCredentialsException badCredentialsException) {
                userAttempts.setAttempts(userAttempts.getAttempts() + 1);
                userAttempts.setAllAttemptsTimestamps(userAttempts.getAllAttemptsTimestamps() + LocalDateTime.now().toString() + " ");
                userAttemptsRepository.save(userAttempts);
                return false;
            }
        }
    }

}
