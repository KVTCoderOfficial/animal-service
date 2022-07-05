package ru.kvt.animalservice.services;

import lombok.RequiredArgsConstructor;
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

import javax.persistence.OptimisticLockException;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserAttemptsRepository userAttemptsRepository;

    private final UserRepository userRepository;

    @Transactional
    public Boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("Incorrect username or password"));
        UserAttempts userAttempts = userAttemptsRepository.findByUser(user).orElseThrow(() -> new UserAttemptsNotFoundException("User attempts for current user not found"));
        if ((Duration.between(userAttempts.getUpdatedAt(), LocalDateTime.now()).toMillis() >= 3600000)) {
            userAttempts.setAttempts(0);
            userAttemptsRepository.save(userAttempts);
        }

        if (userAttempts.getAttempts() >= 10) {
            throw new AttemptsLimitExceededException("Attempt limit exceeded");
        } else {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                return true;
            } catch (BadCredentialsException badCredentialsException) {
                try {
                    userAttempts.setAttempts(userAttempts.getAttempts() + 1);
                } catch (OptimisticLockException optimisticLockException) {
                    authenticateUser(username, password);
                }
                userAttemptsRepository.save(userAttempts);
                return false;
            }
        }
    }

}
