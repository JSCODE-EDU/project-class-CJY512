package study.board.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;
    @Column(length = 15)
    private String password;

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User createUser(String email, String password) {
        return new User(email, password);
    }
}
