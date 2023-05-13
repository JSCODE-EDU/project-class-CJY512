package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findTop100AllByOrderByCreatedDateDesc();

    List<Board> findTop100ByTitleContainingOrderByCreatedDateDesc(String title);

}
