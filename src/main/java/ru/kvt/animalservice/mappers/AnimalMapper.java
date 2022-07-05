package ru.kvt.animalservice.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kvt.animalservice.dto.AnimalDto;
import ru.kvt.animalservice.dto.AnimalToSaveDto;
import ru.kvt.animalservice.dto.AnimalToUpdateDto;
import ru.kvt.animalservice.exceptions.CurrentUsersAnimalNotFoundException;
import ru.kvt.animalservice.exceptions.SpeciesNotFoundException;
import ru.kvt.animalservice.exceptions.UserNotFoundException;
import ru.kvt.animalservice.model.User;
import ru.kvt.animalservice.model.animal.Animal;
import ru.kvt.animalservice.repositories.AnimalRepository;
import ru.kvt.animalservice.repositories.SpeciesRepository;
import ru.kvt.animalservice.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class AnimalMapper {

    private final AnimalRepository animalRepository;

    private final UserRepository userRepository;

    private final SpeciesRepository speciesRepository;

    public AnimalDto toAnimalDto(Animal animal) {
        AnimalDto animalDto = new AnimalDto();
        animalDto.setId(animal.getId());
        animalDto.setNickname(animal.getNickname());
        animalDto.setSpeciesTitle(animal.getSpecies().getTitle());
        animalDto.setGender(animal.getGender());
        animalDto.setBirthday(animal.getBirthday());
        return animalDto;
    }

    public Animal toAnimal(AnimalToSaveDto animalToSaveDto, String username) {
        Animal animal = new Animal();
        animal.setId(null);
        animal.setNickname(animalToSaveDto.getNickname());
        animal.setSpecies(speciesRepository.findByTitle(animalToSaveDto.getSpeciesTitle()).orElseThrow(() -> new SpeciesNotFoundException("Species not found")));
        animal.setGender(animalToSaveDto.getGender());
        animal.setBirthday(animalToSaveDto.getBirthday());
        animal.setUser(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found")));
        return animal;
    }

    public Animal toAnimal(AnimalToUpdateDto animalToUpdateDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        Animal animal = animalRepository.findByIdAndUser(animalToUpdateDto.getId(), user).orElseThrow(() -> new CurrentUsersAnimalNotFoundException("Current user's animal not found"));
        if (animalToUpdateDto.getNickname() != null) {
            animal.setNickname(animalToUpdateDto.getNickname());
        }
        if (animalToUpdateDto.getSpeciesTitle() != null) {
            animal.setSpecies(speciesRepository.findByTitle(animalToUpdateDto.getSpeciesTitle()).orElseThrow(() -> new SpeciesNotFoundException("Species not found")));
        }
        if (animalToUpdateDto.getGender() != null) {
            animal.setGender(animalToUpdateDto.getGender());
        }
        if (animalToUpdateDto.getBirthday() != null) {
            animal.setBirthday(animalToUpdateDto.getBirthday());
        }
        return animal;
    }

}
