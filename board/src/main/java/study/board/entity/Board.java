package study.board.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String title;
    @Column(length = 1005)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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
