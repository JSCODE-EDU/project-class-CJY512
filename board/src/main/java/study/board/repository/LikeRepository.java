package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.entity.Board;
import study.board.entity.Like;
import study.board.entity.Member;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsLikeByBoardAndMember(Board board, Member member);

    void deleteByBoardAndMember(Board board, Member member);

    Optional<Like> findTop1ByBoardAndMember(Board board, Member member);
}
