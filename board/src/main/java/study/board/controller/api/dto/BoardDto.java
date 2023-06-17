package study.board.controller.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import study.board.entity.Board;
import study.board.entity.Comment;
import study.board.entity.Member;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

import static study.board.controller.api.dto.CommentDto.*;

public class BoardDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 저장 요청 DTO")
    public static class BoardSaveRequest {

        @NotBlank
        @Length(min = 1, max = 15)
        @Schema(description = "저장할 게시글 제목", example = "my title")
        private String title;

        @Length(min = 1, max = 1000)
        @Schema(description = "저장할 게시글 내용", example = "my content")
        private String content;

        public Board toEntity(Member member) {
            return Board.createNewBoard(title, content, member);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 저장 응답 DTO")
    public static class BoardResponse {
        @Schema(description = "게시글 id", example = "1")
        private Long id;
        @Schema(description = "게시글 제목", example = "title")
        private String title;
        @Schema(description = "게시글 내용", example = "content")
        private String content;
        @Schema(description = "게시글 생성 시간")
        private LocalDateTime createdDateTime;

        public static BoardResponse fromEntity(Board board) {
            return BoardResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDateTime(board.getCreatedDateTime())
                    .build();
        }
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 저장 응답 DTO")
    public static class BoardDetailsResponse {
        @Schema(description = "게시글 id", example = "1")
        private Long id;
        @Schema(description = "게시글 제목", example = "title")
        private String title;
        @Schema(description = "게시글 내용", example = "content")
        private String content;
        @Schema(description = "게시글 생성 시간")
        private LocalDateTime createdDateTime;
        @Schema(description = "게시글 댓글 정보")
        private List<CommentResponse> comments;

        public static BoardDetailsResponse fromEntity(Board board, List<CommentResponse> comments) {
            return BoardDetailsResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDateTime(board.getCreatedDateTime())
                    .comments(comments)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 수정 요청 DTO")
    public static class BoardUpdateRequest {

        @NotBlank
        @Length(min = 1, max = 15)
        @Schema(description = "수정한 게시글 제목", example = "my new title")
        private String title;

        @Length(min = 1, max = 1000)
        @Schema(description = "수정한 게시글 내용", example = "my new content")
        private String content;

        public Board toEntity() {
            return Board.createBoardForUpdate(title, content);
        }

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 수정 응답 DTO")
    public static class BoardUpdateResponse {
        @Schema(description = "수정한 게시글 id", example = "1")
        private Long id;
        @Schema(description = "수정한 게시글 제목", example = "title")
        private String title;
        @Schema(description = "수정한 게시글 내용", example = "content")
        private String content;
        @Schema(description = "수정한 게시글 수정 시간")
        private LocalDateTime lastModifiedDateTime;

        public static BoardUpdateResponse fromEntity(Board board) {
            return BoardUpdateResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .lastModifiedDateTime(board.getLastModifiedDateTime())
                    .build();
        }
    }

}
