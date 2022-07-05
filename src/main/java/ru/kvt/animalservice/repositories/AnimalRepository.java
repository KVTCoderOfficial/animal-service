package ru.kvt.animalservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kvt.animalservice.model.User;
import ru.kvt.animalservice.model.animal.Animal;

import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Page<Animal> findAllByUser(User user, Pageable pageable);

    void deleteByIdAndUser(Long id, User user);

    Optional<Animal> findByNickname(String nickname);

    Optional<Animal> findByIdAndUser(Long id, User user);

}
