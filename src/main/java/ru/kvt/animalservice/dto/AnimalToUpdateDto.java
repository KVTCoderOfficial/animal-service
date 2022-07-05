package ru.kvt.animalservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kvt.animalservice.model.animal.Gender;
import ru.kvt.animalservice.util.validation.Nickname;
import ru.kvt.animalservice.util.validation.Species;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AnimalToUpdateDto {

    @NotNull
    private Long id;

    @Nickname
    private String nickname;

    @Species
    private String speciesTitle;

    private Gender gender;

    private LocalDate birthday;

}
