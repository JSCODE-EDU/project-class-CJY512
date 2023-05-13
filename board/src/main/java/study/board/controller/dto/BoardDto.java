package study.board.controller.dto;

import lombok.Data;
import study.board.entity.Board;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    private BoardDto(Long id, String title, String content, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static BoardDto from(Board board) {
        return new BoardDto(board.getId(), board.getTitle(), board.getContent(), board.getCreatedDate());
    }
}
