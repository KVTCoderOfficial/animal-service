package ru.kvt.animalservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kvt.animalservice.exceptions.RoleNotFoundException;
import ru.kvt.animalservice.exceptions.UserAlreadyExistsException;
import ru.kvt.animalservice.model.Role;
import ru.kvt.animalservice.model.User;
import ru.kvt.animalservice.model.UserAttempts;
import ru.kvt.animalservice.repositories.RoleRepository;
import ru.kvt.animalservice.repositories.UserAttemptsRepository;
import ru.kvt.animalservice.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserAttemptsRepository userAttemptsRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void createUser(String username, String password) {
        findUserByUsername(username).ifPresent(user -> {
            throw new UserAlreadyExistsException("User already exists");
        });
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RoleNotFoundException("Role 'ROLE_USER' not found"));
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(List.of(role));
        userRepository.save(user);

        UserAttempts userAttempts = new UserAttempts();
        userAttempts.setUser(user);
        userAttempts.setAttempts(0);
        userAttemptsRepository.save(userAttempts);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
