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

import java.time.LocalDateTime;

public class CommentDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentSaveRequest {
        @Length(min = 1, max = 1000)
        private String content;

        public Comment toEntity(Member member, Board board) {
            return Comment.createNewComment(content, member, board);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "댓글 저장 응답 DTO")
    public static class CommentResponse {
        @Schema(description = "댓글 id", example = "1")
        private Long id;
        @Schema(description = "댓글 내용", example = "content")
        private String content;
        @Schema(description = "댓글 생성 시간")
        private LocalDateTime createdDateTime;
        @Schema(description = "댓글 작성자의 이메일")
        private String writerEmail;

        public static CommentResponse fromEntity(Comment comment) {
            return CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .createdDateTime(comment.getCreatedDateTime())
                    .writerEmail(comment.getMember().getEmail())
                    .build();
        }
    }
}
