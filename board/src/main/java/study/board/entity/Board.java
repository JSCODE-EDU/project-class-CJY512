package study.board.entity;

import lombok.*;

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
    @Column(length = 1005, nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    private Board(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public static Board createNewBoard(String title, String content, Member member) {
        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }

    public static Board createBoardForUpdate(String title, String content) {
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }

    public Board update(Board newBoard) {
        title = newBoard.getTitle();
        content = newBoard.getContent();
        return this;
    }

    public boolean isWriter(Long updaterId) {
        return this.member.getId()
                .equals(updaterId);
    }
}
