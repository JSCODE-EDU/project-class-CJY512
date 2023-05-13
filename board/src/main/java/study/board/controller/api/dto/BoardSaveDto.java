package study.board.controller.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import study.board.entity.Board;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class BoardSaveDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardSaveRequest {

        @NotBlank
        @Length(min = 1, max = 15)
        private String title;
        @Length(min = 1, max = 1000)
        private String content;

        public Board toEntity() {
            return Board.createBoard(title, content);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardSaveResponse {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdDate;

        public static BoardSaveResponse fromEntity(Board board) {
            return BoardSaveResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();
        }
    }

}
