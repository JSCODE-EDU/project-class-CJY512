package study.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.board.entity.Board;
import study.board.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c join fetch c.member where c.board = :board",
            countQuery = "select count(c) from Comment c where c.board = :board")
    Page<Comment> findCommentsPageByBoard(@Param("board") Board board, Pageable pageable);


}
