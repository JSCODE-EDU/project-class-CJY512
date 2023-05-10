package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.board.controller.dto.BoardDto;
import study.board.controller.dto.Result;
import study.board.entity.Board;
import study.board.repository.BoardRepository;
import study.board.service.BoardService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@ResponseStatus(HttpStatus.OK) //동적 설정 필요시 수정
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping
    public BoardDto saveBoard(@RequestBody BoardDto boardDto) {
        return BoardDto.from(boardService.save(Board.createBoard(boardDto.getTitle(), boardDto.getContent())));
    }

    @GetMapping
    public Result<BoardDto> findBoards() {
        return new Result(boardService.findBoards().stream()
                .map(BoardDto::from)
                .collect(Collectors.toList()));
    }

    @GetMapping("/search")
    public Result<BoardDto> searchBoards(@RequestParam String query) {
        return new Result(boardService.findByTitle(query).stream()
                .map(BoardDto::from)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public BoardDto findOne(@PathVariable("id") Long id) {
        return BoardDto.from(boardService.findById(id));
    }

    @PostMapping("/{id}")
    public BoardDto updateBoard(@PathVariable("id") Long id, @RequestBody BoardDto boardDto) {
        Board updatedBoard = boardService.update(id, Board.createBoard(boardDto.getTitle(), boardDto.getContent()));
        return BoardDto.from(updatedBoard);
    }

    @DeleteMapping("/{id}")
    public String deleteOne(@PathVariable("id") Long id) {
        boardService.deleteById(id);
        return "ok";
    }

    @PostConstruct
    public void init() {
        for (int i = 1; i < 300; i++) {
            boardService.save(Board.createBoard("title" + i, "content" + i));
        }
    }
}
