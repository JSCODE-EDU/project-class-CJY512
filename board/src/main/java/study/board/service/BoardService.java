package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Board;
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

    public List<Board> findBoards() {
        return boardRepository.findTop100AllByOrderByCreatedDateDesc();
    }

    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no such data"));
    }

    public List<Board> findByTitle(String title) {
        return boardRepository.findTop100ByTitleContainingOrderByCreatedDateDesc(title);
    }

    @Transactional
    public Board update(Long id, Board newBoard) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("id" + id + "에 해당하는 게시판이 없습니다."))
                .update(newBoard);
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.delete(boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException(id + ", id에 해당하는 게시판이 없습니다.")));
    }

}
