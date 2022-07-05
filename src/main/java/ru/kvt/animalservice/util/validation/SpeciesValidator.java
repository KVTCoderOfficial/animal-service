package ru.kvt.animalservice.util.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kvt.animalservice.repositories.SpeciesRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class SpeciesValidator implements ConstraintValidator<Species, String> {

    private final SpeciesRepository speciesRepository;

    @Override
    public boolean isValid(String speciesTitle, ConstraintValidatorContext context) {
        return ((speciesRepository.findByTitle(speciesTitle).isPresent()) || (speciesTitle == null));
    }

}
