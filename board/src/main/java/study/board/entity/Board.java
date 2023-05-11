package study.board.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    private Board(String title, String content){
        this.title = title;
        this.content = content;
    }

    public static Board createBoard(String title, String content) {
        return new Board(title, content);
    }

    public Board update(Board newBoard) {
        title = newBoard.getTitle();
        content = newBoard.getContent();
        return this;
    }
}
