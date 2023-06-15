package study.board.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.board.config.jwt.JwtAuth;
import study.board.controller.api.dto.BoardSearch;
import study.board.controller.api.dto.CommentDto;
import study.board.controller.api.dto.GlobalResponseCode;
import study.board.controller.api.dto.Result;
import study.board.entity.Board;
import study.board.entity.Comment;
import study.board.entity.Member;
import study.board.exhandler.BaseErrorResult;
import study.board.exhandler.ErrorResult;
import study.board.service.BoardService;
import study.board.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

import static study.board.controller.api.dto.BoardDto.*;
import static study.board.controller.api.dto.BoardDto.BoardSaveRequest;
import static study.board.controller.api.dto.BoardDto.BoardResponse;
import static study.board.controller.api.dto.CommentDto.*;

//@SuppressWarnings("unchecked")
@Tag(name = "Board", description = "게시판 API Doc")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@ResponseStatus(HttpStatus.OK) //동적 설정 필요시 수정
public class BoardApiController {

    private final BoardService boardService;
    private final CommentService commentService;

    @Operation(
            summary = "게시글 저장 기능",
            description = "사용자가 작성한 title,content 값으로 게시글 저장",
            responses = {
            @ApiResponse(responseCode = "200", description = "게시글 저장 성공"),
            @ApiResponse(responseCode = "400", description = "`제목`은 1글자 이상 15글자이하여야 한다\n`내용`은 1글자 이상 1000글자 이하여야 한다. ",
                    content = @Content(schema = @Schema(implementation = ErrorResult.class))) })
    @PostMapping
    public Result<BoardResponse> saveBoard(@JwtAuth Member member,
                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "`제목`과 `내용`", required = true)
                                        @Validated @RequestBody BoardSaveRequest boardSaveRequest) {
        BoardResponse boardResponse =
                BoardResponse.fromEntity(boardService.save(boardSaveRequest.toEntity(member)));
        return Result.<BoardResponse>builder()
                .code(GlobalResponseCode.SUCCESS_SAVE.getCode())
                .message(GlobalResponseCode.SUCCESS_SAVE.getMessage())
                .data(boardResponse)
                .build();
    }

    @Operation(
            summary = "게시글 전체 조회 기능",
            description = "사용자가 게시글 전체 조회",
            responses = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공. 단, 최근 생성 시간 기준 100개의 게시글."),
            })
    @GetMapping
    public Result<List<BoardResponse>> findBoards() {
        return Result.<List<BoardResponse>>builder()
                .code(GlobalResponseCode.SUCCESS_BOARD_LIST.getCode())
                .message(GlobalResponseCode.SUCCESS_BOARD_LIST.getMessage())
                .data(boardService.findBoards().stream()
                        .map(BoardResponse::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }


    @Operation(
            summary = "특정 게시글 조회 기능",
            description = "사용자가 id 값으로 특정 게시글 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "특정 게시글 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 접근",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class))) })
    @GetMapping("/{id}")
    public Result<BoardDetailsResponse> findOne(@Parameter(name = "board_id", description = "찾고 싶은 게시글 id", required = true, in = ParameterIn.PATH)
                                      @PathVariable("id") Long id) {

        Board findBoard = boardService.findById(id);

        List<CommentResponse> commentResponses = commentService.findCommentsByBoard(findBoard)
                .stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());

        BoardDetailsResponse boardDetailsResponse = BoardDetailsResponse.fromEntity(findBoard, commentResponses);

        return Result.<BoardDetailsResponse>builder()
                .code(GlobalResponseCode.SUCCESS_BOARD.getCode())
                .message(GlobalResponseCode.SUCCESS_BOARD.getMessage())
                .data(boardDetailsResponse)
                .build();
    }

    @Operation(
            summary = "특정 게시글 수정 기능",
            description = "사용자가 id 값에 해당하는 기존 게시글을 title,content 값으로 수정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "특정 게시글 수정 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 접근",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class))),
                    @ApiResponse(responseCode = "400", description = "`제목`은 1글자 이상 15글자이하여야 한다\n`내용`은 1글자 이상 1000글자 이하여야 한다.",
                            content = @Content(schema = @Schema(implementation = ErrorResult.class)))
            })
    @PostMapping("/{id}")
    public Result<BoardUpdateResponse> updateBoard(@JwtAuth Member member,
                                             @Parameter(name = "board_id", description = "수정할 게시글 id", required = true, in = ParameterIn.PATH)
                                                 @PathVariable("id") Long boardId,
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "수정한 `제목`과 `내용`", required = true)
                                             @Validated @RequestBody BoardUpdateRequest boardUpdateRequest) {
        BoardUpdateResponse boardUpdateResponse = BoardUpdateResponse.fromEntity(boardService.update(boardId, boardUpdateRequest.toEntity(), member));
        return Result.<BoardUpdateResponse>builder()
                .code(GlobalResponseCode.SUCCESS_UPDATE.getCode())
                .message(GlobalResponseCode.SUCCESS_UPDATE.getMessage())
                .data(boardUpdateResponse)
                .build();
    }

    @Operation(
            summary = "특정 게시글 삭제 기능",
            description = "사용자가 id 값으로 게시글 삭제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "특정 게시글 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글 접근",
                            content = @Content(schema = @Schema(implementation = BaseErrorResult.class))) })
    @DeleteMapping("/{id}")
    public String deleteOne(
            @JwtAuth Member member,
            @Parameter(name = "board_id", description = "삭제할 게시글 id", required = true, in = ParameterIn.PATH)
            @PathVariable("id") Long boardId) {
        boardService.deleteById(boardId, member);
        return "ok";
    }

    @Operation(
            summary = "게시글 검색 기능",
            description = "사용자가 query 값을 포함한 게시글 검색",
            responses = {
                    @ApiResponse(responseCode = "200", description = "게시글 검색 성공. 단, 최근 생성 시간 기준 100개의 게시글."),
                    @ApiResponse(responseCode = "400", description = "검색 키워드는 공백을 제외한 1글자 이상이어야 한다",
                            content = @Content(schema = @Schema(implementation = ErrorResult.class))) })
    @GetMapping("/search")
    public Result<List<BoardResponse>> searchBoards(
            @Parameter(name = "검색 키워드", description = "공백을 제외한 1글자 이상", required = true, in = ParameterIn.QUERY)
            @Validated @ModelAttribute BoardSearch boardSearch) {

        return Result.<List<BoardResponse>>builder()
                .code(GlobalResponseCode.SUCCESS_BOARD_LIST.getCode())
                .message(GlobalResponseCode.SUCCESS_BOARD_LIST.getMessage())
                .data(boardService.findByTitle(boardSearch.getQuery()).stream()
                        .map(BoardResponse::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
/*
    @PostConstruct
    public void init() {
        for (int i = 1; i < 300; i++) {
            boardService.save(Board.createBoard("title" + i, "content" + i));
        }
    }
    */

    //-------------------------------------댓글--------------------------------------------

    @PostMapping("/{id}/comments")
    public Result<CommentResponse> saveCommentOnBoard(
            @JwtAuth Member member,
            @PathVariable("id") Long boardId,
            @Validated @RequestBody CommentSaveRequest commentSaveRequest) {
        Board board = boardService.findById(boardId);
        Comment comment = commentService.saveComment(commentSaveRequest.toEntity(member, board));
        return Result.<CommentResponse>builder()
                .code(GlobalResponseCode.SUCCESS_SAVE.getCode())
                .message(GlobalResponseCode.SUCCESS_MEMBER.getMessage())
                .data(CommentResponse.fromEntity(comment))
                .build();
    }
}
