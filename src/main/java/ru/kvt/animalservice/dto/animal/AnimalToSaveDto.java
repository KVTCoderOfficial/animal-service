package ru.kvt.animalservice.dto.animal;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kvt.animalservice.model.animal.Gender;
import ru.kvt.animalservice.util.validation.Nickname;
import ru.kvt.animalservice.util.validation.Species;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AnimalToSaveDto {

    @NotNull
    @Nickname
    private String nickname;

    @NotNull
    @Species
    private String speciesTitle;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate birthday;

}
