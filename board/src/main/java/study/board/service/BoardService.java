package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Board;
import study.board.entity.Member;
import study.board.exception.BoardNotAuthorizedException;
import study.board.exception.BoardNotFoundException;
import study.board.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board save(Board board) {
        boardRepository.save(board);
        return board;
    }

    public Page<Board> findBoards(Pageable pageable) {
        return boardRepository.findAllPageBy(pageable);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("id" + id + "에 해당하는 게시판이 없습니다."));
    }

    public List<Board> findByTitle(String title) {
        return boardRepository.findTop100ByTitleContainingOrderByCreatedDateTimeDesc(title);
    }

    @Transactional
    public Board update(Long id, Board newBoard, Member member) {
        Board findBoard = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("id" + id + "에 해당하는 게시판이 없습니다."));
        validateWriter(findBoard, member.getId());

        return findBoard.update(newBoard);
    }

    @Transactional
    public void deleteById(Long boardId, Member member) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(boardId + ", id에 해당하는 게시판이 없습니다."));
        validateWriter(findBoard, member.getId());
        boardRepository.delete(findBoard);
    }

    private void validateWriter(Board findBoard, Long updaterId) {
        if(!findBoard.isWriter(updaterId)) {
            throw new BoardNotAuthorizedException("게시물에 대한 권한이 없습니다.");
        }
    }

}
