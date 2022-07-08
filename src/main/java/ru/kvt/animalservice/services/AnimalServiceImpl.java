package ru.kvt.animalservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kvt.animalservice.exceptions.CurrentUsersAnimalNotFoundException;
import ru.kvt.animalservice.exceptions.UserNotFoundException;
import ru.kvt.animalservice.model.User;
import ru.kvt.animalservice.model.animal.Animal;
import ru.kvt.animalservice.repositories.AnimalRepository;
import ru.kvt.animalservice.repositories.UserRepository;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    private final UserRepository userRepository;

    @Override
    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }

    @Override
    public Page<Animal> findAllByUsername(String username, int page, int pageSize) {
        return animalRepository.findAllByUser(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("User '%s' not found", username))), PageRequest.of(page - 1, pageSize, Sort.by("nickname")));
    }

    @Override
    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    @Override
    public void deleteByIdAndUsername(Long id, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("User '%s' not found", username)));
        animalRepository.findByIdAndUser(id, user).orElseThrow(() -> new CurrentUsersAnimalNotFoundException("Current user's animal not found"));
        animalRepository.deleteByIdAndUser(id, user);
    }

}
