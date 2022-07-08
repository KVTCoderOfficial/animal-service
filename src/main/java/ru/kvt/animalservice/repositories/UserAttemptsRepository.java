package ru.kvt.animalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import ru.kvt.animalservice.model.User;
import ru.kvt.animalservice.model.UserAttempts;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface UserAttemptsRepository extends JpaRepository<UserAttempts, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<UserAttempts> findByUser(User user);

}
