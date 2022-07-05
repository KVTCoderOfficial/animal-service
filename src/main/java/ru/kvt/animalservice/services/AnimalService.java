package ru.kvt.animalservice.services;

import org.springframework.data.domain.Page;
import ru.kvt.animalservice.model.animal.Animal;

import java.util.Optional;

public interface AnimalService {

    Optional<Animal> findById(Long id);

    Page<Animal> findAllByUsername(String username, int page, int pageSize);

    Animal save(Animal product);

    void deleteByIdAndUsername(Long id, String username);

}
