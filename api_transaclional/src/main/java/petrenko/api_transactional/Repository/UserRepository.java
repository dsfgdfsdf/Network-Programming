package petrenko.api_transactional.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petrenko.api_transactional.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}