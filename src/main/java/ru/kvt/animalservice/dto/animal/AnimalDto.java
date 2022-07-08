package ru.kvt.animalservice.dto.animal;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kvt.animalservice.model.animal.Gender;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AnimalDto {

    private Long id;

    private String nickname;

    private String speciesTitle;

    private Gender gender;

    private LocalDate birthday;

}
