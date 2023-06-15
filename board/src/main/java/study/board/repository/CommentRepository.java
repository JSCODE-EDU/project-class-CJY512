package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.board.entity.Board;
import study.board.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.member where c.board = :board")
    List<Comment> findCommentsByBoard(@Param("board") Board board);

}
