package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
