package ru.kvt.animalservice.dto.jwt;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class JwtRequestDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
