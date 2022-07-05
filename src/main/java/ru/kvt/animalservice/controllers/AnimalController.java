package ru.kvt.animalservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kvt.animalservice.dto.AnimalDto;
import ru.kvt.animalservice.dto.AnimalToSaveDto;
import ru.kvt.animalservice.dto.AnimalToUpdateDto;
import ru.kvt.animalservice.exceptions.AnimalNotFoundException;
import ru.kvt.animalservice.mappers.AnimalMapper;
import ru.kvt.animalservice.model.animal.Animal;
import ru.kvt.animalservice.services.AnimalService;

import javax.validation.Valid;
import java.security.Principal;

@PreAuthorize("hasRole('ROLE_USER')")
@RestController
@RequestMapping("/api/v1/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    private final AnimalMapper animalMapper;

    @GetMapping("/current-user-animals")
    public Page<AnimalDto> findCurrentUserAnimals(Principal principal, @RequestParam(defaultValue = "1") Integer page) {
        if (page < 1) {
            page = 1;
        }
        return animalService.findAllByUsername(principal.getName(), page, 4).map(animalMapper::toAnimalDto);
    }

    @GetMapping("/{id}")
    public AnimalDto findAnimalById(@PathVariable Long id) {
        Animal animal = animalService.findById(id).orElseThrow(() -> new AnimalNotFoundException("Animal with id: " + id + " doesn't exist"));
        return animalMapper.toAnimalDto(animal);
    }

    @PostMapping("/current-user-animals")
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalDto saveNewCurrentUserAnimal(Principal principal, @RequestBody @Valid AnimalToSaveDto animalToSaveDto) {
        Animal animal = animalService.save(animalMapper.toAnimal(animalToSaveDto, principal.getName()));
        return animalMapper.toAnimalDto(animal);
    }

    @PutMapping("/current-user-animals")
    public AnimalDto updateCurrentUserAnimal(Principal principal, @RequestBody @Valid AnimalToUpdateDto animalToUpdateDto) {
        Animal animal = animalService.save(animalMapper.toAnimal(animalToUpdateDto, principal.getName()));
        return animalMapper.toAnimalDto(animal);
    }

    @DeleteMapping("/current-user-animals/{id}")
    public void deleteCurrentUserAnimal(Principal principal, @PathVariable Long id) {
        animalService.deleteByIdAndUsername(id, principal.getName());
    }

}
