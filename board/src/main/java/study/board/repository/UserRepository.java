package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
