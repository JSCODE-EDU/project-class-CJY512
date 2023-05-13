package study.board.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.board.controller.api.dto.BoardSearch;
import study.board.controller.api.dto.GlobalResponseCode;
import study.board.controller.api.dto.Result;
import study.board.entity.Board;
import study.board.service.BoardService;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

import static study.board.controller.api.dto.BoardSaveDto.BoardSaveRequest;
import static study.board.controller.api.dto.BoardSaveDto.BoardSaveResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@ResponseStatus(HttpStatus.OK) //동적 설정 필요시 수정
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping
    public Result<Object> saveBoard(@Validated @RequestBody BoardSaveRequest boardSaveRequest) {
        BoardSaveResponse boardSaveResponse = BoardSaveResponse.fromEntity(boardService.save(boardSaveRequest.toEntity()));
        return Result.builder()
                .code(GlobalResponseCode.SUCCESS_SAVE.getCode())
                .message(GlobalResponseCode.SUCCESS_SAVE.getMessage())
                .data(boardSaveResponse)
                .build();
    }

    @GetMapping
    public Result<Object> findBoards() {
        return Result.builder()
                .code(GlobalResponseCode.SUCCESS_BOARD_LIST.getCode())
                .message(GlobalResponseCode.SUCCESS_BOARD_LIST.getMessage())
                .data(boardService.findBoards().stream()
                        .map(BoardSaveResponse::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    @GetMapping("/search")
    public Result<Object> searchBoards(@Validated @ModelAttribute BoardSearch boardSearch) {

        return Result.builder()
                .code(GlobalResponseCode.SUCCESS_BOARD_LIST.getCode())
                .message(GlobalResponseCode.SUCCESS_BOARD_LIST.getMessage())
                .data(boardService.findByTitle(boardSearch.getQuery()).stream()
                        .map(BoardSaveResponse::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }


    @GetMapping("/{id}")
    public Result<Object> findOne(@PathVariable("id") Long id) {
        BoardSaveResponse boardSaveResponse = BoardSaveResponse.fromEntity(boardService.findById(id));
        Result<Object> result = Result.builder()
                .code(GlobalResponseCode.SUCCESS_BOARD.getCode())
                .message(GlobalResponseCode.SUCCESS_BOARD.getMessage())
                .data(boardSaveResponse)
                .build();
        return result;
    }

    @PostMapping("/{id}")
    public Result<Object> updateBoard(@PathVariable("id") Long id, @Validated @RequestBody BoardSaveRequest boardSaveRequest) {
        BoardSaveResponse boardSaveResponse = BoardSaveResponse.fromEntity(boardService.update(id, boardSaveRequest.toEntity()));
        return Result.builder()
                .code(GlobalResponseCode.SUCCESS_UPDATE.getCode())
                .message(GlobalResponseCode.SUCCESS_UPDATE.getMessage())
                .data(boardSaveResponse)
                .build();
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
