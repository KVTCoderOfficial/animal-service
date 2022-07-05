package ru.kvt.animalservice.util.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kvt.animalservice.repositories.AnimalRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class NicknameValidator implements ConstraintValidator<Nickname, String> {

    private final AnimalRepository animalRepository;

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {
        return ((animalRepository.findByNickname(nickname).isEmpty()) || (nickname == null));
    }

}
