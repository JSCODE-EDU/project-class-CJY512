package study.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import study.board.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findTop100AllByOrderByCreatedDateTimeDesc();

    List<Board> findTop100ByTitleContainingOrderByCreatedDateTimeDesc(String title);

    Page<Board> findAllPageBy(Pageable pageable);
}
