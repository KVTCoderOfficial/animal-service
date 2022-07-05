package ru.kvt.animalservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kvt.animalservice.configs.security.JwtTokenUtil;
import ru.kvt.animalservice.dto.jwt.JwtRequestDto;
import ru.kvt.animalservice.dto.jwt.JwtResponseDto;
import ru.kvt.animalservice.exceptions.UserAlreadyExistsException;
import ru.kvt.animalservice.services.AuthService;
import ru.kvt.animalservice.services.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/api/v1/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid JwtRequestDto authRequest) {
        if (!(authService.authenticateUser(authRequest.getUsername(), authRequest.getPassword()))) {
            throw new BadCredentialsException("Incorrect username or password");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    @PostMapping("/api/v1/reg")
    public ResponseEntity<?> register(@RequestBody @Valid JwtRequestDto regRequest) {
        userService.createUser(regRequest.getUsername(), regRequest.getPassword());
        UserDetails userDetails = userService.loadUserByUsername(regRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    @GetMapping("/api/v1/check-username")
    @ResponseStatus(HttpStatus.OK)
    public String checkName(@RequestParam String username) {
        if (userService.findUserByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        return username;
    }

}
